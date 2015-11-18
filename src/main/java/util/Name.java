package util;

public final class Name {

    public static final Name NULL_NAME =
            new Name("Pres.", "Barack", "Hussein", "Obama");

    private final String title;

    private final String first;

    private final String middle;

    private final String last;

    public Name(String first, String last) {
        this("", first, "", last);
    }

    public Name(String first, String middle, String last) {
        this("", first, middle, last);
    }

    public Name(String title, String first, String middle, String last) {
        this.title = title;
        this.first = first;
        this.middle = middle;
        this.last = last;
    }

    public String title() {
        return title;
    }

    public String first() {
        return first;
    }

    public String middle() {
        return middle;
    }

    public String last() {
        return last;
    }

    public String fullName() {
        return title() + ' ' + first() + ' ' + middle()  + ' ' + last();
    }

    public String firstLast() {
        return first() + ' ' + last();
    }

    public String lastFirst() {
        return last() + ' ' + first();
    }

    public String lastCommaFirst() {
        return last() + ',' + ' ' + first();
    }

    @Override
    public String toString() {
        return firstLast();
    }

}
