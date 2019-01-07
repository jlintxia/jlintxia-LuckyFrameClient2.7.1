package luckyclient.publicclass;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * =================================================================
 * ����һ�������Ƶ�������������������κ�δ�������ǰ���¶Գ����������޸ĺ�������ҵ��;��Ҳ������Գ�������޸ĺ����κ���ʽ�κ�Ŀ�ĵ��ٷ�����
 * Ϊ���������ߵ��Ͷ��ɹ���LuckyFrame�ؼ���Ȩ��Ϣ�Ͻ��۸�
 * ���κ����ʻ�ӭ��ϵ�������ۡ� QQ:1573584944  seagull1985
 * =================================================================
 * @ClassName: DBToolkit 
 * @Description: ������ر����ݿ�����
 * @author�� seagull
 * @date 2014��8��24�� ����9:29:40  
 * 
 */
public class DBToolkit { 
    /** 
     * �������ݿ����ӳ�
     */ 
	public ComboPooledDataSource cpds=null;
	
	public DBToolkit(String urlBase,String usernameBase,String passwordBase){
		cpds=new ComboPooledDataSource();  
        cpds.setUser(usernameBase);  
        cpds.setPassword(passwordBase);  
        cpds.setJdbcUrl(urlBase);  
        try {  
            cpds.setDriverClass("com.mysql.cj.jdbc.Driver");  
        } catch (PropertyVetoException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        cpds.setInitialPoolSize(20);  
        cpds.setMaxIdleTime(20);  
        cpds.setMaxPoolSize(30);  
        cpds.setMinPoolSize(1);  	
	}
	
    static { 
    	//ע�������� 
        try { 
                Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) { 
                e.printStackTrace();
        } 
} 
    
	public  Connection getBaseConnection() throws SQLException{
		// TODO Auto-generated method stub       
        Connection conn = cpds.getConnection();
        return conn; 
	}

    /** 
     * ��һ�����ݿ�������ִ��һ����̬SQL����ѯ 
     * 
     * @param conn            ���ݿ����� 
     * @param staticSql ��̬SQL����ַ��� 
     * @return ���ز�ѯ�����ResultSet���� 
     * @throws SQLException 
     */ 
    public static ResultSet executeQuery(Connection conn, String staticSql) throws SQLException { 
    	//����ִ��SQL�Ķ��� 
            Statement stmt = conn.createStatement(); 
            
          //ִ��SQL������ȡ���ؽ�� 
            ResultSet rs = stmt.executeQuery(staticSql); 
           // stmt.close();
            return rs; 
    } 

    /** 
     * ��һ�����ݿ�������ִ��һ����̬SQL��� 
     * 
     * @param conn���ݿ����� 
     * @param staticSql ��̬SQL����ַ��� 
     * @throws SQLException 
     */ 
    public static int executeSQL(Connection conn, String staticSql) throws SQLException { 
    	//����ִ��SQL�Ķ��� 
                    Statement stmt = conn.createStatement(); 
                  //ִ��SQL������ȡ���ؽ��  
                     stmt.execute(staticSql); 
                     return stmt.getUpdateCount();
           
    } 

    /** 
     * ��һ�����ݿ�������ִ��һ����̬SQL��� 
     * 
     * @param conn        ���ݿ����� 
     * @param sqlList ��̬SQL����ַ������� 
     */ 
    public static void executeBatchSQL(Connection conn, List<String> sqlList) { 
            try { 
            	 //����ִ��SQL�Ķ��� 
                    Statement stmt = conn.createStatement(); 
                    for (String sql : sqlList) { 
                            stmt.addBatch(sql); 
                    } 
                  //ִ��SQL������ȡ���ؽ�� 
                    stmt.executeBatch(); 
            } catch (SQLException e) { 
                    e.printStackTrace();
            } 
    } 

    public static void closeConnection(Connection conn) { 
            if (conn == null){
            	return;
            } 
            try { 
                if (!conn.isClosed()) { 
                	  //�ر����ݿ����� 
                        conn.close(); 
                } 
            } catch (SQLException e) { 
                    e.printStackTrace();
            } 
    } 
}

