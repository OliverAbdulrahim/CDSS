package sql.access;

import util.stream.Streamable;

/**
 * An interface between information in a database and Java objects.
 *
 * <p> Notable operations imposed in this interface include:
 *   <ul>
 *     <li> {@link #insert(Object)}, which inserts the given object into the
 *          database.
 *     <li> {@link #delete(Object)}, which deletes the given object from the
 *          database, if it is present.
 *     <li> {@link #update(Object)}, which updates the given object if it is
 *          present in the database.
 *   </ul>
 *
 * @param <T> The type of elements stored by this accessor.
 * @author Oliver Abdulrahim
 */
public interface BaseAccessor<T>
    extends Streamable<T>
{

    /**
     * Returns a {@code String} representation of the elements contained in this
     * object.
     *
     * @return A {@code String} representation of this object.
     */
    @Override
    String toString();

    /**
     * Inserts the given object into the database, returning {@code true} if the
     * element was added, {@code false} otherwise.
     *
     * @param t The element to insert.
     * @return {@code true} if the operation was successful, {@code false}
     *         otherwise.
     */
    boolean insert(T t);

    /**
     * Deletes the given element from the database, if it is present, returning
     * {@code true} if the element was deleted, {@code false} otherwise.
     *
     * @param t The object to delete.
     * @return {@code true} if the operation was successful, {@code false}
     *         otherwise.
     */
    boolean delete(T t);

    /**
     * Updates the given element in the database, if it is present, returning
     * {@code true} if the element was deleted, {@code false} otherwise.
     *
     * @param t The object to update in the database.
     * @return {@code true} if the operation was successful, {@code false}
     *         otherwise.
     */
    boolean update(T t);

}
