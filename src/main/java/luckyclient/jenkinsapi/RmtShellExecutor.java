package luckyclient.jenkinsapi;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * Զ��ִ��shell�ű���
 * @author l
 */
public class RmtShellExecutor {
	/**
	 * ����JSch��ʵ��Զ������SHELL����ִ��
	 * @param ip ����IP
	 * @param user ������½�û���
	 * @param psw  ������½����
	 * @param port ����ssh2��½�˿ڣ����ȡĬ��ֵ����-1
	 * @param privateKey ��Կ�ļ�·��
	 * @param passphrase ��Կ������
	 * @param command Shell����   cd /home/pospsettle/tomcat-7.0-7080/bin&&./restart.sh
	 */
	public static String sshShell(String ip, String user, String psw
	        ,int port,String command) throws Exception{
		
	    Session session = null;
	    Channel channel = null;
	    String privateKey = "";
	    String passphrase = "";
	    String result = "Status:true"+" ��������ִ�гɹ���";
	    try { 
	    JSch jsch = new JSch();
	    luckyclient.publicclass.LogUtil.APP.info("���뵽����TOMCAT����������");
	    //������Կ������
	    if (privateKey != null && !"".equals(privateKey)) {
	        if (passphrase != null && "".equals(passphrase)) {
	            //���ô��������Կ
	            jsch.addIdentity(privateKey, passphrase);
	        } else {
	            //���ò����������Կ
	            jsch.addIdentity(privateKey);
	        }
	    }
	     
	    if(port <=0){
	        //���ӷ�����������Ĭ�϶˿�
	    	luckyclient.publicclass.LogUtil.APP.info("��������TOMCAT������IP��Ĭ�϶˿ڡ�����");
	        session = jsch.getSession(user, ip);
	    }else{
	        //����ָ���Ķ˿����ӷ�����
	    	luckyclient.publicclass.LogUtil.APP.info("��������TOMCAT������IP���˿ڡ�����");
	        session = jsch.getSession(user, ip ,port);
	        luckyclient.publicclass.LogUtil.APP.info("��������TOMCAT������IP���˿����!");
	    }
	 
	    //������������Ӳ��ϣ����׳��쳣
	    if (session == null) {
	    	luckyclient.publicclass.LogUtil.APP.error("����TOMCAT�����У����ӷ�����session is null");
	    	result = "����TOMCAT�����У����ӷ�����session is null";
	        throw new Exception("session is null");
	    }     
	    //���õ�½����������
	    session.setPassword(psw);
	    //���õ�һ�ε�½��ʱ����ʾ����ѡֵ��(ask | yes | no)
	    session.setConfig("StrictHostKeyChecking", "no");
	    //���õ�½��ʱʱ��   
	    session.connect(30000);        
	    
	        //����sftpͨ��ͨ��
	    channel = (Channel) session.openChannel("shell");
	    channel.connect(1000);
	 
	        //��ȡ�������������
	        InputStream instream = channel.getInputStream();
	        OutputStream outstream = channel.getOutputStream();
	         
	        //������Ҫִ�е�SHELL�����Ҫ��\n��β����ʾ�س�
	        luckyclient.publicclass.LogUtil.APP.info("׼��������TOMCAT��������������!");
	        String shellCommand = command+"  \n";
	        outstream.write(shellCommand.getBytes());
	        outstream.flush();

	        Thread.sleep(10000);
	        //��ȡ����ִ�еĽ��
	        if (instream.available() > 0) {
	            byte[] data = new byte[instream.available()];
	            int nLen = instream.read(data);
	            if (nLen < 0) {
	            	luckyclient.publicclass.LogUtil.APP.error("����TOMCAT�����У���ȡ����ִ�н�������쳣��");
	            	result = "����TOMCAT�����У���ȡ����ִ�н�������쳣��";
	                throw new Exception("network error.");
	            }
	             
	            //ת������������ӡ����
	            String temp = new String(data, 0, nLen,"iso8859-1");
	            luckyclient.publicclass.LogUtil.APP.info("��ʼ��ӡ����TOMCAT����ִ�н��"+temp);
	        }
	        outstream.close();
	        instream.close();
	    } catch (Exception e) {
	    	result = "����TOMCAT�����У������쳣��";
	    	luckyclient.publicclass.LogUtil.APP.error(e.getMessage(), e);
		    return result;
	    } finally {
	    	if(null!=session){
	    		session.disconnect();
	    	}
	    	if(null!=channel){
		        channel.disconnect();
	    	}

	    }
	    return result;
	}

    public static void main(String args[]) throws Exception {
    }
}