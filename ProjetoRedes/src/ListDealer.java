import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class ListDealer extends Main {
    private static HashSet<String> listaBranca = new HashSet<>();
    private static HashSet<String> listaNegra = new HashSet<>();
    private static HashSet<String> onlineUsers = new HashSet<>();

    public ListDealer() {
        readFrom("listaBranca");
        readFrom("listaNegra");
    }

    public void closeLists() throws IOException {
        writeTo("listaBranca");
        writeTo("listaNegra");
    }

    public void addOnlineUser(String ip) {
        onlineUsers.add(ip);
    }

    public ArrayList<String> getListaNegraToString() {
        ArrayList<String> retorno = new ArrayList<>();
        int i = 0;
        for (String user : listaNegra) {
            retorno.add(i + " - " + user);
            i++;
        }
        return retorno;
    }

    public ArrayList<String> getListaBrancaToString() {
        ArrayList<String> retorno = new ArrayList<>();
        int i = 0;
        for (String user : listaBranca) {
            retorno.add(i + " - " + user);
            i++;
        }
        return retorno;
    }

    public ArrayList<String> getListaOnlineUsers() {
        ArrayList<String> retorno = new ArrayList<>();
        int i = 0;
        for (String user : onlineUsers) {
            retorno.add(i + " - " + user);
            i++;
        }
        return retorno;
    }


    public boolean verify(String ip) {
        return listaNegra.contains(ip);
    }


    public void readFrom(String list) {
        try {
            File myObj = new File("ProjetoRedes/src/" + list + ".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if ("listaBranca".equals(list)) {
                    listaBranca.add(data);
                } else {
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

    public void writeTo(String list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("/ProjetoRedes/src/" + list + ".txt"));
        if ("listaBranca".equals(list)) {
            listaBranca
                    .forEach(y -> {
                        try {
                            writer.write(y);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } else {
            listaNegra
                    .forEach(y -> {
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
