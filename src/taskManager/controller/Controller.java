package taskManager.controller;

import taskManager.*;
import taskManager.idGenerator.IdGenerator;
import taskManager.manager.LifeCycleManager;
import taskManager.requests.factory.Factories;
import taskManager.requests.factory.data.Data;
import taskManager.requests.factory.data.NotificationTaskData;
import taskManager.requests.factory.data.TriggerData;
import taskManager.taskLog.Trigger;
import taskManager.tasks.Task;

import java.io.IOException;
import java.util.*;

/**
 * @author Сергей
 */

public class Controller implements Observer {

    private Factories factories;
    private LifeCycleManager lifeCycleManager;
    private TaskManagerThread tmThread;
    private Date nextTaskDate;
    private MyObserver myObserver = MyObserver.getInstance();
    private IdGenerator idGenerator;


    public Controller(Factories factories, IdGenerator idGenerator, LifeCycleManager lifeCycleManager) throws IOException {
        myObserver.addObserver(this);
        this.lifeCycleManager = lifeCycleManager;
        this.factories = factories;
        this.idGenerator = idGenerator;
        if (lifeCycleManager.getAllTriggers().size() > 0) {
            nextTaskDate = lifeCycleManager.getNearestTime();
            tmThread = new TaskManagerThread(nextTaskDate);
        } else {
            tmThread = new TaskManagerThread();
        }
        //tmThread.setDaemon(true);
        tmThread.start();
        //можно добавить notify() на случай первого запуска, когда нет задач, зачем крутиться в while в треде
    }

    public synchronized ArrayList<TaskData> getWorkingTasks() throws IOException {
        ArrayList<Trigger> triggers = lifeCycleManager.getAllTriggers();
        ArrayList<TaskData> Data = new ArrayList<>();
        for (Trigger trigger : triggers) {
            long id = trigger.getIdTask();
            Task task = lifeCycleManager.getTask(id);
            TaskData taskData = new TaskData(trigger.getDate(), task.getDescription(),
                    task.getName(), task.getId(), trigger.getId());
            Data.add(taskData);
        }
        return Data;
    }

    public synchronized ArrayList<TaskData> getUserTasks(Long idUser) throws IOException {
        ArrayList<TaskData> data = new ArrayList<>();
        for (Task task : lifeCycleManager.getTasksOfUser(idUser)) {
            Date date = null;
            for (Trigger trigger : lifeCycleManager.getAllTriggers()) {
                if (trigger.getIdTask() == task.getId())
                    date = trigger.getDate();
            }

            data.add(new TaskData(date, task.getDescription(), task.getName(), task.getId(), 0));
        }

        return data;
    }

    public synchronized ArrayList<TaskData> getTasksOfGroup(Long idUser) throws IOException {
        ArrayList<TaskData> data = new ArrayList<>();
        for (Task task : lifeCycleManager.getTasksOfAllGroups(idUser)) {
            Date date = null;
            for (Trigger trigger : lifeCycleManager.getAllTriggers()) {
                if (trigger.getIdTask() == task.getId())
                    date = trigger.getDate();
            }

            data.add(new TaskData(date, task.getDescription(), task.getName(), task.getId(), 0));
        }

        return data;
    }

//    public synchronized Map<Long, String> getGroups() throws IOException {
//        //return lifeCycleManager.getGroups();
//    }

    public synchronized Map<Long, String> getGroups(long idUser) throws IOException {
        return lifeCycleManager.getChildGroups(idUser);
    }

    public int countGroups() {
        return lifeCycleManager.countGroups();
    }

    /**
     * Отображение всех запланированных задач из журнала задача
     */
    public synchronized String displayTasks() throws IOException {
        ArrayList<Trigger> triggers = lifeCycleManager.getAllTriggers();
        int i = 1;
        String str = "";
        str += "-------------\n";
        for (Trigger trigger : triggers) {
            Task task = lifeCycleManager.getTask(trigger.getIdTask());
            str += i + ") " + task + "--" + trigger.getDate() + "\n";
            i++;
        }
        str += "-------------";
        return str;

       /* ArrayList<Task> tasks = tasklog.getWorkingTasks();
        int i = 1;
        String str = "";
        //System.out.println("-------------");
        str += "-------------\n";
        for (Task task : tasks) {
            //System.out.println(i + ") " + task);
            str += i + ") " + task + "\n";
            i++;
        }
        //System.out.println("-------------");
        str += "-------------";
        return str;*/
    }

