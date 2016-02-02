package sql.access;

import sql.BaseAccessor;
import sql.Database;
import sql.SQLObject;
import sql.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.reflect.ReflectionUtilities.*;

/**
 * This class provides a skeletal implementation of the {@link BaseAccessor}
 * interface.
 *
 * @param <T> {@inheritDoc}
 */
public abstract class AbstractSQLAccessor<T extends SQLObject<? super T>>
    implements BaseAccessor<T>
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
     * Queries the table represented by this object using the given
     * {@code String} SQL statement, returning a {@code Collection} containing
     * the result of the operation.
     *
     * @param statement The SQL statement to execute.
     * @return The result of executing the given SQL statement.
     * @apiNote This implementation returns a {@code Collection} of type
     *          {@code HashSet}.
     */
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
    public String tableName() {
        return table.getName();
    }

    /**
     * Returns a new object of generic type {@code T} and updates its contents
     * using the given {@code ResultSet}.
     *
     * @param result The result containing the data for the object to construct.
     * @return A new object of generic type {@code T} that stores the contents
     *         {@code ResultSet}.
     */
    public abstract T createFromSQL(ResultSet result);

    /**
     * Creates and returns an instance of the given {@code Class}, the
     * attributes of which are loaded from the given {@code ResultSet}.
     *
     * @param c The {@code Class} to create an instance of and populate.
     * @param result The SQL source containing the data for the instance of the
     *        given {@code Class}.
     * @param <T> The type of the object to create.
     * @return An instance of the given {@code Class}, loaded with values from
     *         the given {@code ResultSet}.
     * @implSpec This method uses type introspection and method inference to
     *           set the values of the attributes of an object of the given
     *           {@code Class}.
     */
    public static <T> T createFromSQL(Class<? extends T> c, ResultSet result) {
        T obj = newInstance(c);

        // Attempt to infer the appropriate getter method from the ResultSet
        // class for each field in the target (given) class. Map the fields to
        // said Method object inference.
        Map<Field, Method> fieldsToGetters = fields(c)
                .collect(Collectors.toMap(
                        Function.identity(),
                        f -> getterFor(ResultSet.class, f))
                );

        // Transforms the map with Field-Method pairing into a map with
        // Field-Object pairing by invoking each Method upon its Field key. The
        // resultant map contains the new values for each Field.
        Map<Field, Object> fieldsToNewValues = fieldsToGetters.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Entry :: getKey,
                        e -> invoke(result, e.getValue(), e.getKey().getName()))
                );

        // The new values are assigned to their Field keys here.
        fieldsToNewValues
                .forEach((field, value) -> set(obj, field, value));

        return obj;
    }

    /**
     * Executes the given SQL statement, returning {@code true}.
     *
     * @param statement The SQL statement to execute.
     * @return {@code true}.
     */
    private boolean executeQuery(String statement) {
        query(statement);
        return true;
    }

    /**
     * Returns a {@code Stream} containing all rows in the SQL table that this
     * object represents, expressed as Java objects.
     *
     * @return A {@code Stream} containing all rows in the SQL table as Java
     *         objects.
     */
    @Override
    public Stream<T> all() {
        String q = "SELECT * FROM " + tableName();
        return query(q).stream();
    }

    /**
     * Inserts into the database this object represents the given element,
     * returning {@code true} if the operation was successful, {@code false}
     * otherwise.
     *
     * @param element The element to insert.
     * @return {@code true} if the insert operation was successful,
     *         {@code false} otherwise.
     */
    @Override
    public boolean insert(T element) {
        String q = "INSERT INTO" + tableName()
                + "  VALUES (" + element.toINSERTString() + ')';
        return executeQuery(q);
    }

    /**
     * Deletes from the database this object represents the given element,
     * returning {@code true} if the operation was successful, {@code false}
     * otherwise.
     *
     * @param element The element to delete.
     * @return {@code true} if the delete operation was successful,
     *         {@code false} otherwise.
     */
    @Override
    public boolean delete(T element) {
        String q = "DELETE FROM" + tableName()
                 + "  WHERE id=" + element.getID();
        return executeQuery(q);
    }

    /**
     * Updates the given element, if it exists in the database this object
     * represents, returning {@code true} if the operation was successful,
     * {@code false} otherwise.
     *
     * @param element The element containing the updated data.
     * @return {@code true} if the update operation was successful,
     *         {@code false} otherwise.
     */
    @Override
    public boolean update(T element) {
        String q = "UPDATE " + tableName()
                 + "  SET " + element.toSETString()
                 + "  WHERE id=" + element.getID();
        return executeQuery(q);
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
