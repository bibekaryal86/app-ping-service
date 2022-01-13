package ping.service.app.util;

import ping.service.app.exception.CustomRuntimeException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ConnectorUtil {

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
            System.out.printf("HTTP Request Sent::: Endpoint: [ %s ]%n", endpoint);
            HttpResponse<String> httpResponse = sendHttpRequest(getHttpRequestBuilder(endpoint));
            System.out.printf("HTTP Request Received::: Endpoint: [ %s ], Status: [ %s ], Body: [ %s ]%n",
                    endpoint,
                    httpResponse.statusCode(),
                    httpResponse.body() == null ? null : httpResponse.body().length());
        } catch (InterruptedException ex) {
            System.out.printf("Error in HttpClient Send: [ %s ] | [ %s ]%n", endpoint, ex.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            System.out.printf("Error in HttpClient Send: [ %s ] | [ %s ]%n", endpoint, ex.getMessage());
        }

        throw new CustomRuntimeException("HTTP ERROR");
    }
}
