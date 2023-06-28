import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Doctor extends Person {

    //CREARE PRENOTAZIONI -> FARE COLLABORARE MEDICO CON CLIENTE
    private String specializzazione;
    //private String orario;
    //private int day;

    private String FC;

    private ArrayList<Appointment> app_arr = new ArrayList<>();

    public Doctor(String name, String surname, String specializzazione, String FC) {
        super(name, surname);
        this.specializzazione = specializzazione;
        this.FC=FC;
    }

    public void setApp_arr(Appointment a) {
        this.app_arr.add(a);
    }

    public ArrayList<Appointment> getApp_arr() {
        return app_arr;
    }

    public String getFC() {
        return FC;
    }

    public void setFC(String FC) {
        this.FC = FC;
    }

    @Override
    public String toString() {
        return "Doctor{" + this.getName()+" "+ this.getSurname()+
                ", specializzazione='" + specializzazione + '\'' +
                ", FC='" + FC + '\'' +
                '}';
    }
}
