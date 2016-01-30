package model;

import sql.SQLObject;

/**
 * A mutable implementation of {@link SQLObject} that encapsulates a symptom in
 * a medical context.
 *
 * <p> This is a value-based class. Use of identity-sensitive operations
 * (including reference equality ({@code ==}), identity hash code, or
 * synchronization) on instances of {@code Symptom} may have unpredictable
 * results and should be avoided. The {@link #equals} method should be used for
 * comparisons.
 *
 * @implSpec This class is mutable, and therefore not thread-safe!
 * @author Oliver Abdulrahim
 */
public final class Symptom
    extends SQLObject<Symptom>
{

    /**
     * Constructs a {@code Symptom} with the given values.
     *
     * @param id The identification number for the {@code Symptom}.
     * @param name The name of the {@code Symptom}.
     */
    public Symptom(int id, String name) {
        super(id, name);
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
    public int compareTo(Symptom other) {
        return this.getName().compareTo(other.getName());
    }

}
