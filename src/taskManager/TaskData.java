package taskManager;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sergey on 10.02.15.
 */
public class TaskData implements Serializable {
    private Date date;
    private String description;
    private String name;
    private long idTask;
    private long idTrigger;
    private long idUser = 0;
    private long idGroup = 0;
    private int type;

    public TaskData(Date date, String description, String name, long idTask, long idTrigger) {
        this.date = date;
        this.description = description;
        this.idTask = idTask;
        this.idTrigger = idTrigger;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public long getIdTask() {
        return idTask;
    }

    public long getIdTrigger() {
        return idTrigger;
    }

    public String getName() {
        return name;
    }

    public long getIdUser() {
        return idUser;
    }

    public long getIdGroup() {
        return idGroup;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public void setIdGroup(long idGroup) {
        this.idGroup = idGroup;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {

        return type;
    }
}
