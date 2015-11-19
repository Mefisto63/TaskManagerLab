package taskManager.tasks;

import taskManager.taskLog.Trigger;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Класс, который описывает тип задачи "Напимнить о Дне Рождения"
 * @author Sergey
 */
public class BirthdayReminder extends GeneralTask {
    public static final int TYPE = 3;
    public static final String NAME = "Birthday Reminder";

    public BirthdayReminder(String description, long id) {
        super(description, id);
    }

    public Date moveDate(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
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
