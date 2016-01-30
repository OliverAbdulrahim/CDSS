package model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

/**
 * Enumeration of age groups, each storing a range {@link LocalDate} objects.
 *
 * @author Oliver Abdulrahim
 */
public enum AgeGroup {

    /**
     * Enumeration representing ages between December 31, +999999999 and January
     * 1, +1998.
     */
    UNDERAGE(LocalDate.MAX, LocalDate.of(1998, 1, 1)),

    /**
     * Enumeration representing ages between December 31, +1997 and January 1,
     * +1952.
     */
    ADULT(LocalDate.of(1997, 12, 31), LocalDate.of(1952, 1, 1)),

    /**
     * Enumeration representing ages between December 31, +1951 and January 1,
     * -999999999.
     */
    ELDERLY(LocalDate.of(1951, 1, 1), LocalDate.MIN);

    /**
     * The start date of the enumeration, inclusive.
     */
    private LocalDate startInclusive;

    /**
     * The end date of the enumeration, inclusive.
     */
    private LocalDate endInclusive;

    /**
     * Constructs an {@code AgeGroup} with the given start and end dates.
     *
     * @param lowerInclusive The start date of the enumeration, inclusive.
     * @param upperInclusive The end date of the enumeration, inclusive.
     */
    AgeGroup(LocalDate lowerInclusive, LocalDate upperInclusive) {
        this.startInclusive = lowerInclusive;
        this.endInclusive = upperInclusive;
    }

    /**
     * Returns an {@code Optional} containing the enumeration that includes the
     * given date, or an empty one if there is no such element.
     *
     * @param date The date to return an enumeration for.
     * @return An {@code Optional} containing the enumeration that includes the
     *         given date.
     */
    public static Optional<AgeGroup> asAgeGroup(LocalDate date) {
        return ALL
                .stream()
                .filter(ageGroup -> ageGroup.includes(date))
                .findFirst();
    }

    /**
     * Tests if the given date is included in this {@code AgeGroup}, returning
     * {@code true} if it overlaps, {@code false} otherwise.
     *
     * @param date The date to test.
     * @return {@code true} if the given date is included in this
     *         {@code AgeGroup}, {@code false} otherwise.
     */
    // Temporal is apparently not Comparable - use LocalDate (more concrete)
    public boolean includes(LocalDate date) {
        return !(date.isBefore(startInclusive) || date.isAfter(endInclusive));
    }

    /**
     * Returns a {@code String} representation of the start and end dates of
     * this {@code AgeGroup}.
     *
     * @return A {@code String} representation of this object.
     */
    @Override
    public String toString() {
        return startInclusive + " to " + endInclusive;
    }

    /**
     * Defines a set containing all {@code AgeGroup} objects enumerated in this
     * class.
     */
    public static final Set<AgeGroup> ALL =
            Collections.unmodifiableSet(EnumSet.allOf(AgeGroup.class));

}
