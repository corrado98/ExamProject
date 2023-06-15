public class Paziente extends Persona{

    private String CodiceFiscale;

    public Paziente(String name, String surname, int age, String CodiceFiscale) {
        super(name, surname, age);
        this.CodiceFiscale = CodiceFiscale;
    }

    public String getCodiceFiscale() {
        return CodiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        CodiceFiscale = codiceFiscale;
    }

    @Override
    public String toString() {
        return "Paziente{" +  " name: " + super.getName() +
                ", surname: " + super.getSurname() +
                ", age= " + super.getAge() +
                ", CodiceFiscale= " + this.getCodiceFiscale()  +
                '}';
    }
}
