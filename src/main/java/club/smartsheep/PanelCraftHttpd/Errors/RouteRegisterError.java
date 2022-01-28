package club.smartsheep.PanelCraftHttpd.Errors;

public class RouteRegisterError extends Exception {
    public RouteRegisterError(String reason, String path) {
        super("Failed to register a controller(" + path + "), because " + reason);
    }
}
