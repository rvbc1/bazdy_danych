import java.sql.*;

public class SQL
{
	static String userName = "22770378_bazy_2020";
    static String password = "bazy_danych2020";
    static String server = "serwer1721194.home.pl";
    static String port = "3306";
    static String driver = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://"+ server + ":" + port +"/" + userName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	static Connection conn = null;
	static Statement  s = null;
	public SQL() throws ClassNotFoundException{
		
	}
	
	static Statement open() {	
		try {
			Class.forName (driver);
			conn = DriverManager.getConnection (url, userName, password);
			s = conn.createStatement();
			System.out.println ("Database connection established");
			return s;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static void close() {
		if (conn != null){
			try{
				conn.close ();
				System.out.println ("Database connection terminated");
			}
			catch (Exception e) { /* ignore close errors */ }
		}
	}
	
	public static void update(String sql) {
		try {
            if(s == null){
                open();
			}
			s.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ResultSet exe(String sql) {
		try {
            if(s == null){
                open();
            }
			return s.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void printResponse(ResultSet rs) throws SQLException {
        ResultSetMetaData metadata = rs.getMetaData();
        int columnCount = metadata.getColumnCount();    
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(metadata.getColumnName(i) + ", ");      
        }
        System.out.println();
        while (rs.next()) {
            String row = "";
            for (int i = 1; i <= columnCount; i++) {
                row += rs.getString(i) + ", ";          
            }
            System.out.println();
			System.out.println(row);
        }
    }

}