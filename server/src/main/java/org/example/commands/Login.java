package org.example.commands;

import org.example.exceptions.WrongPasswordException;
import org.example.main.*;
import org.example.main.request.AuthenticateRequest;
import org.example.main.responce.AuthenticateResponse;
import org.example.main.responce.EmptyResponse;
import org.example.main.responce.ErrorResponse;
import org.example.main.responce.Response;
import org.example.utility.Callable;

import java.sql.SQLException;

import static org.example.main.App.collectionManager;

public class Login extends Command implements Callable {
    public Login() {
        super();
        this.commandType = CommandType.VALUE_ARGUMENTED;
    }

    public final String name = "login";

    public static Login staticFactory(String[] args, String value) {
        return new Login();
    }

    public Response calling(String[] args, String v) {
        AuthenticateRequest request = new AuthenticateRequest(args[0]); // Предполагая, что args содержит user, login и password
        User user = request.getUser();
        String login = user.getLogin();
        String password = user.getPassword();

        Response response;

        try {
            if (collectionManager.getConnection().authenticateUser(login, password)) {
                response = new AuthenticateResponse(request.getCommandToEx().getName(), "Успешно", user, true);
            } else {
                response = new EmptyResponse();
            }
        } catch (SQLException e) {
            response = new AuthenticateResponse(request.getCommandToEx().getName(), "Не удалось войти", user, false);
        } catch (WrongPasswordException e) {
            response = new ErrorResponse(e.toString());
        }
        return response;
    }
}
