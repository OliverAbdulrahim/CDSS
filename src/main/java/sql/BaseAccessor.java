package sql;

import util.Collections2;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An interface between an SQL database and Java objects. Several querying
 * operations are specified and by default, implemented in this interface.
 * Implementors may choose to override these methods if desired, but the logic
 * defined in this class provides for the best general-case performance.
 *
 * @param <T> The type of elements stored by this accessor, parametrized over
 *        {@link SQLObject}.
 */
public interface BaseAccessor<T extends SQLObject<? super T>> {

    @Override
    String toString();

    /**
     * Returns a {@link Stream Stream} containing all elements the table. The
     * order of the elements is not guaranteed to be preserved.
     *
     * @return A {@code Stream} of all elements.
     */
    Stream<T> all();

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

    /**
     * Returns a {@code Set} containing all values that match the given
     * {@code Predicate} operation.
     *
     * @param partition The {@code Predicate} to apply to each element.
     * @return A {@code Set} containing the elements that match the given
     *         {@code Predicate}.
     */
    default Set<T> filter(Predicate<? super T> partition) {
        return all()
                .filter(partition)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a {@code Map} that associates keys of arbitrary type with a list
     * of all values that match that key using the given classifying function.
     *
     * @param classifier The function that maps input to keys.
     * @param <K> The type of the keys.
     * @return A {@code Map} that associates keys to a list of values,
     *         partitioned by the given function.
     */
    default <K> Map<K, List<T>> groupBy(Function<? super T, K> classifier) {
        return all()
                .collect(Collectors.groupingBy(classifier));
    }

    /**
     * Returns the value defined by the given {@code Comparator} as the minimal
     * element.
     *
     * @param comparator The comparison function.
     * @return The minimal value.
     */
    default T minBy(Comparator<? super T> comparator) {
        return all()
                .collect(Collectors.minBy(comparator))
                .get();
    }

    /**
     * Returns the value defined by the given {@code Comparator} as the maximal
     * element.
     *
     * @param comparator The comparison function.
     * @return The maximal value.
     */
    default T maxBy(Comparator<? super T> comparator) {
        return all()
                .collect(Collectors.maxBy(comparator))
                .get();
    }

    /**
     * Returns a count of all elements that match the given {@code Predicate}.
     *
     * @param counter The {@code Predicate} to apply to each element.
     * @return A value representing the amount of elements that match the given
     *         {@code Predicate}.
     */
    default long counting(Predicate<? super T> counter) {
        return all()
                .filter(counter)
                .collect(Collectors.counting());
    }

    /**
     * Returns the element occurring most minimally.
     *
     * @return The element occurring most minimally.
     * @throws NoSuchElementException if no element is found.
     */
    default T minimal() {
        return Collections2.min(all())
                .orElseThrow(NoSuchElementException :: new);
    }

    /**
     * Returns the element occurring most maximally.
     *
     * @return The element occurring most maximally.
     * @throws NoSuchElementException if no element is found.
     */
    default T maximal() {
        return Collections2.max(all())
                .orElseThrow(NoSuchElementException :: new);
    }

}
