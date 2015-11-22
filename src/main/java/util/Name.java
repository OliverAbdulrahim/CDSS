package util;

/**
 * The {@code Name} class encapsulates title, first, middle, and last names into
 * a single object. These names may be formatted in {@link #firstLast()
 * "first last"},  {@link #lastFirst() "last first"}, and
 * {@link #lastCommaFirst() "last, first"} format. Additionally, they can be
 * retrieved individually.
 *
 * @author Oliver Abdulrahim
 */
public final class Name {

    public static final Name NULL_NAME =
            new Name("Pres.", "Barack", "Hussein", "Obama");

    /**
     * Stores the title of this {@code Name}, for example, {@code "Mrs."} or
     * {@code "Dr."}.
     */
    private final String title;

    /**
     * Stores the first name of this {@code Name}, for example,
     * {@code "Daniel"}.
     *
     * <p> This value must be specified during construction.
     */
    private final String first;

    /**
     * Stores the middle name of this {@code Name}, for example,
     * {@code "Green"}.
     */
    private final String middle;

    /**
     * Stores the last name of this {@code Name}, for example, {@code "Smith"}.
     *
     * <p> This value must be specified during construction.
     */
    private final String last;

    /**
     * Constructs a {@code Name} with the first and last given arguments.
     *
     * @param first The first name of the object.
     * @param last THe last name of the object.
     */
    public Name(String first, String last) {
        this("", first, "", last);
    }

    /**
     * Constructs a {@code Name} with the first, middle, and last given
     * arguments.
     *
     * @param first The first name of the object.
     * @param middle The middle name of the object.
     * @param last The last name of the object.
     */
    public Name(String first, String middle, String last) {
        this("", first, middle, last);
    }

    /**
     * Constructs a {@code Name} with the first, middle, and last given
     * arguments.
     *
     * <p> This constructor normalizes the capitalization of the given names.
     *
     * @param title The title of the object.
     * @param first The first name of the object.
     * @param middle The middle name of the object.
     * @param last The last name of the object.
     */
    public Name(String title, String first, String middle, String last) {
        this.title = title;
        this.first = first;
        this.middle = middle;
        this.last = last;
    }

    /**
     * Returns the title of this {@code Name}, or an empty {@code String} if
     * none (or {@code null}) was provided.
     *
     * @return The title of this object.
     */
    public String title() {
        return title;
    }

    /**
     * Returns the first name of this {@code Name}, or an empty {@code String}
     * if none (or {@code null}) was provided.
     *
     * @return The first name of this object.
     */
    public String first() {
        return first;
    }

    /**
     * Returns the middle name of this {@code Name}, or an empty {@code String}
     * if none (or {@code null}) was provided.
     *
     * @return The middle name of this object.
     */
    public String middle() {
        return middle;
    }

    /**
     * Returns the last name of this {@code Name}, or an empty {@code String} if
     * none (or {@code null}) was provided.
     *
     * @return The last name of this object.
     */
    public String last() {
        return last;
    }

    /**
     * Returns a {@code String} containing all names stored within this object.
     * This includes the title, first, middle, and last names, in that order.
     *
     * @return The full name represented by this object.
     */
    public String fullName() {
        return title() + ' ' + first() + ' ' + middle()  + ' ' + last();
    }

    /**
     * Returns a {@code String} containing the first name followed by the last
     * name, delimited by a space character.
     *
     * @return The first name followed by the last name.
     */
    public String firstLast() {
        return first() + ' ' + last();
    }

    /**
     * Returns a {@code String} containing the last name followed by the first
     * name, delimited by a space character.
     *
     * @return The first name followed by the last name.
     */
    public String lastFirst() {
        return last() + ' ' + first();
    }

    /**
     * Returns a {@code String} containing the last name followed by the first
     * name, delimited by a comma and a space.
     *
     * @return The first name followed by the last name.
     */
    public String lastCommaFirst() {
        return last() + ',' + ' ' + first();
    }

    /**
     * Returns a {@code String} representation of this name, containing the
     * title, first, middle, and last names, in that order.
     *
     * @return A {@code String} representation of this name.
     * @see #firstLast()
     */
    @Override
    public String toString() {
        return firstLast();
    }

}
