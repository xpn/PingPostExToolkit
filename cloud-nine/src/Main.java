import java.net.URI;
import com.xpnsec.cloudnine.WebSockClient;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        System.out.println("Cloud-Nine... PING Edition\n\tby @_xpn_\n");

        if (args.length != 1) {
            System.out.println("[!] Please pass token as argument\n");
            return;
        }

        String token = args[0];
        
        try {
            System.out.println("[*] Running...");
            WebSockClient.NewClient(new URI("wss://gateways-eu-central-1.pingone.eu"), token);
            WebSockClient.NewClient(new URI("wss://gateways-eu-west-1.pingone.eu"), token);
            Thread.sleep(511000);
        } catch (Exception e) {
            System.out.println("[!] Exception: " + e.getMessage());
        }
    }
}