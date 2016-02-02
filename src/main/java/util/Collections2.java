package util;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.frequency;

/**
 * The {@code Collections2} class contains utility methods related to the
 * {@link Collection} and {@link Stream} hierarchies.
 *
 * @author Oliver Abdulrahim
 * @see Collection
 * @see Stream
 */
public final class Collections2 {

    /**
     * Returns a random element from the given {@code List}.
     *
     * @param <T> The type of the element contained in the collection, and the
     *            type of the object to return.
     * @param list The {@code List} to select a random element from.
     * @return A random element from the given {@code List}.
     */
    public static <T> T randomElementFrom(List<? extends T> list) {
        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
    }

    /**
     * Return a standard error message intended for use as a detail message
     * for any thrown {@code IndexOutOfBoundsException}.
     *
     * @param c The {@code List} to generate a message for.
     * @param index The invalid index to generate a message with.
     * @return A formatted {@code String} for use as a detail message for an
     *         {@code IndexOutOfBoundsException}.
     */
    public static String outOfBoundsMessage(List<?> c, int index) {
        return "Index = " + index + ", Capacity = " + c.size();
    }

    /**
     * Returns a {@code Set} containing all keys that match a given value in the
     * given map. If there are no keys that match the value, then the returned
     * set will be empty.
     *
     * @param <K> The type of key that is stored in the given map and type of
     *        object stored in the returned set.
     * @param <V> The type of value stored in the given map.
     * @param map The map whose keys to retrieve from the given value.
     * @param value The value that the desired keys are mapped to.
     * @return A {@code Set} containing all keys that match a given value in the
     *         given map.
     */
    public static <K, V> Set<K> getKeysByValue(
            Map<? extends K, ? extends V> map,
            V value)
    {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry :: getKey)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an {@code Optional} containing the first key in the given map
     * that matches the given value, or an empty one if there is no such key.
     *
     * @param <K> The type of key that is stored in the given map and the type
     *        of the {@code Optional} return value.
     * @param <V> The type of value stored in the given map.
     * @param map The map whose key to attempt to match to the given value.
     * @param value The value that the desired key is mapped to.
     * @return An {@code Optional} containing the first key in the given map
     *         that matches the given value.
     */
    public static <K, V> Optional<K> getKeyByValue(
            Map<? extends K, ? extends V> map,
            V value)
    {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Entry :: getKey)
                .findFirst();
    }

    /**
     * Compares the given {@code Ailment} to this one for order, returning a
     * negative number, zero, or a positive number if {@code c1} is less than,
     * equal to, or greater than {@code c2}, respectively.
     *
     * @param c1 The {@code Collection} to test against {@code c2}.
     * @param c2 The {@code Collection} to test against {@code c1}.
     * @return A negative number, zero, or a positive number if {@code c1} is
     *         less than, equal to, or greater than {@code c2}, respectively.
     */
    public static int compare(Collection<?> c1, Collection<?> c2) {
        return c1
                .stream()
                .reduce(c1.size(),
                        (accumulator, obj) -> accumulator + frequency(c2, obj),
                        Integer :: sum
                );
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
     * {@code Stream}.
     *
     * @param s The {@code Stream} whose elements to unify.
     * @return A {@code Set} union of the elements of the given {@code Stream}.
     */
    public static <T> Set<T> union(Stream<? extends T> s) {
        return union(s, Stream :: of);
    }

    /**
     * Returns a {@code Set} containing the union of the elements in the given
     * {@code Stream}, partitioned by the supplied mapping function.
     *
     * @param s The {@code Stream} whose elements to unify using the specified
     *        mapping {@code Function}.
     * @param mapper The mapping function that produces the unification of the
     *        elements in the given {@code Stream}.
     * @return A {@code Set} union of the elements of the given {@code Stream}.
     */
    public static <T, R> Set<R> union(
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
    public static <T> Set<T> intersection(
            Stream<? extends T> a,
            Stream<? extends T> b)
    {
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
    private Collections2() {
        throw new InstantiationError("No instances allowed, pal!");
    }

}
