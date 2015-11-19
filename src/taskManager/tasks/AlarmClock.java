package taskManager.tasks;

import taskManager.taskLog.Trigger;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Класс, который описывает тип задачи "Будильник"
 *@author Sergey
 */
public class AlarmClock extends GeneralTask {
    public static final int TYPE = 2;
    private int repeatCount;
    private int interval;
    private int currentRepeat = 0;
    public static final String NAME = "Alarm Clock";

    public AlarmClock(String description, int repeatCount, long id, int interval) {
        super(description, id);
        this.repeatCount = repeatCount;
        this.interval = interval;
    }

    public boolean check(){
        return repeatCount-1 > currentRepeat;
    }

    public Date moveDate(Date date){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, interval);
        currentRepeat++;
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

    public int getInterval() {
        return interval;
    }

    public int getCurrentRepeat() {
        return currentRepeat;
    }

    public void setCurrentRepeat(int currentRepeat) {
        this.currentRepeat = currentRepeat;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
