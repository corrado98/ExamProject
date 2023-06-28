import java.io.Serializable;

public class Appointment implements Serializable {

    private int day;
    private String hour;

    public Appointment(int day, String hour) {
        this.day = day;
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
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
        return "Appointment{" +
                "day=" + day +
                ", hour='" + hour + '\'' +
                '}';
    }
}
