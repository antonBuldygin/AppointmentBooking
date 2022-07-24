package appBooking;

import java.util.Date;

public class Appointment implements Comparable {
private long idApp;
private long idD;
private long idP;
private long idStatus;
private Date date;

    public Appointment(long idD, long idP, Date date) {
        this.idD = idD;
        this.idP = idP;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment{" +

                " idD=" + idD +
                ", idP=" + idP +
                ", Date =" + date+
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return 0;


    }
}
