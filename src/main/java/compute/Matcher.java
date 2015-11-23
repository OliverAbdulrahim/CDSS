package compute;

import java.util.Collection;
import java.util.TreeSet;

@FunctionalInterface
public interface Matcher<T extends Comparable<? super T>> {

    T compute(Collection<? extends T> data, T t);

    static <T extends Comparable<? super T>> Matcher<T> of() {
        return new MeanSquaredErrorMatcher<>();
    }

}

class MeanSquaredErrorMatcher<T extends Comparable<? super T>>
    implements Matcher<T>
{

    @Override
    public T compute(Collection<? extends T> data, T t) {
        TreeSet<T> diff = new TreeSet<>(this :: meanSquaredError);
        T candidate = diff.ceiling(t);
        return candidate;
    }

    private int meanSquaredError(T first, T second) {
        int error = first.compareTo(second);
        return (int) Math.pow(error, 2) / 2;
    }

}
