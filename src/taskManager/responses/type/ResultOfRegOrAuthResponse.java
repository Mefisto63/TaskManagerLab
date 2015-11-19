package taskManager.responses.type;

/**
 * Created by sergey on 30.01.15.
 */
public class ResultOfRegOrAuthResponse extends GeneralResponse{
    boolean flag;
    String msg;

    public ResultOfRegOrAuthResponse(String msg, boolean flag) {
        super(4);
        this.msg = msg;
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public String getMsg() {
        return msg;
    }

}
