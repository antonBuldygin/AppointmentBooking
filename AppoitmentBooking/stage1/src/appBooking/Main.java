//package appBooking;
//
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class Main {
//    static Map<Long, String> patients = new TreeMap<>();
//    static Map<Long, String> doctors = new TreeMap<>();
//    static Map<Long, Appointment> appointments = new TreeMap<>();
//
//    public static void main(String[] args) {
//        int count = 1;
//        int docCount = 1;
//        int appId = 0;
//        Scanner scanner = new Scanner(System.in);
//        boolean flag = true;
//
//        doctors.put((long) docCount, "DD");
//        while (flag) {
//            System.out.println("Welcome to Doctor appointment service. Please choose the following Options:\n\n" +
//                    "1) Register as patient, choose your doctor and make appointment\n" +
//                    "2) Option to enter new doctor to the system\n" +
//                    "3) Show all appointments\n" +
//                    "4) Show list of doctors\n" +
//                    "5) Show list of registered patients\n" +
//                    "6) Show statistics by appointments quantity to doctors\n" +
//                    "7) Show statistics by patients appointments count\n" +
//                    "8) Exit \n");
//            String n = scanner.next();
//
//            switch (n) {
//                case "1":
//
//                    System.out.println("Please Enter your Name and Surname for registration");
//
//
//                    scanner.nextLine();
//                    String name = scanner.nextLine();
//
//                    System.out.println();
//
//
//                    long patientKey = 0L;
//                    if (!patients.containsValue(name)) {
//                        patientKey = count++;
//                        patients.put(patientKey, name);
//                    } else {
//                        for (Map.Entry entry : patients.entrySet()) {
//                            if (entry.getValue().equals(name)) {
//                                patientKey = (long) entry.getKey();
//                            }
//                            ;
//                        }
//
//                    }
//
//                    System.out.println("Please choose doctor to visit from the list below and enter id number\n");
//
//                    for (Map.Entry<Long, String> entry : doctors.entrySet()) {
//                        System.out.println("Doctor: " + entry.getValue() + "; id number for appointment # " + entry.getKey());
//                    }
//
//                    long doctorName = Long.parseLong(scanner.next());
//                    appId++;
//                    Calendar dateOfVisit = new GregorianCalendar();
//                    List<Integer> NON_BUSINESS_DAYS = Arrays.asList(
//                            Calendar.SATURDAY,
//                            Calendar.SUNDAY
//                    );
//                    boolean check= true;
//                    SimpleDateFormat format = new SimpleDateFormat("EEEE,dd/MM/yyyy", new Locale("en", "UK"));
//                    while(check){
//                    System.out.println("Please enter date of visit dd.mm");
//                    String input = scanner.next();
//                    int month = Integer.parseInt(input.substring(3, 5))-1;
//                    int day = Integer.parseInt(input.substring(0, 2));
//                     dateOfVisit = new GregorianCalendar(2022, month,
//                            day);
//                        if (NON_BUSINESS_DAYS.contains(dateOfVisit.get(Calendar.DAY_OF_WEEK))){
//                            System.out.println("This non-business day . The day of week is "+ format.format(dateOfVisit.getTime()));;
//                        }
//                        else{check=false;}
//                    }
//                    appointments.put((long) appId, new Appointment(doctorName, patientKey, dateOfVisit.getTime()));
//
//                    System.out.println("Thank you. Your appointment number is:  " + appId+ "; Date of visit: " + dateOfVisit.getTime());
//                    System.out.println();
//                    System.out.println("Please enter any symbol and press ENTER to continue");
//
////                        scanner.nextLine();
//                    scanner.next();
//
//
//                    break;
//
//                case "2":
//
//                    System.out.println("Please enter doctor name");
//                    scanner.nextLine();
//                    String newDoctorName = scanner.nextLine();
//
//                    long doctorKey = 0L;
//
//
//                    if (!doctors.containsValue(newDoctorName)) {
//                        doctorKey = ++docCount;
//                        doctors.put(doctorKey, newDoctorName);
//                    } else {
//                        for (Map.Entry entry : doctors.entrySet()) {
//                            if (entry.getValue().equals(newDoctorName)) {
//                                doctorKey = (long) entry.getKey();
//                            }
//                            ;
//                            ;
//                        }
//                    }
//                    System.out.println("Doc name " + doctors.get(doctorKey) + " Doc ID " + doctorKey);
//
//                    System.out.println();
//
//                    break;
//
//                case "3":
//                    System.out.println("List of all appointments:");
//                    System.out.println(appointments.toString());
//                    break;
//
//                case "4":
//                    System.out.println("List of all doctors:");
//                    System.out.println(doctors.toString());
//                    break;
//                case "5":
//                    System.out.println("List of all registered patients:");
//                    System.out.println(patients.toString());
//                    break;
//                case "6":
//
//                    break;
//                case "7":
//
//                    break;
//
//                case "8":
//                    flag = false;
//                    System.out.println("Buy");
//                    scanner.close();
//                    break;
//                default:
//                    System.out.println("1) Register as Patient, choose your doctor and make appointment +\n" +
//                            "2) Only for Doctors to enter new Doctor to the system\\n\" +\n" +
//                            "3) Exit ");
//            }
//        }
//    }
//}
