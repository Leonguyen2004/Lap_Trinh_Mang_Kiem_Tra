package TCP;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class gDon2Vzh {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2206;
        String studentCode = "B22DCCN508";
        String qCode = "TCP.gDon2Vzh";

        try (Socket socket = new Socket()) {
            // a. Kết nối với timeout 5s
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // a. Gửi "studentCode;qCode"
            String request = studentCode + ";" + qCode;
            out.write((request + "\n").getBytes());
            out.flush();
            System.out.println("Đã gửi: " + request);

            // b. Nhận chuỗi số từ server (1 dòng)
            // BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            // String numbersLine = reader.readLine();
            byte[] buffer = new byte[1024];
            int length = in.read(buffer); // đọc 1 lần
            String numbersLine = new String(buffer, 0, length).trim();
            System.out.println("Nhận từ server: " + numbersLine);

            // c. Tính tổng các số nguyên
            int sum = Arrays.stream(numbersLine.split("\\|"))
                            .map(String::trim)
                            .mapToInt(Integer::parseInt)
                            .sum();
            System.out.println("Tổng = " + sum);

            // Gửi lại tổng cho server
            out.write((sum + "\n").getBytes());
            out.flush();
            System.out.println("Đã gửi tổng: " + sum);

            // d. Đóng kết nối
            System.out.println("Đóng kết nối.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
