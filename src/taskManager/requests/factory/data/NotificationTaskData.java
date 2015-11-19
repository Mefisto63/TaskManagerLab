package taskManager.requests.factory.data;

import taskManager.tasks.Notification;

import java.util.Date;

/**
 * Created by sergey on 24.11.14.
 */
public class NotificationTaskData extends TriggerData{
    private String description;
    private long idTask;
    private long idUser;

    public NotificationTaskData(String description, Date date, long idUser) {
        super(date, Notification.TYPE);
        this.description = description;
        this.idUser = idUser;
    }


    public NotificationTaskData(String description, Date date, int type, long idUser) {
        super(date, type);
        this.description = description;
        this.idUser = idUser;
    }

    public NotificationTaskData(Date date, int type, long idTrigger, long idTask, long idUser, String description) {
        super(date, type, idTrigger);
        this.idTask = idTask;
        this.idUser = idUser;
        this.description = description;
    }

    public NotificationTaskData(Date date, long idTrigger, long idTask, String description) {
        super(date, Notification.TYPE, idTrigger);
        this.idTask = idTask;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public long getIdTask() {
        return idTask;
    }

    public long getIdUser() {
        return idUser;
    }
}
