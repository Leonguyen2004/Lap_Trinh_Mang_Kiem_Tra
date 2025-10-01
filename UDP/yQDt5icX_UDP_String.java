package UDP;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class yQDt5icX_UDP_String {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2208;
        String studentCode = "B22DCCN508";
        String qCode =  "yQDt5icX";

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
            String recvString = new String(recvPacket.getData(),0,  recvPacket.getLength());

            String[] part = recvString.split(";");
            String reqeustId = part[0];
            String data = part[1];
            System.out.println(recvString);

            //c
            String[] arrWords = data.trim().split("\\s+");
            Arrays.sort(arrWords, (a,b) -> b.compareToIgnoreCase(a));
            StringBuilder sb = new StringBuilder();
            for (String word : arrWords) {
                sb.append(word);
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            String sendStr = reqeustId + ";" + sb.toString().trim();
            sendData = sendStr.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println(sendStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
