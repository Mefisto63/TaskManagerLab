package taskManager.requests.type;

/**
 * Created by sergey on 19.12.14.
 */
public class RegistrationRequest extends GeneralRequest {
    private String login;
    private String pass;
    public static final int TYPE = 11;

    public RegistrationRequest(String login, String pass) {
        super(TYPE);
        this.login = login;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }
}
