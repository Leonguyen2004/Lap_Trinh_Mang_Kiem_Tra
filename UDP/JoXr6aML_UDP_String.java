package UDP;
import java.util.*;
import java.net.*;

public class JoXr6aML_UDP_String {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2208;
        String studentCode = "B22DCCN508";
        String qCode = "JoXr6aML";

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
            System.out.println("a");

            //b
            byte[] recvData = new byte[4096];
            DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
            client.receive(recvPacket);

            String recvString = new String(recvPacket.getData(), 0, recvPacket.getLength());
            String[] part = recvString.split(";");
            String requestId = part[0];
            String strData = part[1];
            System.out.println(recvString);

            //c
            String[] words = strData.trim().toLowerCase().split("\\s+");
            StringBuilder sb = new StringBuilder();
            for (String word : words) {
                if (!word.isEmpty()) {
                    String tmp = Character.toUpperCase(word.charAt(0)) + word.substring(1);
                    sb.append(tmp);
                    sb.append(" ");
                }
            }
            sendData = String.format("%s;%s", requestId, sb.toString().trim()).getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println("c");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
