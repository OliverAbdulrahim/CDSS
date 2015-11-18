package util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

public final class Pair<A, B>
    implements Entry<A, B>, Comparable<Pair<A, B>>, Serializable
{

    private A key;

    private B value;

    public Pair(A key, B value) {
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public A getKey() {
        return key;
    }

    @Override
    public B getValue() {
        return value;
    }

    public A setKey(A key) {
        Objects.requireNonNull(key);
        A replaced = this.key;
        this.key = key;
        return replaced;
    }

    @Override
    public B setValue(B value) {
        Objects.requireNonNull(value);
        B replaced = this.value;
        this.value = value;
        return replaced;
    }

    public <R> R map(Function<Pair<? super A, ? super B>, ? extends R> mapper) {
        return mapper.apply(this);
    }

    @Override
    public int compareTo(Pair<A, B> other) {
        return Objects.compare(this, other, Comparator.naturalOrder());
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

}
