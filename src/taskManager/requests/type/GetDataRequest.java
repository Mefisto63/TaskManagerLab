package taskManager.requests.type;

/**
 * Created by sergey on 13.12.14.
 */
public class GetDataRequest extends GeneralRequest {
    private String name;
    public static final int TYPE = 9;

    public GetDataRequest(String name) {
        super(TYPE);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
