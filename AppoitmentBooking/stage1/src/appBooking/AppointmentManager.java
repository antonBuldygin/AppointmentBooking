package appBooking;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class AppointmentManager {
    public AppointmentManager() {
    }

    public void appoint() throws SQLException, ParseException {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            System.out.print("\n" +
                    "1) Create a new appointment\n" +
                    "2) Show appointments\n" +
                    "3) Delete appointment\n" +
                    "0) Exit \n\n" +
                    "Please select: ");
            String n = scanner.next();
            System.out.println();
            switch (n) {
                case "1":

                    System.out.println("Create a new appointment");
                    System.out.print("Please enter doctor's name: ");
                    scanner.nextLine();
                    String newDoctorName = scanner.nextLine();

// check if doctor's name is in the doctors table
                    DbSQLrequestesManagement forPatient = new DbSQLrequestesManagement();
                    long idDocCheckResults = forPatient.checkForConstaints(newDoctorName, "doctor", "idDoc", "doctorName");
                    long doctorKey = 0L;
                    if (idDocCheckResults == 0L) {
                        doctorKey = forPatient.insertNewDoctor(newDoctorName);
                    } else {
                        doctorKey = idDocCheckResults;
                    }

                    DbSQLrequestesManagement dbSQLrequestesManagement = new DbSQLrequestesManagement();
                    System.out.print("Please enter patient's name: ");
                    String name = scanner.nextLine();
                    System.out.print("Please enter appointment time: ");
                    String date = scanner.nextLine();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
                    Date dateTime = formatter.parse(date);
//                    System.out.println(formatter.format(dateTime));
                    java.sql.Date sqlDate = new java.sql.Date(dateTime.getTime());
                    Time time = new java.sql.Time(dateTime.getTime());
//                    System.out.println(time);
//                    System.out.println(formatter.format(sqlDate));
                    long idCheckResults = dbSQLrequestesManagement.checkForConstaints(name, "patient", "idP", "patientName");

// check if patient's name is in the patients table
                    long patientKey = 0L;
                    if (idCheckResults == 0L) {
                        patientKey = dbSQLrequestesManagement.insertNewPatient(name);
                    } else {
                        patientKey = idCheckResults;
                    }

// insert new appointment
                    DbSQLrequestesManagement request = new DbSQLrequestesManagement();
                    long appointmentNumber = request.insertNewAppointment(doctorKey, patientKey,sqlDate, time);
                    System.out.println();
                    System.out.println("Thank you. Your appointment number is:  " + appointmentNumber);
                    break;

                case "2":
                    System.out.println("List of all appointments:");
                    DbSQLrequestesManagement dbSQLShowAllApp = new DbSQLrequestesManagement();
                    dbSQLShowAllApp.showAllAppointments();
                    dbSQLShowAllApp.getConnection().close();
                    break;

                case "9":

                    System.out.println("Please enter doctor name");
                    scanner.nextLine();
                    String newDoctorName1 = scanner.nextLine();

                    DbSQLrequestesManagement forPatient1 = new DbSQLrequestesManagement();
                    long idDocCheckResults1 = forPatient1.checkForConstaints(newDoctorName1, "doctor", "idDoc", "doctorName");
                    long doctorKey1 = 0L;
                    if (idDocCheckResults1 == 0L) {
                        doctorKey = forPatient1.insertNewDoctor(newDoctorName1);
                    } else {
                        doctorKey = idDocCheckResults1;
                    }

                    forPatient1.getConnection().close();
                    System.out.println();

                    break;

                case "3":
                    System.out.println("Chose appointment to delete:");
                    DbSQLrequestesManagement dbSQLShowAllApp1 = new DbSQLrequestesManagement();
                    dbSQLShowAllApp1.showAllAppointments();
                    System.out.println();
                    System.out.print("Please select: ");
                    String id = scanner.next();
                    System.out.println();
                    dbSQLShowAllApp1.deleteById(id);
                    System.out.println("The appointment has been deleted!");
                    dbSQLShowAllApp1.getConnection().close();
                    break;

                case "4":
                    System.out.println("List of all doctors:");
                    DbSQLrequestesManagement dbSQLShowAllDoctors = new DbSQLrequestesManagement();
                    dbSQLShowAllDoctors.showListOfDoctors();
                    System.out.println();
                    dbSQLShowAllDoctors.getConnection().close();
                    break;
                case "5":
                    System.out.println("List of all registered patients:");
                    DbSQLrequestesManagement dbSQLShowAllPatients = new DbSQLrequestesManagement();
                    dbSQLShowAllPatients.showListOfPatients();
                    dbSQLShowAllPatients.getConnection().close();
                    break;
                case "6":
                    System.out.println("Statistics for appointments per doctor");
                    DbSQLrequestesManagement dbSQLCountDoctorsappointment = new DbSQLrequestesManagement();
                    dbSQLCountDoctorsappointment.countAppointmentsByDoc();
                    dbSQLCountDoctorsappointment.getConnection().close();
                    break;
                case "7":
                    System.out.println("Statistics for appointments per patient");
                    DbSQLrequestesManagement dbSQLCountPatientsappointment = new DbSQLrequestesManagement();
                    dbSQLCountPatientsappointment.countAppointmentsByPatient();
                    dbSQLCountPatientsappointment.getConnection().close();
                    break;

                case "0":
                    flag = false;
                    scanner.close();
                    System.out.println("Buy");
                    break;
                default:
                    System.out.println("The choice is not available\n");
            }
        }
    }
}


