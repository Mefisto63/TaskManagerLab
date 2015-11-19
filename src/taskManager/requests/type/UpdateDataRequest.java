package taskManager.requests.type;

import taskManager.requests.factory.data.Data;

/**
 * Created by sergey on 13.02.15.
 */
public class UpdateDataRequest extends ModifyTaskRequest {
    private Data data;
    public static final int TYPE = 13;

    public UpdateDataRequest(Data data) {
        super(TYPE);
        this.data = data;
    }

    public Data getData() {
        return data;
    }
}
