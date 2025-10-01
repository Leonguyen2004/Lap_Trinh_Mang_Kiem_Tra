package TCP;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 20231107L;
    private int id;
    private String name;
    private double price;
    private int discount;
    public Product(int id, String name, double price, int discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }
    public double getPrice() {
        return price;
    }
    public int getDiscount() {
        return discount;
    }
    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
