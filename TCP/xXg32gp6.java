package TCP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class xXg32gp6 {
    public static void main(String[] args) {
        // Thay thế "B15DCCN001" bằng mã sinh viên của bạn
        final String STUDENT_CODE = "B15DCCN001";
        final String Q_CODE = "TCP.xXg32gp6";
        final String SERVER_IP = "203.162.10.109";
        final int SERVER_PORT = 2207;

        DatagramSocket clientSocket = null;

        try {
            // Khởi tạo socket cho client
            clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(SERVER_IP);

            // a. Gửi thông điệp chứa mã sinh viên và mã câu hỏi
            String initialMessage = ";" + STUDENT_CODE + ";" + Q_CODE;
            byte[] sendData = initialMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
            clientSocket.send(sendPacket);
            System.out.println("Đã gửi: " + initialMessage);

            // b. Nhận chuỗi từ server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
            System.out.println("Đã nhận: " + serverResponse);

            // Tách requestId và chuỗi số
            String[] parts = serverResponse.split(";");
            if (parts.length != 2) {
                throw new IOException("Định dạng phản hồi từ server không hợp lệ.");
            }
            String requestId = parts[0];
            String[] numberStrings = parts[1].split(",");

            // Chuyển đổi mảng chuỗi số thành danh sách các số nguyên
            List<Integer> numbers = Arrays.stream(numberStrings)
                                          .map(String::trim)
                                          .map(Integer::parseInt)
                                          .collect(Collectors.toList());

            // c. Tìm giá trị lớn nhất và nhỏ nhất
            int max = Collections.max(numbers);
            int min = Collections.min(numbers);

            // Gửi kết quả lên server
            String resultMessage = requestId + ";" + max + "," + min;
            sendData = resultMessage.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
            clientSocket.send(sendPacket);
            System.out.println("Đã gửi kết quả: " + resultMessage);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // d. Đóng socket
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
                System.out.println("Đã đóng socket và kết thúc chương trình.");
            }
        }
    }
}
