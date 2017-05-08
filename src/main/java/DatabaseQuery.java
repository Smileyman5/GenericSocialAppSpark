import java.sql.*;
import java.util.ArrayList;

/**
 * Created by JON on 5/7/2017.
 */
public class DatabaseQuery {

    public static boolean execute(String sqlQuery) {
        Connection con = null;
        Statement state = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "root", "");
            state = con.createStatement();
            state.execute(sqlQuery);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (state != null) { state.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static ArrayList<String> executeQuery(String sqlQuery, String value) {
        ResultSet set = null;
        Connection con = null;
        Statement state = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_data2?useSSL=false", "root", "");
            state = con.createStatement();
            list = buildList(state.executeQuery(sqlQuery), value);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (state != null) { state.close(); }
                if (con != null) { con.close(); }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private static ArrayList<String> buildList(ResultSet set, String value) {
        ArrayList<String> list = null;
        try {
            list = new ArrayList<String>();
            while (set.next()) {
                list.add(set.getString(value));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }


}
