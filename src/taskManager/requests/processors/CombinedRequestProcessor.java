package taskManager.requests.processors;

import taskManager.controller.Controller;
import taskManager.requests.RequestProcessorsMap;
import taskManager.requests.type.CombinedRequest;
import taskManager.requests.type.GeneralRequest;
import taskManager.responses.type.ErrorMessageResponse;
import taskManager.responses.type.GeneralResponse;
import taskManager.responses.type.GetDataResponse;

import java.io.IOException;

/**
 * Created by sergey on 27.02.15.
 */
public class CombinedRequestProcessor implements RequestProcessor<CombinedRequest> {

    private RequestProcessorsMap processorsMap;
    private Controller controller;

    public CombinedRequestProcessor(RequestProcessorsMap processorsMap, Controller controller) {
        this.processorsMap = processorsMap;
        this.controller = controller;
    }


    @Override
    public GeneralResponse processTheRequest(CombinedRequest request) {
        for (GeneralRequest generalRequest : request.getRequests()) {
            RequestProcessor requestProcessor = processorsMap.getProcessor(generalRequest.getType());
            requestProcessor.processTheRequest(generalRequest);
        }

        try {
            return new GetDataResponse(controller.getWorkingTasks());
        } catch (IOException e) {
            return new ErrorMessageResponse("Произошла ошибка в обработке запроса");
        }
    }
}