    /**
     * Создание новой задачи
     */
    public synchronized Task createTask(Data request, List<Long> groupsId, List<Long> usersId) throws IOException {
        Task task = null;
        task = factories.getFactory(request.getType()).createTask(request);
        NotificationTaskData ntd = (NotificationTaskData) request;
        lifeCycleManager.addTask(task, ntd.getIdUser(), groupsId, usersId);
        return task;
    }

    public synchronized Trigger createTrigger(Date date, long idTask) throws IOException {
        Trigger trigger = new Trigger(date, idTask, idGenerator.generateId());
        lifeCycleManager.addTrigger(trigger);
        updateThreadDate();
        save();
        return trigger;
    }


    /**
     * Удаление задачи из журнала задач
     */
    public void deleteTrigger(long idTrigger) throws IOException, IndexOutOfBoundsException { // или операция по ID
        try {
            lifeCycleManager.deleteTrigger(idTrigger);
            updateThreadDate();
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("Такой задачи не существует");
        }
        save();
    }

    public void deleteTask(long idTask) throws IOException {
        lifeCycleManager.deleteTask(idTask);
        updateThreadDate();
        save();
    }

    /**
     * Изменение времени задачи из журнала задач
     */
    public void updateTrigger(Date date, long idTrigger) throws IOException { // или операция по ID
        try {
            for (Trigger trigger : lifeCycleManager.getTrigger(idTrigger)) {
                trigger.setDate(date);
                lifeCycleManager.updateTrigger(trigger);
            }

            updateThreadDate();
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("Такой задачи не существует");
            //System.out.println("Такой задачи не существует");
        }
        save();
    }

    public Task getTask(long id) throws IOException {
        return lifeCycleManager.getTask(id);
    }

    public ArrayList<Trigger> getTrigger(long id) throws IOException {
        return lifeCycleManager.getTrigger(id);
    }

    public User authorization(String login, String pass) throws IOException {
        return lifeCycleManager.authorization(login, pass);
    }

    public boolean registration(String login, String pass, long idGroup) throws IOException {
        return lifeCycleManager.registration(login, pass, idGroup);
    }

    public void update(Data data) throws IOException {
        if (data.getType() == 4) {
            TriggerData triggerData = (TriggerData) data;
            long idTrigger = triggerData.getIdTrigger();
            for (Trigger trigger : lifeCycleManager.getTrigger(idTrigger)) {
                trigger.setDate(((TriggerData) data).getDate());
                //Trigger curTrigger = factories.getFactory(data.getType()).updateData(data, trigger);
                lifeCycleManager.updateTrigger(trigger);
            }
        } else {
            NotificationTaskData ntd = (NotificationTaskData) data;
            Task task = lifeCycleManager.getTask(ntd.getIdTask());
            task = factories.getFactory(data.getType()).updateData(data, task);
            lifeCycleManager.updateTask(task);
        }
        save();
    }

    /**
     * Изменение описания задачи из журнала задач
     */
    public void updateTask(String description, long idTask) throws IOException { // или операция по ID
        try {
            Task task = lifeCycleManager.getTask(idTask);
            task.setDescription(description);
            lifeCycleManager.updateTask(task);
            updateThreadDate();
        } catch (IndexOutOfBoundsException e) {
            throw new IOException("Такой задачи не существует");
        }
        save();
    }

    /**
     * Изменение времени и описания задачи из журнала задач
     */
//    public void updateTask(Date date, String description, long idTask) throws IOException { // или операция по ID
//        try {
//            Trigger trigger = triggerLog.getTriggers(idTask);
//            trigger.setDate(date);
//            triggerLog.updateTrigger(trigger, idTask);
//            Task task = tasklog.getTask(idTask);
//            task.setDescription(description);
//            tasklog.updateTask(task, idTask);
//            updateThreadDate();
//        } catch (IndexOutOfBoundsException e){
//            throw new IOException("Такой задачи не существует");
//        }
//        save();
//    }

