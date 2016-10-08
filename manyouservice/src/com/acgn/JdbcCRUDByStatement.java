package com.acgn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcCRUDByStatement {

	public void insert() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 获取一个数据库连接
			conn = JdbcUtils.getConnection();
			// 通过conn对象获取负责执行SQL命令的Statement对象
			st = conn.createStatement();
			// 要执行的SQL命令
			String sql = "insert into users(id,name,password,email,birthday) values(3,'白虎神皇','123','bhsh@sina.com','1980-09-09')";
			// 执行插入操作，executeUpdate方法返回成功的条数
			int num = st.executeUpdate(sql);
			if (num > 0) {
				System.out.println("插入成功！！");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// SQL执行完成之后释放相关资源
			JdbcUtils.release(conn, st, rs);
		}
	}
	
	public void delete(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtils.getConnection();
            String sql = "delete from users where id=3";
            st = conn.createStatement();
            int num = st.executeUpdate(sql);
            if(num>0){
                System.out.println("删除成功！！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            
        }finally{
            JdbcUtils.release(conn, st, rs);
        }
    }
    
    public void update(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtils.getConnection();
            String sql = "update users set name='孤傲苍狼',email='gacl@sina.com' where id=3";
            st = conn.createStatement();
            int num = st.executeUpdate(sql);
            if(num>0){
                System.out.println("更新成功！！");
            }
        }catch (Exception e) {
            e.printStackTrace();
            
        }finally{
            JdbcUtils.release(conn, st, rs);
        }
    }
    
    public void find(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtils.getConnection();
            String sql = "select * from users where id=3";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                System.out.println(rs.getString("name"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.release(conn, st, rs);
        }
    }

}
