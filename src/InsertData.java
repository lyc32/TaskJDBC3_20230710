import java.sql.Connection;
import java.sql.SQLException;

public interface InsertData
{
    public int insertData(Connection connection, String tableName, String columList, String valueList);
}
