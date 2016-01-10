package sql;

import model.Ailment;
import model.Patient;
import model.Symptom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    private static final Logger LOG =
            Logger.getLogger(Database.class.getName());

    public static final Table<Patient> PATIENT_TABLE =
            new Table<>("Patient", Patient.class);


    public static final Table<Ailment> AILMENT_TABLE =
            new Table<>("Ailment", Ailment.class);


    public static final Table<Symptom> SYMPTOM_TABLE =
            new Table<>("Symptom", Symptom.class);

    private final DatabaseProperties properties;

    private Connection connection;

    public Database(String name) {
        properties = new DatabaseProperties(name);
        connection = getConnection(properties.getURL());
    }

    private Connection getConnection(String where) {
        Connection c = null;
        try {
            c = DriverManager.getConnection(where);
        }
        catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return c;
    }

}
