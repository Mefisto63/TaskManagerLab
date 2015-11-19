package taskManager.requests.processors;

import taskManager.controller.Controller;
import taskManager.requests.type.UpdateDataRequest;
import taskManager.responses.type.ErrorMessageResponse;
import taskManager.responses.type.GeneralResponse;
import taskManager.responses.type.ReplyResponse;

import java.io.IOException;

/**
 * Created by sergey on 13.02.15.
 */
public class UpdateDataProcessor implements RequestProcessor<UpdateDataRequest>{
    private Controller controller;

    public UpdateDataProcessor(Controller controller) {
        this.controller = controller;
    }

    @Override
    public GeneralResponse processTheRequest(UpdateDataRequest request) {
        try {
            controller.update(request.getData());
            return new ReplyResponse("");
        } catch (IOException e) {
            return new ErrorMessageResponse(e.getMessage());
        }
    }
}
