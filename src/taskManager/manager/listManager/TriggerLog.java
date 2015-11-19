package taskManager.manager.listManager;

import org.jdom2.JDOMException;
import taskManager.manager.LifeCycleManager;
import taskManager.taskLog.LogXml;
import taskManager.taskLog.Trigger;
import taskManager.tasks.Task;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by sergey on 03.02.15.
 */
public class TriggerLog {
    private ArrayList<Trigger> triggers = new ArrayList<>();
    private LogXml logXml = new LogXml("Triggers.xml");
    public TriggerLog() {
        try {
            triggers = logXml.loadLogTriggers();
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addTrigger(Trigger trigger){
        triggers.add(trigger);
        try {
            logXml.addTrigger(trigger);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }

    public Date getNearestTime(){
        Date date = triggers.get(0).getDate();
        for (int i = 1; i < triggers.size(); i++){
            if (date.after(triggers.get(i).getDate()))
                date = triggers.get(i).getDate();
        }
        return date;
    }

    public ArrayList<Trigger> getAllTriggers() {
        return triggers;
    }

    public ArrayList<Trigger> getTrigger(long id){
        ArrayList<Trigger> listTriggers = new ArrayList<>();
        Iterator iterator = triggers.iterator();
        while (iterator.hasNext()){
            Trigger trigger = (Trigger) iterator.next();
            if (trigger.getId() == id)
                listTriggers.add(trigger);
        }
        return listTriggers;
    }

    public void setTrigger(Trigger trigger){
        Iterator iterator = triggers.iterator();
        while (iterator.hasNext()){
            Trigger curTrigger = (Trigger) iterator.next();
            if (curTrigger.getId() == trigger.getId())
                curTrigger = trigger;
        }
    }

    public void deleteTrigger(long id){
        Iterator iterator = triggers.iterator();
        while (iterator.hasNext()){
            Trigger trigger = (Trigger) iterator.next();
            if (trigger.getId() == id)
                iterator.remove();
        }
    }


    public synchronized void saveLog() throws IOException {
        logXml.saveLogTriggers(triggers);
    }
}
