package model;

/**
 * Contains an enumeration genders.
 *
 * @implNote {@link Enum The enum class} is already {@code Comparable}, and
 *           the {@code super} implementation is sufficient in this case
 *           (the method is not actually virtual anyway). Just implement the
 *           interface here for the sake of clarity.
 */
public enum Gender
    implements Comparable<Gender>
{
    MALE, FEMALE;

    /**
     * Returns a {@code String} of length {@code 1} containing the first
     * character of the name of this {@code Gender}.
     *
     * @return A {@code String} containing a single-character representation of
     *         this {@code Gender}.
     */
    @Override
    public String toString() {
        // Can use charAt, but would also have to use String.valueOf(char).
        // That's kind of ugly and so is this comment.
        return super.toString().substring(0, 1);
    }

}
