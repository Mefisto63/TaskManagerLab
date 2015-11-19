package taskManager;

import taskManager.requests.type.GeneralRequest;
import taskManager.responses.type.GeneralResponse;
import taskManager.tasks.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by sergey on 23.12.14.
 */
public class Transport {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Transport(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Transport(String address, Integer port) throws IOException {
        try {
            socket = new Socket(InetAddress.getByName(address),port);
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public GeneralRequest getRequest() throws IOException, ClassNotFoundException {
        try {
            return (GeneralRequest) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
           throw new IOException("Ошибка при чтении из сокета");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Ошибка при приведении типов");
        }
    }

    public void sendResponse(GeneralResponse response) throws IOException {
        try {
            out.writeObject(response);
        } catch (IOException e) {
            throw new IOException("Ошибка при чтении из сокета");
        } finally {
            socket.close();
        }
    }

    public void sendTask(Task task) throws IOException {
        try {
            out.writeObject(task);
        } catch (IOException e) {
            throw new IOException("Ошибка при чтении из сокета");
        } finally {
            socket.close();
        }
    }
}
