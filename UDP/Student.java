package UDP;

import java.io.Serializable;

public class Student implements Serializable {
    public String id, name, code, email;
    private static final long serialVersionUID = 20171107L;

    public Student(String id, String name, String code, String email) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.email = email;
    }

    public Student(String code) {
        this.code = code;
    }
}
