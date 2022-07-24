import org.hyperskill.hstest.dynamic.DynamicTest;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testing.TestedProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class appBookingTest extends StageTest {


    @DynamicTest()
    CheckResult test1() {
        TestedProgram pr = new TestedProgram();
        String output = pr.start();
        pr.execute("0").toLowerCase().trim();
        if (!pr.isFinished()) {

            throw new WrongAnswer("Your programm is not closed . Option \"0\" should close the programm");
        }
        return CheckResult.correct();
    }

    @DynamicTest()
    CheckResult test2() {
        TestedProgram pr = new TestedProgram();
        String output = pr.start();
        int count = 1;
        List<String> num = new ArrayList<>();

        String[] array = {
                "Suzan Vegas", "Dr. Eyee Bolite", "John Gramm", "Phill Good", "Phill Good", "John Gramm"
        };

// TEST option 1 "Create a new appointment"
        for (String patient : array
        ) {
            output = pr.execute("1").toLowerCase().trim();

            if (!output.contains(
                    "Please enter doctor's name: ".trim().toLowerCase())) {
                throw new WrongAnswer("Your output should contain " +
                        "\"Please enter doctor's name:\" after inputting \"1\"" + "but output is\n" + output);
            }

            output = pr.execute(patient).trim().toLowerCase();

            if (!output.contains("Please enter patient's name:".trim().toLowerCase())) {
                throw new WrongAnswer("Your output should contain " +
                        "Please enter patient's name:\" but " + output);
            }
            output = pr.execute(patient).trim().toLowerCase();

            if (!output.contains("Please enter appointment time: ".trim().toLowerCase())) {
                throw new WrongAnswer("Your output should contain " +
                        "Please enter appointment time: \" but " + output);
            }
            output = pr.execute("2021-01-01 15:00").trim().toLowerCase();

            if (!output.contains("Thank you. Your appointment number is:".trim().toLowerCase())) {
                throw new WrongAnswer("Your output should contain " +
                        "Thank you. Your appointment number is: \" but " + output);
            } else {
                Pattern pattern1 = Pattern.compile("number is:\\s*\\d+");

                java.util.regex.Matcher matcher1 = pattern1.matcher(output);

                while (matcher1.find()) {

                    num.add(matcher1.group().trim());
//                    System.out.println(matcher1.group() + " found");
                }

                Pattern pattern2 = Pattern.compile("\\d+");
                java.util.regex.Matcher matcher2 = pattern2.matcher(num.get(num.size() - 1));
                while (matcher2.find()) {

                    num.set(num.size() - 1, matcher2.group().trim());
//                    System.out.println(matcher2.group() + " found");
                }

                if (Integer.parseInt(num.get(num.size() - 1)) != (count)) {
                    throw new WrongAnswer("Appointment id should be " + count + ". But it is equal " + Integer.parseInt(num.get(num.size() - 1))
                    );

                } else count++;
            }
        }
// TEST option 2 "Show appointments"
        output = pr.execute("2").toLowerCase().trim();

        List<String> resultOfAllApointments = getListOfAppointments(output);
        checkListOfAppointments(resultOfAllApointments, array);

// TEST option 3 "Delete appointment"

        output = pr.execute("3").toLowerCase().trim();

        List<String> checkToDelete = getListOfAppointments(output);

        checkListOfAppointments(checkToDelete, array);

        Pattern pattern3 = Pattern.compile("^\\d+ ");

        String [] choices = new String[6];

        for (int i = 0; i < checkToDelete.size(); i++) {
            java.util.regex.Matcher matcher3 = pattern3.matcher(checkToDelete.get(i));
            while (matcher3.find()) {
                choices[i] = matcher3.group().trim();
                          }
        }



        for (int i = 0; i < choices.length; i++) {
            output = pr.execute(choices[i]).toLowerCase().trim();
            output = pr.execute("3").toLowerCase().trim(); ;
            }

        List<String> listAfterAllAppointmentsDeleted = getListOfAppointments(output);
        if (listAfterAllAppointmentsDeleted.size()!=0) {
            throw new WrongAnswer("List of appointments should be empty " +
                    " but  it contains " + listAfterAllAppointmentsDeleted.toString());
        }

//        for (String item : checkToDelete
//        ) {
//            System.out.println(item + " URA " + count++);
//
//        }
//        output = pr.execute("5").toLowerCase().trim();
//        output = pr.execute("4").toLowerCase().trim();
//        output = pr.execute("6").toLowerCase().trim();

        return CheckResult.correct();
    }

    public List<String> getListOfAppointments(String output) {
        Pattern pattern3 = Pattern.compile("\\d+ - date: 2021-01-01 15:00," +
                " doctor: [\\w.]+[\\w\\s]*, patient: [\\w.]+[\\w\\s]*[\n]+");
        java.util.regex.Matcher matcher3 = pattern3.matcher(output);

        List<String> resultOfAllApointments = new ArrayList<>();
        while (matcher3.find()) {

            resultOfAllApointments.add(matcher3.group().trim());

        }
        return resultOfAllApointments;
    }

    public void checkListOfAppointments(List<String> resultOfAllApointments, String[] array) {
        for (int i = 0; i < resultOfAllApointments.size(); i++) {
            if (!resultOfAllApointments.get(i).contains(array[i].toLowerCase())) {
                throw new WrongAnswer("Appointment should contain " + array[i]

                );
            }
            if (!resultOfAllApointments.get(i).contains("2021-01-01 15:00")) {
                throw new WrongAnswer("Appointment should contain date and time 2021-01-01 15:00"

                );
            }
            if (resultOfAllApointments.size() != array.length) {
                throw new WrongAnswer("There are "
                        + resultOfAllApointments.size() + " appointments, but should be " + array.length

                );
            }

        }
    }
}
