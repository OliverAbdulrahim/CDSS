package model;

import sql.SQLObject;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A mutable implementation of {@link SQLObject} that encapsulates information
 * about a patient in a medical context.
 *
 * <p> This is a value-based class. Use of identity-sensitive operations
 * (including reference equality ({@code ==}), identity hash code, or
 * synchronization) on instances of {@code Patient} may have unpredictable
 * results and should be avoided. The {@link #equals} method should be used for
 * comparisons.
 *
 * @implSpec This class is mutable, and therefore not thread-safe!
 *
 * @author Oliver Abdulrahim
 */
public class Patient
    extends SQLObject<Patient>
{

    /**
     * The age group of this {@code Patient}.
     */
    private AgeGroup ageGroup;

    /**
     * The gender of this {@code Patient}.
     */
    private Gender gender;

    /**
     * The birth date of this {@code Patient}.
     */
    private LocalDate birthDate;

    /**
     * The ailments predicted to belong to this {@code Patient}.
     */
    private Set<Ailment> ailments;

    /**
     * The symptoms of this {@code Patient}.
     */
    private Set<Symptom> symptoms;

    /**
     * Constructs a {@code Patient} that is an exact copy of the given one.
     *
     * @param other The object to copy.
     */
    public Patient(Patient other) {
        this(other.getID(),
             other.getName(),
             other.getBirthDate(),
             other.getGender());
        this.ailments.addAll(other.ailments);
    }

    /**
     * Constructs a {@code Patient} using the given arguments.
     *
     * @param id The identification number for the {@code Patient}.
     * @param name The name of the {@code Patient}.
     * @param birthDate The birth date of the {@code Patient}.
     * @param gender The gender of the {@code Patient}.
     */
    public Patient(int id, String name, LocalDate birthDate, Gender gender) {
        super(id, name);
        setGender(gender);
        setBirthDate(birthDate);
        setAgeGroup(AgeGroup.asAgeGroup(birthDate).orElse(AgeGroup.ADULT));
        this.ailments = new HashSet<>();
    }

    /**
     * Returns the age group of this {@code Patient}.
     *
     * @return The age group of this {@code Patient}.
     */
    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    /**
     * Sets the age group of this {@code Patient} to the given object.
     *
     * @param ageGroup The new birth date for this object.
     * @throws NullPointerException if the given argument is {@code null}.
     */
    public void setAgeGroup(AgeGroup ageGroup) {
        Objects.requireNonNull(ageGroup);
        mutated();
        this.ageGroup = ageGroup;
    }

    /**
     * Returns the birth date of this {@code Patient}.
     *
     * @return The birth date of this {@code Patient}.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date of this {@code Patient} to the given object.
     *
     * @param birthDate The new birth date of the {@code Patient}.
     * @throws NullPointerException if the given argument is {@code null}.
     */
    public void setBirthDate(LocalDate birthDate) {
        Objects.requireNonNull(birthDate);
        mutated();
        this.birthDate = birthDate;
    }

    /**
     * Returns the gender of this {@code Patient}.
     *
     * @return The gender of this {@code Patient}.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender of this {@code Patient} to the given object.
     *
     * @param gender The new gender of the {@code Patient}.
     * @throws NullPointerException if the given argument is {@code null}.
     */
    public void setGender(Gender gender) {
        Objects.requireNonNull(birthDate);
        mutated();
        this.gender = gender;
    }

    /**
     * Returns a {@code Set} copy of the ailments of this object.
     *
     * @return A {@code Set} containing the ailments of this object.
     */
    public Set<Ailment> getAilments() {
        return new HashSet<>(ailments);
    }

    /**
     * Sets the symptoms of this {@code Ailment} to the given object.
     *
     * @param ailments The new ailments for this object.
     * @throws NullPointerException if the given {@code Set} is {@code null}.
     */
    public void setSymptoms(Set<Ailment> ailments) {
        Objects.requireNonNull(ailments);
        mutated();
        this.ailments = ailments;
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
     * Sets the symptoms of this {@code Patient} to the given object.
     *
     * @param symptom The new symptoms for this object.
     * @throws NullPointerException if the given {@code Set} is {@code null}.
     */
    public void setAilments(Set<Symptom> symptom) {
        Objects.requireNonNull(symptom);
        mutated();
        this.symptoms = symptom;
    }

    /**
     * Compares the given {@code Patient} to this one for order, returning a
     * negative number, zero, or a positive number if this object is less than,
     * equal to, or greater than the given object, respectively.
     *
     * @param other The {@code Patient} to compare to this one.
     * @return A negative number, zero, or a positive number if this object is
     *         less than, equal to, or greater than the given object,
     *         respectively.
     */
    @Override
    public int compareTo(Patient other) {
        return this.getName().compareTo(other.getName())
             + this.ageGroup.compareTo(other.ageGroup)
             + this.birthDate.compareTo(other.birthDate)
             + this.gender.compareTo(other.gender);
    }

}
