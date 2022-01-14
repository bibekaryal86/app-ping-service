package ping.service.app.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ping.service.app.util.ConnectorUtil;

import java.io.IOException;

import static ping.service.app.util.EndpointUtil.endpointList;

public class AppPing extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        endpointList().forEach(ConnectorUtil::sendHttpRequest);

        response.setCharacterEncoding("utf-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setStatus(200);
        response.getWriter().print("{\"ping\": \"successful\"}");
    }
}