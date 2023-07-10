import java.sql.Connection;
import java.sql.SQLException;

public interface DeleteData
{
    public int deleteData(Connection connection, String tableName, String whereCondition);
}
