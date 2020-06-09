import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

public class ListDealer extends Main{
    private static HashSet<String> listaBranca=new HashSet<>();
    private static HashSet<String> listaNegra=new HashSet<>();
    private static HashSet<String> onlineUsers=new HashSet<>();

    public ListDealer() {
        readFrom("listaBranca");
        readFrom("listaNegra");
    }
    public void closeLists() throws IOException {
        writeTo("listaBranca");
        writeTo("listaNegra");
    }

    public void addOnlineUser(String ip){
        onlineUsers.add(ip);
    }

    public HashSet<String> getListaBranca() {
        return listaBranca;
    }

    public HashSet<String> getListaNegra() {
        return listaNegra;
    }

    public String listaNegraToString(){
        return listaNegra.toString();
    }

    public String listaBrancaToString(){
        return listaBranca.toString();
    }

    public HashSet<String> getOnlineUsers() {
        return onlineUsers;
    }

    public boolean verify(String ip){
        return listaNegra.contains(ip);
    }

    public void trataIPs(String ip){
        if (!listaBranca.contains(ip)&&!listaNegra.contains(ip)){
            listaBranca.add(ip);
        }else if (listaBranca.contains(ip)&&listaNegra.contains(ip)){
            listaBranca.remove(ip);
        }
        System.out.println("white list"+listaBranca.toString());
        System.out.println("black list"+listaNegra.toString());
    }

    public void readFrom(String list)  {
        try {
            File myObj = new File("/home/tiago/IdeaProjects/ProjetoRedes/src/"+list+".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if ("listaBranca".equals(list)){
                    listaBranca.add(data);
                }else {
                    listaNegra.add(data);
                }
                System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public  void writeTo(String list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("/home/tiago/IdeaProjects/ProjetoRedes/src/"+list+".txt"));
        if ("listaBranca".equals(list)) {
            listaBranca
                    .forEach(y-> {
                        try {
                            writer.write(y);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }else{
            listaNegra
                    .forEach(y-> {
                        try {
                            writer.write(y);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
        writer.close();
    }
}
