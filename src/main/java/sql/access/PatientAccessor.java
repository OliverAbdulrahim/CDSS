package sql.access;

import model.Ailment;
import model.Patient;
import model.Symptom;
import sql.Database;
import util.Streams;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of {@link AbstractSQLAccessor} that wraps the
 * {@link Patient} class, allowing for the creation of {@code Patient} objects
 * from SQL.
 *
 * @author Oliver Abdulrahim
 * @see Patient
 */
public class PatientAccessor
    extends AbstractSQLAccessor<Patient>
{

    /**
     * Constructs a {@code PatientAccessor} using the given {@code Connection}.
     *
     * @param connection The database connection for the object.
     */
    public PatientAccessor(Connection connection) {
        super(connection, Database.PATIENT_TABLE);
    }

    /**
     * Returns a {@code Set} containing all {@code Patient}s with the given
     * {@code Ailment}.
     *
     * @param ailment The {@code Ailment} to collect.
     * @return A {@code Set} containing all {@code Patient}s with the given
     *         {@code Ailment}.
     */
    public Set<Patient> findAll(Ailment ailment) {
        return all() // No method reference for you, friend! :(
                .filter(patient -> patient.hasAilment(ailment))
                .collect(Collectors.toSet());
    }

    /**
     * Returns a {@code Set} containing the union of ailments of all
     * {@code Patient}s in the given {@code Collection}, or in other words, all
     * ailments shared by the given patients.
     *
     * @return A {@code Set} containing the union of ailments of all
     *         {@code Patient}s in the given {@code Collection}.
     */
    public Set<Ailment> union() {
        return Streams.flatUnion(
                all(),
                patient -> patient.getAilments().stream()
        );
    }

    /**
     * Returns a {@code Set} containing the intersection of the symptoms of the
     * given {@code Patient}s.
     *
     * @param first The patient whose symptoms to intersect with {@code second}.
     * @param second The patient whose symptoms to intersect with {@code first}.
     * @return A {@code Set} containing the intersection of the symptoms of the
     *         given {@code Patient}s.
     */
    public static Set<Symptom> intersection(Patient first, Patient second) {
        return Streams.intersection(
                first.getSymptoms().stream(),
                second.getSymptoms().stream()
        );
    }

    /**
     * Constructs a new {@code Patient}, whose attributes are loaded from the
     * given {@code ResultSet}.
     *
     * @param result The result containing the data for the object to construct.
     * @return A new {@code Patient} containing the data stored in the given
     *         {@code ResultSet}.
     */
    @Override
    public Patient createFromSQL(ResultSet result) {
        return createFromSQL(Patient.class, result);
    }

}
