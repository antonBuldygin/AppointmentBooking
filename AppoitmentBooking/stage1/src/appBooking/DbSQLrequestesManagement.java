package appBooking;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DbSQLrequestesManagement {
    // Блок объявления констант
    public static final String DB_URL = "jdbc:h2:D:\\AppointmentBooking\\AppoitmentBooking\\stage1\\src\\appBooking\\db.mv.db";
    public static final String DB_Driver = "org.h2.Driver";
    public static final String INSERT_DOCTOR = "INSERT INTO DOCTOR (doctorName) VALUES(?)";
    public static final String INSERT_PATIENT = "INSERT INTO PATIENT (patientName) VALUES(?)";
    public static final String INSERT_APPOINMENT = "INSERT INTO appointment (idD, idP, dat, time) VALUES(?,?,?,?)";
    public static final String DELETE_APPOINMENT = "DELETE FROM appointment WHERE idApp = ?";

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private Connection connection;

    public DbSQLrequestesManagement() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, "sa","abc123");//соединениесБД
            this.connection = connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public long insertNewPatient(String patientName) throws SQLException {
        long key = execute(INSERT_PATIENT, connection, patientName);
        connection.close();
        return key;
    }

    public long insertNewAppointment(long docId, long patientId, Date dat, Time time) throws SQLException {
//        System.out.println(dat);
        long key = execute(INSERT_APPOINMENT, connection, docId, patientId, dat, time);
        connection.close();
        return key;
    }

    public long insertNewDoctor(String docName) throws SQLException {
        long key = execute(INSERT_DOCTOR, connection, docName);
        connection.close();
        return key;
    }

    public long deleteById(String id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_APPOINMENT);
        statement.setString(1, id);
        return statement.executeUpdate();
    }

    public long checkForConstaints(String name, String table, String id, String whereName) throws SQLException {

        String queryCheck = "SELECT "+id+" FROM "+table+" WHERE "+whereName+" = ?";
        PreparedStatement statement = connection.prepareStatement(queryCheck);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            long ids = resultSet.getLong(id);
//            System.out.println("You are already registered in the system. Registration number is : " + ids);
//            System.out.println();
            return ids;
        } else return 0L;
    }

    public void showListOfDoctors() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT*FROM doctor");


        while (resultSet.next()) {
            System.out.print("Doctor: " + resultSet.getString("doctorName") + "; id number for appointment # ");
            System.out.println(resultSet.getLong("idDoc") + ";");
        }

    }

    public void showListOfPatients() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT*FROM patient");


        while (resultSet.next()) {
            System.out.print("Patient name: " + resultSet.getString("patientName"));
            System.out.print(", Registred id number in the system: " + resultSet.getLong("patient.idP"));
            System.out.println();
        }
        System.out.println();
    }

    public void showAllAppointments() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT idApp, time, dat, patientName, doctorName FROM doctor INNER JOIN appointment " +
                "ON doctor.idDoc = appointment.idD INNER JOIN patient ON patient.idP = appointment.idP");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:ss", Locale.ENGLISH);
        while (resultSet.next()) {
            System.out.print( resultSet.getLong("idApp")+ " - date: ");
            System.out.print( formatter.format(resultSet.getDate("dat"))+" ");
            System.out.print( timeFormatter.format(resultSet.getTime("time")));
            System.out.print(", doctor: " + resultSet.getString("doctorName"));
            System.out.println(", patient: " + resultSet.getString("patientName"));


        }
//        System.out.println();
    }

    public void countAppointmentsByDoc() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT doctorName, COUNT(idApp) AS Quantity FROM doctor LEFT JOIN appointment " +
                "ON doctor.idDoc=appointment.idD GROUP BY doctorName ORDER BY doctorName");

        while (resultSet.next()) {

            System.out.print(" Quantity of appointments: " + resultSet.getLong("Quantity"));
            System.out.println(" for doctor: " + resultSet.getString("doctorName"));


        }
        System.out.println();

    }

    public void countAppointmentsByPatient() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT patientName, COUNT(idApp) AS Quantity FROM patient LEFT JOIN appointment " +
                "ON patient.idP=appointment.idP GROUP BY patientName ORDER BY patientName");

        while (resultSet.next()) {


            System.out.print(" Patient:  " + resultSet.getString("patientName"));
            System.out.print(", have registered  " + resultSet.getLong("Quantity") + " times to doctors");
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) throws SQLException {

        try (Connection connection = DriverManager.getConnection(DB_URL,"sa","abc123");//соединениесБД
        ) {
            DbSQLrequestesManagement dbSQLrequestesManagement = new DbSQLrequestesManagement();

//            System.out.println("Connection to DB completed");
//            System.out.println();
            //Проверяем наличие JDBC драйвера для работы с БД
            Class.forName(DB_Driver);
            DatabaseCreation creation = new DatabaseCreation(connection);
            creation.createTablesofDB();

            AppointmentManager appointmentManager = new AppointmentManager();
            try {
                appointmentManager.appoint();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE appointment");
            statement.execute("DROP TABLE doctor");
            statement.execute("DROP TABLE patient");

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(); // обработка ошибки  Class.forName
            System.out.println("JDBC drive was not found");
        } catch (SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println(" SQL Error !");
        }
    }

    private static long execute(final String sql, Connection connection, Object... values) {
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            long one = statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                one = generatedKeys.getLong(1);
//                System.out.println("Key is " + one);
            }

            return one;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}

