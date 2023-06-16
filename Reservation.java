public class Reservation {

    private Patient p;
    private Doctor m;

    /*private String orario;
    private Date giorno;*/

    public Reservation(Patient p, Doctor m) {
        this.p = p;
        this.m = m;
        /*
        this.orario = orario;
        this.giorno = giorno;
        */
    }

    public Patient getP() {
        return p;
    }

    public void setP(Patient p) {
        this.p = p;
    }

    public Doctor getM() {
        return m;
    }

    public void setM(Doctor m) {
        this.m = m;
    }

    @Override
    public String toString() {
        return "Reservation { " +
                "Doctor: " + m.getName() +" "+ m.getSurname() +
                ", Patient: "+ p.getName()+" "+p.getSurname() +
                '}';
    }
}
