package taskManager.requests.factory.data;

import java.util.Date;

/**
 * Created by sergey on 13.02.15.
 */
public class TriggerData extends Data{
    private Date date;
    private long idTrigger;

    public TriggerData(Date date) {
        super(4);
        this.date = date;
    }

    public TriggerData(Date date, int type) {
        super(type);
        this.date = date;
    }

    public TriggerData(Date date, int type, long idTrigger) {
        super(type);
        this.date = date;
        this.idTrigger = idTrigger;
    }

    public TriggerData(long idTrigger, Date date) {
        super(4);
        this.idTrigger = idTrigger;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public long getIdTrigger() {
        return idTrigger;
    }
}
