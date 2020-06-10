import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/*
COMUNICAÇÕES DE CLIENTE PARA SERVIDOR É TCP
MENSSAGENS PARA OUTROS CLIENTES É EM UDP
 */

public class Main extends Thread{
    static byte[] buf = new byte[1024];
    static InetAddress address;
    static DatagramSocket socketUDP;
    static ListDealer dealer=new ListDealer();

    static {
        try {
            socketUDP = new DatagramSocket(9031);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static String sendEcho(String msg, DatagramSocket socketUDP,Socket socketTCP) throws IOException {
        socketUDP.getRemoteSocketAddress();
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length,socketTCP.getInetAddress(),9031);
        socketUDP.send(packet);
        byte[] buf2=new byte[1024];
        packet = new DatagramPacket(buf2, buf2.length);
        socketUDP.receive(packet);
        String received = new String(
                packet.getData(), 0, packet.getLength());
        return received;
    }



    public static void run(Socket socketTCP) throws IOException {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(socketTCP.getInputStream()));
        PrintStream ps = new PrintStream(socketTCP.getOutputStream());
        System.out.println("current threas ==="+currentThread());
        InetAddress ip=null;
        System.out.println("cliente acepted");
        System.out.println("waiting mesage from cliente");

        //InetSocketAddress a = (InetSocketAddress) socketTCP.getRemoteSocketAddress();
        //recive udp  packet
        //DatagramPacket packet=new DatagramPacket(buf,buf.length);
        //socketUDP.receive(packet);
        //****************

        //ip = a.getAddress();
        String inputFromUser=br.readLine();
        System.out.println("Message from user="+inputFromUser);
        switch (inputFromUser){
            case "0": {

                break;
            }
            case "1": {
                ps.println(dealer.listaBrancaToString());
                /*
                System.out.println("entrou no 1");

                InetAddress address = packet.getAddress();
                int destinyPort = packet.getPort();

                buf = dealer.listaBrancaToString().getBytes();
                address=socketUDP.getInetAddress();
                packet = new DatagramPacket(buf, buf.length, address, destinyPort);
                socketUDP.send(packet);
                ///sendEcho(dealer.listaBrancaToString(),socketUDP,socketTCP);
                */

                break;

            }
            case "2": {
                sendEcho(dealer.listaNegraToString(),socketUDP,socketTCP);
                break;
            }
            case "3": {
                break;
            }
            case "4": {
                break;
            }
            case "5": {

                break;
            }
            case "99": {
                break;
            }
        }





        dealer.trataIPs(ip.toString());
        if (dealer.verify(ip.toString())){
            sendEcho("YOU ARE IN THE BLACK LIST",socketUDP,socketTCP);
            socketTCP.close();
        }else{
            //ps.println("Welcome");
            dealer.addOnlineUser(ip.toString());
        }

    }





    public static void main(String args[]) throws IOException{

        ServerSocket serverTCP = new ServerSocket(6500);




        while(true) {
            Socket socketTCP = null;
            System.out.println("Waiting client");
            socketTCP = serverTCP.accept();

            if (socketTCP!=null){
                run(socketTCP);
            }


        }
    }
}