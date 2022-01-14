package ping.service.app.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Util {
    private Util() throws IllegalAccessException {
        throw new IllegalAccessException("SonarLint java:S1118");
    }

    // provided at runtime
    public static final String SERVER_PORT = "PORT";
    public static final String TIME_ZONE = "TZ";

    // others
    public static final int SERVER_MAX_THREADS = 100;
    public static final int SERVER_MIN_THREADS = 20;
    public static final int SERVER_IDLE_TIMEOUT = 120;

    public static String getSystemEnvProperty(String keyName) {
        return (System.getProperty(keyName) != null) ? System.getProperty(keyName) : System.getenv(keyName);
    }

    public static String getCurrentTimestamp() {
        return LocalDateTime.now(ZoneId.of(getSystemEnvProperty(TIME_ZONE))).toString();
    }

    public static boolean hasText(String s) {
        return (s != null && !s.trim().isEmpty());
    }
}
