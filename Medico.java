public class Medico extends Persona{

    private String specializzazione;


    public Medico(String name, String surname, String specializzazione) {
        super(name, surname);
        this.specializzazione = specializzazione;
    }

    public String getSpecializzazione() {
        return specializzazione;
    }

    public void setSpecializzazione(String specializzazione) {
        this.specializzazione = specializzazione;
    }

    @Override
    public void setAge(int age) throws Exception{
        if(age <25 || age > 65){
            throw new Exception("The doctor must be between 25 and 65 years of age");
        }else {
            super.setAge(age);
        }
    }

    @Override
    public String toString() {
        return "Medico{" +  " name: " + super.getName() +
                ", surname: " + super.getSurname() +
                ", age= " + super.getAge() +
                ", specializzazione: " + this.getSpecializzazione() +
                '}';
    }
}
