import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {

        var doc_lis = new ArrayList<Doctor>();
        Doctor d1 = new Doctor("Mario", "Rossi", "Cardiologist");
        Doctor d2 = new Doctor("Luigi", "Bianchi", "Orthopedic");
        Doctor d3 = new Doctor("Fausta", "Verdi", "Otolaryngologist");
        Doctor d4 = new Doctor("Maria", "Gialli", "Psychiatrist");
        Doctor d5 = new Doctor("Guglielmo", "Neri", "Pulmonologist");
        Doctor d6 = new Doctor("Laura", "Grigi", "Gynecologist");

        doc_lis.add(d1);
        doc_lis.add(d2);
        doc_lis.add(d3);
        doc_lis.add(d4);
        doc_lis.add(d5);
        doc_lis.add(d6);

        String ip = args[0];
        int port = Integer.parseInt(args[1]);


        try {
            var socket = new Socket(ip,port);
            System.out.println("Connected");

            var is = socket.getInputStream();
            var os = socket.getOutputStream();

            var scanner = new Scanner(is);
            var pw = new PrintWriter(os);

            var input = new Scanner(System.in);


            String choice = "";

            while(!choice.equals("q")){
                System.out.println("****************************************");
                System.out.println("WELCOME TO POLYCLINIC ANFUCAIA");
                System.out.println("Select one of the following choices: ");
                System.out.println("");
                System.out.println(" a : Book a visit ");
                System.out.println(" b : Cancel your reservation ");
                System.out.println(" c : Get my reservation ");
                System.out.println(" d : Our Contacts");
                System.out.println(" q : quit ");
                System.out.println("****************************************");
                System.out.print(" Enter choice: ");
                choice = input.nextLine();

                switch (choice){
                    case "a":
                        System.out.println(" Insert your name: ");
                        var name = input.nextLine();
                        System.out.println(" Insert your surname: ");
                        var surname = input.nextLine();
                        System.out.println(" Insert your age: ");
                        var age = input.nextLine();
                        System.out.println(" Insert your Fiscal Code: ");
                        var FC = input.nextLine();

                        System.out.println("Select one of the following doctors: ");
                        int i=1;
                        for(Doctor d:doc_lis) {
                            System.out.println(i+") "+d);
                            i=i+1;
                        }
                        System.out.println("Insert the number corresponding to the doctor: ");
                        var number = Integer.parseInt(input.nextLine());
                        var doc = doc_lis.get(number - 1);
                        //var doctor = (String) doc;
                        var doc_name = doc.getName();
                        var doc_surname = doc.getSurname();
                        var doc_spec = doc.getSpecializzazione();


                        boolean control = false;
                        int month = 0;
                        while(control == false){
                            System.out.println("Select a Month (Indicate the corresponding number, example: 1 is January)");
                            int m = Integer.parseInt(input.nextLine()) ;
                            if( m> 0 && m<13){
                                month = m;
                                control = true;
                            }
                        }

                        pw.println("CMD_ADD_PERSON");
                        pw.flush();
                        pw.println(name);
                        pw.flush();
                        pw.println(surname);
                        pw.flush();
                        pw.println(age);
                        pw.flush();
                        pw.println(FC);
                        pw.flush();
                        pw.println(doc_name);
                        pw.flush();
                        pw.println(doc_surname);
                        pw.flush();
                        pw.println(doc_spec);
                        pw.flush();
                        pw.println(month);
                        pw.flush();
                        pw.println("END_CMD");
                        pw.flush();
                        System.out.println(scanner.nextLine());
                        break;

                    case "b":

                        boolean control2= false;
                        var f_code = "";
                        while(control2 == false) {
                            System.out.println(" Insert your Fiscal Code: ");
                            f_code = input.nextLine();
                            if (f_code.length() != 16) {
                                System.err.println("Fiscal Code must have 16 characters ");
                            }else{
                                control2 = true;
                            }
                        }
                        pw.println("CMD_REMOVE");
                        pw.flush();
                        pw.println(f_code);
                        pw.flush();
                        pw.println("END_CMD");
                        pw.flush();
                        System.out.println(scanner.nextLine());
                        break;

                    case "c":
                        boolean control3= false;
                        var f_code2 = "";
                        while(control3 == false) {
                            System.out.println(" Insert your Fiscal Code: ");
                            f_code2 = input.nextLine();
                            if (f_code2.length() != 16) {
                                System.err.println("Fiscal Code must have 16 characters ");
                            }else{
                                control3 = true;
                            }
                        }
                        pw.println("CMD_GET");
                        pw.flush();
                        pw.println(f_code2);
                        pw.flush();
                        pw.println("END_CMD");
                        pw.flush();
                        System.out.println(scanner.nextLine());
                        break;

                    case "d":
                        System.out.println(" Address: Street White Mount 9");
                        System.out.println(" e-mail: anfucaia@gmail.com");
                        System.out.println(" Telephone number: +39 0931456789");


                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
