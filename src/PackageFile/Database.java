package PackageFile;

import java.sql.*;


public class Database {

    private final String url;
    private final String user;
    private final  String password;

    public Database(String url, String user, String password) {
        this.url= url;
        this.user= user;
        this.password = password;
    }

        public Connection db_connect(){

        try{

        Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e){
            System.out.println(e.getMessage());
                    }
        try{
        return ( DriverManager.getConnection(url,user,password) );
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
        }

}
