import java.net.*;
import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.io.IOException;


// Connectar ao porto 6500 de um servidor especifico,
// envia uma mensagem e imprime resultado,

public class Cliente1 {

    static InetAddress address;
    static byte[] buf = new byte[1024];
    private DatagramSocket socketUDP;


    public static String getInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }



    public Cliente1(String address) throws SocketException, UnknownHostException {

        this.address = InetAddress.getByName(address);
         socketUDP = new DatagramSocket(9031,this.address);
    }

    public static String getMenu() {

        return "MENU CLIENTE\n" +
                "0-Menu Inicial\n" +
                "1-Listar utilizadores online\n" +
                "2-Enviar mensagem a um utilizador\n" +
                "3-Enviar mensagem a todos os utilizadores\n" +
                "4-lista branca de utilizadores\n" +
                "5-lista negra de utilizadores\n" +
                "99-Sair";
    }
//

    // usage: java EchoClient <servidor> <mensagem>
    public static void main(String args[]) throws Exception {

        DatagramPacket packet = new DatagramPacket(buf, buf.length);//recebe menssagens UDP

        try {
            Socket socketTCP = new Socket("localhost", 6500);//Porto TCP
            PrintStream ps = new PrintStream(socketTCP.getOutputStream());//iniciar comunicaçoes tcp
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socketTCP.getInputStream()));//iniciar recebimento de comunicaçoes tcp

            address= socketTCP.getLocalAddress();

            System.out.println("socket=" + socketTCP);
            System.out.println(getMenu());//
            System.out.println("trying to send");
            switch (getInput()){
                case "0": {
                    ps.println("0");
                    System.out.println(br.readLine());
                    break;
                }
                case "1": {
                    ps.println("1");
                    System.out.println(br.readLine());
                    break;
                }
                case "2": {
                    ps.println("2");
                    break;
                }
                case "3": {
                    ps.println("3");
                    break;
                }
                case "4": {
                    ps.println("4");
                    break;
                }
                case "5": {
                    ps.println("5");
                    break;
                }
                case "99": {
                    ps.println("99");
                    break;
                }
            }
            //
            String received;
            InetAddress address = packet.getAddress();
            //int port = packet.getPort();
            //packet=new DatagramPacket(buf ,buf.length,address,9031);
            /*
            System.out.println("trying to recive");
            do {
                received
                        = new String(packet.getData(), 0, packet.getLength());
                received=received.trim();
            }while (received.isEmpty());

            System.out.println("recebido="+received);
            System.out.println("socket=" + socketTCP);
            System.out.println("server message=" + received);

             */
            /*
            if ("YOU ARE IN THE BLACK LIST".equals(received)) {
                socketTCP.close();
            }

             */


            System.out.println("socket=" + socketTCP);

        } catch (ConnectException e) {
            long timer = System.currentTimeMillis();
            while (System.currentTimeMillis() < timer + 15000) {//timer caso falhe ligação

            }
            System.out.println("Server Down try again later\nConection shutting down");
        }

    }

}
