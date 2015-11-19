package taskManager.responses.type;

import java.io.Serializable;

/**
 * Created by sergey on 13.12.14.
 */
public class GeneralResponse implements Serializable {
    private int type;

    public GeneralResponse(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
