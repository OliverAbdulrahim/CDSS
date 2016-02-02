package sql.access;

import model.Ailment;
import sql.Database;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Implementation of {@link AbstractSQLAccessor} that wraps the
 * {@link Ailment} class, allowing for the creation of {@code Ailment} objects
 * from SQL.
 *
 * @author Oliver Abdulrahim
 * @see Ailment
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
     * Constructs a new {@code Ailment}, whose attributes are loaded from the
     * given {@code ResultSet}.
     *
     * @param result The result containing the data for the object to construct.
     * @return A new {@code Ailment} containing the data stored in the given
     *         {@code ResultSet}.
     */
    @Override
    public Ailment createFromSQL(ResultSet result) {
        return createFromSQL(Ailment.class, result);
    }

}
