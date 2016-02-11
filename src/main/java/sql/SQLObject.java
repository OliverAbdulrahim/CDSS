package sql;

import util.reflect.ReflectiveToStringHelper;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Abstract implementation of a Java object that persists in SQL.
 *
 * @param <T> The type of object that this class represents, upper bounded by
 *        this class.
 * @author Oliver Abdulrahim
 */
public abstract class SQLObject<T extends SQLObject>
    implements Comparable<T>, Serializable
{

    /**
     * The identification number for this object.
     */
    private int id;

    /**
     * The name of this object.
     */
    private String name;

    /**
     * Stores the last time this object was mutated.
     */
    private LocalDate lastUpdated;

    /**
     * Helper variable for converting this object to an SQL {@code INSERT} or
     * {@code SET} statement.
     */
    private ReflectiveToStringHelper f;

    /**
     * Constructs a new {@code SQLObject} with the given arguments.
     *
     * @param id The identification number for the object.
     * @param name The name for the object.
     * @throws NullPointerException if any of the given arguments is
     *         {@code null}.
     */
    protected SQLObject(int id, String name) {
        setID(id);
        setName(name);
        setLastUpdated(LocalDate.now());
    }

    /**
     * Returns the identification number of this {@code SQLObject}.
     *
     * @return The identification number of this {@code SQLObject}.
     */
    public int getID() {
        return id;
    }

    /**
     * Sets the identification number of this {@code SQLObject} to the given
     * one.
     *
     * @param id The new identification number of the {@code SQLObject}.
     */
    public void setID(int id) {
        mutated();
        this.id = id;
    }

    /**
     * Returns the name of this {@code SQLObject}.
     *
     * @return The name of this {@code SQLObject}.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this {@code SQLObject} to the given one.
     *
     * @param name The new name of the {@code SQLObject}.
     * @throws NullPointerException if the given argument is {@code null}.
     */
    public void setName(String name) {
        Objects.requireNonNull(name);
        mutated();
        this.name = name;
    }

    /**
     * Returns the last updated date of this object.
     *
     * @return The last date this object was mutated.
     */
    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets the last update date for this {@code Patient} to the object.
     *
     * @param lastUpdated The new last update date for this object.
     * @throws NullPointerException if the given argument is {@code null}.
     */
    public void setLastUpdated(LocalDate lastUpdated) {
        Objects.requireNonNull(lastUpdated);
        this.lastUpdated = lastUpdated;
    }

    /**
     * Handles updating of the {@link #lastUpdated} field for this object.
     */
    protected void mutated() {
        setLastUpdated(LocalDate.now());
    }

    /**
     * Tests the given object against this one for equality, returning
     * {@code true} if and only if the given object has the same identification
     * number as this one, {@code false} otherwise.
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
        SQLObject sqlObj = (SQLObject) other;
        return this.id == sqlObj.id;
    }

    /**
     * Computes and returns a hash code value for this object by using its
     * identification number.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Compares the given object to this one for order, returning a negative
     * number, zero, or a positive number if this object is less than, equal to,
     * or greater than the given object, respectively.
     *
     * @param other The object to compare to this one.
     * @return A negative number, zero, or a positive number if this object is
     *         less than, equal to, or greater than the given object,
     *         respectively.
     */
    @Override
    public int compareTo(T other) {
        return Integer.compare(this.getID(), other.getID());
    }

    /**
     * Returns a {@code String} containing the names of the attributes of this
     * object formatted to their values.
     *
     * @return A {@code String} containing the attributes of this object.
     */
    @Override
    public String toString() {
        f = new ReflectiveToStringHelper(this);
        return f.toString();
    }

    /**
     * Returns a {@code String} containing the names of the attributes of this
     * object formatted to their values for use in an SQL {@code INSERT}
     * statement.
     *
     * @return A {@code String} containing the attributes of this object
     *         suitable for an SQL {@code INSERT} statement.
     */
    public String toINSERTString() {
        f = new ReflectiveToStringHelper(this);
        return f.toString(e -> e.getKey() + '=' + f.get(e.getKey()));
    }

    /**
     * Returns a {@code String} containing the names of the attributes of this
     * object formatted to their values for use in an SQL {@code SET} statement.
     *
     * @return A {@code String} containing the attributes of this object
     *         suitable for an SQL {@code SET} statement.
     */
    public String toSETString() {
        f = new ReflectiveToStringHelper(this);
        return f.toString(e -> f.get(e.getKey()));
    }


}
