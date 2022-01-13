package ping.service.app.util;

public class Util {
    // provided at runtime
    public static final String PROFILE = "SPRING_PROFILES_ACTIVE";

    public static String getSystemEnvProperty(String keyName) {
        return (System.getProperty(keyName) != null) ? System.getProperty(keyName) : System.getenv(keyName);
    }

    public static boolean hasText(String s) {
        return (s != null && !s.trim().isEmpty());
    }
}
