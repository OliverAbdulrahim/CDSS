package sql;

import util.AutoToStringHelper;

import java.io.Serializable;
import java.util.Objects;

public abstract class SQLObject<T extends SQLObject>
    implements Comparable<T>, Serializable
{

    private int id;

    private String name;

    private AutoToStringHelper f;

    protected SQLObject(int id, String name) {
        setID(id);
        setName(name);
    }

    public int id() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        SQLObject other = (SQLObject) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        f = new AutoToStringHelper(this);
        return f.toString();
    }

    public String toINSERTString() { // TODO - Detail that this is suitable for INSERT INTO * VALUES (resultant)
        f = new AutoToStringHelper(this);
        return f.toString(e -> e.getKey() + '=' + f.get(e.getKey()));
    }

    public String toSETString() { // TODO - Detail that this is suitable for UPDATE * SET resultant
        f = new AutoToStringHelper(this);
        return f.toString(e -> f.get(e.getKey()));
    }

    @Override
    public int compareTo(T other) {
        return Integer.compare(this.id(), other.id());
    }

}
