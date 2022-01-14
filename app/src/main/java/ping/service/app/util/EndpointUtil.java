package ping.service.app.util;

import ping.service.app.exception.CustomRuntimeException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public class EndpointUtil {
    private EndpointUtil() throws IllegalAccessException {
        throw new IllegalAccessException("SonarLint java:S1118");
    }

    private static List<String> theEndpointList = null;

    private static List<String> setTheEndpointList() {
        List<String> endpointList = new ArrayList<>();
        try (InputStream input = EndpointUtil.class.getClassLoader().getResourceAsStream("endpoints.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                throw new CustomRuntimeException("UNABLE TO FIND INPUT FILE");
            } else {
                prop.load(input);

                for (int i = 1; i < 101; i++) {
                    endpointList.add(prop.getProperty(String.format("ENDPOINT%s", i)));
                }
            }
        } catch (Exception ex) {
            throw new CustomRuntimeException(ex.getMessage());
        }

        theEndpointList = endpointList.stream()
                .filter(Util::hasText)
                .collect(Collectors.toList());

        return theEndpointList;
    }

    public static List<String> endpointList() {
        return Objects.requireNonNullElseGet(theEndpointList, EndpointUtil::setTheEndpointList);
    }
}
