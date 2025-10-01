package TCP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class URrwuACy {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2206;
        String studentCode = "B22DCCN508";
        String qCode = "TCP.URrwuACy";

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            //a
            String send = studentCode + ";" + qCode;
            os.write(send.getBytes());
            os.flush();

            //b
            byte[] numbers = new byte[1024];
            int length = is.read(numbers);
            String str = new String(numbers, 0,  length).trim();

            //c
            String[] listString = str.split(",");
            int sum = 0;
            for (String s : listString) {
                if (isPrime(s)) {
                    sum += Integer.parseInt(s);
                }
            }
            os.write(String.valueOf(sum).getBytes());
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isPrime(String a) {
        int num = Integer.parseInt(a);
        for(int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return num >= 2;
    } 
}
