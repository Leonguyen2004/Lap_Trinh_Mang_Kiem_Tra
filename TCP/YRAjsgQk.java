package TCP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class YRAjsgQk {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2207;
        String studentCode = "B22DCCN508";
        String qCode = "TCP.YRAjsgQk";

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            //a
            String send = studentCode + ";" + qCode;
            dos.writeUTF(send);
            dos.flush();

            //b
            int a = dis.readInt(); // đọc số lượng
            int b = dis.readInt(); // đọc số lượng

            //c
            int sum = a + b;
            int mul = a * b;
            dos.writeInt(sum);
            dos.writeInt(mul);
            dos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
