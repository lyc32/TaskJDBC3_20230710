import com.sun.net.httpserver.Authenticator;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Runner
{
    Runner(DataBaseConfig dataBaseConfig, Scanner scanner)
    {
        System.out.println("[Try to Connect DataBase]");
        ConnectDB connectDB = new ConnectDB(dataBaseConfig.driver, dataBaseConfig.url, dataBaseConfig.userName, dataBaseConfig.password);
        for(;true;)
        {
            connectDB.connectToDataBase();
            if(connectDB.getConnection() != null)
            {
                System.out.println("[Connect Successful]");
                break;
            }
            else
            {
                System.out.println("[Connect Failed]\nPlease input any key to Re-Connect, Entry 'Q' to quit");
                String tmp = scanner.next();
                if(tmp.equals("Q"))
                {
                    System.exit(0);
                }
                System.out.println("[Try to Re-Connect DataBase]");
            }

        }


        int choice = -1;
        for(;true;)
        {
            if(choice < 0)
            {
                System.out.println("===========================");
                System.out.println("Please Enter Your Choice\n 1.Search Product\n 2.Edit Product\n 3.Add Product\n 4.Delete Product\n 5.List All Product\n 6.Re-Create 'Product' Table\n 7.Reset 'Product' Table\n 8.Delete 'Product' Table\n 9.Exit");
                for (;true;)
                {
                    try
                    {
                        choice = scanner.nextInt();
                        if(choice>=1 && choice <= 9)
                        {
                            break;
                        }
                        else
                        {
                            System.out.println("Wrong choice, please try again");
                            scanner.next();
                        }
                    }
                    catch (InputMismatchException e)
                    {
                        System.out.println("Please Enter a Int Number");
                        scanner.next();
                    }
                }
            }
            if(choice == 1)
            {
                System.out.println("[Search Product]");
                System.out.println("Please Enter Product ID");
                String id = scanner.next();
                ResultSet resultSet = (new JdbcCRUD()).searchData(connectDB.getConnection(), "*", "Product", "pid = " + id);
                if(resultSet != null)
                {
                    System.out.println(String.format("%4s %15s %4s %6s", "ID", "Product Name", "Qty", "Price"));
                    System.out.println("---------------------------------");
                    try
                    {
                        while (resultSet.next())
                        {
                            String list = String.format("%4d %15s %4d  %4.2f", resultSet.getInt("Pid"), resultSet.getString("Pname"), resultSet.getInt("Qty"), resultSet.getFloat("Price"));
                            System.out.println(list);
                        }
                    }
                    catch (SQLException e)
                    {
                        throw new RuntimeException(e);
                    }
                    System.out.println("---------------------------------\n");
                    choice = -1;
                }
                else
                {
                    System.out.println("[Error] Can Not Get Product List\n Please input any key to try again, Entry 'Q' to skip");
                    String tmp = scanner.next();
                    if(tmp.equals("Q"))
                    {
                        choice = -1;
                    }
                }
            }
            else if(choice ==2)
            {
                System.out.println("[Edit Product]");
                System.out.println("---------------------------------");
                int result = 0;
                for(;true;)
                {
                    try
                    {
                        System.out.println("please input p_id:");
                        int pid = scanner.nextInt();
                        System.out.println("please input p_name:");
                        String pname = scanner.next();
                        System.out.println("please input qty:");
                        int qty = scanner.nextInt();
                        System.out.println("please input price:");
                        float price = scanner.nextFloat();
                        result = new JdbcCRUD().updateData(connectDB.getConnection(), "`product`", "pname='" + pname + "', qty=" +qty + ", price=" + price, "pid = " + pid);
                        if(result != 0)
                        {
                            System.out.println("[success] Update Product\n");
                        }
                        else
                        {
                            System.out.println("[Failed] Update Product, Please try again\n");

                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("[Exception] " + e);
                    }
                    System.out.println("Enter any key to continue, Entry 'Q' to skip");
                    String tmp = scanner.next();
                    if(tmp.equals("Q"))
                    {
                        choice = -1;
                        break;
                    }

                }
            }
            else if(choice == 3)
            {
                System.out.println("[Add Product]");
                System.out.println("---------------------------------");
                int result = 0;
                for(;true;)
                {
                    try
                    {
                        System.out.println("please input p_id:");
                        int pid = scanner.nextInt();
                        System.out.println("please input p_name:");
                        String pname = scanner.next();
                        System.out.println("please input qty:");
                        int qty = scanner.nextInt();
                        System.out.println("please input price:");
                        float price = scanner.nextFloat();
                        result = new JdbcCRUD().insertData(connectDB.getConnection(), "`product`", "(`Pid`, `Pname`, `Qty`, `Price`)", "("+ pid + ", '" + pname + "'," +qty + "," + price + ")");
                        if(result != 0)
                        {
                            System.out.println("[success] Add Product\n");
                        }
                        else
                        {
                            System.out.println("[Failed] Add Product, Please try again\n");
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println("[Exception] please try again\n");
                        scanner.next();
                    }
                    System.out.println("do you want to keep adding?\nEnter any key to continue, Entry 'Q' to skip");
                    String tmp = scanner.next();
                    if(tmp.equals("Q"))
                    {
                        choice = -1;
                        break;
                    }
                }
            }
            else if(choice == 4)
            {
                System.out.println("[Delete Product]");
                System.out.println("Please Enter Product ID");
                String id = scanner.next();
                int result = (new JdbcCRUD()).deleteData(connectDB.getConnection(), "Product", "pid = " + id);
                if(result != 0)
                {
                    System.out.println("[Success] Delete Product\n");
                    choice = -1;
                }
                else
                {
                    System.out.println("[Error] Can Not Find or Delete Product\n Please input any key to try again, Entry 'Q' to skip");
                    String tmp = scanner.next();
                    if(tmp.equals("Q"))
                    {
                        choice = -1;
                    }
                }
            }
            else if(choice == 5)
            {
                System.out.println("[List All Product]");
                ResultSet resultSet = (new JdbcCRUD()).getAllData(connectDB.getConnection(), "*", "Product");
                if(resultSet != null)
                {
                    System.out.println(String.format("%4s %15s %4s %6s", "ID", "Product Name", "Qty", "Price"));
                    System.out.println("---------------------------------");
                    try
                    {
                        while (resultSet.next())
                        {
                            String list = String.format("%4d %15s %4d  %4.2f", resultSet.getInt("Pid"), resultSet.getString("Pname"), resultSet.getInt("Qty"), resultSet.getFloat("Price"));
                            System.out.println(list);
                        }
                    }
                    catch (SQLException e)
                    {
                        throw new RuntimeException(e);
                    }
                    System.out.println("---------------------------------\n");
                    choice = -1;
                }
                else
                {
                    System.out.println("[Error] Can Not Get Product List\n Please input any key to try again, Entry 'Q' to skip");
                    String tmp = scanner.next();
                    if(tmp.equals("Q"))
                    {
                        choice = -1;
                    }
                }
            }
            else if(choice == 6)
            {
                System.out.println("[Re-Create 'Product' Table]");
                if((new TableOperation()).creatTable(connectDB.getConnection(), "Product","(Pid Int(5), Pname varchar(30), Qty Int(4), Price float(10))"))
                {
                    System.out.println("[Success] Re-Create 'Product' Table\n");
                    choice = -1;
                }
                else
                {
                    System.out.println("[Error] Re-Create 'Product' Table Failed\n Please input any key to try again, Entry 'Q' to skip");
                    String tmp = scanner.next();
                    if(tmp.equals("Q"))
                    {
                        choice = -1;
                    }
                }
            }
            else if(choice == 7)
            {
                System.out.println("[Reset 'Product' Table]");
                if((new TableOperation()).resetTable(connectDB.getConnection(), "Product"))
                {
                    System.out.println("[Success] Reset 'Product' Table\n");
                    choice = -1;
                }
                else
                {
                    System.out.println("[Error] Reset 'Product' Table Failed\n Please input any key to try again, Entry 'Q' to skip");
                    String tmp = scanner.next();
                    if(tmp.equals("Q"))
                    {
                        choice = -1;
                    }
                }
            }
            else if(choice == 8)
            {
                System.out.println("[Delete 'Product' Table]");
                if((new TableOperation()).dropTable(connectDB.getConnection(), "Product"))
                {
                    System.out.println("[Success] Delete 'Product' Table\n");
                    choice = -1;
                }
                else
                {
                    System.out.println("[Error] Delete 'Product' Table Failed\n Please input any key to try again, Entry 'Q' to skip");
                    String tmp = scanner.next();
                    if(tmp.equals("Q"))
                    {
                        choice = -1;
                    }
                }
            }
            else
            {
                break;
            }
        }
        connectDB.closeConnection();
    }
}
