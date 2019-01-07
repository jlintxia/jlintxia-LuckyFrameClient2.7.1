package luckyclient.dblog;

import java.util.Properties;

import luckyclient.publicclass.DBOperation;

/**
 * =================================================================
 * ����һ�������Ƶ�������������������κ�δ�������ǰ���¶Գ����������޸ĺ�������ҵ��;��Ҳ������Գ�������޸ĺ����κ���ʽ�κ�Ŀ�ĵ��ٷ�����
 * Ϊ���������ߵ��Ͷ��ɹ���LuckyFrame�ؼ���Ȩ��Ϣ�Ͻ��۸�
 * ���κ����ʻ�ӭ��ϵ�������ۡ� QQ:1573584944  seagull1985
 * =================================================================
 * 
 * @author�� seagull
 * @date 2017��12��1�� ����9:29:40
 * 
 */
public class DbLink {
	
	/**
	 * =================================================================
 * ����һ�������Ƶ�������������������κ�δ�������ǰ���¶Գ����������޸ĺ�������ҵ��;��Ҳ������Գ�������޸ĺ����κ���ʽ�κ�Ŀ�ĵ��ٷ�����
 * �˲��Կ����Ҫ����testlink���ֲ��ܣ��������������Լ����������֣����κ����ʻ�ӭ��ϵ�������ۡ�
 * QQ:24163551 seagull1985
	 * =================================================================
	 * @ClassName: DbLogLink 
	 * @Description: ����������־���ݿ����ӵ�ַ
	 * @author�� seagull
	 * @date 2015��4��20�� ����9:29:40  
	 * 
	 */
	public  static DBOperation dbLogLink(){
		Properties properties = luckyclient.publicclass.SysConfig.getConfiguration();
		String urlBase = "jdbc:mysql://"+properties.getProperty("mysql.db.ip")+":"+properties.getProperty("mysql.db.port")
		+"/"+properties.getProperty("mysql.db.dbname")+"?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
		String userNameBase = properties.getProperty("mysql.db.username");
		String passwordBase = properties.getProperty("mysql.db.userpwd");
		return new DBOperation(urlBase, userNameBase, passwordBase);
	}
	/**
	 * ����ִ�����ͣ� 0   �������ģʽ    1   ����̨ģʽ
	 */
	public static int exetype;      

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
