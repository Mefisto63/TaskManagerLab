package taskManager.responses.type;

/**
 * Created by sergey on 13.12.14.
 */
public class ErrorMessageResponse extends GeneralResponse {
    private String msg;

    public ErrorMessageResponse(String msg) {
        super(2);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
