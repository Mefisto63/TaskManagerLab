package taskManager.tasks;

import java.io.Serializable;
import java.util.Date;

/**
 * Описывает основные методы для работы с задачей
 * @author Sergey
 */
public interface Task extends Serializable {

//    /** Позволяет получить дату */
//    public Date getDate();
//
//    /** Позволяет установить дату */
//    public void setDate(Date date);

    /** Позволяет получить описание задачи */
    public String getDescription();

    /** Позволяет установить описание задачи*/
    public void setDescription(String description);

    /** Позволяет получить тип задачи */
    public int getType();

    public String getName();

    /** Позволяет получить ID */
    public long getId();
}
