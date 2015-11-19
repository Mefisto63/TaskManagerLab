package taskManager.requests.type;

import taskManager.requests.factory.data.Data;

/**
 * Created by sergey on 09.12.14.
 */
public class CreateTaskRequest extends GeneralRequest {
    private Data request;
    public static final int TYPE = 4;

    public CreateTaskRequest(Data request) {
        super(TYPE);
        this.request = request;
    }

    public Data getRequest() {
        return request;
    }

}
