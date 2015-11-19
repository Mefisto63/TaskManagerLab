package taskManager.tasks;

import java.util.Date;

/**
 *
 * @author Sergey
 */
public abstract class GeneralTask implements Task {
    private Date date;
    private String description;
    private long id;

    public GeneralTask(String description, long id) {
        this.description = description;
        this.id = id;
    }

//    @Override
//    public Date getDate() {
//        return date;
//    }
//
//    @Override
//    public void setDate(Date date) {
//        this.date = date;
//    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public long getId(){
        return id;
    }

}
