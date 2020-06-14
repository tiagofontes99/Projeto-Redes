import java.net.*;
import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.io.IOException;


// Connectar ao porto 6500 de um servidor especifico,
// envia uma mensagem e imprime resultado,

public class Cliente1 {

    static String input1;
    static String input2;
    static String input3;

    static Socket socketTCP;//Porto TCP

    static PrintStream senderTCP;//iniciar comunicaçoes tcp

    static {
        try {
            senderTCP = new PrintStream(socketTCP.getOutputStream());
        } catch (IOException | NullPointerException e) {

        }
    }

    static BufferedReader reciverTCP;//iniciar recebimento de comunicaçoes tcp

    static {
        try {
            reciverTCP = new BufferedReader(new InputStreamReader(socketTCP.getInputStream()));
        } catch (IOException | NullPointerException e) {

        }
    }


    static byte[] buffer = new byte[225];//Vai necessitar uma String .getBytes
    static InetAddress address;

    static {
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    static DatagramPacket packet;
    static DatagramSocket datagramSocket;

    static {
        try {
            datagramSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    static DatagramSocket socketUDP;

    static {
        try {
            socketUDP = new DatagramSocket(9031);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


    public static String getInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    static class ConstantReciver implements Runnable {
        ConstantReciver() {
            System.out.println("Starting message reciver");
        }

        public void run() {


            while (true) {
                try {

                    String recived = reciveEcho();
                    System.out.println(recived);
                    if ("YOU ARE IN THE BLACKLIST\n Conection closing...\n".equals(recived.trim())) {
                        socketTCP.close();
                        socketUDP.close();
                        System.out.println(recived);
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }


            }


        }

    }


    public static String reciveEcho() throws IOException {
        DatagramPacket receivePacket = new DatagramPacket(buffer,
                buffer.length);
        socketUDP.receive(receivePacket);
        String recived = new String(receivePacket.getData(), 0, receivePacket.getLength());

        return recived;

    }

    public static String getMenu() {

        return "MENU CLIENTE\n" +
                "0-Menu Inicial\n" +
                "1-Listar utilizadores online\n" +
                "2-Enviar mensagem a um utilizador\n" +
                "3-Enviar mensagem a todos os utilizadores\n" +
                "4-lista branca de utilizadores\n" +
                "5-lista negra de utilizadores\n" +
                "99-Sair\n\n";
    }

    public static void main(String args[]) throws Exception {

        Thread reciver = new Thread(new ConstantReciver());
        reciver.start();

        try {
            Socket socketTCP = new Socket("localhost", 6500);//Porto TCP
            PrintStream senderTCP = new PrintStream(socketTCP.getOutputStream());//iniciar comunicaçoes tcp
            BufferedReader reciverTCP = new BufferedReader(new InputStreamReader(socketTCP.getInputStream()));//iniciar recebimento de comunicaçoes tcp


            System.out.print(getMenu());//
            String input;
            do {
                System.out.println("Opção? ");
                input = getInput();
                switch (input) {
                    case "0": {
                        System.out.println(getMenu());
                        break;
                    }
                    case "1": {
                        input1="1";
                        senderTCP.println("1");
                        System.out.println(reciverTCP.readLine());
                        break;
                    }
                    case "2": {
                        input1="2";
                        senderTCP.println("2");
                        System.out.print("Utilizador?");
                        input2=getInput();
                        senderTCP.println(input2);
                        System.out.print("Menssagem?");
                        input3=getInput();
                        senderTCP.println(input3);
                        System.out.println(reciverTCP.readLine());
                        break;
                    }
                    case "3": {
                        input1="3";
                        senderTCP.println("3");
                        System.out.print("Menssagem?");
                        input2=getInput();
                        senderTCP.println(input2);
                        System.out.println(reciverTCP.readLine());
                        break;

                    }
                    case "4": {
                        senderTCP.println("4");
                        System.out.println(reciverTCP.readLine());
                        break;
                    }
                    case "5": {
                        senderTCP.println("5");
                        System.out.println(reciverTCP.readLine());
                        break;
                    }
                    case "99": {
                        senderTCP.println("99");
                        System.out.println(reciverTCP.readLine());
                        socketTCP.close();
                        break;
                    }
                }
                try {
                    socketTCP = new Socket("localhost", 6500);//Porto TCP
                } catch (ConnectException a) {
                    System.out.println("We found a problem in the conection, trying again...");
                    long timer = System.currentTimeMillis();
                    while (System.currentTimeMillis() < timer + 10000) ;
                    socketTCP = new Socket("localhost", 6500);//Porto TCP
                    senderTCP = new PrintStream(socketTCP.getOutputStream());//iniciar comunicaçoes tcp
                    reciverTCP = new BufferedReader(new InputStreamReader(socketTCP.getInputStream()));//iniciar recebimento de comunicaçoes tcp

                }

            } while (!"99".equals(input));

            System.exit(0);
        } catch (ConnectException e) {
            System.out.println("Unable to Conect to Server try again later...");
            System.exit(0);
        }

    }

}
