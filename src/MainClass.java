import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.util.Scanner;

public class MainClass
{
    public static void main(String[] args)
    {
        new Runner(new DataBaseConfig(), new Scanner(System.in));
    }
}
