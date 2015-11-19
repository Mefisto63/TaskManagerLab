package taskManager.requests.factory.data;

import java.io.Serializable;

/**
 * Created by sergey on 13.02.15.
 */
public class Data implements Serializable {
    private int type;

    public Data(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
