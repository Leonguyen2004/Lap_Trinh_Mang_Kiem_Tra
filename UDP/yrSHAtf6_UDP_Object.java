package UDP;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class yrSHAtf6_UDP_Object {
    public static void main(String[] args) {
        String host = "203.162.10.109";
        int port = 2209;
        String studentCode  = "B22DCCN508";
        String qCode = "yrSHAtf6";

        try (DatagramSocket client = new DatagramSocket()) {
            client.setSoTimeout(5000);
            InetAddress svAddress = InetAddress.getByName(host);
            byte[] sendData = null;
            DatagramPacket sendPacket = null;

            //a
            String login = ";" + studentCode + ";" +qCode;
            sendData = login.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println("A");

            //b
            byte[] recvData = new byte[4096];
            DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
            client.receive(recvPacket);

            byte[] requestIdByte = Arrays.copyOfRange(recvData, 0, 8);

            ByteArrayInputStream bais = new ByteArrayInputStream(recvData, 8, recvPacket.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Book book = (Book) ois.readObject();
            System.out.println("B");

            //c
            String title = book.title;
            String[] arrTtile = title.toLowerCase().split("\\s+");
            for (int i = 0; i < arrTtile.length; i++) {
                String tmp = Character.toUpperCase(arrTtile[i].charAt(0)) + arrTtile[i].substring(1);
                arrTtile[i] = tmp;
            }
            book.title = String.join(" ", arrTtile);
            System.out.println(book.title);

            String author = book.author;
            String[] arrAuthor = author.toLowerCase().split("\\s+");
            for (int i = 0; i < arrAuthor.length; i++) {
                String tmp = Character.toUpperCase(arrAuthor[i].charAt(0)) + arrAuthor[i].substring(1);
                arrAuthor[i] = tmp;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(arrAuthor[0].toUpperCase());
            sb.append(", ");
            for (int i = 1; i < arrAuthor.length; i++) {
                sb.append(arrAuthor[i]);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            book.author = sb.toString();
            System.out.println(book.author);

            String isbn = book.isbn;
            StringBuilder sbIsbn = new StringBuilder();
            int index = 0;
            for (int i = 0; i < 3; i++) {
                sbIsbn.append(isbn.charAt(index++));
            }
            sbIsbn.append("-");
            sbIsbn.append(isbn.charAt(index++));
            sbIsbn.append("-");
            for (int i = 0; i < 2; i++) {
                sbIsbn.append(isbn.charAt(index++));
            }
            sbIsbn.append("-");
            for (int i = 0; i < 6; i++) {
                sbIsbn.append(isbn.charAt(index++));
            }
            sbIsbn.append("-");
            sbIsbn.append(isbn.charAt(index++));
            book.isbn = sbIsbn.toString();
            System.out.println(book.isbn);

            DateTimeFormatter ip = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter op = DateTimeFormatter.ofPattern("MM/yyyy");
            LocalDate date = LocalDate.parse(book.publishDate, ip);
            book.publishDate = date.format(op);
            System.out.println(book.publishDate);

            //d
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(requestIdByte);
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(book);
            oos.flush();

            sendData = baos.toByteArray();
            sendPacket = new DatagramPacket(sendData, sendData.length, svAddress, port);
            client.send(sendPacket);
            System.out.println("D");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
