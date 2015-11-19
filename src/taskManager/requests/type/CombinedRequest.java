package taskManager.requests.type;

import java.util.List;

/**
 * Created by sergey on 27.02.15.
 */
public class CombinedRequest extends GeneralRequest {
    private List<GeneralRequest> requests;
    public static final int TYPE = 15;

    public CombinedRequest(List<GeneralRequest> requests) {
        super(TYPE);
        this.requests = requests;
    }

    public List<GeneralRequest> getRequests() {
        return requests;
    }
}
