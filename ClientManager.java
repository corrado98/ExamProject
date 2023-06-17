import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientManager implements Runnable{

    Socket client_socket;
    Server my_server;

    public ClientManager(Socket client, Server server) {
        System.out.println("Creating a new Client Manager! ");
        this.client_socket = client;
        this.my_server = server;
    }

    public void execute(){

        Scanner sc = null;
        try {
            sc = new Scanner(client_socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(client_socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String received_command = "";

        while(!received_command.equals("CMD_QUIT")){
            received_command = sc.nextLine();
            System.out.println("Received command "+ received_command);
            switch (received_command){
                case "CMD_ADD_PERSON":
                    var name = sc.nextLine();
                    var surname = sc.nextLine();
                    var age = sc.nextLine();
                    var FC = sc.nextLine();
                    var doctor_name = sc.nextLine();
                    var doctor_surname = sc.nextLine();
                    var doctor_spec  = sc.nextLine();
                    var end_cmd= sc.nextLine();

                    if(!end_cmd.equals("END_CMD")){
                        System.err.println("Format Error !");
                    }

                    System.out.println("Adding patient... "+name + " "+ surname+ " "+ age + " "+ FC );
                    var patient = new Patient(name, surname, Integer.parseInt(age));

                    Doctor d = new Doctor(doctor_name, doctor_surname, doctor_spec);
                    System.out.println("Adding Reservation... "+name + " "+ surname+ ", "+ d );

                    Reservation res = new Reservation(patient, d);
                    my_server.commandAddReservation(patient.getFiscalCode(), res);

                    break;
                case "CMD_QUIT":
                    System.out.println("Closing connection ");
                    break;
            }

        }
    }
    @Override
    public void run() {
        System.out.println("  [STARTING THE THREAD]  ");
        execute();
    }
}
