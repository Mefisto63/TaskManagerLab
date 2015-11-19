package taskManager.taskLog;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sergey on 03.02.15.
 */
public class Trigger implements Serializable{
    private long id;
    private Date date;
    private long idTask;

    public Trigger(Date date, long idTask, long id) {
        this.id = id;
        this.date = date;
        this.idTask = idTask;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setIdTask(long idTask) {
        this.idTask = idTask;
    }

    public long getIdTask() {
        return idTask;
    }
}
