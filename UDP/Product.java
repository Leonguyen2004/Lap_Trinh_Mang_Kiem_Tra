package UDP;

import java.io.Serializable;

public class Product implements Serializable {
    public String id, code, name;
    public int quantity;
    private static final long serialVersionUID = 20161107;

    public Product(String id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }
}
