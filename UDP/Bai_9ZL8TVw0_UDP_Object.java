package UDP;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Bai_9ZL8TVw0_UDP_Object {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2209;
        String studentCode = "B22DCCN508";
        String qCode = "9ZL8TVw0";

        try (DatagramSocket client = new DatagramSocket()) {
            client.setSoTimeout(5000);
            InetAddress svAddress = InetAddress.getByName(host);
            byte[] sendData = null;
            DatagramPacket sendPacket = null;

            //a
            String login = ";" + studentCode +";" + qCode;
            sendData = login.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println("a");

            //b
            byte[] recvData = new byte[4096];
            DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
            client.receive(recvPacket);

            byte[] requestIdByte = Arrays.copyOfRange(recvData, 0, 8);

            ByteArrayInputStream bais = new ByteArrayInputStream(recvData, 8, recvPacket.getLength());
            ObjectInputStream ois = new ObjectInputStream(bais);
            Product laptop = (Product) ois.readObject();

            //c
            String name = laptop.name;
            String[] arrName = name.split("\\s+");
            int len = arrName.length;
            String tmp = arrName[0];
            arrName[0] = arrName[len - 1];
            arrName[len - 1] = tmp;
            laptop.name = String.join(" ", arrName);
            System.out.println(laptop.name);

            int quantity = laptop.quantity;
            String strQuantity = String.valueOf(quantity);
            StringBuilder sb = new StringBuilder();
            for (int i = strQuantity.length() - 1; i >= 0 ; i--) {
                sb.append(strQuantity.charAt(i));
            }
            laptop.quantity = Integer.parseInt(sb.toString());
            System.out.println(laptop.quantity);

            //d
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(requestIdByte);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(laptop);
            oos.flush();

            sendData = baos.toByteArray();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println("D");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
