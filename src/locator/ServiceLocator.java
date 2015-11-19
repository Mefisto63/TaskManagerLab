package locator;

import lockStrategy.LockStrategy;
import taskManager.controller.Controller;
import taskManager.manager.LifeCycleManager;
import taskManager.requests.RequestProcessorsMap;

/**
 * Created by sergey on 05.03.15.
 */
public class ServiceLocator {
    private Controller controller;
    private RequestProcessorsMap processorsMap;
    private LifeCycleManager lifeCycleManager;
    private LockStrategy lockStrategy;
    private static ServiceLocator ourInstance;

    public static ServiceLocator getInstance() {
        if (ourInstance == null){
            synchronized (ServiceLocator.class){
                if (ourInstance == null)
                    ourInstance = new ServiceLocator();
            }
        }
        return ourInstance;
    }

    protected ServiceLocator() {
    }

    public RequestProcessorsMap getProcessorsMap() {
        return processorsMap;
    }

    public Controller getController() {
        return controller;
    }

    public LifeCycleManager getLifeCycleManager() {
        return lifeCycleManager;
    }

    protected void setLockStrategy(LockStrategy lockStrategy) {
        this.lockStrategy = lockStrategy;
    }

    public LockStrategy getLockStrategy() {

        return lockStrategy;
    }

    protected void setProcessorsMap(RequestProcessorsMap processorsMap) {
        this.processorsMap = processorsMap;
    }

    protected void setController(Controller controller) {
        this.controller = controller;
    }

    protected void setLifeCycleManager(LifeCycleManager lifeCycleManager) {this.lifeCycleManager = lifeCycleManager;}

    protected boolean checkInstance(){
       return ourInstance == null;
    }
}
