package sql;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {

    private static final Logger LOG =
            Logger.getLogger(Database.class.getName());

    private static final String DEFAULT_URL = "sql/data.db";

    private final Properties props;

    public DatabaseProperties(String name) {
        props = new Properties();
        try {
            props.load(DatabaseProperties.class.getResourceAsStream(name));
        }
        catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public String getURL() {
        Optional<String> url = Optional.ofNullable(props.getProperty("url"));
        return url
                .map(String :: trim)
                .orElse(DEFAULT_URL);
    }

    public Optional<String> getDriver() {
        return Optional.ofNullable(props.getProperty("driver"));
    }

    public Optional<String> getUsername() {
        return Optional.ofNullable(props.getProperty("username"));
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(props.getProperty("password"));
    }


}