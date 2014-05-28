package obj;

import anntt.Column;
import anntt.Entity;

/**
 * Created by tish on 18.05.2014.
 */
@Entity(name = "Student")
public class Student {
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "AGE")
    private int age;
    @Column(name = "ID")
    private long id;

    public Student(String firstName, String secondName, int age) {
        this.firstName = firstName;
        this.lastName = secondName;
        this.age = age;
    }

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

}
