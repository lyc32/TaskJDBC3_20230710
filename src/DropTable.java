import java.sql.Connection;
import java.sql.SQLException;

public interface DropTable
{
    public Boolean dropTable(Connection connection, String tableName);
}
