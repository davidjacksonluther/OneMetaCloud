package cn.ideartefact.accessingdatajsonorm;

import cn.ideartefact.gson.JsonObject;
import cn.ideartefact.gson.JsonPrimitive;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

@Component
@Configuration
// @ConfigurationProperties(prefix = "mysql")
public class MySQLConnector implements InitializingBean {
    @Value("${mysql.instance.server-url}")
    private String MysqlURL;

    @Value("${mysql.instance.username}")
    private String username;


    @Value("${mysql.instance.password}")
    private String password;

    @Value("${mysql.instance.driver}")
    private String driver;


    public Connection connection =null;

    public PreparedStatement preparedStatement = null;

    public MySQLConnector (){

    }

    static ArrayList<String> arrcolname = new ArrayList<String>();
    static ArrayList<String> arrcolout = new ArrayList<String>();

    public String Query (String SQL){
        String ColName = "";
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(MysqlURL,username,password);
            preparedStatement = connection.prepareStatement(SQL);
            ResultSet ret =  preparedStatement.executeQuery();
            while(ret.next()){
                ResultSetMetaData rsmd = ret.getMetaData();
                ColName= rsmd.getColumnName(1);
            }
            this.close();

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.close();
        }
        return ColName;
    }

    public JsonPrimitive QueryJsonPrimitive(String SQL){
        return new JsonPrimitive( Query(SQL));
    }
    public void close(){
        try{
            this.connection.close();
            this.preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(MysqlURL);
        System.out.println(username);
        System.out.println(password);

    }
}
