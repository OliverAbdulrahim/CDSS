package sql.access;

import sql.Database;
import sql.SQLObject;
import sql.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides a skeletal implementation of the {@link BaseAccessor}
 * interface.
 *
 * <p> Several querying operations are specified in this class. Subclasses may
 * choose to override these methods if desired, but the logic defined in this
 * class provides for the best general-case performance.
 *
 * @param <T> {@inheritDoc}
 */
public abstract class AbstractSQLAccessor<T extends SQLObject<? super T>>
    implements SQLAccessor<T>
{

    private static final Logger LOG =
            Logger.getLogger(AbstractSQLAccessor.class.getName());

    /**
     * Provides for a connection to the SQL database.
     */
    private final Connection connection;

    /**
     * Encapsulation of the SQL table that this object represents.
     */
    private final Table<? extends T> table;

    /**
     * Sole constructor for use by subclasses only. Constructs a new
     * {@code AbstractSQLAccessor} with the given arguments.
     *
     * @param connection The database connection for the object.
     * @param table The table for the object. Common {@code Table} objects are
     *        enumerated in the {@link Database} class.
     */
    protected AbstractSQLAccessor(Connection connection, Table<T> table) {
        this.connection = connection;
        this.table = table;
    }


    /**
     * Returns the {@code Class} of the Java objects represented by this object.
     *
     * @return The {@code Class} of the Java objects represented by this object.
     */
    protected Class<? extends T> targetClass() {
        return table.getTargetClass();
    }


    /**
     * Returns the name of the SQL table this object represents.
     *
     * @return The name of the SQL table this object represents.
     */
    @Override
    public String tableName() {
        return table.getName();
    }

    /**
     * Queries the table represented by this object using the given
     * {@code String} SQL statement, returning a {@code Collection} containing
     * the result of the operation.
     *
     * @param statement The SQL statement to execute.
     * @return The result of executing the given SQL statement.
     * @apiNote This implementation returns a {@code Collection} of type
     *          {@code HashSet}.
     */
    @Override
    public Collection<T> query(String statement) {
        Set<T> data = new HashSet<>();
        try (PreparedStatement s = connection.prepareStatement(statement);
             ResultSet result = s.executeQuery(statement))
        {
            while (result.next()) {
                T next = createFromSQL(result);
                data.add(next);
            }
        }
        catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return data;
    }

    /**
     * Returns a {@code String} containing the name, Java object class, and
     * database connection of this object.
     *
     * @return A {@code String} representation of this object.
     */
    @Override
    public String toString() {
        return "Accessor for table [" + tableName()
                + "] containing Java objects of type [" + targetClass()
                + "] connected at [" + connection
                + "].";
    }

}
