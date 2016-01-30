package compute;

import java.util.Collection;

/**
 * Represents a function that accepts a {@link Comparable} object and selects
 * a candidate defined to be most similar from a given {@code Collection} of the
 * same type.
 *
 * <p>This is a <em>functional interface</em> whose functional method is
 * {@link #compute(Collection, Comparable)}.
 *
 * @param <T> The type of the input to the function, lower bounded by
 *        {@code Comparable}.
 * @author Oliver Abdulrahim
 */
@FunctionalInterface
public interface Matcher<T extends Comparable<? super T>> {

    /**
     * Selects and returns an object from the given collection defined to be
     * most similar to the given object.
     *
     * @param data The candidate pool to be tested for similarity against
     *        {@code t}.
     * @param t The base object to test.
     * @return An object from the given collection that is closest to the given
     *         one.
     */
    T compute(Collection<? extends T> data, T t);

    /**
     * Returns an implementation of this interface.
     *
     * @param <T> The type of the input to the function, lower bounded by
     *        {@code Comparable}.
     * @return An implementation of this interface.
     * @implSpec This method returns an object of type
     *           {@link MeanSquaredErrorMatcher}.
     */
    static <T extends Comparable<? super T>> Matcher<T> of() {
        return new MeanSquaredErrorMatcher<>();
    }

}
