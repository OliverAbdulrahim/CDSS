package sql.access;

import model.Symptom;
import sql.Database;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Implementation of {@link AbstractSQLAccessor} that wraps the
 * {@link Symptom} class, allowing for the creation of {@code Symptom} objects
 * from SQL.
 *
 * @author Oliver Abdulrahim
 * @see Symptom
 */
public class SymptomAccessor
    extends AbstractSQLAccessor<Symptom>
{

    /**
     * Constructs an {@code SymptomAccessor} with the given {@code Connection}.
     *
     * @param connection The database connection.
     */
    public SymptomAccessor(Connection connection) {
        super(connection, Database.SYMPTOM_TABLE);
    }

    /**
     * Constructs a new {@code Symptom}, whose attributes are loaded from the
     * given {@code ResultSet}.
     *
     * @param result The result containing the data for the object to construct.
     * @return A new {@code Symptom} containing the data stored in the given
     *         {@code ResultSet}.
     */
    @Override
    public Symptom createFromSQL(ResultSet result) {
        return createFromSQL(Symptom.class, result);
    }

}
