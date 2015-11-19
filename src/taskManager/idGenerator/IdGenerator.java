package taskManager.idGenerator;

import java.io.Serializable;

/**
 * Created by sergey on 08.02.15.
 */
public interface IdGenerator extends Serializable{

    public long generateId();
}
