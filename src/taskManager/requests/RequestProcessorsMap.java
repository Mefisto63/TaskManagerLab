package taskManager.requests;

import taskManager.requests.processors.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by sergey on 10.12.14.
 */
public class RequestProcessorsMap {
    private Map<Integer, RequestProcessor> processorMap;

    public RequestProcessorsMap(Map<Integer, RequestProcessor> processorMap) {
        this.processorMap = processorMap;
    }

    public RequestProcessor getProcessor(int type){
        return processorMap.get(type);
    }

    public void addProcessor(int type,RequestProcessor requestProcessor){
        processorMap.put(type, requestProcessor);
    }
}
