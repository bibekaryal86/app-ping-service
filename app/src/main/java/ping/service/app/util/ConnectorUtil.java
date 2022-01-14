package ping.service.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.service.app.exception.CustomRuntimeException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static ping.service.app.util.Util.getCurrentTimestamp;

public class ConnectorUtil {
    private static final Logger log = LoggerFactory.getLogger(ConnectorUtil.class);

    private ConnectorUtil() throws IllegalAccessException {
        throw new IllegalAccessException("SonarLint java:S1118");
    }

    private static HttpClient getHttpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5L))
                .build();
    }

    private static URI getUri(String endpoint) {
        return URI.create(endpoint);
    }

    private static HttpRequest getHttpRequestBuilder(String endpoint) {
        return HttpRequest.newBuilder()
                .uri(getUri(endpoint))
                .header("Content-Type", "application/json")
                .GET()
                .build();
    }

    private static HttpResponse<String> sendHttpRequest(HttpRequest httpRequest) throws IOException, InterruptedException {
        return getHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }

    public static void sendHttpRequest(String endpoint) {
        try {
            log.info("HTTP Request Sent::: Endpoint: [ {} ]", endpoint);
            HttpResponse<String> httpResponse = sendHttpRequest(getHttpRequestBuilder(endpoint));
            log.info("HTTP Request Received::: Endpoint: [ {} ], Status: [ {} ]", endpoint, httpResponse.statusCode());
        } catch (InterruptedException ex) {
            log.error("HTTP Request ERROR::: Endpoint: [ {} ] | [ {} ]", endpoint, ex.getMessage());
            Thread.currentThread().interrupt();
            throw new CustomRuntimeException("HTTP INTERRUPTED ERROR");
        } catch (Exception ex) {
            log.error("HTTP Request ERROR::: Endpoint: [ {} ] | [ {} ]", endpoint, ex.getMessage());
            throw new CustomRuntimeException("HTTP EXCEPTION ERROR");
        }
    }
}
