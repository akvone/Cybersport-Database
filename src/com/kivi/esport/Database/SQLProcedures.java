package com.kivi.esport.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created on 23.05.2016.
 */

/**
 * Work with views
 */
public class SQLProcedures {

    private Connection con;
    private CallableStatement cs;
    private Statement st;
    private ResultSet rs;

    Scanner scanner;

    public SQLProcedures(Connection connection){
        con = connection;
    }

    /**
     * Common method for adding any entity
     */
    public String addEntity(String nameOfProcedure, ArrayList<String> fields){
        StringBuffer sb = new StringBuffer();
        sb.append("exec").append(" ").append(nameOfProcedure).append(" ");
        appendArgumentsCorrectfully(sb,fields);
        try {
            st = con.createStatement();
            st.executeUpdate(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "Successfully added";
    }

    public Integer getAvgSPforNbestPlayers(int number) {
        try {
            cs = con.prepareCall("{call dbo.avgSPforNPlayersProc('"+number+"',?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.execute();
            int result = (int) cs.getObject(1);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Common method for getting any table by procedure name and fields text
     */
    public ResultSet receiveTableSendFields(String nameOfProcedure, ArrayList<String> fields){
        StringBuffer sb = new StringBuffer();
        sb.append("exec").append(" ").append(nameOfProcedure).append(" ");
        appendArgumentsCorrectfully(sb,fields);
        try {
            st = con.createStatement();
            //ResultSet получает результирующую таблицу
            rs = st.executeQuery(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return rs;
    }

    public void appendArgumentsCorrectfully(StringBuffer sb, ArrayList<String> fields){
        for (String field:fields) {
            if (field.equals("NULL")) sb.append(""+field+",");
            else sb.append("'"+field+"',");
        }
        sb.deleteCharAt(sb.length()-1);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (cs != null) {
            cs.close();
        }
        if (st != null) {
            st.close();
        }
        if (rs != null) {
            rs.close();
        }
    }
}
