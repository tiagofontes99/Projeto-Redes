import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Main extends Thread{
    static byte[] buf = new byte[1024];
    static InetAddress address;
    static DatagramSocket socketUDP;

    static {
        try {
            socketUDP = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    static String sendEcho(String msg,Socket socketTCP) throws IOException {
        socketTCP.getRemoteSocketAddress();
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 4445);
        socketUDP.send(packet);
        byte[] buf2=new byte[1024];
        packet = new DatagramPacket(buf2, buf2.length);
        socketUDP.receive(packet);
        String received = new String(
                packet.getData(), 0, packet.getLength());
        return received;
    }

    public class HelloRunnable implements Runnable {

        public void run() {
            System.out.println("Hello from a thread!");
        }


    }


    public static void main(String args[]) throws IOException{
        InetAddress ip=null;
        ServerSocket serverTCP = new ServerSocket(6500);
        ListDealer dealer=new ListDealer();



        while(true) {
            Socket socketTCP = null;
            System.out.println("Waiting client");
            socketTCP = serverTCP.accept();



            System.out.println("cliente acepted");
            InetSocketAddress a = (InetSocketAddress) socketTCP.getRemoteSocketAddress();
            ip = a.getAddress();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socketTCP.getInputStream()));



            dealer.trataIPs(ip.toString());
            if (dealer.verify(ip.toString())){
                sendEcho("YOU ARE IN THE BLACK LIST",socketTCP);
                socketTCP.close();
            }else{
                //ps.println("Welcome");
                dealer.addOnlineUser(ip.toString());
            }

        }
    }
}