import java.util.ArrayList;

public class Prova {

    public static void main(String[] args) {
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

        Reservation reservation1 = new Reservation(paz1, m1);
        System.out.println(reservation1);

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
    }
}
