package ping.service.app.util;

import ping.service.app.exception.CustomRuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public class EndpointUtil {

    private static List<String> theEndpointList = null;

    private static List<String> setTheEndpointList() {
        String profile = Util.getSystemEnvProperty(Util.PROFILE);

        if (!Util.hasText(profile)) {
            throw new CustomRuntimeException("PROFILE NOT SET AT RUNTIME");
        }

        String fileName;
        switch (profile) {
            case "development":
                fileName = "endpoint.development.properties";
                break;
            case "docker":
                fileName = "endpoint.docker.properties";
                break;
            case "propduction":
                fileName = "endpoint.production.properties";
                break;
            default:
                throw new CustomRuntimeException("INCORRECT PROFILE SET AT RUNTIME");
        }

        List<String> endpointList = new ArrayList<>();
        try (InputStream input = EndpointUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            } else {
                prop.load(input);

                for (int i=1; i<101; i++) {
                    endpointList.add(prop.getProperty(String.format("ENDPOINT%s", i)));
                }
            }
        } catch (Exception ex) {
            System.err.printf("Something went wrong: [ %s ]", ex.getMessage());
        }

        theEndpointList = endpointList.stream()
                .filter(Util::hasText)
                .collect(Collectors.toList());

        return endpointList;
    }

    public static List<String> endpointList() {
        return Objects.requireNonNullElseGet(theEndpointList, EndpointUtil::setTheEndpointList);
    }
}
