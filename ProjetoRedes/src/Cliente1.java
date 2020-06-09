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


    // usage: java EchoClient <servidor> <mensagem>
    public static void main(String args[]) throws Exception {

        DatagramPacket packet = new DatagramPacket(buf, buf.length);//recebe menssagens UDP

        try {
            Socket socketTCP = new Socket("localhost", 6500);//Porto TCP
            PrintStream ps = new PrintStream(socketTCP.getOutputStream());

            address= socketTCP.getLocalAddress();

            System.out.println("socket=" + socketTCP);
            System.out.println(getMenu());//
            System.out.println("trying to recive msg");
            String received
                    = new String(packet.getData(), 0, packet.getLength());
            received=received.trim();
            System.out.println("recebido="+received);
            System.out.println("socket=" + socketTCP);
            System.out.println("server message=" + received);
            if ("YOU ARE IN THE BLACK LIST".equals(received)) {
                socketTCP.close();
            }
            try {
                System.out.println("trying to send message");
                Cliente1 client = new Cliente1("localhost");

            } catch (IOException e) {
                e.printStackTrace();
            }//inicialização de output para servidor


            /*

             * */
            System.out.println("socket=" + socketTCP);

        } catch (ConnectException e) {
            long timer = System.currentTimeMillis();
            while (System.currentTimeMillis() < timer + 15000) {

            }
            System.out.println("Server Down try again later\nConection shutting down");
        }

    }

}
