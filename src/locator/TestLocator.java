package locator;

import lockStrategy.LockStrategy;
import taskManager.controller.Controller;
import taskManager.manager.LifeCycleManager;
import taskManager.requests.RequestProcessorsMap;

/**
 * Created by sergey on 07.03.15.
 */
public class TestLocator extends ServiceLocator {

    public TestLocator(Controller controller, RequestProcessorsMap map, LifeCycleManager lifeCycleManager, LockStrategy lockStrategy) {
        if (checkInstance()) {
            ServiceLocator.getInstance().setController(controller);
            ServiceLocator.getInstance().setProcessorsMap(map);
            ServiceLocator.getInstance().setLifeCycleManager(lifeCycleManager);
            ServiceLocator.getInstance().setLockStrategy(lockStrategy);
        }
    }
}
