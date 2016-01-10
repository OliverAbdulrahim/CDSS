package sql.access;

import model.Ailment;
import model.Symptom;
import sql.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class SymptomAccessor
    extends AbstractSQLAccessor<Symptom>
{

    public SymptomAccessor(Connection connection) {
        super(connection, Database.SYMPTOM_TABLE);
    }

    public Set<Symptom> collect(Ailment ailment) {
        throw new UnsupportedOperationException();
    }

    public Symptom minimal() {
        throw new UnsupportedOperationException();
    }

    public Symptom maximal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Symptom createFromSQL(ResultSet result) throws SQLException {
        return createFromSQL(Symptom.class, result);
    }

}
