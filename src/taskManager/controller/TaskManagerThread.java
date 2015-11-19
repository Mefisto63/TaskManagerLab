package taskManager.controller;

import java.util.Date;

/**
 *  This class keep track of the triggered task
 */
public class TaskManagerThread extends Thread {

    private Date date;
    private MyObserver controller = MyObserver.getInstance();

    public TaskManagerThread(Date date) {
        this.date = date;
    }

    TaskManagerThread() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void run() {
        while (true) {
            if (date != null) {
                try {
                    Date taskDate = date;
                    long sleepTime = date.getTime() - System.currentTimeMillis();
                    if (sleepTime > 0)
                        synchronized (this){
                            this.wait(sleepTime);
                        }
                    if (taskDate == date)
                        controller.changeData(date);
                } catch (InterruptedException ex) {
                    System.out.println("Произошла программная ошибка");
                }
            } else {
                try {
                    synchronized (this){
                        this.wait();
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Произошла программная ошибка");
                }
            }
        }
    }

}
