package taskManager.requests.processors;

import taskManager.requests.type.GeneralRequest;
import taskManager.responses.type.GeneralResponse;

/**
 * Created by sergey on 09.12.14.
 */
public interface RequestProcessor<T extends GeneralRequest> {
    GeneralResponse processTheRequest(T request);
}
