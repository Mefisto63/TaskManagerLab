package taskManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergey on 19.12.14.
 */
public class ClientsMap {
    private static ClientsMap ourInstance = new ClientsMap();
    private Map<Integer, String> clientsMap = new HashMap<>();

    public static ClientsMap getInstance() {
        return ourInstance;
    }

    private ClientsMap() {
    }

    public Map<Integer, String> getClientsMap() {
        return clientsMap;
    }

    public void setClient(int port, String ip) {
        clientsMap.put(port, ip);
    }

    public void deleteClient(int port){
        clientsMap.remove(port);
    }
}
