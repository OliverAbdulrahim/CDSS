package compute;

import java.util.Collection;
import java.util.TreeSet;

/**
 * An implementation of the {@link Matcher} interface that preforms a
 * mean-squared error computation to test object similarity.
 *
 * @param <T> The type of the input to the function, lower bounded by
 *        {@code Comparable}.
 */
public class MeanSquaredErrorMatcher<T extends Comparable<? super T>>
    implements Matcher<T>
{

    /**
     * Computes a mean-squared error to find the object in the given
     * {@code Collection} most similar to the given one.
     *
     * @param data The candidate pool to be tested for similarity against
     *        {@code t}.
     * @param t The base object to test.
     * @return An object from the given collection that is closest to the given
     *         one.
     */
    @Override
    public T compute(Collection<? extends T> data, T t) {
        TreeSet<T> diff = new TreeSet<>(this :: meanSquaredError);
        T candidate = diff.ceiling(t);
        return candidate;
    }

    /**
     * Returns the mean-squared error between the given objects.
     *
     * @param first The object to test against {@code second}.
     * @param second The object to test against {@code first}.
     * @return The error between the given objects.
     */
    private int meanSquaredError(T first, T second) {
        int error = first.compareTo(second);
        return (int) Math.pow(error, 2) / 2;
    }

}
