package model;

import sql.SQLObject;
import util.Collections2;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A mutable implementation of {@link SQLObject} that encapsulates a set of
 * {@code Symptom} objects.
 *
 * <p> This is a value-based class. Use of identity-sensitive operations
 * (including reference equality ({@code ==}), identity hash code, or
 * synchronization) on instances of {@code Ailment} may have unpredictable
 * results and should be avoided. The {@link #equals} method should be used for
 * comparisons.
 *
 * @implSpec This class is mutable, and therefore not thread-safe!
 * @author Oliver Abdulrahim
 */
public final class Ailment
    extends SQLObject<Ailment>
{

    /**
     * The symptoms associated with this {@code Ailment}, not {@code null}.
     */
    private Set<Symptom> symptoms;

    /**
     * Constructs a {@code Patient} using the given arguments.
     *
     * @param id The identification number for the {@code Ailment}.
     * @param name The name of the {@code Ailment}.
     */
    public Ailment(int id, String name) {
        super(id, name);
        this.symptoms = new HashSet<>();
    }

    /**
     * Returns a {@code Set} copy of the symptoms of this object.
     *
     * @return A {@code Set} containing the symptoms of this object.
     */
    public Set<Symptom> getSymptoms() {
        return new HashSet<>(symptoms);
    }

    /**
     * Sets the symptoms of this {@code Ailment} to the given object.
     *
     * @param symptoms The new symptoms for this object.
     * @throws NullPointerException if the given {@code Set} is {@code null}.
     */
    public void setSymptoms(Set<Symptom> symptoms) {
        Objects.requireNonNull(symptoms);
        mutated();
        this.symptoms = symptoms;
    }

    /**
     * Inserts the given object into the symptoms of this object, returning
     * {@code true} if the operation was successful, {@code false} otherwise.
     *
     * @param symptom The symptom to add.
     * @return {@code true} if the add operation was successful, {@code false}
     *         otherwise.
     * @throws NullPointerException if the given {@code Symptom} is
     *         {@code null}.
     */
    public boolean addSymptom(Symptom symptom) {
        Objects.requireNonNull(symptom);
        mutated();
        return symptoms.add(symptom);
    }

    /**
     * Compares the given {@code Ailment} to this one for order, returning a
     * negative number, zero, or a positive number if this object is less than,
     * equal to, or greater than the given object, respectively.
     *
     * @param other The {@code Ailment} to compare to this one.
     * @return A negative number, zero, or a positive number if this object is
     *         less than, equal to, or greater than the given object,
     *         respectively.
     */
    @Override
    public int compareTo(Ailment other) {
        return Collections2.compare(this.getSymptoms(), other.getSymptoms());
    }

}
