package ping.service.app.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static ping.service.app.util.ConnectorUtil.sendHttpRequest;
import static ping.service.app.util.EndpointUtil.endpointList;

public class AppPing extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(AppPing.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            endpointList().forEach(endpoint -> CompletableFuture.runAsync(() -> sendHttpRequest(endpoint)));
        } catch (Exception ex) {
            log.error("Exception in AppPing: [ {} ]", ex.getMessage());
        }

        response.setCharacterEncoding("utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setStatus(200);
        response.getWriter().print("{\"ping\": \"successful\"}");
    }
}