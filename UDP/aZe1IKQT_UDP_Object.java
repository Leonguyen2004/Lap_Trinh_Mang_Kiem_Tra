package UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class aZe1IKQT_UDP_Object {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2209;
        String studentCode = "B22DCCN508";
        String qCode = "aZe1IKQT";

        try (DatagramSocket client = new DatagramSocket()) {
            InetAddress svAddress = InetAddress.getByName(host);
            byte[] sendData = null;
            DatagramPacket sendPacket = null;

            //a
            String login = ";" + studentCode + ";" + qCode;
            sendData = login.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println("a");

            //b
            byte[] recvData = new byte[4096];
            DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
            client.receive(recvPacket);

            byte[] requestIdByte = Arrays.copyOfRange(recvData, 0, 8);

            ByteArrayInputStream bais = new ByteArrayInputStream(recvData, 8, recvPacket.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Student st = (Student) ois.readObject();
            System.out.println("b");

            //c
            String name = st.name;
            String[] nameWords = name.toLowerCase().split("\\s+");
            StringBuilder sb = new StringBuilder();
            for (String word : nameWords) {
                String tmp = Character.toUpperCase(word.charAt(0)) + word.substring(1);
                sb.append(tmp);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            st.name = sb.toString().trim();

            StringBuilder email = new StringBuilder();
            email.append(nameWords[nameWords.length - 1]);
            for (int i = 0; i < nameWords.length - 1; i++) {
                email.append(nameWords[i].charAt(0));
            }
            email.append("@ptit.edu.vn");
            st.email = email.toString();
            System.out.println(st.name + " " + st.email);

            //d
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(requestIdByte);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(st);
            oos.flush();

            sendData = baos.toByteArray();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println("d");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
