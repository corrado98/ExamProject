import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private String surname;
    private int age;

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws Exception {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{ " +
                "name: " + this.getName() +
                ", surname: " + this.getSurname()+
                ", age=" + this.getAge() + " }";
    }
}
