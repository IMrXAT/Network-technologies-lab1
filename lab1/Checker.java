import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Checker extends Thread{
    private ConcurrentHashMap<SocketAddress, Long> copies;


    Checker(ConcurrentHashMap<SocketAddress, Long> copies){
        this.copies = copies;
    }

    @Override
    public void run() {
        boolean flag = false;
        while (true){
            for (Map.Entry<SocketAddress, Long> copy: copies.entrySet()) {
                if (System.currentTimeMillis() - copy.getValue() > 5000){
                    flag = true;
                    copies.remove(copy.getKey());
                }
            }
            if (flag){
                print_copies();
                flag = false;
            }
        }
    }

    public void print_copies(){
        for (Map.Entry<SocketAddress, Long> copy: copies.entrySet()){
            System.out.println(copy.getKey());
        }
        System.out.println();
    }
}
