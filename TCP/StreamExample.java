package TCP;

import java.io.*;

public class StreamExample {
    public static void main(String[] args) {
        // Output stream: write to a file
        try (FileOutputStream fos = new FileOutputStream("output.txt");
             PrintWriter writer = new PrintWriter(fos)) {
            writer.println("Hello, this is output using streams!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Input stream: read from a file
        try (FileInputStream fis = new FileInputStream("output.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Read from file: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
