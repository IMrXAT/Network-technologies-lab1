import java.net.InetAddress;
import java.io.*;
public class Main {

    public static void main(String[] args) throws IOException{
/*
        if (args.length != 2){
            System.out.println("not enough arguments");
            return;
        }
        String multicastIP = args[0];
        int port = Integer.parseInt(args[1]);
*/
        String multicastIP = "230.0.0.1";
        int port = 8888;
        InetAddress group = InetAddress.getByName(multicastIP);

        Sender sender = new Sender(group, port);
        sender.start();

        Receiver receiver = new Receiver(group, port);
        receiver.start();
    }
}