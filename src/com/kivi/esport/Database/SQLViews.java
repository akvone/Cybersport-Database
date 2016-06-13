package com.kivi.esport.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created on 02.06.2016.
 */

/**
 * Work with tables
 */
public class SQLViews {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public SQLViews(Connection connection){
        con = connection;
    }

    /**
     * Get Result Set with table through simple select
     */
    public ResultSet getRSofView(String viewName){
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from "+viewName);
        } catch (SQLException e) {
            System.out.println("Exeption. MB this table does not exists");
            e.printStackTrace();
            return null;
        }
        return rs;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (st != null) {
            st.close();
        }
        if (rs != null) {
            rs.close();
        }
    }
}
