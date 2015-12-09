package sql;

public final class Table<T> {

    private final String tableName;

    private final Class<T> targetClass;

    public Table(String tableName, Class<T> targetClass) {
        this.tableName = tableName;
        this.targetClass = targetClass;
    }

    public String getTableName() {
        return tableName;
    }

    public Class<T> getTargetClass() {
        return targetClass;
    }

    @Override
    public String toString() {
        return tableName;
    }

}
