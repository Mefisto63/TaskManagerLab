package taskManager.manager.listManager;

import org.jdom2.JDOMException;
import taskManager.manager.LifeCycleManager;
import taskManager.taskLog.LogXml;
import taskManager.taskLog.Trigger;
import taskManager.tasks.Task;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Используется для хранения списка задач и
 * осуществляет работу с ним.
 * @author Sergey
 */
public class TaskLog {
    private ArrayList<Task> log = new ArrayList<>();
    private String filePath;
    private LogXml xml;

    public TaskLog(String filePath) throws IOException {
        this.filePath = filePath;
        xml = new LogXml(filePath);
        try {
            log = xml.loadLog();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        //sortLog();
    }

    /** Добавление новой задачи в список */
    public void addTask(Task task){
        log.add(task);
        //sortLog();
    }

    /** Получение списка задач */
    public ArrayList<Task> getTasks(){
        try {
            log = xml.loadLog();
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        return log;

    }

    /** Получение задачи по заданному индексу */
//    public Task getTask(int index){
//        return log.get(index);
//    }

    public Task getTask(long id){
        try {
            return xml.getTask(id);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /** Замена старой задачи на новую по индексу */
    public void setTask(Task task){
//        log.set(index, task);
        try {
            xml.setTask(task, task.getId());
            log = xml.loadLog();
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
        //sortLog();
    }


    /** Удаление задачи */
    public void deleteTask(long id){
        Iterator iterator = log.iterator();
        while (iterator.hasNext()){
            Task task = (Task) iterator.next();
            if (task.getId() == id)
                iterator.remove();
        }
        //sortLog();
    }

//    /** Получение времени у задачи, которая раньше всех запустится */
//    public Date getNearestTime(){
//        Date date = log.get(0).getDate();
//        for (int i = 1; i < log.size(); i++){
//            if (date.after(log.get(i).getDate()))
//                date = log.get(i).getDate();
//        }
//        //return Collections.min(log, (Task task1, Task task2) -> task1.getDate().compareTo(task2.getDate()));
//        return date;
//    }

    /**
     * Сохранение списка задач в файл
     */
    public synchronized void saveLog() throws IOException {
        //new LogIO().saveLog(log, filePath);
        new LogXml("Out.xml").saveLog(log);
    }

//    /** Сортировка списка задача по времени по возрастанию */
//    public void sortLog(){
//        Collections.sort(log, new Comparator<Task>(){
//            @Override
//            public int compare(Task task, Task task2) {
//                return task.getDate().compareTo(task2.getDate());
//            }
//        });
//    }
}
