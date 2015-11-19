package taskManager.requests.factory.data;

import taskManager.tasks.BirthdayReminder;

import java.util.Date;

/**
 * Created by sergey on 24.11.14.
 */
public class BirthdayTaskData extends NotificationTaskData {

    public BirthdayTaskData(String description, Date date, long idUser) {
        super(description, date, BirthdayReminder.TYPE, idUser);
    }

    public BirthdayTaskData(Date date, long idTrigger, long idTask, long idUser, String description) {
        super(date, BirthdayReminder.TYPE, idTrigger, idTask, idUser, description);
    }
}
