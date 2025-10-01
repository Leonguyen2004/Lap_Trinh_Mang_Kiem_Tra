package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class QF80acIf {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2209;
        String studentCode = "B22DCCN508";
        String qCode = "TCP.QF80acIf";

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            //a
            String send = studentCode + ";" + qCode;
            oos.writeObject(send);
            oos.flush();

            //b
            Product p = (Product) ois.readObject();

            //c
            int discount = calculateDiscountedPrice(p.getPrice());
            p.setDiscount(discount);
            oos.writeObject(p);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int calculateDiscountedPrice(double price) {
        int intPart = (int) price;
        int sum = 0;
        while(intPart > 0) {
            sum += intPart % 10;
            intPart /= 10;
        }
        return sum;
    }
}
