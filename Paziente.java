public class Paziente extends Persona{

    private String CodiceFiscale;

    public Paziente(String name, String surname, int age) {
        super(name, surname, age);
    }


    public String getCodiceFiscale() {
        return CodiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) throws Exception{
        if(codiceFiscale.length() != 14){
            throw new Exception("The fiscal code must have 14 characters");
        }else {
            CodiceFiscale = codiceFiscale;
        }
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
