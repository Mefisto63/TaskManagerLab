package taskManager.taskLog;

import taskManager.tasks.Task;

import java.io.*;
import java.util.ArrayList;

/**
 * Используется для сохранение и загрузки
 * списка задач из файла.
 * @author Sergey
 */
public class LogIO {

    /** Позволяет загружать список задач из файла */
    public ArrayList<Task> loadLog(String filePath){
        ArrayList<Task> tasks = new ArrayList<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))){
            tasks =(ArrayList<Task>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return tasks;
        }
        return tasks;
    }

    /** Позволяет сохранять список задач в файл */
    public synchronized void  saveLog(ArrayList<Task> tasks, String filePath) throws IOException {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))){
            out.writeObject(tasks);
            out.flush();
            System.out.println("Список успешно сохранен");
        } catch (IOException e) {
            throw new IOException("Произошла ошибка при сохранении");
        }
    }
}
