package luckyclient.publicclass;

import java.io.File;
import java.util.Enumeration;

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
public class JarClassFind {

	private static void findClassInLocalSystem(String path,String classname) {
		int count = 0;
		//String path = "D:\\web_task\\TestFrame\\lib\\";   //ָ������·��
		classname = classname.replace('.', '/') + ".class";

		//JavaBaseTest.LogUtil.APP.info("����ȥ������·����" + path + "���²����ࡾ" + classname + "�� ��");
		System.out.println("����ȥ������·����" + path + "���²����ࡾ" + classname + "�� ��");
		if (path.charAt(path.length() - 1) != '\\') {
			path += '\\';
		}
		File file = new File(path);
		if (!file.exists()) {
			//JavaBaseTest.LogUtil.ERROR.error("�Ҳ�������ָ��������·����");
			System.out.println("�Ҳ�������ָ��������·����");
			return;
		}
		
		String[] filelist = file.list();
		for (int i = 0; i < filelist.length; i++) {
			File temp = new File(path + filelist[i]);
			if ((temp.isDirectory() && !temp.isHidden() && temp.exists())&&filelist[i].equals(classname)) {
				count++;
				System.out.println("��"+path + filelist[i]+"�����ҵ���"+count+"��JAR������ָ���࣡");
			} else {
				if (filelist[i].endsWith("jar")) {
					try {
						java.util.jar.JarFile jarfile = new java.util.jar.JarFile(path + filelist[i]);
						for (Enumeration e = jarfile.entries(); e.hasMoreElements();) {
							String name = e.nextElement().toString();
							if (name.equals(classname) || name.indexOf(classname) > -1) {
								count++;
								//JavaBaseTest.LogUtil.APP.info("��"+path + filelist[i]+"�����ҵ���"+count+"��JAR������ָ���࣡");
								System.out.println("��"+path + filelist[i]+"�����ҵ���"+count+"��JAR������ָ���࣡");
							}
						}
					} catch (Exception eee) {
					}
				}
			}
		}
		
		if(count==0){
			//JavaBaseTest.LogUtil.APP.info("û���ڵ�ǰ·�����ҵ�ָ����");
			System.out.println("û���ڵ�ǰ·�����ҵ�ָ����");
		}else if(count==1){
			//JavaBaseTest.LogUtil.APP.info("�ڵ�ǰ·����ֻ�ҵ�һ�����ڵ�ָ���࣬���������ͻ�����");
			System.out.println("�ڵ�ǰ·����ֻ�ҵ�һ�����ڵ�ָ���࣬���������ͻ�����");
		}
	}
	
	static public void main(String[] args) {
		String path = args[0];
		String classname = args[1];
		findClassInLocalSystem(path,classname);
	}
	
}
