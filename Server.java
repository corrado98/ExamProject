import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class Server {

    private HashMap<String, Reservation> map = new HashMap<String, Reservation>();
//definire command save list
    public synchronized void commandAddReservation(String s, Reservation r){
        map.put(s,r);
        //salvare la mappa -> save map;
        System.out.println(map);
    }
    public synchronized void commandRemoveReservation(String s){ map.remove(s);}

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
