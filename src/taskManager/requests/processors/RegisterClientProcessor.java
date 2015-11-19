package taskManager.requests.processors;

import org.jdom2.JDOMException;
import taskManager.ClientsXML;
import taskManager.requests.type.RegistrationRequest;
import taskManager.responses.type.ErrorMessageResponse;
import taskManager.responses.type.GeneralResponse;
import taskManager.responses.type.ResultOfRegOrAuthResponse;

import java.io.IOException;

/**
 * Created by sergey on 19.12.14.
 */
public class RegisterClientProcessor implements RequestProcessor<RegistrationRequest> {
    @Override
    public GeneralResponse processTheRequest(RegistrationRequest request) {
        ClientsXML clientsXML = new ClientsXML();
        try {
            clientsXML.addClient(request.getLogin(),request.getPass());
            return new ResultOfRegOrAuthResponse("Регистрация прошла успешно", true);
        } catch (JDOMException e) {
            return new ErrorMessageResponse("Произошла ошибка на сервере. Повторите операцию");
        } catch (IOException e) {
            return new ErrorMessageResponse(e.getMessage());
        }
    }
}
