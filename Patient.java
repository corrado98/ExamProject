public class Patient extends Person {

    private String FiscalCode;

    public Patient(String name, String surname, int age) {
        super(name, surname, age);
    }

    public String getFiscalCode() {
        return FiscalCode;
    }

    public void setFiscalCode(String FiscalCode) throws Exception{
        if(FiscalCode.length() != 16){
            throw new Exception("The fiscal code must have 16 characters");
        }else {
            this.FiscalCode = FiscalCode;
        }
    }

    @Override
    public String toString() {
        return "Patient{" +  " name: " + super.getName() +
                ", surname: " + super.getSurname() +
                ", age= " + super.getAge() +
                ", CodiceFiscale= " + this.getFiscalCode()  +
                '}';
    }
}
