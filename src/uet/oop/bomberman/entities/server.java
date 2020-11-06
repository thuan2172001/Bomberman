package uet.oop.bomberman.entities;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;


public class server {
    private static ServerSocket server;
    private static int port = 9876;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        server = new ServerSocket(port);
        Socket socket = null;
        while (true) {
            //Wait for client connect
            System.out.println("Waiting for client connect...");
            socket = server.accept();

            //create input/out stream
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            int status = 0;
            while (true) {
                //String str = scan.nextLine();
                //System.out.println(str);
                //received messages
                String mesRecv = (String) ois.readObject();
                System.out.println("A: " + mesRecv);
                String str = scan.nextLine();
                oos.writeObject("B: " + str);

//                //check massages
//                if (mesRecv.equals("QUIT") ) {
//                    System.out.println("close socket");
//                    oos.writeObject("500 bytes");
//                    socket.close();
//                    break;
//                }
//                if (status == 0) {
//                    if(mesRecv.equals("HELLO Server")){
//                        oos.writeObject("200 Hello Client");
//                        status = 1;
//                    } else{
//                        oos.writeObject("400 bad request");
//                    }
//                    continue;
//                }
//                if (status == 1) {
//                    if(mesRecv.equals("USER INFO")){
//                        oos.writeObject("210 OK");
//                        status = 2;
//                    } else{
//                        oos.writeObject("400 bad request");
//                    }
//                    continue;
            }

            /**
             * ubuntu.
             */

//                if (status == 2) {
//                    try {
//                        Object obj = JSONValue.parse(mesRecv);
//                        JSONObject jsonObject = (JSONObject) obj;
//                        String name = (String) jsonObject.get("User name");
//                        String age_str = (String) jsonObject.get("User age");
//                        int age = Integer.parseInt(age_str);
//
//                        System.out.println("User name: " + name);
//                        System.out.println("User age: " + age);
//                        oos.writeObject("211 User Info OK");
//                    } catch (Exception e) {
//                        System.out.println("Eror: " + e);
//                        oos.writeObject("400 User Info Error");
//                    }
//                    status = 1;
//                    continue;
//                }

            //           }
            //oos.close();
            //oos.close();
        }
    }
}
