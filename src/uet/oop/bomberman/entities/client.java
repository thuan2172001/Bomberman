package uet.oop.bomberman.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //get the localhost IP address
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = new Socket(host.getHostAddress(), 9876);

        //create input/output stream
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        Scanner sc = new Scanner(System.in);
        while(true){
            //write to socket
            String mes = sc.nextLine();
            oos.writeObject(mes);

            if(mes.equals("QUIT")){
                String responce = (String) ois.readObject();
                System.out.println(responce);
                break;
            }

            //read the server response message
            String responce = (String) ois.readObject();
            System.out.println(responce);
        }
        //close resources
        ois.close();
        oos.close();
        socket.close();
    }
}
