package sql.access;

import model.Ailment;
import model.Patient;
import sql.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * Implementation of {@link AbstractSQLAccessor} that allows for the creation of
 * {@link Ailment} objects from SQL.
 */
public class AilmentAccessor
    extends AbstractSQLAccessor<Ailment>
{

    /**
     * Constructs an {@code AilmentAccessor} with the given {@code Connection}.
     *
     * @param connection The database connection.
     */
    public AilmentAccessor(Connection connection) {
        super(connection, Database.AILMENT_TABLE);
    }

    /**
     * Returns a {@code Set} containing all {@code Patient}s with the given
     * {@code Ailment}.
     *
     * @param ailment The {@code Ailment} to collect.
     * @return A {@code Set} containing all {@code Patient}s with the given
     *         {@code Ailment}.
     */
    public Set<Patient> collect(Ailment ailment) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the {@code Ailment} occurring most minimally.
     *
     * <p> This method performs a special case of reduction. // TODO add example and detail implementation
     *
     * @return The {@code Ailment} occurring most minimally.
     */
    public Ailment minimal() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the value defined by the given {@code Comparator} as the maximal
     * element.
     *
     * <p> This method performs a special case of reduction. // TODO add example and detail implementation
     *
     * @return The {@code Ailment} occurring most minimally.
     */
    public Ailment maximal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Ailment createFromSQL(ResultSet result) throws SQLException {
        return createFromSQL(Ailment.class, result);
    }

}
