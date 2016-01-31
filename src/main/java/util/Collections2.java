package util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.util.Collections.frequency;

/**
 * The {@code Collections2} class contains utility methods related to the
 * {@link Collection} hierarchy.
 *
 * @author Oliver Abdulrahim
 * @see java.util.Collection
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
     *        of the return value.
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
     * Don't let anyone instantiate this class.
     */
    private Collections2() {
        throw new InstantiationError("No instances allowed, pal!");
    }

}
