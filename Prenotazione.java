import java.util.Date;

public class Prenotazione {

    private Paziente p;
    private Medico m;

    private String orario;
    private Date giorno;

    public Prenotazione(Paziente p, Medico m) {
        this.p = p;
        this.m = m;
        /*
        this.orario = orario;
        this.giorno = giorno;
        */
    }

    @Override
    public String toString() {
        return "Prenotazione{ " +
                "Medico: " + m.getName() +" "+ m.getSurname() +
                ", Paziente: "+ p.getName()+" "+p.getSurname() +
                '}';
    }
}
