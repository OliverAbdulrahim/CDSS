package sql;

import util.stream.Streamable;

import java.util.Optional;

/**
 * An interface between an SQL database and Java objects. Several querying
 * operations are specified and implemented by default in this interface.
 * Implementors may choose to override these methods if desired, but the logic
 * defined in this class provides for the best general-case performance.
 *
 * @param <T> The type of elements stored by this accessor, parametrized over
 *        {@link SQLObject}.
 */
public interface BaseAccessor<T extends SQLObject<? super T>>
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

}
