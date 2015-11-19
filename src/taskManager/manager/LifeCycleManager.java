package taskManager.manager;

import taskManager.Group;
import taskManager.TaskData;
import taskManager.User;
import taskManager.taskLog.Trigger;
import taskManager.tasks.Task;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by sergey on 13.02.15.
 */
public interface LifeCycleManager  extends Serializable{

    void addTask(Task task, long idUser, List<Long> groupList, List<Long> usersList) throws IOException;
    Set<Task> getTasksOfUser(long idUser) throws IOException;
    Task getTask(long id) throws IOException;
    Set<Task> getTasksOfAllGroups(long idUser) throws IOException;
    void updateTask(Task task) throws IOException;
    void deleteTask(long id) throws IOException;

    void addTrigger(Trigger trigger) throws IOException;
    ArrayList<Trigger> getAllTriggers() throws IOException;
    ArrayList<Trigger> getTrigger(long id) throws IOException;
    void updateTrigger(Trigger trigger) throws IOException;
    void deleteTrigger(long id) throws IOException;
    Date getNearestTime() throws IOException;

    User authorization(String login, String pass) throws IOException;
    boolean registration(String login, String pass, long idGroup);
    Map<Long, String> getGroups(long id);
    Map<Long, String> getChildGroups(long idUser);
    int countGroups();
    int countUsers();
    long getVersion(long id);
    Group getGroup(long idUser);
    List<Task> getTasksOfCurGroup(long id);
    List<Group> getChildGroup(long id);
    List<User> getUsersInGroup(long idGroup);
    int getLevel(long idGroup);
    void createGroup(long pid, String name);
    Map<Long, String> getUsers(long id);
    List<TaskData> getTasksData();
}
