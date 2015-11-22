package util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

/**
 * A {@link Comparable Comparable}, mutable implementation of {@link Map.Entry
 * Entry} that stores a non-nullable pair of two objects.
 *
 * @param <A> The type of the key.
 * @param <B> The type of the value.
 * @author Oliver Abdulrahim
 */
public final class Pair<A, B>
    implements Entry<A, B>, Comparable<Pair<A, B>>, Serializable
{

    /**
     * The key for this {@code Pair}, not {@code null}.
     */
    private A key;

    /**
     * The value for this {@code Pair}, not {@code null}.
     */
    private B value;

    /**
     * Creates a {@code Pair} with the given key-value mapping.
     *
     * @param key The key for the {@code Pair}.
     * @param value The value for the {@code Pair}.
     * @throws NullPointerException if the given key is {@code null} or if the
     *         given {@code value} is {@code null}.
     */
    public Pair(A key, B value) {
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Returns the key corresponding with this {@code Pair}, not {@code null}.
     *
     * @return The key corresponding with this {@code Pair}.
     */
    @Override
    public A getKey() {
        return key;
    }

    /**
     * Returns the value corresponding to this {@code Pair}, not {@code null}.
     *
     * @return The value corresponding to this {@code Pair}.
     */
    @Override
    public B getValue() {
        return value;
    }

    /**
     * Replaces the key corresponding to this {@code Pair} with the given one,
     * returning the key previously stored.
     *
     * @param key The new key to be stored in this {@code Pair}.
     * @return The old key replaced by the given one.
     * @throws NullPointerException if the given key is {@code null}.
     */
    public A setKey(A key) {
        Objects.requireNonNull(key);
        A replaced = this.key;
        this.key = key;
        return replaced;
    }

    /**
     * Replaces the value corresponding to this {@code Pair} with the given one,
     * returning the value previously stored.
     *
     * @param value The new value to be stored in this {@code Pair}.
     * @return The old value replaced by the given one.
     * @throws NullPointerException if the given value is {@code null}.
     */
    @Override
    public B setValue(B value) {
        Objects.requireNonNull(value);
        B replaced = this.value;
        this.value = value;
        return replaced;
    }

    /**
     * Applies the given {@code Function} to this object, returning the result.
     *
     * @param mapper The mapping function.
     * @param <R> The type of the result.
     * @return The value produced by applying the given {@code Function} to this
     *         object.
     */
    public <R> R map(Function<Pair<? super A, ? super B>, ? extends R> mapper) {
        return mapper.apply(this);
    }

    /**
     * Compares the given {@code Pair} to this one for order, returning a
     * negative number, zero, or a positive number if this object is less than,
     * equal to, or greater than the given object, respectively.
     *
     * @param other The {@code Pair} to compare to this one.
     * @return A negative number, zero, or a positive number if this object is
     *         less than, equal to, or greater than the given object,
     *         respectively.
     */
    @Override
    public int compareTo(Pair<A, B> other) {
        return Objects.compare(this, other, Comparator.naturalOrder());
    }

    /**
     * Tests the given object against this one for equality, returning
     * {@code true} if and only if the given object has the same key-value
     * mapping as this one. {@code false} otherwise.
     *
     * @param other The object to test against this one.
     * @return {@code true} if the given object is identical to this one,
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) other;
        return Objects.equals(this.key, pair.key)
            && Objects.equals(this.value, pair.value);
    }

    /**
     * Computes and returns a hash code value for the key-value mapping in this
     * object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

}
