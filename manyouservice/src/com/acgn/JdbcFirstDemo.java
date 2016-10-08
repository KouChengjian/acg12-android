/**
 * 
 */
package com.acgn;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author KCJ
 *
 */
public class JdbcFirstDemo {

	public static void main(String[] args) throws Exception {

		// 要连接的数据库URL
		String url = "jdbc:mysql://localhost:3306/acgn";
		// 连接的数据库时使用的用户名
		String username = "root";
		// 连接的数据库时使用的密码
		String password = "123456";

		// 1.加载驱动
		// DriverManager.registerDriver(new
		// com.mysql.jdbc.Driver());不推荐使用这种方式来加载驱动
		Class.forName("com.mysql.jdbc.Driver");// 推荐使用这种方式来加载驱动
		// 2.获取与数据库的链接
		Connection conn = DriverManager.getConnection(url, username, password);

		// 3.获取用于向数据库发送sql语句的statement
		Statement st = conn.createStatement();

		String sql = "select id,name,password,email,birthday from users";
		// 4.向数据库发sql,并获取代表结果集的resultset
		ResultSet rs = st.executeQuery(sql);

		// 5.取出结果集的数据
		while (rs.next()) {
			System.out.println("id=" + rs.getObject("id"));
			System.out.println("name=" + rs.getObject("name"));
			System.out.println("password=" + rs.getObject("password"));
			System.out.println("email=" + rs.getObject("email"));
			System.out.println("birthday=" + rs.getObject("birthday"));
		
		}
		testDataBaseMetaData();
		// 6.关闭链接，释放资源
		rs.close();
		st.close();
		conn.close();
	}
	
	
	
	
	
	/**
	 * @Method: testDataBaseMetaData
	 * @Description: 获取数据库的元信息
	 *
	 * @throws SQLException
	 */
	public static void testDataBaseMetaData() throws SQLException {
		Connection conn = JdbcUtils.getConnection();
		DatabaseMetaData metadata = conn.getMetaData();
		// getURL()：返回一个String类对象，代表数据库的URL
		System.out.println(metadata.getURL());
		// getUserName()：返回连接当前数据库管理系统的用户名
		System.out.println(metadata.getUserName());
		// getDatabaseProductName()：返回数据库的产品名称
		System.out.println(metadata.getDatabaseProductName());
		// getDatabaseProductVersion()：返回数据库的版本号
		System.out.println(metadata.getDatabaseProductVersion());
		// getDriverName()：返回驱动驱动程序的名称
		System.out.println(metadata.getDriverName());
		// getDriverVersion()：返回驱动程序的版本号
		System.out.println(metadata.getDriverVersion());
		// isReadOnly()：返回一个boolean值，指示数据库是否只允许读操作
		System.out.println(metadata.isReadOnly());
		JdbcUtils.release(conn, null, null);
	}
}
