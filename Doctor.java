import java.util.ArrayList;

public class Doctor extends Person {

    private String specializzazione;
    private String orario;
    private int day;
    private ArrayList<Integer> day_Arr[];
    private String FiscalCode;


    public Doctor(String name, String surname, String specializzazione, String fiscalCode) {
        super(name, surname);
        this.specializzazione = specializzazione;
        this.FiscalCode = fiscalCode;
    }

    public ArrayList<Integer>[] getDay_Arr() {
        return day_Arr;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public String day_week(){
        if(this.getDay() == 1){return "sunday";}
        else if(this.getDay() == 2){return "monday";}
        else if(this.getDay() == 3){return "tuesday";}
        else if(this.getDay() == 4){return "wednesday";}
        else if(this.getDay() == 5){return "thursday";}
        else if(this.getDay() == 6){return "friday";}
        else {return "saturday";}

    }
//faccio diventare array di stringhe con cui abbino ora e giorno
    public void setDay_Arr(int d) {
        this.day_Arr[day_Arr.length].add(d);
    }

    public void setDay(String d) {
        boolean cond = false;
        while (cond == false) {
            if (d.equals("sunday") || d.equals("monday") || d.equals("tuesday") || d.equals("wednesday") || d.equals("thursday") || d.equals("friday") || d.equals("saturday")) {
                if (d.equals("sunday")) {
                    this.day = 1;
                   // this.setDay_Arr(1);
                } else if (d.equals("monday")) {
                    this.day = 2;
                } else if (d.equals("tuesday")) {
                    this.day = 3;
                } else if (d.equals("wednesday")) {
                    this.day = 4;
                } else if (d.equals("thursday")) {
                    this.day = 5;
                } else if (d.equals("friday")) {
                    this.day = 6;
                } else {
                    this.day = 7;
                }
                cond = true;
            }
            else{
                System.out.println("Please insert a valid day");
            }
        }
    }

    public String getOrario() {
        return orario;
    }

    public void setOrario(int start, int end) {
        this.orario = start + "/" + end;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }

    @Override
    public void setAge(int age) throws Exception{
        if(age <25 || age > 65){
            throw new Exception("The doctor must be between 25 and 65 years of age");
        }else {
            super.setAge(age);
        }
    }

    @Override
    public String toString() {
        return "Doctor{" +  " name: " + super.getName() +
                ", surname: " + super.getSurname() +
                ", specialization: " + this.getSpecializzazione() +
                ", day of receipt: " + this.day_week()+
                ", hour of receipt: " + this.orario +
                '}';
    }
}
