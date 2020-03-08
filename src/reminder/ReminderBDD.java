package reminder;

import java.sql.*;

public class ReminderBDD {

    private static ReminderBDD thisBDD = null;
    private static Statement stmt = null;
    private static Connection c = null;

    private String bddPath = "./src/reminder/reminder.db";

    /**
     * Patron Singleton
     * @return instancia unica de ReminderBDD
     */
    public static ReminderBDD getInstance() {
        if(thisBDD == null)
            thisBDD = new ReminderBDD();
        return thisBDD;
    }

    /**
     * Constructor de clase.
     */
    private ReminderBDD() {
        createTables();
        restoreTareasFromBDD();
    }

    /**
     * Creacion de tablas correspondientes a la base de
     * datos, solamente si no están ya creadas
     */
    private void createTables() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            c.setAutoCommit(true);
            stmt = c.createStatement();

            if(!tableExists("TAREAS")) {
                String sql = 	"CREATE TABLE TAREAS " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " COMENTARIO         TEXT    NOT NULL, " +
                        " FECHA			 INT	 NOT NULL)";
                stmt.executeUpdate(sql);
                stmt.close();
            }
            c.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Chequea si la tabla existe en la base de datos
     * @param tableName nombre de tabla
     * @return true si la tabla existe, de lo contrario false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean tableExists(String tableName) {
        boolean bool = false;
        try {
            DatabaseMetaData md = c.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);

            if(rs.next())
                bool = true;

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public void insertarTarea(int id, String comentario, String fecha) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();
            String sql = "INSERT INTO TAREAS (ID,COMENTARIO,FECHA) " +
                    "VALUES (" + id + ", '" + comentario + "', '" +
                    fecha + "');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTarea(int id) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();
            String sql = "DELETE from TAREAS where ID=" + id + ";";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cambiarTarea(int id, String comentarioNuevo, String fechaNueva) {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();
            String sql = "UPDATE TAREAS SET COMENTARIO='" + comentarioNuevo +
                    "' WHERE ID=" + id;
            stmt.executeUpdate(sql);

            sql = "UPDATE TAREAS SET FECHA='" + fechaNueva +
                    "' WHERE ID=" + id;
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lectura de la base de datos y creacion de objetos java correspondiente
     * a los clientes. Metodo llamado al comienzo de la ejecucion, y cada vez
     * que se agrega un cliente.
     */
    public void restoreTareasFromBDD() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM TAREAS;");

            while (rs.next()) {
                Tarea tarea = new Tarea();
                tarea.setComentario(rs.getString("COMENTARIO"));
                tarea.setFechaLimite(rs.getString("FECHA"));

                int id = rs.getInt("ID");
                tarea.setId(id);

                Controller.addTareaToList(tarea);
                Tarea.setIdGlobal(id + 1);
            }
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restablecer la base de datos
     * Borrado de todos los datos
     */
    public void restablecerBDD() {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:" + bddPath);
            stmt = c.createStatement();

            String sql = "DROP TABLE TAREAS;";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
