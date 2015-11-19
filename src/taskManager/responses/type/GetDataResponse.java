package taskManager.responses.type;

import taskManager.TaskData;
import taskManager.tasks.Notification;
import taskManager.tasks.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergey on 13.12.14.
 */
public class GetDataResponse extends GeneralResponse{
    private ArrayList<TaskData> tasks;

    public GetDataResponse(ArrayList<TaskData> tasks) {
        super(1);
        this.tasks = tasks;
    }

    public ArrayList<TaskData> getTasks() {
        return tasks;
    }
}
