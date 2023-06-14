public class Medico extends Persona{

    private String specializzazione;

    public Medico(String name, String surname, int age, String specializzazione) {
        super(name, surname, age);
        this.specializzazione = specializzazione;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }

    @Override
    public String toString() {
        return "Medico{" +  " name: " + super.getName() +
                ", surname: " + super.getSurname() +
                ", age= " + super.getAge() +
                ", specializzazione: " + specializzazione +
                '}';
    }
}
