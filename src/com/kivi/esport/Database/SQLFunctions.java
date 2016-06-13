package com.kivi.esport.Database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

/**
 * Created on 22.05.2016.
 */

/**
 * Work with functions
 */
public class SQLFunctions {

    private Connection con;
    private CallableStatement cs;

    public SQLFunctions(Connection connection) {
        con = connection;
    }

    public int getSkillPoints(String nickname) {
        try {
            cs = con.prepareCall("{? = call dbo.getSkillPoints('" + nickname + "')}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.execute();
            int set = (int) cs.getObject(1);
            return set;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return 0;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (cs != null) {
            cs.close();
        }
    }
}
