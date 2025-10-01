package TCP;

import java.io.*;
import java.net.*;
import java.util.*;

public class n45Sm1vy {
    public static void main(String[] args) {
        String host = "203.162.10.109"; 
        int port = 2208;
        String studentCode = "B22DCCN508";  
        String qCode = "TCP.n45Sm1vy";

        try (Socket socket = new Socket()) {
            // timeout 5s
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            
            // a. Gửi "studentCode;qCode"
            String request = studentCode + ";" + qCode;
            writer.write(request);
            writer.newLine();
            writer.flush();
            System.out.println("Đã gửi: " + request);
            
            // b. Nhận danh sách domain
            String domainsLine = reader.readLine();
            System.out.println("Nhận từ server: " + domainsLine);

            // c. Lọc các domain .edu
            List<String> eduDomains = new ArrayList<>();
            if (domainsLine != null && !domainsLine.isEmpty()) {
                String[] domains = domainsLine.split(",\\s*");
                for (String d : domains) {
                    if (d.endsWith(".edu")) {
                        eduDomains.add(d.trim());
                    }
                }
            }
            
            // Gửi lại danh sách .edu cho server
            String eduResult = String.join(", ", eduDomains);
            writer.write(eduResult);
            writer.newLine();
            writer.flush();
            System.out.println("Gửi lại .edu: " + eduResult);

            // d. Đóng kết nối
            writer.close();
            reader.close();
            socket.close();
            System.out.println("Đóng kết nối.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}