

public class Prova {

    public static void main(String[] args) {
        Persona p1 = new Persona("Corrado", "Caia", 24);
        System.out.println(p1);

        Medico m1 = new Medico("Fede", "Anfu", "Cardiologo");
        try {
            m1.setAge(25);
        } catch (Exception e) {
            System.err.println(e);
        }

        System.out.println(m1);

        Paziente paz1 = new Paziente("Filippo", "Filippi", 89);
        try {
            paz1.setCodiceFiscale("HOIHBJKBOTIODR");
        } catch (Exception e) {
            System.err.println(e);
        }
        System.out.println(paz1);

        Prenotazione prenotazione1 = new Prenotazione(paz1, m1);
        System.out.println(prenotazione1);
    }
}
