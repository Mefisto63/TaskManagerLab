package taskManager.responses.type;

import taskManager.requests.type.GeneralRequest;

/**
 * Created by sergey on 19.12.14.
 */
public class ReplyResponse extends GeneralResponse {
    private String msg;

    public ReplyResponse(String msg) {
        super(3);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
