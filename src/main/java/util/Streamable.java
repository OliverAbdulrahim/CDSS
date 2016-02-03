package util;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents an object whose contents may be streamed. This class provides a
 * the best general-case implementation of basic {@code Stream} operations.
 *
 * @param <T> The type of object in the {@code Stream}.
 * @see Stream
 */
public interface Streamable<T>
    extends Stream<T>
{

    /**
     * Returns a {@link Stream Stream} containing all elements the table. The
     * order of the elements is not guaranteed to be preserved.
     *
     * @return A {@code Stream} of all elements.
     */
    Stream<T> all();

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
     * Returns an {@code Optional} containing the value defined by the given
     * {@code Comparator} as the minimal element, or an empty one if there is no
     * such element.
     *
     * @param comparator The comparison function.
     * @return An {@code Optional} containing the minimal value.
     */
    default Optional<T> minBy(Comparator<? super T> comparator) {
        return all()
                .collect(Collectors.minBy(comparator));
    }

    /**
     * Returns an {@code Optional} containing the value defined by the given
     * {@code Comparator} as the maximal element, or an empty one if there is no
     * such element.
     *
     * @param comparator The comparison function.
     * @return An {@code Optional} containing the maximal value.
     */
    default Optional<T> maxBy(Comparator<? super T> comparator) {
        return all()
                .collect(Collectors.maxBy(comparator));
    }

    /**
     * Returns an {@code Optional} containing the element occurring most
     * minimally, or an empty one if there is no such element.
     *
     * @return An {@code Optional} containing the element occurring most
     *         minimally.
     */
    default Optional<T> minimal() {
        return Streams
                .min(all());
    }

    /**
     * Returns an {@code Optional} containing the element occurring most
     * maximally, or an empty one if there is no such element.
     *
     * @return An {@code Optional} containing the element occurring most
     *         maximally.
     */
    default Optional<T> maximal() {
        return Streams
                .max(all());
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

}
