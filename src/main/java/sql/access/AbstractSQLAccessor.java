package sql.access;

import sql.BaseAccessor;
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

public abstract class AbstractSQLAccessor<T extends SQLObject<T>>
    implements BaseAccessor<T>
{

    private static final Logger LOG =
            Logger.getLogger(AbstractSQLAccessor.class.getName());

    private final Connection connection;

    private final Table<T> table;

    public AbstractSQLAccessor(Connection connection, Table<T> table) {
        this.connection = connection;
        this.table = table;
    }

    // Provides an interface with the table
    public Collection<T> query(String sql) {
        Set<T> data = new HashSet<>();
        try (PreparedStatement s = connection.prepareStatement(sql);
             ResultSet result = s.executeQuery(sql))
        {
            while (result.next()) {
                T next = createFromSQL(targetClass(), result);
                data.add(next);
            }
        }
        catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public Class<? extends T> targetClass() {
        return table.getTargetClass();
    }

    public String tableName() {
        return table.getTableName();
    }

    public abstract T createFromSQL(ResultSet result) throws SQLException;

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

    private boolean doQuery(String sql) {
        query(sql);
        return true;
    }

    @Override
    public Stream<T> all() {
        String q = "SELECT * FROM " + tableName();
        return query(q).stream();
    }

    @Override
    public boolean insert(T element) {
        String q = "INSERT INTO" + tableName()
                + "  VALUES (" + element.toINSERTString() + ')'; // TODO figure out if it needs parenthesis
        return doQuery(q);
    }

    @Override
    public boolean delete(T element) {
        String q = "DELETE FROM" + tableName()
                 + "  WHERE id=" + element.id();
        return doQuery(q);
    }

    @Override
    public boolean update(T element) {
        String q = "UPDATE " + tableName()
                 + "  SET " + element.toSETString()
                 + "  WHERE id=" + element.id();
        return doQuery(q);
    }

    @Override
    public String toString() {
        return "Accessor for table [" + tableName()
                + "] containing Java objects of type [" + targetClass()
                + "] connected at [" + connection
                + "].";
    }

}
