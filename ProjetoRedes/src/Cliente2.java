import java.net.*;
import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

// Connectar ao porto 6500 de um servidor especifico,
// envia uma mensagem e imprime resultado,

public class Cliente2 {
    public static String getInput(){
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
    public static String getIP(){
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            return socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        return "unable to get ip";
    }


    // usage: java EchoClient <servidor> <mensagem>
    public static void main(String args[]) throws Exception {

        Socket socket = new Socket("localhost", 6500);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintStream ps = new PrintStream(socket.getOutputStream());
        ps.println(getIP());
        String input=getInput();
        ps.println(input);

        System.out.println("Recebido : " + br.readLine());
        if ("close".equals(input)) {
            socket.close();
        }
    }



}
