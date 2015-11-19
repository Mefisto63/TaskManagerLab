package taskManager.manager.listManager;

import taskManager.Group;
import taskManager.TaskData;
import taskManager.User;
import taskManager.manager.LifeCycleManager;
import taskManager.taskLog.Trigger;
import taskManager.tasks.Task;

import java.io.IOException;
import java.util.*;

/**
 * Created by sergey on 14.02.15.
 */
public class ListBased implements LifeCycleManager {
    private TaskLog taskLog;
    private TriggerLog triggerLog;

    public ListBased(TaskLog taskLog, TriggerLog triggerLog) {
        this.taskLog = taskLog;
        this.triggerLog = triggerLog;
    }

    @Override
    public void addTask(Task task, long idUser, List<Long> groupList, List<Long> usersList) {
        taskLog.addTask(task);
    }

    @Override
    public Set<Task> getTasksOfUser(long idUser) {
        return new HashSet<>(taskLog.getTasks());
    }

    @Override
    public Task getTask(long id) {
        return taskLog.getTask(id);
    }

    @Override
    public Set<Task> getTasksOfAllGroups(long idUser) throws IOException {
        return null;
    }

    @Override
    public void updateTask(Task task) {
        taskLog.setTask(task);
    }

    @Override
    public void deleteTask(long id) {
        taskLog.deleteTask(id);
    }


    @Override
    public void addTrigger(Trigger trigger) {
        triggerLog.addTrigger(trigger);
    }

    @Override
    public ArrayList<Trigger> getAllTriggers() {
        return triggerLog.getAllTriggers();
    }

    @Override
    public ArrayList<Trigger> getTrigger(long id) {
        return triggerLog.getTrigger(id);
    }

    @Override
    public void updateTrigger(Trigger trigger) {
        triggerLog.setTrigger(trigger);
    }

    @Override
    public void deleteTrigger(long id) {
        triggerLog.deleteTrigger(id);
    }

    @Override
    public Date getNearestTime() {
        return triggerLog.getNearestTime();
    }

    @Override
    public User authorization(String login, String pass) throws IOException {
        return null;
    }

    @Override
    public boolean registration(String login, String pass, long idGroup) {
        return false;
    }

    @Override
    public Map<Long, String> getGroups(long id) {
        return null;
    }

    @Override
    public Map<Long, String> getChildGroups(long idUser) {
        return null;
    }

    @Override
    public int countGroups() {
        return 0;
    }

    @Override
    public int countUsers() {
        return 0;
    }

    @Override
    public long getVersion(long id) {
        return 0;
    }

    @Override
    public Group getGroup(long idUser) {
        return null;
    }

    @Override
    public List<Task> getTasksOfCurGroup(long id) {
        return null;
    }

    @Override
    public List<Group> getChildGroup(long id) {
        return null;
    }

    @Override
    public List<User> getUsersInGroup(long idGroup) {
        return null;
    }

    @Override
    public int getLevel(long idGroup) {
        return 0;
    }

    @Override
    public void createGroup(long pid, String name) {

    }

    @Override
    public Map<Long, String> getUsers(long id) {
        return null;
    }

    @Override
    public List<TaskData> getTasksData() {
        return null;
    }

}
