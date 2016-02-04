package sql;

import util.stream.Streamable;

/**
 * An interface between a information in a database and Java objects. Several
 *
 * @param <T> The type of elements stored by this accessor.
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
     * Inserts the given element into the database, returning {@code true} if
     * the element was added, {@code false} otherwise.
     *
     * @param element The element to insert.
     * @return {@code true} if the element was added, {@code false} otherwise.
     */
    boolean insert(T element);

    /**
     * Deletes the given element from the database if it is present, returning
     * {@code true} if the element was deleted, {@code false} otherwise.
     *
     * @param element The element to delete.
     * @return {@code true} if the element was deleted, {@code false} otherwise.
     */
    boolean delete(T element);

    /**
     * Updates the given element if it is present in the database, returning
     * {@code true} if the element was deleted, {@code false} otherwise.
     *
     * @param element The element containing the updated data.
     * @return {@code true} if the element was successfully updated,
     *         {@code false} otherwise.
     */
    boolean update(T element);

}
