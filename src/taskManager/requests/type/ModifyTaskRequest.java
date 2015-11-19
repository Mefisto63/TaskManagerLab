package taskManager.requests.type;

/**
 * Created by sergey on 13.12.14.
 */
public class ModifyTaskRequest extends GeneralRequest {
    private long id;

    public ModifyTaskRequest(int type, long id) {
        super(type);
        this.id = id;
    }

    public ModifyTaskRequest(int type) {
        super(type);
    }

    public long getId() {
        return id;
    }

}
