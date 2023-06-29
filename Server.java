import jdk.jshell.execution.LoaderDelegate;

import java.io.*;
import java.net.ServerSocket;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    //private HashMap<String, Reservation> map = new HashMap<>();

    private HashMap<String, Doctor> doc_map = new HashMap<>();
    private HashMap<String, Doctor> copy_map = new HashMap<>();
    private HashMap<String, Patient> p_map = new HashMap<>();

    private ArrayList<Reservation> res_list = new ArrayList<>();
    String st = "";


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
        if(doc_map.get(d_fc2)==null){
            response(pw, "Doctor not present");
        }else{
            doc_map.remove(d_fc2);
            commandSaveMapDoc("Doctormap.txt");
            response(pw, "Doctor removed");
            System.out.println(doc_map);
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
                    present = true; //se il giorno della visita già c'è non permette di aagiungere un altro slot
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

        pw.println("[SERVER]: "+ st);
        pw.flush();
    }
    /*
    public synchronized void readMap(){
        //map = commandLoadMap();
        for(String r : map.keySet()){
            System.out.println(map.get(r));
        }
    }
    public synchronized void commandLoadMap(String s){
        FileInputStream fis= null;
        ObjectInputStream ois= null;
        try {
            fis = new FileInputStream(s);
            ois = new ObjectInputStream(fis);
            map = (HashMap<String, Reservation>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }*/

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
        //copy_map=doc_map;
        //commandSaveCopyMapDoc("copymap.txt");
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
            /*
            for(int n = 0; n<doc_map.size();n++){
                pw.println("[SERVER]: Doctor List, Choose one of these doctors: "+ d_list);
                pw.flush();
            }*/
        }
        pw.println("[SERVER]: Doctor List, Choose one of these doctors: "+ d_list);
        pw.flush();
    }

    public synchronized void verify(String fc, PrintWriter pw){
        commandLoadMapPatient("personmap.txt");
        if(p_map.get(fc) != null){
            response3(pw);
        }else{
            response(pw, "Person not present! Please Sign in");
        }
    }

    public synchronized void giveappointments(String dfc2,PrintWriter pw){
        ArrayList<Appointment> copy_app= doc_map.get(dfc2).getApp_arr();
        ArrayList<Integer> day_app = new ArrayList<>();

        LocalDate start = LocalDate.now();
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
        res_list.add(r);
        commandSaveList("list.txt");
        response(pw, "Visit created: "+ r);
        System.out.println(res_list);
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

        pw.println("[SERVER]: "+ a);
        pw.flush();
    }
    /*
    public synchronized boolean control_visit( Reservation r){
    boolean visit_control = true;
        for(String str : map.keySet()){
            if(map.get(str).getHour() == r.getHour() && map.get(str).getDay() == r.getDay() && map.get(str).getM() == r.getM()){
                visit_control = false;
            }
        }
        return visit_control;
    }



    public synchronized void commandAddReservation(String s, Reservation r, PrintWriter pw){
        commandLoadMap("Reservation.txt");
        if(control_visit(r)) {
            if (map.get(s) != null) {
                System.err.println("Reservation already present !");
                System.err.println("Please remove your precedent reservation");
                st = "Reservation already present, please remove your precedent reservation ..";
                response(pw, st);
                st= "";

            } else {

                map.put(s, r);
                st =  r+ " added";
                response(pw, st);
                st = "";
            }

        }else{
            System.out.println("Please Retry... ");
        }
    }
    public synchronized void commandRemoveReservation(String s, PrintWriter pw){
        commandLoadMap("Reservation.txt");
        if(map.get(s)==null){
            System.err.println(" This reservation doesn't exist.. Please retry");
            st = " This reservation doesn't exist.. Please retry ";
            response(pw, st);
            st= "";
        }else {
            System.out.println("Mappa prima: ");
            readMap();
            map.remove(s);
            System.out.println("Mappa DOPO CANCELLAZIONE: ");
            readMap();
            //commandSaveMap();
            st = " Reservation removed ";
            response(pw, st);
            st= "";

        }
    }

    public synchronized void commandGetReservation(PrintWriter pw, String s){
        commandLoadMap("Reservation.txt");
        if(map.get(s)==null){
            System.err.println(" This reservation doesn't exist.. Please retry");
            st = " This reservation doesn't exist.. Please retry ";
            response(pw, st);
            st= "";
        }else{
            Reservation r =map.get(s);
            System.out.println("Your reservation is "+ r);
            st = "Your reservation is "+ r;
            response(pw, st);
            st= "";
        }

    }*/
    public static void main(String[] args) {
        var my_server = new Server();

        int port = Integer.parseInt(args[0]);
        try {
            var serverSocket = new ServerSocket(port);
            while(true){
                System.out.println("[SERVER]: Waiting for connections.. ");
                var client_socket = serverSocket.accept();
                System.out.println("[SERVER]: Accepted connection from "+ client_socket.getRemoteSocketAddress()); // così so l'indirizzo del client


                var cm = new ClientManager(client_socket, my_server);
                new Thread(cm).start(); //per avviare il client manager
            }
        } catch (IOException e) {
            System.err.println("Server error");
            System.exit(-1);
        }
    }

}
