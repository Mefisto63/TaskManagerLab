package taskManager.requests.processors;

import org.jdom2.JDOMException;
import taskManager.ClientsMap;
import taskManager.ClientsXML;
import taskManager.requests.type.LoginRequest;
import taskManager.responses.type.ErrorMessageResponse;
import taskManager.responses.type.GeneralResponse;
import taskManager.responses.type.ResultOfRegOrAuthResponse;

import java.io.IOException;

/**
 * Created by sergey on 30.01.15.
 */
public class LoginRequestProcessor implements RequestProcessor<LoginRequest>{
    @Override
    public GeneralResponse processTheRequest(LoginRequest request) {
        ClientsXML clientsXML = new ClientsXML();
        try {
            if (clientsXML.checkClient(request.getLogin(), request.getPass())) {
                ClientsMap.getInstance().setClient(request.getPort(), request.getIp());
                return new ResultOfRegOrAuthResponse("Вы залогинились", true);
            } else
                return new ErrorMessageResponse("Неверно введен логин и/или пароль");
        } catch (JDOMException | IOException e) {
            return new ErrorMessageResponse(e.getMessage());
        }
    }
}
