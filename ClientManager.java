import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;

public class ClientManager implements Runnable{

    Socket client_socket;
    Server my_server;

    public ClientManager(Socket client, Server server) {
        System.out.println("Creating a new Client Manager! ");
        this.client_socket = client;
        this.my_server = server;
    }

    public void execute() {

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


            while (!received_command.equals("CMD_QUIT")) {
                received_command = sc.nextLine();
                System.out.println("Received command " + received_command);
                switch (received_command) {
                    case "CMD_ADD_DOCTOR":
                        var d_name = sc.nextLine();
                        var d_surname = sc.nextLine();
                        var d_spec = sc.nextLine();
                        var d_FC = sc.nextLine();
                        var d_day = Integer.parseInt(sc.nextLine());
                        var d_hour = sc.nextLine();
                        var d_end_cmd = sc.nextLine();


                        if (!d_end_cmd.equals("END_CMD")) {
                            System.err.println("Format Error !");
                        }

                        Doctor d = new Doctor(d_name, d_surname, d_spec, d_FC);
                        Appointment a = new Appointment(d_day, d_hour);
                        d.setApp_arr(a);
                        this.my_server.commandAddDoctor(d.getFC(), d, pw);
                        break;

                    case "CMD_ADD_VISIT":

                        var d_fc = sc.nextLine();
                        var d_day2 =Integer.parseInt(sc.nextLine());
                        var d_hour2 = sc.nextLine();
                        var d_end_cmd2 = sc.nextLine();

                        if (!d_end_cmd2.equals("END_CMD")) {
                            System.err.println("Format Error !");
                        }

                        this.my_server.addappointment(d_fc, d_day2,d_hour2, pw);
                        break;

                    case "CMD_GIVE_APPOINTMENTS":
                        var dfc2 = sc.nextLine();
                        LocalDate data = LocalDate.parse(sc.nextLine());
                        var d_end_cmd3 = sc.nextLine();


                        if (!d_end_cmd3.equals("END_CMD")) {
                            System.err.println("Format Error !");
                        }

                        this.my_server.giveappointments(dfc2, pw, data);
                        break;

                    case "CMD_CREATE_VISIT":
                        var date = sc.nextLine();
                        var hour = sc.nextLine();
                        var d_cf = sc.nextLine();
                        var c_cf = sc.nextLine();
                        var en = sc.nextLine();

                        if(!en.equals("END_CMD")){
                            System.err.println("Format Error !");
                        }

                        this.my_server.createvisit(pw, date, hour, d_cf, c_cf);
                        break;

                    case "CMD_ADD_PATIENT":

                        var name = sc.nextLine();
                        var surname = sc.nextLine();
                        var age = Integer.parseInt(sc.nextLine());
                        var FC = sc.nextLine();
                        var p_end_cmd = sc.nextLine();

                        if (!p_end_cmd.equals("END_CMD")) {
                            System.err.println("Format Error !");
                        }

                        Patient p = new Patient(name, surname, age);
                        try {
                            p.setFiscalCode(FC);
                        } catch (Exception e) {

                            String str = e.getMessage();
                            this.my_server.response(pw, str);
                            break;
                        }

                        this.my_server.commandAddPatient(p.getFiscalCode(), p, pw);
                        break;

                    case "CMD_ADD_RESERVATION":
                        var p_fc = sc.nextLine();
                        var endcmd = sc.nextLine();
                        if (!endcmd.equals("END_CMD")) {
                            System.err.println("Format Error !");
                        }

                        this.my_server.verify(p_fc,pw);
                        break;
                    case "CMD_REMOVE_DOCTOR":
                        var d_fc2 = sc.nextLine();
                        var end_c = sc.nextLine();
                        if (!end_c.equals("END_CMD")) {
                            System.err.println("Format Error !");
                        }
                        System.out.println("Removing doctor "+ d_fc2);
                        this.my_server.remove_doc(d_fc2, pw);

                        break;

                    case "CMD_GET_DOCTOR":
                        var D_fc2 = sc.nextLine();
                        var End_c = sc.nextLine();
                        if (!End_c.equals("END_CMD")) {
                            System.err.println("Format Error !");
                        }
                        this.my_server.get_doc(D_fc2, pw);
                        break;
                    case "CMD_QUIT":
                        System.out.println("Closing connection ");
                        break;


                    case "CMD_REM_PATIENT":

                        var fc2= sc.nextLine();
                        var end3 = sc.nextLine();

                        if (!end3.equals("END_CMD")) {
                            System.err.println("Format Error !");
                        }

                        this.my_server.remove_patient(fc2, pw);
                        break;

                    case "CMD_GET_RES":

                        var fc3= sc.nextLine();
                        var end4 = sc.nextLine();

                        if (!end4.equals("END_CMD")){
                            System.err.println("Format Error !");
                        }

                        this.my_server.get_res(fc3, pw);


                        break;


                    default:
                        if (!received_command.isBlank()) {
                            System.out.println("Unknown command");
                        }
                }

            }
        }


    private String getHour() {
        double random_h = Math.random();
        double random_m = Math.random();

        int hour = (int) ((random_h * 5) + 15);
        //int minute = 00;
        int minute = 0;
        if(random_m < 0.5){ minute = 00;}
        else{ minute = 30;}
        String complete = hour +":"+minute;
        return complete;
    }

    private int getReservation(int month) {
        double random = Math.random();
        int day;
        if(month == 2){
            day = (int) ((random * 28)+1);
        }else if(month == 4 || month == 6 || month == 9 || month == 11){
            day = (int) ((random * 30)+1);
        }else{
            day = (int) ((random * 31)+1);
        }
        return day;
    }

    @Override
    public void run() {
        System.out.println("  [STARTING THE THREAD]  ");
        this.execute();
    }
}
