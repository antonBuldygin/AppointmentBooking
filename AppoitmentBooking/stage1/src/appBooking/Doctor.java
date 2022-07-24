package appBooking;

public class Doctor {
    private String doctorName;
    private long idD;

    public Doctor() {
    }

    public Doctor(String doctorName, long idD) {
        this.doctorName = doctorName;
        this.idD = idD;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public long getIdD() {
        return idD;
    }

    public void setIdD(long idD) {
        this.idD = idD;
    }
}
