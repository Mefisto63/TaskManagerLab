package taskManager.requests.processors;

import taskManager.controller.Controller;
import taskManager.requests.type.CreateTriggerRequest;
import taskManager.responses.type.ErrorMessageResponse;
import taskManager.responses.type.GeneralResponse;
import taskManager.responses.type.ReplyResponse;

import java.io.IOException;

/**
 * Created by sergey on 10.02.15.
 */
public class CreateTriggerProcessor implements RequestProcessor<CreateTriggerRequest> {

    private Controller controller;

    public CreateTriggerProcessor(Controller controller) {
        this.controller = controller;
    }

    @Override
    public GeneralResponse processTheRequest(CreateTriggerRequest request) {
        try {
            controller.createTrigger(request.getDate(), request.getId());
            return new ReplyResponse("Задача добавлена");
        } catch (IOException e) {
            return new ErrorMessageResponse("Произошла ошибка при создании задачи");
        }
    }
}
