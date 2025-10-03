package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class MoFrlKc2_UDP_Data_Type {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2207;
        String studentCode = "B22DCCN508";
        String qCode = "MoFrlKc2";

        try(DatagramSocket client = new DatagramSocket()) {
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

            String recvString = new String(recvData, 0, recvPacket.getLength());
            String[] part = recvString.split(";");
            String requestId = part[0];
            String data = part[1];
            System.out.println("b");

            //c
            int[] nums = Arrays.stream(data.split(",")).mapToInt(Integer::parseInt).toArray();
            Arrays.sort(nums);
            int sMax = nums[nums.length - 2];
            int sMin = nums[1];
            System.out.println("C");

            //d
            sendData = String.format("%s;%d,%d", requestId, sMax, sMin).getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println("D");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
