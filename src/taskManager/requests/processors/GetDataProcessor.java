package taskManager.requests.processors;

import taskManager.controller.Controller;
import taskManager.requests.type.GetDataRequest;
import taskManager.responses.type.ErrorMessageResponse;
import taskManager.responses.type.GetDataResponse;
import taskManager.responses.type.GeneralResponse;

import java.io.IOException;

/**
 * Created by sergey on 13.12.14.
 */
public class GetDataProcessor implements RequestProcessor<GetDataRequest>{

    private Controller controller;

    public GetDataProcessor(Controller controller) {
        this.controller = controller;
    }

    @Override
    public GeneralResponse processTheRequest(GetDataRequest request) {
        long l = 1;
        try {
            switch (request.getName()) {
                case "Triggers":
                    return new GetDataResponse(controller.getWorkingTasks());
                case "Tasks":
                    return new GetDataResponse(controller.getUserTasks(l));
                default:
                    return new ErrorMessageResponse("Произошла ошибка на сервере");
            }
        } catch (IOException e) {
            return new ErrorMessageResponse("Недостаточно ресурсов на сервере");
        }

//        ArrayList<Task> tasks = new ArrayList<>();
//        tasks.add(new Notification("dasda",1));
//        tasks.add(new Notification("dasda1",2));
//        tasks.add(new Notification("dasda2",3));
//        return new GetAllTasksResponse(tasks);
    }
}
