package UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class VqTsZv3k_UDP_Object {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port  = 2209;
        String studentCode = "B22DCCN508";
        String qCode = "VqTsZv3k";

        try (DatagramSocket client = new DatagramSocket()) {
            client.setSoTimeout(5000);
            InetAddress svAddress = InetAddress.getByName(host);
            byte[] sendData = null;
            DatagramPacket sendPacket = null;

            //a
            String login = ";" + studentCode + ";" + qCode;
            sendData = login.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println("A");

            //b
            byte[] recvData = new byte[4096];
            DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
            client.receive(recvPacket);

            byte[] requestIdByte = Arrays.copyOfRange(recvData, 0, 8);

            ByteArrayInputStream bais = new ByteArrayInputStream(recvData, 8, recvPacket.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Customer customer = (Customer) ois.readObject();
            System.out.println("b");

            //c
            String name = customer.name;
            String[] arrName = name.toLowerCase().split("\\s+");
            StringBuilder sb = new StringBuilder();
            sb.append(arrName[arrName.length - 1].toUpperCase());
            sb.append(", ");
            for (int i = 0; i < arrName.length - 1; i++) {
                String tmp = Character.toUpperCase(arrName[i].charAt(0)) + arrName[i].substring(1);
                sb.append(tmp);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            customer.name = sb.toString();
            System.out.println(customer.name);

            DateTimeFormatter ip = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            DateTimeFormatter op = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(customer.dayOfBirth, ip);
            customer.dayOfBirth = date.format(op);
            System.out.println(customer.dayOfBirth);

            sb = new StringBuilder();
            for (int i = 0; i < arrName.length - 1; i++) {
                sb.append(arrName[i].charAt(0));
            }
            sb.append(arrName[arrName.length - 1]);
            customer.userName = sb.toString();
            System.out.println(customer.userName);

            //d
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(requestIdByte);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(customer);
            oos.flush();

            sendData = baos.toByteArray();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println("DD");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
