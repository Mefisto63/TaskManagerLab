package taskManager.requests.type;

import java.util.Date;

/**
 * Created by sergey on 10.02.15.
 */
public class CreateTriggerRequest extends ModifyTaskRequest {
    private Date date;
    public static final int TYPE = 12;

    public CreateTriggerRequest(Date date, long idTask) {
        super(TYPE, idTask);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
