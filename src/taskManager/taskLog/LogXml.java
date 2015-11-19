package taskManager.taskLog;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import taskManager.tasks.AlarmClock;
import taskManager.tasks.BirthdayReminder;
import taskManager.tasks.Notification;
import taskManager.tasks.Task;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sergey on 04.01.15.
 */
public class LogXml implements Serializable{
    private String path;

    public LogXml(String path) {
        this.path = path;
    }

    public synchronized void saveLog(ArrayList<Task> tasks) throws IOException {
        Document document = new Document();
        Element rootElement = new Element("tasks");
        document.addContent(rootElement);
        for (Task task : tasks) {
            Element element = new Element("task");
            element.addContent(new Element("type").addContent(String.valueOf(task.getType())));
            element.addContent(new Element("id").addContent(String.valueOf(task.getId())));
            element.addContent(new Element("description").addContent(task.getDescription()));
            //element.addContent(new Element("date").addContent(task.getDate().toString()));
            if (task.getType() == 2) {
                AlarmClock alarm = (AlarmClock) task;
                element.addContent(new Element("interval").addContent(String.valueOf(alarm.getInterval())));
                element.addContent(new Element("repeatCount").addContent(String.valueOf(alarm.getRepeatCount())));
                element.addContent(new Element("currentRepeat").addContent(String.valueOf(alarm.getCurrentRepeat())));
            }
            rootElement.addContent(element);
        }
        Format format = Format.getPrettyFormat();
        XMLOutputter out = new XMLOutputter(format);
        out.output(document, new FileOutputStream(new File(path)));
    }

    public ArrayList<Task> loadLog() throws JDOMException, IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(path);
        List elements = document.getRootElement().getContent(new ElementFilter("task"));
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            int type = Integer.parseInt(element.getChildText("type"));
            long id = Long.parseLong(element.getChildText("id"));
            String desc = element.getChildText("description");
            String dateFormat = "E MMM dd HH:mm:ss z yyyy";
            SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

