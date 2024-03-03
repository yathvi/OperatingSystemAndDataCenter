import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ConfigLoader {

    private static Properties properties;

    static {
        properties = loadProperties();
        startConfigReloadingTask();
    }

    public static String getConfigValue(String key) {
        return properties.getProperty(key);
    }

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (FileReader reader = new FileReader("src/main/resources/config.properties")) {
            props.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception based on your application's requirements.
        }
        return props;
    }

    private static void startConfigReloadingTask() {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                properties = loadProperties();
                System.out.println("Configuration reloaded.");
                String databaseUrl = getConfigValue("database.url");
                System.out.println("Database URL: " + databaseUrl);
            }
        }, 0, 5000); // Reload every 5 seconds, adjust as needed
    }

    public static void main(String[] args) {
        // Example: Accessing a config value during runtime
        String databaseUrl = getConfigValue("database.url");
        System.out.println("Database URL: " + databaseUrl);

        // Run the Java process for 2 minutes
        try {
            TimeUnit.MINUTES.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Java process completed after 2 minutes.");
        System.exit(0);
    }
}
