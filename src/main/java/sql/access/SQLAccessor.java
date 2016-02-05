package sql.access;

import sql.SQLObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.reflect.ReflectionUtilities.*;

/**
 * An interface between an SQL database and Java objects.
 *
 * @param <T> The type of elements stored by this accessor, parametrized over
 *        {@link SQLObject}.
 */
public interface SQLAccessor<T extends SQLObject<? super T>>
    extends BaseAccessor<T>
{

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
    Collection<T> query(String statement);

    /**
     * Returns a new object of generic type {@code T} and updates its contents
     * using the given {@code ResultSet}.
     *
     * @param result The result containing the data for the object to construct.
     * @return A new object of generic type {@code T} that stores the contents
     *         {@code ResultSet}.
     */
    T createFromSQL(ResultSet result);

    /**
     * Returns the name of the SQL table this object represents.
     *
     * @return The name of the SQL table this object represents.
     */
    String tableName();

    /**
     * Returns a {@code Stream} containing all rows in the SQL table that this
     * object represents, expressed as Java objects.
     *
     * @return A {@code Stream} of the data contained in the SQL table this
     *         object represents.
     */
    @Override
    default Stream<T> all() {
        String q = "SELECT * FROM " + tableName();
        return query(q).stream();
    }

    /**
     * Inserts the given object into the database using an {@code INSERT}
     * statement, returning {@code true} if the operation was successful.
     *
     * @param t The object to insert.
     * @return {@code true} if the add operation was successful.
     */
    @Override
    default boolean insert(T t) {
        String sql = "INSERT INTO" + tableName()
                + "  VALUES (" + t.toINSERTString() + ')';
        query(sql);
        return true;
    }

    /**
     * Deletes the given object, if it is present, from the database using a
     * {@code DELETE} statement, returning {@code true} if the operation was
     * successful.
     *
     * @param t The element to delete.
     * @return {@code true} if the delete operation was successful.
     */
    @Override
    default boolean delete(T t) {
        String sql = "DELETE FROM" + tableName()
                + "  WHERE id=" + t.getID();
        query(sql);
        return true;
    }

    /**
     * Updates the given element, if it is present, in the database using an
     * {@code UPDATE} statement, returning {@code true} if the operation was
     * successful.
     *
     * @param t The object containing the updated data.
     * @return {@code true} if the update operation was successful.
     */
    @Override
    default boolean update(T t) {
        String sql = "UPDATE " + tableName()
                + "  SET " + t.toSETString()
                + "  WHERE id=" + t.getID();
        query(sql);
        return true;

    }

    /**
     * Returns an {@code Optional} containing the element with the given
     * identification number, or an empty one if there is no such element.
     *
     * @param id The identification number.
     * @return An {@code Optional} containing the element with the given
     *         identification number
     */
    default Optional<T> find(int id) {
        return all()
                .filter(t -> t.getID() == id)
                .findFirst();
    }

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
    static <T extends SQLObject> T createFromSQL(
            Class<? extends T> c,
            ResultSet result)
    {
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
                        Entry:: getKey,
                        e -> invoke(result, e.getValue(), e.getKey().getName()))
                );

        // The new values are assigned to their Field keys here.
        fieldsToNewValues
                .forEach((field, value) -> set(obj, field, value));

        return obj;
    }

}
