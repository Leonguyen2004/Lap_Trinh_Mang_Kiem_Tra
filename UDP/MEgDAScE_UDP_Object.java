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
import java.util.Date;

public class MEgDAScE_UDP_Object {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2209;
        String studentCode = "B22DCCN508";
        String qCodde = "MEgDAScE";

        try (DatagramSocket client = new DatagramSocket()) {
            client.setSoTimeout(5000);
            InetAddress svAddress = InetAddress.getByName(host);
            byte[] sendData = null;
            DatagramPacket sendPacket = null;

            //a
            String login = ";" + studentCode + ";" + qCodde;
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
            Employee empl = (Employee) ois.readObject();
            System.out.println("B");

            //c
            String name = empl.name;
            String[] arrName = name.toLowerCase().split("\\s+");
            for (int i = 0; i < arrName.length; i++) {
                String tmp = Character.toUpperCase(arrName[i].charAt(0)) + arrName[i].substring(1);
                arrName[i] = tmp;
            }
            empl.name = String.join(" ", arrName);

            String year = empl.hireDate.substring(0, 4);
            int x = 0;
            for (int i = 0; i < 4; i++) {
                x += year.charAt(i) - '0';
            }
            empl.salary *= (1.0 + (x / 100.0));


            DateTimeFormatter ip = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter op = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(empl.hireDate, ip);
            empl.hireDate = date.format(op);

            //d
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(requestIdByte);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(empl);
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
