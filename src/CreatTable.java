import java.sql.Connection;
import java.sql.SQLException;

public interface CreatTable
{
    public Boolean creatTable(Connection connection, String tableName, String valueList);
}
