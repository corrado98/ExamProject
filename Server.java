import java.io.*;
import java.net.ServerSocket;
import java.util.HashMap;

public class Server {

    private HashMap<String, Reservation> map = new HashMap<>();
    HashMap<String, Reservation> copy_map = new HashMap<>();
    //private HashMap<String, Reservation> map = new HashMap<>();
//definire command save list


    public synchronized void commandSaveMap(){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        //copy_map = map;
        try {
            fos = new FileOutputStream("Reservation.txt");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            System.out.println("Saving map...");
            oos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized void readMap(){
        //map = commandLoadMap();
        for(String r : map.keySet()){
            System.out.println(map.get(r));
        }
    }
    public synchronized void commandLoadMap(){
        FileInputStream fis= null;
        ObjectInputStream ois= null;
        try {
            fis = new FileInputStream("Reservation.txt");
            ois = new ObjectInputStream(fis);
            map = (HashMap<String, Reservation>) ois.readObject();
            ois.close();
            //return copy_map;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public synchronized boolean control_visit( Reservation r){
    boolean visit_control = true;
        for(String str : map.keySet()){
            if(map.get(str).getHour() == r.getHour() && map.get(str).getDay() == r.getDay() && map.get(str).getM() == r.getM()){
                visit_control = false;
            }
        }
        return visit_control;
    }

    public synchronized void commandAddReservation(String s, Reservation r){
        commandLoadMap();
        if(control_visit(r)) {
            if (map.get(s) != null) {
                System.err.println("Reservation already present !");
                System.err.println("Please remove your precedent reservation");
            } else {
                //System.out.println("Mappa di prima : ------> " + map);
                map.put(s, r);
                //System.out.println("Mappa  dopo : --------> " + map);
                //System.out.println("Sto per salvare la mappa");
                commandSaveMap();
                System.out.println(map);

            }
        }else{
            System.out.println("Please Retry... ");
        }
    }
    public synchronized void commandRemoveReservation(String s){
        commandLoadMap();
        if(map.get(s)==null){
            System.err.println(" This reservation doesn't exist.. Please retry");
        }else {
            System.out.println("Mappa prima: ");
            readMap();
            map.remove(s);
            System.out.println("Mappa DOPO CANCELLAZIONE: ");
            readMap();
        }
    }

    public synchronized void commandGetReservation(String s){map.get(s);}
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
