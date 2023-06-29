import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Prova {

    public static void main(String[] args) {
        /*
        Person p1 = new Person("Corrado", "Caia", 24);
        System.out.println(p1);

        Doctor m1 = new Doctor("Fede", "Anfu", "Cardiologo");
        try {
            m1.setAge(25);
        } catch (Exception e) {
            System.err.println(e);
        }

        System.out.println(m1);

        Patient paz1 = new Patient("Filippo", "Filippi", 89);
        try {
            paz1.setFiscalCode("HOIHBJKBOTIODR");
        } catch (Exception e) {
            System.err.println(e);
        }
        System.out.println(paz1);

        //Reservation reservation1 = new Reservation(paz1, m1);
        //System.out.println(reservation1);

        System.out.println("*******");
        var doc_lis = new ArrayList<Doctor>();
        Doctor d1 = new Doctor("Mario", "Rossi", "Cardiologist");
        Doctor d2 = new Doctor("Luigi", "Bianchi", "Orthopedic");
        Doctor d3 = new Doctor("Fausta", "Verdi", "Otolaryngologist");
        Doctor d4 = new Doctor("Maria", "Gialli", "Psychiatrist");
        Doctor d5 = new Doctor("Guglielmo", "Neri", "Pulmonologist");
        Doctor d6 = new Doctor("Laura", "Grigi", "gynecologist");

        doc_lis.add(d1);
        doc_lis.add(d2);
        doc_lis.add(d3);
        doc_lis.add(d4);
        doc_lis.add(d5);
        doc_lis.add(d6);

        int i=1;
        for(Doctor d:doc_lis) {
            System.out.println(i+") "+d);
            i=i+1;
        }

        System.out.println("Insert the number corresponding to the doctor");

        System.out.println("********* Utilizzo futuro di una mappa *********");
        HashMap<String, Doctor> map = new HashMap<String, Doctor>();

        map.put(d1.getName(), d1);
        map.put(d2.getName(),d2);
        System.out.println(map);
        System.out.println(map.get("Mario"));
        System.out.println("Levo Mario Rossi");
        map.remove("Mario");
        System.out.println(map);


        String s = "Ciao come va";
        String[] array1= s.split(" ");
        //char[] s1=s.toArray();
        //System.out.println(array1);
        for(String st : array1){
            System.out.println(st);
        }

        String data= "";
        int giorno = 19;
        String giorno_str = String.valueOf(giorno);
        int mese = 11;
        String mese_str = String.valueOf(mese);
        data = (giorno_str + "/" + mese);
        System.out.println(data);

        double random = Math.random();
        int day;
        day = (int) ((random * 28)+1);
        System.out.println(day);

        int month = 0;
        boolean control = false;
        int numero = 1;
        while(control == false){
            System.out.println("Select a Month (Indicate the corresponding number, example: 1 is January");
            if(numero> 0 && numero<13){
                month = numero;
                control = true;
            }
        }
        System.out.println("Sono fuori");

         */


        //Doctor d1 = new Doctor("Mario", "Rossi", "Cardiologist");
        //Doctor d2 = new Doctor("Luigi", "Bianchi", "Orthopedic");
        //map.put("FPJVPIERVNPIE", d1);
        //map.put("FPJVPIERVNPIy", d2);


        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.of(2023,12,31);

        while(!start.isAfter(end)){
            DayOfWeek dayOfWeek = start.getDayOfWeek();
            int day = dayOfWeek.getValue();

            System.out.println("Data: "+ start + ", giorno della settimana: "+ day);

            start = start.plusDays(1);
        }

        //System.out.println(cal);

    }
}