    /**
     * Вывод уведомления о выполнении задачи
     */
    public synchronized void showTask(Date date) throws IOException {
        String msg = null;
        Task task = null;
        Iterator iterator = lifeCycleManager.getTasksData().iterator();
        while (iterator.hasNext()) {
            TaskData data = (TaskData) iterator.next();
            if (data.getDate().equals(date)){
                TasksPool.getInstance().setTask(data);
                lifeCycleManager.deleteTrigger(data.getIdTrigger());
//                switch (data.getType()) {
//                    case Notification.TYPE:
//                        lifeCycleManager.deleteTrigger(data.getIdTrigger());
//                        break;
//                    case AlarmClock.TYPE:
//                        AlarmClock alarmClock = (AlarmClock) task;
//                        if (alarmClock.check()) {
//                            trigger.setDate(alarmClock.moveDate(trigger.getDate()));
//                            lifeCycleManager.updateTask(task);
//                            lifeCycleManager.updateTrigger(trigger);
//                        } else lifeCycleManager.deleteTrigger(trigger.getId());
//                        break;
//                    case BirthdayReminder.TYPE:
//                        BirthdayReminder reminder = (BirthdayReminder) task;
//                        trigger.setDate(reminder.moveDate(trigger.getDate()));
//                        break;
//                }
            }
//            Trigger trigger = (Trigger) iterator.next();
//            if (trigger.getDate().equals(date)) {
////                Iterator entries = ClientsMap.getInstance().getClientsMap().entrySet().iterator();
////                while (entries.hasNext()) {
////                /*for (Map.Entry<Integer, String> client : ClientsMap.getInstance().getClientsMap().entrySet())*/
////                    Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) entries.next();
////                    try {
////                        Transport transport = new Transport(entry.getValue(), entry.getKey());
//                task = lifeCycleManager.getTask(trigger.getIdTask());
//                TasksPool.getInstance().setTask(task);
////                        transport.sendTask(task);
////                    } catch (IOException e) {
////                        entries.remove();
////                        System.out.println("Данного клинента нет в сети");
////                    }
//                switch (task.getType()) {
//                    case Notification.TYPE:
//                        lifeCycleManager.deleteTrigger(trigger.getId());
//                        break;
//                    case AlarmClock.TYPE:
//                        AlarmClock alarmClock = (AlarmClock) task;
//                        if (alarmClock.check()) {
//                            trigger.setDate(alarmClock.moveDate(trigger.getDate()));
//                            lifeCycleManager.updateTask(task);
//                            lifeCycleManager.updateTrigger(trigger);
//                        } else lifeCycleManager.deleteTrigger(trigger.getId());
//                        break;
//                    case BirthdayReminder.TYPE:
//                        BirthdayReminder reminder = (BirthdayReminder) task;
//                        trigger.setDate(reminder.moveDate(trigger.getDate()));
//                        break;
//                }
////                }
//            }
        }
        updateThreadDate();
    }


    /**
     * Завершение работы менеджера исохранение журнала задач
     */
    public synchronized void save() throws IOException {
        //lifeCycleManager.saveLog();
    }


    /**
     * Обновление треда, если произошли какие-то изменения в журнале задач
     */
    private void updateThreadDate() throws IOException {
        if (lifeCycleManager.getAllTriggers().size() != 0) {
            if (nextTaskDate != lifeCycleManager.getNearestTime()) {
                nextTaskDate = lifeCycleManager.getNearestTime();
                tmThread.setDate(nextTaskDate);
                synchronized (tmThread) {
                    tmThread.notify();
                }
            }
        } else {
            tmThread.setDate(null);
            synchronized (tmThread) {
                tmThread.notify();
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Date date = (Date) o;
        try {
            showTask(date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortTasks(String str) {
        switch (str) {
            case "stbt":
                //tasklog.sortLog();
        }
    }

    public int getCountTasks() throws IOException {
        return lifeCycleManager.getTasksOfUser(0).size();
    }
}