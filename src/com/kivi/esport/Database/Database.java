package com.kivi.esport.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created on 21.05.2016.
 */

/**
 * This class is responsible for connection between this program and SQL Server
 */
public class Database {
    private Connection con = null;
    private String URL;

    public Database() throws SQLException {
        try {
            //Load driver
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connect user by ip, port, login and password
     * @throws SQLException
     */
    public void connectUser(String ip, String port, String login,String password) throws SQLException{
        URL = "jdbc:sqlserver://"+ip+":"+port+";" + "databaseName=esport;user="+login+";password="+password+";";
        con = DriverManager.getConnection(URL);
    }

    /**
     * Connect user only by login and password. This uses if your database locates on local machine
     */
    public void connectUser(String login,String password) throws SQLException {
        URL = "jdbc:sqlserver://localhost\\SQLEXPRESS;" + "databaseName=esport;user=" + login + ";password=" + password + ";";
        con = DriverManager.getConnection(URL);
    }

        public void dropConnection(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        return con;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (con != null) {
            con.close();
        }
    }
}
