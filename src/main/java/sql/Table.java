package sql;

/**
 * A class representation of an SQL table.
 *
 * @param <T> The type of Java object the table represents.
 * @author Oliver Abdulrahim
 */
public final class Table<T> {

    /**
     * The name of the table in SQL.
     */
    private final String name;

    /**
     * The {@code Class} of the Java object this {@code Table} represents.
     */
    private final Class<T> targetClass;

    /**
     * Constructs a table with the given arguments.
     *
     * @param name The name of the {@code Table}.
     * @param targetClass The {@code Class} the {@code Table} represents.
     */
    public Table(String name, Class<T> targetClass) {
        this.name = name;
        this.targetClass = targetClass;
    }

    /**
     * Returns the name of the table.
     *
     * @return The name of the table.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the {@code Class} of the Java object this {@code Table}
     * represents.
     *
     * @return the {@code Class} this {@code Table} represents.
     */
    public Class<T> getTargetClass() {
        return targetClass;
    }

    /**
     * Returns a {@code String} containing the name of this {@code Table}.
     *
     * @return A {@code String} containing the name of this {@code Table}.
     */
    @Override
    public String toString() {
        return name;
    }

}
