package taskManager.requests.type;

/**
 * Created by sergey on 30.01.15.
 */
public class LoginRequest extends GeneralRequest{
    private int port;
    private String login;
    private String pass;
    private String ip;
    public static final int TYPE = 10;

    public LoginRequest(int port, String ip, String login, String pass) {
        super(TYPE);
        this.port = port;
        this.ip = ip;
        this.login = login;
        this.pass = pass;

    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
