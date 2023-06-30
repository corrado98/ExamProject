import java.io.Serializable;

public class Reservation implements Serializable {

    private Patient p;
    private Doctor m;

    private String day;

    private String hour;

    private boolean free = true;

    public Reservation(Patient p, Doctor m, String day, String hour) {
        this.p = p;
        this.m = m;
        this.day = day;
        this.hour = hour;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Patient getP() {
        return p;
    }

    public void setP(Patient p) {
        this.p = p;
    }

    public Doctor getM() {
        return m;
    }

    public void setM(Doctor m) {
        this.m = m;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "Reservation { " +
                "Doctor: " + m.getName() +" "+ m.getSurname() +
                ", Patient: "+ p.getName()+" "+p.getSurname() + ", Day: "+ this.getDay() + ", Hour: "+ this.getHour() +
                '}';
    }
}
