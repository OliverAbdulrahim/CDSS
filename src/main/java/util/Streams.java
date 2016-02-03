package util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The {@code Streams} class contains utility methods related to the
 * {@link Collection} and {@link Stream} hierarchies.
 *
 * @author Oliver Abdulrahim
 * @see Collection
 * @see Stream
 */
public final class Streams {

    /**
     * Compares the contents of two {@code Collection} objects for order,
     * returning a negative number, zero, or a positive number if {@code c1} is
     * less than, equal to, or greater than {@code c2}, respectively.
     *
     * @param c1 The {@code Collection} to test against {@code c2}.
     * @param c2 The {@code Collection} to test against {@code c1}.
     * @return A negative number, zero, or a positive number if {@code c1} is
     *         less than, equal to, or greater than {@code c2}, respectively.
     */
    public static long compare(Collection<?> c1, Collection<?> c2) {
        return compare(c1.stream(), c2.stream());
    }

    /**
     * Compares the contents of two {@code Stream} objects for order, returning
     * a negative number, zero, or a positive number if {@code s1} is less than,
     * equal to, or greater than {@code s2}, respectively.
     *
     * @param s1 The {@code Stream} to test against {@code s2}.
     * @param s2 The {@code Stream} to test against {@code s1}.
     * @return A negative number, zero, or a positive number if {@code s1} is
     *         less than, equal to, or greater than {@code s2}, respectively.
     */
    public static long compare(Stream<?> s1, Stream<?> s2) {
        return s1
                .reduce(s1.count(),
                        (accumulator, obj) -> accumulator + frequency(s2, obj),
                        Long :: sum
                );
    }

    /**
     * Returns a count of the elements in the specified {@code Collection} that
     * equal the given object.
     *
     * @param c The {@code Collection} in which to determine the frequency of
     *        the given object.
     * @param obj The object whose frequency to test.
     * @return A count of the elements that match the given object contained in
     *         the given {@code Collection}.
     */
    public static long frequency(Collection<?> c, Object obj) {
        return frequency(c.stream(), obj);
    }

    /**
     * Returns a count of the elements in the specified {@code Stream} that
     * equal the given object.
     *
     * @param s The {@code Stream} in which to determine the frequency of the
     *        given object.
     * @param obj The object whose frequency to test.
     * @return A count of the elements that match the given object contained in
     *         the given {@code Stream}.
     */
    public static long frequency(Stream<?> s, Object obj) {
        return s.map(o -> o.equals(obj)).count();
    }

    /**
     * Returns a {@code Stream} containing the concatenation of the elements in
     * the given {@code Collection}(s). If no argument is given, then the
     * {@code Stream} will be empty.
     *
     * @param cs The {@code Collection} or {@code Collection}s to concatenate.
     * @param <T> The type of the element in the {@code Collection}(s) to
     *        concatenate and the type of the returned {@code Stream}.
     * @return A {@code Stream} containing the elements of the given
     *         {@code Collection}(s).
     */
    @SafeVarargs
    public static <T> Stream<T> concat(Collection<? extends T>... cs) {
        return Arrays
                .stream(cs)
                .flatMap(Collection :: stream);
    }

    /**
     * Returns a {@code Stream} containing concatenation of the elements in the
     * given {@code Stream}(s). If no argument is given, then the {@code Stream}
     * will be empty.
     *
     * @param ss The {@code Stream} or {@code Stream}s to concatenate.
     * @param <T> The type of the element in the {@code Stream}(s) to
     *        concatenate and the type of the returned {@code Stream}.
     * @return A {@code Stream} containing the elements of the given
     *         {@code Stream} or {@code Stream}s.
     */
    @SafeVarargs
    public static <T> Stream<T> concat(Stream<? extends T>... ss) {
        return Arrays
                .stream(ss)
                .reduce(Stream.empty(), Stream :: concat)
                .map(Function.identity());
    }

