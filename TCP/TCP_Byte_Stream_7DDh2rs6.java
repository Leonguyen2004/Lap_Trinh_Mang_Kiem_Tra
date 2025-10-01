package TCP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;

public class TCP_Byte_Stream_7DDh2rs6 {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2206;
        String studentCode = "B22DCCN508";
        String qCode = "7DDh2rs6";

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            //a
            String send = studentCode + ";" + qCode;
            os.write(send.getBytes());

            //b
            byte[] buffer = new byte[2048];
            int length = is.read(buffer);
            String numberLine = new String(buffer, 0, length);
            List<Integer> listNum = new ArrayList<>(
                Arrays.stream(numberLine.split(","))
                                            .map(String::trim)
                                            .map(Integer::parseInt)
                                            .toList()
            );

            //c
            listNum.sort(Comparator.naturalOrder());
            int l = 0;
            int r = listNum.size() - 1;
            double target = listNum.stream().mapToInt(Integer::intValue).average().orElse(0) * 2;
            double bestDiff = Double.MAX_VALUE;
            int num1 = listNum.get(0);
            int num2 = listNum.get(r);
            while(r > l) {
                int sum = listNum.get(l) + listNum.get(r);
                double diff = Math.abs(sum - target);
                if (diff < bestDiff) {
                    bestDiff = diff;
                    num1 = listNum.get(l);
                    num2 = listNum.get(r);
                }

                if (sum < target) {
                    l++;
                } else if (sum > target) {
                    r--;
                } else {
                    break;
                }
            }
            String sendC = num1 + "," + num2;
            os.write(sendC.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
