import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Sender extends Thread {
    private final InetAddress group;
    private final int port;
    private String message;
    Sender(InetAddress group, int port){
        this.group = group;
        this.port = port;
    }

    @Override
    public void run() {
        try(DatagramSocket socket = new DatagramSocket()){
            Scanner scanner = new Scanner(System.in);

            message = "msg";
            byte[] msg = message.getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
            socket.send(packet);
            //System.out.println("message sent");
            while (true){
            //while (!message.equals("quit")){
                //message = scanner.nextLine();
                //msg = message.getBytes();
                //packet = new DatagramPacket(msg, msg.length, group, port);
                sleep(5000);
                socket.send(packet);
                //System.out.println("message sent");
            }
            //System.out.println("stop sending messages");
        }
        catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
