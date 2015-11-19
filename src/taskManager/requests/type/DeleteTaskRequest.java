package taskManager.requests.type;

/**
 * Created by sergey on 10.12.14.
 */
public class DeleteTaskRequest extends ModifyTaskRequest {
    private String name;
    public static final int TYPE = 5;

    public DeleteTaskRequest(long id, String name) {
        super(TYPE, id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
