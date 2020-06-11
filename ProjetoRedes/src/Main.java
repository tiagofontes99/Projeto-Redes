import java.net.*;
import java.io.*;

/*
COMUNICAÇÕES DE CLIENTE PARA SERVIDOR É TCP
MENSSAGENS PARA OUTROS CLIENTES É EM UDP
 */

public class Main extends Thread{
    static byte[] buf = new byte[1024];
    static InetAddress address;
    static DatagramSocket socketUDP;
    static ListDealer dealer = new ListDealer();


    static {
        try {
            socketUDP = new DatagramSocket(9031);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    static class RunnableDemo implements Runnable{

        private Thread t;
        private Socket socketTCP;


        RunnableDemo( Socket socketTCP) {
            this.socketTCP = socketTCP;
            System.out.println("Creating " +  socketTCP );
        }

        public void start() throws IOException {
            System.out.println("Starting "+ socketTCP.toString());

            if (t==null){
                t=new Thread(this,socketTCP.toString());
                t.start();
            }
        }


        public void run() {
            try {
                System.out.println("cliente acepted");
                System.out.println("waiting mesage from cliente");
                System.out.println("current threas ===" + currentThread());

                BufferedReader reciverTCP = new BufferedReader(new InputStreamReader(socketTCP.getInputStream()));//TCP port para receber menssagens
                PrintStream senderTCP = new PrintStream(socketTCP.getOutputStream());//TCP para enviar menssagens


                InetAddress ip = socketTCP.getLocalAddress();
                if (Main.dealer.verify(ip.toString())) {
                    senderTCP.println("YOU ARE IN THE BLACK LIST");
                    socketTCP.close();
                } else {
                    Main.dealer.addOnlineUser(ip.toString());
                }
                String inputFromUser;
                do {
                    inputFromUser = reciverTCP.readLine();
                    System.out.println("Message from user=" + inputFromUser);
                    switch (inputFromUser) {
                        case "1": {
                            senderTCP.println(Main.dealer.getOnlineUsers());
                            break;
                        }
                        case "2": {

                            break;
                        }
                        case "3": {
                            break;
                        }
                        case "4": {
                            senderTCP.println(Main.dealer.getListaBrancaToString());
                            break;
                        }
                        case "5": {
                            senderTCP.println(Main.dealer.getListaNegraToString());
                            break;
                        }
                        case "99": {
                            senderTCP.println("A Sair\nCliente Desconectando");
                            socketTCP.close();
                            break;
                        }
                    }
                } while (!"99".equals(inputFromUser));
            }catch (IOException e){
            }
        }

    }

    public static String sendEcho(String msg, DatagramSocket socketUDP, Socket socketTCP) throws IOException {
        socketUDP.getRemoteSocketAddress();
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, socketTCP.getInetAddress(), 9031);
        socketUDP.send(packet);
        byte[] buf2 = new byte[1024];
        packet = new DatagramPacket(buf2, buf2.length);
        socketUDP.receive(packet);
        String received = new String(
                packet.getData(), 0, packet.getLength());
        return received;
    }


    public static void main(String args[]) throws IOException {

        ServerSocket serverTCP = new ServerSocket(6500);

        while (true) {
            Socket socketTCP = null;
            System.out.println("Waiting client");
            socketTCP = serverTCP.accept();
            if (socketTCP != null) {
                RunnableDemo runnable=new RunnableDemo(socketTCP);
                runnable.start();
            }
        }
    }
}