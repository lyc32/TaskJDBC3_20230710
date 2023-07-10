import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB
{
    private String driver;
    private String url;
    private String userName;
    private String password;
    private Connection connection;

    public ConnectDB(String driver, String url, String userName, String password)
    {
        this.driver = driver;
        this.url = url;
        this.userName = userName;
        this.password = password;
        connection = null;
    }

    public void connectToDataBase()
    {
        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,userName,password);
        }
        catch (SQLException e)
        {
            System.out.println("[Connect Failed]\n" + e);
            connection = null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("[Load Driver Error]\n" + e);
            connection = null;
        }
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void closeConnection()
    {
        try
        {
            if( connection != null)
            {
                this.connection.close();
            }
            else
            {
                System.out.println("[Error] There is no DataBase Connection");
            }
        }
        catch (SQLException e)
        {
            System.out.println("[Close Connection Failed]\n" + e);
        }
    }
}
