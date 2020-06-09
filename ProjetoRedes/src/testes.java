/*public class testes {//inicio classe HelloRunnableThread

        public static void main(String args[]) {//inicio metodo main
            String threadName = Thread.currentThread().getName();//nome da thread
            System.out.format("Eu sou %s%n", threadName);
            Thread t = new Thread(new MsgThread("hello world"));//criar thread
            t.start();//executar thread
            System.out.println("Terminou "+threadName);
        }//fim metodo main

        public static class MsgThread implements Runnable{//inicio classe MsgThread
            private String msg1;
            public MsgThread(String msg){
                this.msg1=msg;
            }
            /* codigo a executar numa thread separada */
/*
            public void run(){//inicio metodo run
                String threadName = Thread.currentThread().getName();//nome da thread
                System.out.format("Eu sou %s: %s%n",threadName,msg1);
                System.out.println("Terminou "+threadName);
            }//fim run
        }//fim MsgThread
}//fim HelloRunnableThrea*/
import java.net.*;
import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

// Connectar ao porto 6500 de um servidor especifico,
// envia uma mensagem e imprime resultado,

public class testes {
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


