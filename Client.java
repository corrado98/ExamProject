import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {

        var doc_map = new HashMap<String, Doctor>();

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

            System.out.println("****************************************");
            System.out.println("WELCOME TO POLYCLINIC ANFUCAIA");
            System.out.println("Insert your choice: ");
            System.out.println(" a : Doctor");
            System.out.println(" b : Patient");
            System.out.println("****************************************");
            System.out.print("Enter Choice: ");
            choice = input.nextLine();


            if(choice.equals("a")) {
                while(!choice.equals("5")) {
                    System.out.println("***** DOCTOR'S PAGE *****");
                    System.out.println(" 1) Work with us");
                    System.out.println(" 2) Add your day visit");
                    System.out.println(" 3) Remove yourself");
                    System.out.println(" 4) Get your day visits");
                    System.out.println(" 5) Quit");
                    System.out.print("Enter Number Choice: ");
                    choice = input.nextLine();
                    switch (choice) {
                        case "1":
                            System.out.println("Please insert your credentials");
                            System.out.print("Name: ");
                            var doc_name = input.nextLine();
                            System.out.print("Surname: ");
                            var doc_surname = input.nextLine();
                            System.out.print("Specialization: ");
                            var doc_spec = input.nextLine();
                            System.out.print("Fiscal Code: ");
                            var doc_FC = input.nextLine();
                            if(doc_FC.length() != 16){
                                System.out.println("Retry! Fiscal Code must have 16 characters");
                            }else {
                                System.out.print("Day of your visit (7: sunday, 1: monday, 2: tuesday, 3: wednesday, 4: thursday, 5: friday, 6: saturday): ");
                                var doc_day_visit = input.nextLine();
                                System.out.print("Select an hour range (ex. start-end): ");
                                String doc_hour = input.nextLine();
                                pw.println("CMD_ADD_DOCTOR");
                                pw.flush();
                                pw.println(doc_name);
                                pw.flush();
                                pw.println(doc_surname);
                                pw.flush();
                                pw.println(doc_spec);
                                pw.flush();
                                pw.println(doc_FC);
                                pw.flush();
                                pw.println(doc_day_visit);
                                pw.flush();
                                pw.println(doc_hour);
                                pw.flush();
                                pw.println("END_CMD");
                                pw.flush();
                                System.out.println(scanner.nextLine());
                            }
                            break;
                        case "2":
                            System.out.print("Insert your Fiscal Code: ");
                            var f_doc_code = input.nextLine();
                            System.out.print("Day of your visit (7: sunday, 1: monday, 2: tuesday, etc..):  ");
                            var doc_day_visit2 = input.nextLine();
                            System.out.print("Select an hour range (ex. start-end): ");
                            String doc_hour_2 = input.nextLine();

                            pw.println("CMD_ADD_VISIT");
                            pw.flush();
                            pw.println(f_doc_code);
                            pw.flush();
                            pw.println(doc_day_visit2);
                            pw.flush();
                            pw.println(doc_hour_2);
                            pw.flush();
                            pw.println("END_CMD");
                            pw.flush();
                            System.out.println(scanner.nextLine());



                            break;
                        case "3":
                            System.out.print("Insert your Fiscal Code:  ");
                            var f_doc_code2 = input.nextLine();

                            pw.println("CMD_REMOVE_DOCTOR");
                            pw.flush();
                            pw.println(f_doc_code2);
                            pw.flush();
                            pw.println("END_CMD");
                            pw.flush();
                            System.out.println(scanner.nextLine());
                            break;
                        case "4":
                            System.out.print("Insert your Fiscal Code:  ");
                            var f_doc_code3 = input.nextLine();
                            pw.println("CMD_GET_DOCTOR");
                            pw.flush();
                            pw.println(f_doc_code3);
                            pw.flush();
                            pw.println("END_CMD");
                            pw.flush();
                            System.out.println(scanner.nextLine());
                            break;





                        
                    }
                }

            }else if(choice.equals("b")){
                while(!choice.equals("q")){
                    System.out.println("****************************************");
                    System.out.println("**** PATIENT'S PAGE ****");
                    System.out.println("Select one of the following choices: ");
                    System.out.println("");
                    System.out.println(" a : Sign in ");
                    System.out.println(" b : Book a visit ");
                    System.out.println(" c : Cancel yourself ");
                    System.out.println(" d : Get my reservation ");
                    System.out.println(" e : Our Contacts");
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

                            pw.println("CMD_ADD_PATIENT");
                            pw.flush();
                            pw.println(name);
                            pw.flush();
                            pw.println(surname);
                            pw.flush();
                            pw.println(age);
                            pw.flush();
                            pw.println(FC);
                            pw.flush();
                            pw.println("END_CMD");
                            pw.flush();
                            System.out.println(scanner.nextLine());

                            break;


                        case "b":

                            System.out.println(" Insert your Fiscal Code: ");
                            var FC2 = input.nextLine();

                            pw.println("CMD_ADD_RESERVATION");
                            pw.flush();
                            pw.println(FC2);
                            pw.flush();
                            pw.println("END_CMD");
                            pw.flush();
                            String ris = scanner.nextLine();



                            if(ris.equals("[SERVER]: Person not present! Please Sign in")){
                                System.out.println(ris);
                                break;
                            }else {
                                System.out.println(ris); //qui stampa lista medici
                                System.out.print(" Please insert the Fiscal Code of the doctor :");
                                var f_cd = input.nextLine();
                                LocalDate start = LocalDate.now();
                                pw.println("CMD_GIVE_APPOINTMENTS");
                                pw.flush();
                                pw.println(f_cd);
                                pw.flush();
                                pw.println(start);
                                pw.flush();
                                pw.println("END_CMD");
                                pw.flush();

                                LocalDate proposed = LocalDate.parse(scanner.nextLine());
                                String hour = scanner.nextLine();
                                System.out.print("[SERVER]: Date proposed: ");
                                System.out.println(proposed);
                                System.out.print("[SERVER]: Hour proposed: " );
                                System.out.println(hour);
                                System.out.print(" Do you want to accept this reservation? [Y/N]: " );
                                var ch = input.nextLine();
                                while (!ch.equals("Y")){
                                    //proponi visite
                                    proposed = proposed.plusDays(1);
                                    pw.println("CMD_GIVE_APPOINTMENTS");
                                    pw.flush();
                                    pw.println(f_cd);
                                    pw.flush();
                                    pw.println(proposed);
                                    pw.flush();
                                    pw.println("END_CMD");
                                    pw.flush();

                                    proposed = LocalDate.parse(scanner.nextLine());
                                    hour = scanner.nextLine();
                                    System.out.print("[SERVER]: Date proposed: ");
                                    System.out.println(proposed);
                                    System.out.print("[SERVER]: Hour proposed: " );
                                    System.out.println(hour);
                                    System.out.print(" Do you want to accept this reservation? [Y/N]: " );
                                    ch = input.nextLine();

                                }
                                    pw.println("CMD_CREATE_VISIT");
                                    pw.flush();
                                    pw.println(proposed);
                                    pw.flush();
                                    pw.println(hour);
                                    pw.flush();
                                    pw.println(f_cd);
                                    pw.flush();
                                    pw.println(FC2);
                                    pw.flush();
                                    pw.println("END_CMD");
                                    pw.flush();

                                    System.out.println(scanner.nextLine());



                                    break;
                            }
                        case "c":
                            System.out.println(" Insert your Fiscal Code: ");
                            var fc2 = input.nextLine();

                            pw.println("CMD_REM_PATIENT");
                            pw.flush();
                            pw.println(fc2);
                            pw.flush();
                            pw.println("END_CMD");
                            pw.flush();

                            System.out.println(scanner.nextLine());
                            break;

                        case "d":
                            System.out.println(" Insert your Fiscal Code: ");
                            var fc3 = input.nextLine();

                            pw.println("CMD_GET_RES");
                            pw.flush();
                            pw.println(fc3);
                            pw.flush();
                            pw.println("END_CMD");
                            pw.flush();

                            System.out.println(scanner.nextLine());

                            break;

                        case "e":
                            System.out.println(" Address: Street White Mount 9");
                            System.out.println(" e-mail: anfucaia@gmail.com");
                            System.out.println(" Telephone number: +39 0931456789");

                            break;

                    }

                }

            }else {
                System.out.println("Please insert a valid choice !");
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
