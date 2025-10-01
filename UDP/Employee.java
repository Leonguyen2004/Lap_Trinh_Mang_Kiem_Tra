package UDP;

import java.io.Serializable;

public class Employee implements Serializable {
    public String id, name, hireDate;
    public double salary;
    private static final long serialVersionUID = 20261107L;

    public Employee(String id, String name, String hireDate, double salary) {
        this.id = id;
        this.name = name;
        this.hireDate = hireDate;
        this.salary = salary;
    }
}
