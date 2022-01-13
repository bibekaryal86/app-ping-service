package ping.service.app.util;

public class Util {
    // provided at runtime
    public static final String SERVER_PORT = "PORT";
    public static final String PROFILE = "SPRING_PROFILES_ACTIVE";

    // server context-path
    public static final String CONTEXT_PATH = "/ping-service";     // NOSONAR

    // others
    public static final int SERVER_MAX_THREADS = 100;
    public static final int SERVER_MIN_THREADS = 20;
    public static final int SERVER_IDLE_TIMEOUT = 120;

    public static String getSystemEnvProperty(String keyName) {
        return (System.getProperty(keyName) != null) ? System.getProperty(keyName) : System.getenv(keyName);
    }

    public static boolean hasText(String s) {
        return (s != null && !s.trim().isEmpty());
    }
}
