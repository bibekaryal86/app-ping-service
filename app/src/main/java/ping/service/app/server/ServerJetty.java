package ping.service.app.server;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import ping.service.app.servlet.AppPing;

import static ping.service.app.util.Util.SERVER_IDLE_TIMEOUT;
import static ping.service.app.util.Util.SERVER_MAX_THREADS;
import static ping.service.app.util.Util.SERVER_MIN_THREADS;
import static ping.service.app.util.Util.SERVER_PORT;
import static ping.service.app.util.Util.getSystemEnvProperty;

public class ServerJetty {

    public void start() throws Exception {
        QueuedThreadPool threadPool = new QueuedThreadPool(SERVER_MAX_THREADS, SERVER_MIN_THREADS, SERVER_IDLE_TIMEOUT);
        Server server = new Server(threadPool);

        try (ServerConnector connector = new ServerConnector(server)) {
            String port = getSystemEnvProperty(SERVER_PORT);
            connector.setPort(port == null ? 8080 : Integer.parseInt(port));
            server.setConnectors(new Connector[]{connector});
        }

        server.setHandler(getServletHandler());
        server.start();
    }

    private ServletHandler getServletHandler() {
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(AppPing.class, "/*");
        return servletHandler;
    }
}
