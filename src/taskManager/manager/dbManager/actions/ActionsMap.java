package taskManager.manager.dbManager.actions;

import taskManager.requests.factory.genericFactory.Factory;

import java.util.Map;

/**
 * this clas contains ...
 */
public class ActionsMap {
    private Map<Integer, TaskActions> actionsMap;

    public ActionsMap(Map<Integer,TaskActions> actionsMap) {
        this.actionsMap = actionsMap;

    }

    public TaskActions getAction(int type) {
        return actionsMap.get(type);
    }
}
