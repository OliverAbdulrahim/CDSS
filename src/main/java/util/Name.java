package util;

import java.util.Arrays;
import java.util.Optional;

/**
 * The {@code Name} class encapsulates title, first, middle, and last names into
 * a single object. These names may be formatted in {@link #firstLast()
 * "first last"}, {@link #lastFirst() "last first"}, and
 * {@link #lastCommaFirst() "last, first"} format. Additionally, they can be
 * retrieved individually.
 *
 * @author Oliver Abdulrahim
 */
public final class Name {

    /**
     * Specifies a default, "{@code null} object" name.
     */
    public static final Name NULL_NAME =
            new Name("Pres.", "Barack", "Hussein", "Obama");

    /**
     * Stores the title of this {@code Name}, for example, {@code "Mrs."} or
     * {@code "Dr."}.
     */
    private final Optional<String> title;

    /**
     * Stores the first name of this {@code Name}, for example,
     * {@code "Daniel"}.
     *
     * <p> This value must be specified during construction.
     */
    private final Optional<String> first;

    /**
     * Stores the middle name of this {@code Name}, for example,
     * {@code "Green"}.
     */
    private final Optional<String> middle;

    /**
     * Stores the last name of this {@code Name}, for example, {@code "Smith"}.
     *
     * <p> This value must be specified during construction.
     */
    private final Optional<String> last;

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
        this.title = asOptional(title);
        this.first = asOptional(first);
        this.middle = asOptional(middle);
        this.last = asOptional(last);
    }

    /**
     * Capitalizes the first character of the given {@code String} and returns
     * the result.
     *
     * @param str The {@code String} to capitalize
     * @return A capitalized version of the given {@code String}.
     */
    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Returns an {@code Optional} containing the specified {@code String}. If
     * the given value is not {@code null}, then it is formatted by the
     * {@link #capitalize(String)}} method.
     *
     * @param str The object to wrap with an {@code Optional}.
     * @return An {@code Optional} containing the given {@code String}.
     */
    private static Optional<String> asOptional(String str) {
        return Optional.ofNullable(str).map(Name :: capitalize);
    }

    /**
     * Returns the title of this {@code Name}, or an empty {@code String} if
     * none (or {@code null}) was provided.
     *
     * @return The title of this object.
     */
    public Optional<String> title() {
        return title;
    }

    /**
     * Returns the first name of this {@code Name}, or an empty {@code String}
     * if none (or {@code null}) was provided.
     *
     * @return The first name of this object.
     */
    public Optional<String> first() {
        return first;
    }

    /**
     * Returns the middle name of this {@code Name}, or an empty {@code String}
     * if none (or {@code null}) was provided.
     *
     * @return The middle name of this object.
     */
    public Optional<String> middle() {
        return middle;
    }

    /**
     * Returns the last name of this {@code Name}, or an empty {@code String} if
     * none (or {@code null}) was provided.
     *
     * @return The last name of this object.
     */
    public Optional<String> last() {
        return last;
    }

    /**
     * Returns a {@code String} containing all names stored within this object.
     * This includes the title, first, middle, and last names, in that order.
     *
     * @return The full name represented by this object.
     */
    public String fullName() {
        return format(title(), first(), middle(), last());
    }

    /**
     * Returns a {@code String} containing the first name followed by the last
     * name, delimited by a space character.
     *
     * @return The first name followed by the last name.
     */
    public String firstLast() {
        return format(first(), last());
    }

    /**
     * Returns a {@code String} containing the last name followed by the first
     * name, delimited by a space character.
     *
     * @return The first name followed by the last name.
     */
    public String lastFirst() {
        return format(last(), first());
    }

    /**
     * Returns a {@code String} containing the last name followed by the first
     * name, delimited by a comma and a space.
     *
     * @return The first name followed by the last name.
     */
    public String lastCommaFirst() {
        return format(last(), first()).replace(" ", ", ");
    }

    /**
     * Returns a {@code String} containing a concatenation of the values of the
     * given {@code Optional} objects, if present, delimited by a single space.
     *
     * @param what The {@code Optional} objects whose contents to format.
     * @return A {@code String} formatted to contain the contents of the given
     *         {@code Optional} objects.
     */
    @SafeVarargs
    private static String format(Optional<String>... what) {
        return Arrays
                .stream(what)
                .map(o -> o.map(s -> s + ' ').orElse(""))
                .reduce("", String :: concat);
    }

    /**
     * Returns a {@code String} representation of this name, containing the
     * first and last names stored by this class, if present, in that order.
     *
     * @return A {@code String} representation of this name.
     * @see #firstLast()
     */
    @Override
    public String toString() {
        return firstLast();
    }

}
