package taskManager.controller;

import java.util.Observable;

/**
 * Created by sergey on 16.11.14.
 */
public class MyObserver extends Observable {

    private static MyObserver instance = new MyObserver();

    private MyObserver() {
        super();
    }

    public static MyObserver getInstance() {
        return instance;
    }

    void changeData(Object data) {
        setChanged();
        notifyObservers(data);
    }
}
