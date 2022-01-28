package club.smartsheep.PanelCraftHttpd;

import club.smartsheep.PanelCraftHttpd.Errors.RouteRegisterError;
import club.smartsheep.PanelCraftHttpd.Responsor.ErrorResponse;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class PanelHttpServer {
    public enum RequestMethod {
        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE"),
        PATCH("PATCH"),
        TRACE("TRACE"),
        OPTIONS("OPTIONS");

        public final String name;

        RequestMethod(String name) {
            this.name = name;
        }
    }

    private static final PanelHttpServer instance = null;

    private final HttpServer server;

    public PanelHttpServer(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
    }

    public boolean addRoute(RequestMethod method, PanelHttpHandler handler, String path) throws RouteRegisterError {
        if (!path.equals("/") && (!path.startsWith("/") || path.endsWith("/"))) {
            throw new RouteRegisterError("Path is need start by a slash, and cannot end by slash", path);
        }
        server.createContext(path, exchange -> {
            if (!exchange.getRequestMethod().equalsIgnoreCase(method.name)) {
                ErrorResponse.MethodNotAllowResponse(exchange);
                return;
            }

            handler.handle(new PanelHttpExchange(exchange));
        });
        return true;
    }

    public void startup() {
        this.server.start();
    }

    public void shutdown() {
        this.shutdown(0);
    }

    public void shutdown(int delay) {
        server.stop(delay);
    }
}
