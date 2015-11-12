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
 *
 * @author Oliver Abdulrahim
 */
public final class Symptom
    extends SQLObject<Symptom>
{

    public Symptom(int id, String name) {
        super(id, name);
    }

    @Override
    public int compareTo(Symptom other) {
        return this.name().compareTo(other.name());
    }

}
