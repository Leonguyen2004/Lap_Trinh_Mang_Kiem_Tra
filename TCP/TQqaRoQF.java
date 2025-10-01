package TCP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class TQqaRoQF {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2206;
        String studentCode = "B22DCCN508";
        String qCode = "TCP.TQqaRoQF";

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            //a
            String sendA = studentCode + ";" + qCode;
            os.write(sendA.getBytes());

            //b
            byte[] buffer = new byte[2048];
            int len = is.read(buffer);
            String nums = new String(buffer, 0 , len);
            int[] arr = Arrays.stream(nums.split(",")).
                                            map(String::trim).
                                            mapToInt(Integer::parseInt).
                                            toArray();

            //c
            int sum = Arrays.stream(arr).sum();
            int sumLeft = 0;

            int bestDiff = Integer.MAX_VALUE;
            int bestSumLeft = 0;
            int bestSumRight = 0;
            int bestIndex = 0;
            for(int i = 1; i < arr.length - 1; i++) {
                sumLeft += arr[i-1];
                int sumRight = sum - sumLeft - arr[i];
                int diff = Math.abs(sumLeft - sumRight);
                if (diff < bestDiff) {
                    bestDiff = diff;
                    bestIndex = i;
                    bestSumLeft = sumLeft;
                    bestSumRight = sumRight;
                }
            }

            String sendC = String.format("%d,%d,%d,%d", bestIndex, bestSumLeft, bestSumRight, bestDiff);
            os.write(sendC.getBytes());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
