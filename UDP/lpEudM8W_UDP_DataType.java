package UDP;

import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class lpEudM8W_UDP_DataType {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2207;
        String studentCode = "B22DCCN508";
        String qCode = "lpEudM8W";

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
            String a = part[1];
            String b = part[2];
            System.out.println(recvString);

            //c
            BigInteger biga = new BigInteger(a);
            BigInteger bigb = new BigInteger(b);
            BigInteger bigSum = biga.add(bigb);
            BigInteger bigSub = biga.subtract(bigb);
            String sendStr = String.format("%s;%s,%s", requestId, bigSum.toString(), bigSub.toString());
            sendData = sendStr.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println(bigSum + " " + bigSub);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
