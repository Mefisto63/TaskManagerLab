package taskManager.requests.factory.data;

import taskManager.tasks.AlarmClock;

import java.util.Date;

/**
 * Created by sergey on 24.11.14.
 */
public class AlarmTaskData extends NotificationTaskData {
    private int repeat;
    private int interval;

    public AlarmTaskData(String description, Date date, int repeat, int interval, long idUser) {
        super(description, date, AlarmClock.TYPE, idUser);
        this.repeat = repeat;
        this.interval = interval;
    }

    public AlarmTaskData(Date date, long idTrigger, long idTask, long idUser, String description, int repeat, int interval) {
        super(date, AlarmClock.TYPE, idTrigger, idTask, idUser, description);
        this.repeat = repeat;
        this.interval = interval;
    }

    public int getRepeat() {
        return repeat;
    }

    public int getInterval() {
        return interval;
    }
}