                //date = format.parse(element.getChildText("date"));
                switch (type) {
                    case 1:
                        tasks.add(new Notification(desc, id));
                        break;
                    case 2:
                        int interval = Integer.parseInt(element.getChildText("interval"));
                        int repeatCount = Integer.parseInt(element.getChildText("repeatCount"));
                        int currentRepeat = Integer.parseInt(element.getChildText("currentRepeat"));
                        AlarmClock task = new AlarmClock(desc, repeatCount, id, interval);
                        task.setCurrentRepeat(currentRepeat);
                        tasks.add(task);
                        break;
                    case 3:
                        tasks.add(new BirthdayReminder(desc, id));
                }

        }
        return tasks;
    }

    public synchronized void addTask(Task task) throws JDOMException, IOException {
        SAXBuilder parser = new SAXBuilder();
        Document document = parser.build(path);
        Element element = new Element("task");
        element.addContent(new Element("type").addContent(String.valueOf(task.getType())));
        element.addContent(new Element("id").addContent(String.valueOf(task.getId())));
        element.addContent(new Element("description").addContent(task.getDescription()));
        document.getRootElement().addContent(element);
        Format format = Format.getPrettyFormat();
        XMLOutputter out = new XMLOutputter(format);
        out.output(document, new FileOutputStream(new File(path)));
    }

    public synchronized Task getTask(long id) throws JDOMException, IOException {
        Task task = null;
        SAXBuilder parser = new SAXBuilder();
        Document document = parser.build(path);
        List elements = document.getRootElement().getContent(new ElementFilter("task"));
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()){
            Element  element = (Element) iterator.next();
            if (id == Long.parseLong(element.getChildText("id"))){
                int type = Integer.parseInt(element.getChildText("type"));
                String desc = element.getChildText("description");
                switch (type) {
                    case 1:
                        task = new Notification(desc, id);
                        break;
                    case 2:
                        int interval = Integer.parseInt(element.getChildText("interval"));
                        int repeatCount = Integer.parseInt(element.getChildText("repeatCount"));
                        int currentRepeat = Integer.parseInt(element.getChildText("currentRepeat"));
                        AlarmClock alarmClock = new AlarmClock(desc, repeatCount, id, interval);
                        alarmClock.setCurrentRepeat(currentRepeat);
                        task = alarmClock;
                        break;
                    case 3:
                        task = new BirthdayReminder(desc, id);
                }
            }
        }
        return task;
    }

    public synchronized void setTask(Task task, long id) throws JDOMException, IOException {
        SAXBuilder parser = new SAXBuilder();
        Document document = parser.build(path);
        List elements = document.getRootElement().getContent(new ElementFilter("task"));
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()){
            Element  element = (Element) iterator.next();
            if (id == Long.parseLong(element.getChildText("id"))){
                element.getChild("description").setText(task.getDescription());
                if (task.getType() == 2){
                    AlarmClock alarmClock = (AlarmClock) task;
                    element.getChild("currentRepeat").setText(String.valueOf(alarmClock.getCurrentRepeat()));
                }
            }
        }
        Format format = Format.getPrettyFormat();
        XMLOutputter out = new XMLOutputter(format);
        out.output(document, new FileOutputStream(new File(path)));
    }

    public synchronized void addTrigger(Trigger trigger) throws JDOMException, IOException {
        SAXBuilder parser = new SAXBuilder();
        Document document = parser.build(path);
        Element element = new Element("trigger");
        element.addContent(new Element("id").addContent(String.valueOf(trigger.getId())));
        element.addContent(new Element("date").addContent(String.valueOf(trigger.getDate())));
        element.addContent(new Element("idTask").addContent(String.valueOf(trigger.getIdTask())));
        document.getRootElement().addContent(element);
        Format format = Format.getPrettyFormat();
        XMLOutputter out = new XMLOutputter(format);
        out.output(document, new FileOutputStream(new File(path)));
    }

    public ArrayList<Trigger> loadLogTriggers() throws JDOMException, IOException {
        ArrayList<Trigger> triggers = new ArrayList<>();
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(path);
        List elements = document.getRootElement().getContent(new ElementFilter("trigger"));
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            long id = Long.parseLong(element.getChildText("id"));
            String dateFormat = "E MMM dd HH:mm:ss z yyyy";
            long idTask = Long.parseLong(element.getChildText("idTask"));
            SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
            Date date = null;
            try {
                date = format.parse(element.getChildText("date"));
                triggers.add(new Trigger(date, idTask, id));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return triggers;
    }

    public synchronized void saveLogTriggers(ArrayList<Trigger> triggers) throws IOException {
        Document document = new Document();
        Element rootElement = new Element("triggers");
        document.addContent(rootElement);
        for (Trigger trigger : triggers) {
            Element element = new Element("trigger");
            element.addContent(new Element("id").addContent(String.valueOf(trigger.getId())));
            element.addContent(new Element("date").addContent(trigger.getDate().toString()));
            element.addContent(new Element("idTask").addContent(String.valueOf(trigger.getIdTask())));
            rootElement.addContent(element);
        }
        Format format = Format.getPrettyFormat();
        XMLOutputter out = new XMLOutputter(format);
        out.output(document, new FileOutputStream(new File(path)));
    }

    public synchronized void setRepeat(int repeat, long id) throws JDOMException, IOException {
        SAXBuilder parser = new SAXBuilder();
        Document document = parser.build(path);
        List elements = document.getRootElement().getContent(new ElementFilter("task"));
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()){
            Element  element = (Element) iterator.next();
            if (id == Long.parseLong(element.getChildText("id"))){
                element.getChild("currentRepeat").setText(String.valueOf(repeat));
            }
        }
        Format format = Format.getPrettyFormat();
        XMLOutputter out = new XMLOutputter(format);
        out.output(document, new FileOutputStream(new File(path)));
    }

}
