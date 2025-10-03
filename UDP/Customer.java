package UDP;

import java.io.Serializable;

public class Customer implements Serializable {
    public String id, code, name, dayOfBirth, userName;
    private static final long serialVersionUID = 20151107;

    public Customer(String id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }
}
