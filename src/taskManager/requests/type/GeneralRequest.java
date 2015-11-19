package taskManager.requests.type;

import java.io.Serializable;

/**
 * Created by sergey on 09.12.14.
 */
public class GeneralRequest implements Serializable {
    private int type;

    public GeneralRequest(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
