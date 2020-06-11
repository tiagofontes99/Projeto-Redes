import java.net.*;
import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.io.IOException;


// Connectar ao porto 6500 de um servidor especifico,
// envia uma mensagem e imprime resultado,

public class Cliente1 {


    public static String getInput() {
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }


    public Cliente1(String address) throws SocketException, UnknownHostException {
/*
        this.address = InetAddress.getByName(address);
         socketUDP = new DatagramSocket(9031,this.address);*/
    }

    public static String getMenu() {

        return "MENU CLIENTE\n" +
                "0-Menu Inicial\n" +
                "1-Listar utilizadores online\n" +
                "2-Enviar mensagem a um utilizador\n" +
                "3-Enviar mensagem a todos os utilizadores\n" +
                "4-lista branca de utilizadores\n" +
                "5-lista negra de utilizadores\n" +
                "99-Sair\n\n" +
                "Opção? ";
    }

    public static void main(String args[]) throws Exception {

        try {
            Socket socketTCP = new Socket("localhost", 6500);//Porto TCP
            PrintStream senderTCP = new PrintStream(socketTCP.getOutputStream());//iniciar comunicaçoes tcp
            BufferedReader reciverTCP = new BufferedReader(new InputStreamReader(socketTCP.getInputStream()));//iniciar recebimento de comunicaçoes tcp


            System.out.println(getMenu());//
            String input;
            do {
                input = getInput();
                switch (input) {
                    case "0": {
                        System.out.println(getMenu());
                        break;
                    }
                    case "1": {
                        senderTCP.println("1");
                        System.out.println(reciverTCP.readLine());
                        break;
                    }
                    case "2": {
                        senderTCP.println("2");
                        break;
                    }
                    case "3": {
                        senderTCP.println("3");
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
                System.out.println("Opção? ");
            } while (!"99".equals(input));

            System.out.println("socket=" + socketTCP);

        } catch (ConnectException e) {
            long timer = System.currentTimeMillis();
            while (System.currentTimeMillis() < timer + 15000) {//timer caso falhe ligação

            }
            System.out.println("Server Down try again later\nConection shutting down");
        }

    }

}
