package taskManager;

import taskManager.requests.RequestProcessorsMap;
import taskManager.requests.type.GeneralRequest;
import taskManager.responses.type.GeneralResponse;

import javax.xml.ws.Response;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by sergey on 13.12.14.
 */
public class RequestThread extends Thread{
    private Socket socket;
    private Transport transport;
    private RequestProcessorsMap processorsMap;

    public RequestThread(Socket socket) {
        this.socket = socket;
    }

    public RequestThread(Transport transport, RequestProcessorsMap processorsMap){
        this.transport = transport;
        this.processorsMap = processorsMap;
    }

    @Override
    public void run() {
        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//            outputStream.flush();
//            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
//
//            GeneralRequest request = (GeneralRequest) inputStream.readObject();
            GeneralRequest request = transport.getRequest();

            GeneralResponse response= processorsMap.getProcessor(request.getType()).processTheRequest(request);
            transport.sendResponse(response);

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
