package taskManager.tasks;

import java.util.Date;

/**
 * Класс, который описывает тип задачи "Напомнить"
 * @author Sergey
 */
public class Notification extends GeneralTask {
    public static final int TYPE = 1;
    public static final String NAME = "Notification";

    public Notification(String description, long id) {
        super(description, id);
    }

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public String getName() {
        return NAME;
    }


    @Override
    public String toString() {
        StringBuilder stBuilder = new StringBuilder();
        stBuilder.append(NAME).append(" - ").append(getDescription());
        return stBuilder.toString();
    }

}
