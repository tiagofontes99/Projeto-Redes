import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
COMUNICAÇÕES DE CLIENTE PARA SERVIDOR É TCP
MENSSAGENS PARA OUTROS CLIENTES É EM UDP
 */

public class Main extends Thread {
    static byte[] buf = new byte[1024];
    static InetAddress address;
    static DatagramSocket socketUDP;
    static ListDealer dealer = new ListDealer();
    static ServerSocket serverTCP;

    static {
        try {
            serverTCP = new ServerSocket(6500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    static class ServerShutdown implements Runnable {
        ServerShutdown() {
            System.out.println("A ler a command line");
        }

        public void run() {

            while (true) {


                if ("99".equals(getInput())){
                    System.out.println("Shutting Server Down");
                    buf = "Shutting Server Down".getBytes();
                    for (String adress : dealer.getListaOnlineUsers()) {
                        try {
                            RunnableDemo.sendEcho("menssagem de [Servidor] to everyone  : ServerShutting Down in 10 seconds" , adress);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        long timer = System.currentTimeMillis();
                        while (System.currentTimeMillis() < timer + 10000);
                        try {
                            dealer.closeLists();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.exit(0);
                    }

                }
            }


        }

    }

    static class RunnableDemo implements Runnable {

        private Thread t;
        private Socket socketTCP;


        RunnableDemo(Socket socketTCP) {
            this.socketTCP = socketTCP;
        }

        public void start() {
            if (t == null) {
                t = new Thread(this, socketTCP.toString());
                t.start();
            }
        }


        public static void sendEcho(String msg, String address) throws IOException {
            InetAddress addressSend = InetAddress.getByName(address);
            buf = msg.getBytes();
            DatagramPacket packet = new DatagramPacket(
                    buf, buf.length, addressSend, 9031
            );
            socketUDP = new DatagramSocket();
            socketUDP.send(packet);


            packet = new DatagramPacket(
                    buf, buf.length, addressSend, 9032
            );
            socketUDP = new DatagramSocket();
            socketUDP.send(packet);
        }


        public void run() {
            try {
                String currentClientIp = socketTCP.getRemoteSocketAddress().toString();
                BufferedReader reciverTCP = new BufferedReader(new InputStreamReader(socketTCP.getInputStream()));//TCP port para receber menssagens
                PrintStream senderTCP = new PrintStream(socketTCP.getOutputStream());//TCP para enviar menssagens
                InetAddress ip = socketTCP.getLocalAddress();

                if (Main.dealer.verify(ip.toString())) {
                    System.out.println(ip.toString());
                    sendEcho("YOU ARE IN THE BLACKLIST\n Conection closing...\n", ip.toString());
                    socketTCP.close();
                    socketUDP.close();
                    return;
                } else {
                    Main.dealer.addOnlineUser(ip.toString());
                }
                String inputFromUser;
                do {
                    inputFromUser = reciverTCP.readLine();


                    try {

                        if (!inputFromUser.isEmpty()) {
                            switch (inputFromUser) {
                                case "1": {
                                    senderTCP.println(Main.dealer.getListaOnlineUsers());
                                    break;
                                }
                                case "2": {
                                    try {
                                        int user = 0;
                                        try {
                                            user = Integer.parseInt(reciverTCP.readLine());
                                        } catch (Exception e) {

                                        }
                                        String recived = reciverTCP.readLine();
                                        String ipToSend = "";
                                        int i = 0;
                                        for (String adress : dealer.getListaOnlineUsers()) {
                                            if (i == user) {
                                                ipToSend = adress;
                                            }
                                            i++;
                                        }
                                        if (!"".equals(ipToSend)) {
                                            sendEcho("menssagem de [" + currentClientIp + "] : " + recived, ipToSend);
                                        } else {
                                            sendEcho("Não foi possivel enviar menssagem a utilizador pretendido tente mais tarde", currentClientIp);
                                        }
                                    } catch (IOException e) {
                                        System.out.println(e);
                                    }
                                    break;
                                }
                                case "3": {
                                    try {
                                        String recived = reciverTCP.readLine();
                                        System.out.println(recived);
                                        buf = recived.getBytes();
                                        for (String adress : dealer.getListaOnlineUsers()) {
                                            sendEcho("menssagem de [" + currentClientIp + "] to everyone  : " + recived, adress);
                                        }
                                        senderTCP.println("Menssagem enviada para todos oa utilizadores");

                                    } catch (IOException e) {
                                        System.out.println(e);
                                    }
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
                                    senderTCP.println("A Sair, Cliente Desconectando...");
                                    socketTCP.close();
                                    return;
                                }
                            }
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Cliente Desconectou\nA fechar conexão");
                        return;
                    }
                } while (!"99".equals(inputFromUser));
            } catch (IOException e) {
                System.out.println(e + ":::Cliente na Black list conection closing...");
            }
        }

    }




    public static void main(String args[]) throws IOException {
        Thread serverPowerButton = new Thread(new ServerShutdown());
        serverPowerButton.start();
        while (true) {
            Socket socketTCP = null;
            socketTCP = serverTCP.accept();
            if (socketTCP != null) {
                RunnableDemo runnable = new RunnableDemo(socketTCP);
                runnable.start();
            }
        }
    }
}
//