    /**
     * Returns a {@code Set} containing the union of the elements in the given
     * {@code Stream}s.
     *
     * @param ss The {@code Stream}s whose elements to unify.
     * @return A {@code Set} union of the elements of the given {@code Stream}.
     */
    @SafeVarargs
    public static <T> Set<T> union(Stream<? extends T>... ss) {
        return flatUnion(Arrays.stream(ss), Function.identity());
    }

    /**
     * Returns a {@code Set} union containing the result of applying given
     * flat-mapping {@code Function} to the given {@code Stream}.
     *
     * @param s The {@code Stream} whose elements to unify using the specified
     *        mapping {@code Function}.
     * @param mapper The mapping function that produces the unification of the
     *        elements in the given {@code Stream}.
     * @return A {@code Set} union of the elements of the given {@code Stream}.
     */
    public static <T, R> Set<R> flatUnion(
            Stream<? extends T> s,
            Function<? super T, ? extends Stream<? extends R>> mapper)
    {
        return s
                .flatMap(mapper)
                .distinct()
                .collect(Collectors.toSet());
    }

    /**
     * Returns a {@code Set} containing the intersection of the elements in the
     * given {@code Stream}s, partitioned by the specified mapping function.
     *
     * @param a The {@code Stream} whose elements to intersect with {@code b}
     *        using the specified mapping {@code Function}.
     * @param b The {@code Stream} whose elements to intersect with {@code a}
     *        using the specified mapping {@code Function}.
     * @return A {@code Set} intersection of the elements of the given
     *         {@code Stream}s.
     */
    public static <T> Set<T> intersection(Stream<T> a, Stream<T> b) {
        return a
                .filter(t -> contains(b, t))
                .collect(Collectors.toSet());
    }

    /**
     * Returns {@code true} if the given {@code Stream} contains the given
     * object, {@code false} otherwise.
     *
     * @param s The {@code Stream} to test for the given object.
     * @param t The object whose presence to test in the given {@code Stream}.
     * @param <T> The type of the object in the given {@code Stream}.
     * @return {@code true} if the given object occurs at least once in the
     *         given {@code Stream}, {@code false} otherwise.
     */
    public static <T> boolean contains(Stream<? extends T> s, T t) {
        return s.anyMatch(element -> element.equals(t));
    }

    /**
     * Returns an {@code Optional} containing the key occurring most minimally,
     * as defined by the given comparing function, or an empty one if there is
     * no such element.
     *
     * @param s The {@code Stream} containing the element to find.
     * @param <T> The type of value stored in the given map.
     * @return An {@code Optional} containing the key occurring most minimally,
     *         as defined by the given comparing function.
     */
    public static <T> Optional<T> min(Stream<? extends T> s) {
        return occurrences(s).entrySet()
                .stream()
                .min(Comparator.comparingLong(Entry :: getValue))
                .map(Entry :: getKey);
    }

    /**
     * Returns an {@code Optional} containing the key occurring most maximally,
     * as defined by the given comparing function, or an empty one if there is
     * no such element.
     *
     * @param s The {@code Stream} containing the element to find.
     * @param <T> The type of value stored in the given map.
     * @return An {@code Optional} containing the key occurring most maximally,
     *         as defined by the given comparing function.
     */
    public static <T> Optional<T> max(Stream<? extends T> s) {
        return occurrences(s).entrySet()
                .stream()
                .max(Comparator.comparingLong(Entry :: getValue))
                .map(Entry :: getKey);
    }

    /**
     * Returns a {@code Map} associating keys of arbitrary type with the amount
     * of times they occur in the given {@code Stream}.
     *
     * @param s The {@code Stream} whose elements to count.
     * @param <T> The type of the key.
     * @return A {@code Map} associating keys of arbitrary type with the amount
     *         of times they occur in the given {@code Stream}.
     */
    public static <T> Map<T, Long> occurrences(Stream<? extends T> s) {
        return s
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting())
                );
    }

    /**
     * Don't let anyone instantiate this class.
     */
    private Streams() {
        throw new InstantiationError("No instances allowed, pal!");
    }

}
