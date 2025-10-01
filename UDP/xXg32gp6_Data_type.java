package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class xXg32gp6_Data_type {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        String studentCode = "B22DCCN508";
        String qCode = "TCP.xXg32gp6";
        int port = 2207;

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
            byte[] recvData =  new byte[4096];
            DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
            client.receive(recvPacket);

            String recvString = new String(recvPacket.getData(), 0, recvPacket.getLength());
            String[] part = recvString.split(";");
            String requestId = part[0];
            String[] arrStr = part[1].split(",");
            int[] arrNums = Arrays.stream(arrStr).mapToInt(Integer::parseInt).toArray();
            System.out.println(recvString);

            //c
            Arrays.sort(arrNums);
            int max = arrNums[arrNums.length - 1];
            int min = arrNums[0];
            sendData = String.format("%s;%d,%d", requestId, max, min).getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println("c");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
