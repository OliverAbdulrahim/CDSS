package sql.access;

import model.Ailment;
import model.Patient;
import sql.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Cylin on 1/17/2016.
 */
public class PatientAccessor
    extends AbstractSQLAccessor<Patient>
{

    public PatientAccessor(Connection connection) {
        super(connection, Database.PATIENT_TABLE);
    }

    /**
     * Returns a {@code Set} containing the union of ailments of all
     * {@code Patient}s in the given {@code Collection}, or in other words, all
     * ailments shared by the given patients.
     *
     * @param patients The {@code Collection} containing the {@code Patient}s
     *        whose ailments to unify.
     * @return A {@code Set} containing the union of ailments of all
     *         {@code Patient}s in the given {@code Collection}.
     */
    Set<Ailment> union(Collection<? extends Patient> patients) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a {@code Set} containing the intersection of ailments of all
     * {@code Patient}s in the given {@code Collection}, or in other words, all
     * ailments shared by the given patients.
     *
     * @param patients The {@code Collection} containing the {@code Patient}s
     *        whose ailments to intersect.
     * @return A {@code Set} containing the intersection of ailments of all
     *         {@code Patient}s in the given {@code Collection}.
     */
    Set<Ailment> intersection(Collection<? extends Patient> patients) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Patient createFromSQL(ResultSet result) throws SQLException {
        return createFromSQL(Patient.class, result);
    }

}
