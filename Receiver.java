import javax.naming.ldap.SortKey;
import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Receiver extends Thread {
    private final int port;
    private final InetAddress group;
    private ConcurrentHashMap<SocketAddress, Long> copies;
    Receiver(InetAddress group, int port, ConcurrentHashMap<SocketAddress, Long> copies) {
        this.port = port;
        this.group = group;
        this.copies = copies;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1000];

        try (MulticastSocket socket = new MulticastSocket(port)){
            socket.joinGroup(new InetSocketAddress(group, port), NetworkInterface.getByInetAddress(group));
            while (true){
                //System.out.println("waiting for multicast message...");
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                if (!copies.containsKey(packet.getSocketAddress())){
                    copies.put(packet.getSocketAddress(), System.currentTimeMillis());
                    print_copies();
                }
                copies.put(packet.getSocketAddress(), System.currentTimeMillis());
                //System.out.println(packet.getSocketAddress());
                //String message = new String(packet.getData(), packet.getOffset(), packet.getLength());
                //System.out.println("received message is " + message);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print_copies(){
        for (Map.Entry<SocketAddress, Long> copy: copies.entrySet()){
            System.out.println(copy.getKey());
        }
        System.out.println();
    }

}

