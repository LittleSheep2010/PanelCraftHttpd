package club.smartsheep.PanelCraftHttpd;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PanelHttpErrorSender {
    private PanelHttpExchange exchange;

    public PanelHttpErrorSender(PanelHttpExchange exchange) {
        this.exchange = exchange;
    }

    public void CustomErrorResponse(String errorName, String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", errorName);
        response.put("message", errorMessage);
        this.exchange.setStatusCode(400);
        try {
            this.exchange.send(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CustomErrorResponse(String errorName, String errorMessage, int statusCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", errorName);
        response.put("message", errorMessage);
        this.exchange.setStatusCode(statusCode);
        try {
            this.exchange.send(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void BodyProcessErrorResponse(String reason) {
        this.CustomErrorResponse("FormatBodyFailed", "Please check your body, " + reason + ".");
    }

    public void InsufficientPermissionsErrorResponse() {
        this.CustomErrorResponse("InsufficientPermissions", "Please check your password or reset it, the root password is wrong.");
    }

    public void DataErrorResponse(String message) {
        this.CustomErrorResponse("DataError", message);
    }

    public void ModuleUnactivatedErrorResponse(String unactivatedModuleName) {
        this.CustomErrorResponse("ModuleUnactivatedError", unactivatedModuleName + " module isn't activate, please check it api is hooked(installed) and reload try again!", 500);
    }

    public void SQLErrorResponse(String state, String message) {
        this.CustomErrorResponse("SQLExecuteFailedError", "Execute script (state: " + state + ") failed with message: " + message + "; Detail message please view at server console!", 500);
    }

    public void MethodNotAllowResponse() {
        this.CustomErrorResponse("MethodNotAllowError", "This API do not allow you used method! Please query the developer document!", 405);
    }

    public void MissingHeaderErrorResponse(String missingThings) {
        this.CustomErrorResponse("MissingHeaders", "Missing " + missingThings + ".");
    }

    /**
     * Return missing parameter error response
     * @param missingThings What parameter are missing.
     */
    public void MissingArgumentsErrorResponse(String missingThings) {
        this.CustomErrorResponse("MissingArguments", "Missing " + missingThings + ".");
    }
}
