import jdk.jshell.execution.LoaderDelegate;

import java.io.*;
import java.net.ServerSocket;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    private HashMap<String, Doctor> doc_map = new HashMap<>();
    private HashMap<String, Doctor> copy_map = new HashMap<>();
    private HashMap<String, Patient> p_map = new HashMap<>();

    private ArrayList<Reservation> res_list = new ArrayList<>();
    String st = "";
    boolean free;


    public synchronized void commandSaveMapDoc(String s){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(s);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(doc_map);
            System.out.println("Saving Doctors map...");
            oos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized void commandSaveMapPatient(String s){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(s);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(p_map);
            System.out.println("Saving Patient map...");
            oos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized void response2(PrintWriter pw , ArrayList<Appointment> a){

        pw.println("[SERVER]: "+ a);
        pw.flush();
    }
    public synchronized void getApp(PrintWriter pw, String s){
        commandLoadMapDoc("Doctormap.txt");
        if(doc_map.get(s)==null){
            response(pw, "Doctor not present");
        }else{
            response2(pw, doc_map.get(s).getApp_arr());
        }


    }


    public synchronized void remove_doc(String d_fc2, PrintWriter pw){
        commandLoadMapDoc("Doctormap.txt");
        commandLoadlist("list.txt");

        if(doc_map.get(d_fc2)==null){
            response(pw, "Doctor not present");
        }else{
            doc_map.remove(d_fc2);
            if(res_list.size()!=0) {
                for (int i = 0; i < res_list.size(); i++) {
                    if (res_list.get(i).getM().getFC().equals(d_fc2)) {
                        res_list.remove(i);
                    }
                }
                commandSaveList("list.txt");
            }
            commandSaveMapDoc("Doctormap.txt");
            response(pw, "Doctor removed");
            System.out.println(doc_map);
            System.out.println(res_list);
        }


    }

    public void get_doc(String dFc2, PrintWriter pw) {

        commandLoadMapDoc("Doctormap.txt");
        if(doc_map.get(dFc2)==null){
            response(pw, "Doctor not present");
        }else{
            response2(pw, doc_map.get(dFc2).getApp_arr());
        }
    }
    public synchronized void addappointment(String d_fc, int d_day2, String d_hour, PrintWriter pw ){
        commandLoadMapDoc("Doctormap.txt");
        copy_map = doc_map;
        if(copy_map.get(d_fc)!= null){
            boolean present = false;

            for(int i=0; i < copy_map.get(d_fc).getApp_arr().size(); i++){ //Scorro gli appuntamenti per quel dottore
                if(copy_map.get(d_fc).getApp_arr().get(i).getDay() == d_day2 ){
                    present = true; //se il giorno della visita già c'è non permette di aggiungere un altro slot
                }
            }

            if(present == false) {
                System.out.println("doc_map_iniziale: " + copy_map);
                Appointment a = new Appointment(d_day2, d_hour);
                copy_map.get(d_fc).setApp_arr(a);
                doc_map = copy_map;
                commandSaveMapDoc("Doctormap.txt");
                System.out.println("doc_map_finale: " + doc_map);
                System.out.println("Appuntamenti di " + doc_map.get(d_fc).getApp_arr());
                st = "Appointment added: " + a;
                response(pw, st);
                st = "";
            }else {
                System.out.println("You have already a slot of hours in that day! Please choose another day");
                response(pw, "You have already a slot of hours in that day! Please choose another day");
                st = "";
            }

        }else{
            System.out.println("Doctor not present");
            response(pw, "Doctor not present");
            st="";
        }

    }



    public synchronized void response(PrintWriter pw , String st){

        System.out.println("HO RICEVUTO "+ st);
        pw.println("[SERVER]: "+ st);
        pw.flush();
    }

    public synchronized void commandLoadMapDoc(String s){
        FileInputStream fis= null;
        ObjectInputStream ois= null;
        try {
            fis = new FileInputStream(s);
            ois = new ObjectInputStream(fis);
            doc_map = (HashMap<String, Doctor>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void commandLoadMapPatient(String s){
        FileInputStream fis= null;
        ObjectInputStream ois= null;
        try {
            fis = new FileInputStream(s);
            ois = new ObjectInputStream(fis);
            p_map = (HashMap<String, Patient>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public synchronized void commandAddDoctor(String s, Doctor d, PrintWriter pw){
        commandLoadMapDoc("Doctormap.txt");
        System.out.println("La mappa iniziale è :");
        doc_map.put(s,d);
        commandSaveMapDoc("Doctormap.txt");
        System.out.println("Mappa dei medici modificata");
        System.out.println(doc_map);
        st =  d+ " added";
        response(pw, st);
        st = "";

    }
    public synchronized void commandAddPatient(String s, Patient p, PrintWriter pw){
        commandLoadMapPatient("personmap.txt");
        System.out.println("PersonMap iniziale: ");
        System.out.println(p_map);
        p_map.put(s,p);
        commandSaveMapPatient("personmap.txt");
        System.out.println("PersonMap finale: ");
        System.out.println(p_map);
        st =  p + " added";
        response(pw, st);
        st = "";

    }

    public synchronized void response3(PrintWriter pw ){

        ArrayList<String> d_list= new ArrayList<>();
        commandLoadMapDoc("Doctormap.txt");
        System.out.println(doc_map);
        int i = 1;
        for(String s : doc_map.keySet()){
            Doctor d = doc_map.get(s);
            var name = d.getName();
            var surname = d.getSurname();
            var sp = d.getSpecializzazione();
            var code = s;
            String stamp = (i+")"+" "+ name + " "+ surname+ ", "+ sp+ ", Fiscal Code: "+ code).toString();
            d_list.add(stamp);
            i+=1;

        }
        pw.println("[SERVER]: Doctor List, Choose one of these doctors: "+ d_list);
        pw.flush();
    }

    public synchronized void remove_patient(String fc2, PrintWriter pw){
        commandLoadMapPatient("personmap.txt");
        commandLoadlist("list.txt");

        if(p_map.size()==0){
            response(pw, "Map of patients is empty! ");
        }else {
            p_map.remove(fc2);
            if(res_list.size()!=0) {
                for (int i = 0; i < res_list.size(); i++) {
                    if (res_list.get(i).getP().getFiscalCode().equals(fc2)) {
                        res_list.remove(i);
                    }
                }
                commandSaveList("list.txt");
            }

        }
        response(pw, "Patient removed");
        commandSaveMapPatient("personmap.txt");
        System.out.println(p_map);
        System.out.println(res_list);
    }

    public synchronized void get_res(String fc3, PrintWriter pw){
        commandLoadlist("list.txt");
        ArrayList<Reservation> getres = new ArrayList<>();

        if(res_list.size()==0){
            response(pw, "List of reservations is empty! ");
        }else {
            for(int i= 0; i< res_list.size(); i++){
            if(res_list.get(i).getP().getFiscalCode().equals(fc3)){
                getres.add(res_list.get(i));
            }
            }
            response4(pw, getres);
        }
    }


    public synchronized void verify(String fc, PrintWriter pw){
        commandLoadMapPatient("personmap.txt");
        if(p_map.get(fc) != null){
            response3(pw);
        }else{
            st = "Person not present! Please Sign in";
            response(pw, st);
            st ="";
        }
    }

    public synchronized void giveappointments(String dfc2,PrintWriter pw, LocalDate start){
        ArrayList<Appointment> copy_app= doc_map.get(dfc2).getApp_arr();
        ArrayList<Integer> day_app = new ArrayList<>();

        LocalDate end = LocalDate.of(2023,12,31);

        int d = 0;
        for(int s= 0; s < copy_app.size(); s++ ){
           d = copy_app.get(s).getDay();
           day_app.add(d);
        }//creo mappa con soli interi che sono uguali ai giorni di visita

        while(!start.isAfter(end)){
            boolean stop = false;
            DayOfWeek dayOfWeek = start.getDayOfWeek();
            int day = dayOfWeek.getValue();

            for(int z=0; z< day_app.size();z++){
                int co = day_app.get(z);
                if(co == day){
                    responselocal(pw, start, doc_map.get(dfc2).getApp_arr().get(z).getHour());
                    stop = true;
                }
            }
            if(stop == false){
            start = start.plusDays(1);
        }else{
            start = LocalDate.of(2024,1,1);
        }

        }


    }
    public synchronized void responselocal(PrintWriter pw, LocalDate d, String h){
        pw.println(d);
        pw.flush();
        pw.println(h);
        pw.flush();
    }

    public synchronized void createvisit(PrintWriter pw, String date, String hour, String d_cf, String c_cf){
        commandLoadlist("list.txt");
        commandLoadMapDoc("Doctormap.txt");
        commandLoadMapPatient("personmap.txt");
        Reservation r = new Reservation(p_map.get(c_cf), doc_map.get(d_cf), date, hour);
        free = true;
        if(res_list.size()!=0) {
            for (int i = 0; i < res_list.size(); i++) {
                if ((res_list.get(i).getDay().equals(date)) && (res_list.get(i).getM().getFC().equals(d_cf))) {
                    free = false;
                }
            }
        }
        if(free) {
            res_list.add(r);
            commandSaveList("list.txt");
            response(pw, "Visit created: " + r);
            System.out.println(res_list);
        }else{
            response(pw, "Doctor is already occupied in this day, please retry");
        }

    }
    public synchronized void commandLoadlist(String s){
        FileInputStream fis= null;
        ObjectInputStream ois= null;
        try {
            fis = new FileInputStream(s);
            ois = new ObjectInputStream(fis);
            res_list = (ArrayList<Reservation>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public synchronized void commandSaveList(String s){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(s);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(res_list);
            System.out.println("Saving Reservation List...");
            oos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public synchronized void response4(PrintWriter pw , ArrayList<Reservation> a){
        pw.println("[SERVER]: Your reservations: "+ a);
        pw.flush();
    }




    public static void main(String[] args) {
        var my_server = new Server();

        int port = Integer.parseInt(args[0]);
        try {
            var serverSocket = new ServerSocket(port);
            while(true){
                System.out.println("[SERVER]: Waiting for connections.. ");
                var client_socket = serverSocket.accept();
                System.out.println("[SERVER]: Accepted connection from "+ client_socket.getRemoteSocketAddress()); 


                var cm = new ClientManager(client_socket, my_server);
                new Thread(cm).start();
            }
        } catch (IOException e) {
            System.err.println("Server error");
            System.exit(-1);
        }
    }

}
