package luckyclient.caserun.exwebdriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

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
public class BaseWebDrive {

	/**
	 * @param args
	 * @throws IOException
	 * @throws IOException
	 */
	public static Boolean webScreenShot(WebDriver driver,String imgname) {
		Boolean result = false;
		String relativelyPath = System.getProperty("user.dir");
		String pngpath=relativelyPath +File.separator+ "log"+File.separator+"ScreenShot" +File.separator+ imgname + ".png";

		// ��Զ��ϵͳ���н�ͼ
		driver = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(pngpath));
		} catch (IOException e) {
			luckyclient.publicclass.LogUtil.APP.error("��ͼ����ʧ�ܣ��׳��쳣��鿴��־...", e);
			e.printStackTrace();
		}
		scrFile.deleteOnExit();
		luckyclient.publicclass.LogUtil.APP
				.info("�ѶԵ�ǰ������н�ͼ��������ͨ������ִ�н������־��ϸ�鿴��Ҳ����ǰ���ͻ����ϲ鿴...��" + pngpath + "��");
		return result;
	}
	//�Լ������ҳ��ͼ
		public static Boolean mywebScreenShot(WebDriver driver,String imgname) {
			Boolean result = false;
			String relativelyPath = System.getProperty("user.dir");

			// ��Զ��ϵͳ���н�ͼ
			driver = new Augmenter().augment(driver);
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
			  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		  	  String a=df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		  	  String str2 = a.replaceAll(" ", "");
		  	  String str3 = str2.replaceAll("-", ""); 
		  	  String str4 = str3.replaceAll(":", ""); 
		  	  System.out.println(str4);
			
			
			try {
				FileUtils.copyFile(scrFile, new File(relativelyPath + "\\ScreenShot\\web������ͼ\\"+ imgname + ".png"));
			} catch (IOException e) {
				luckyclient.publicclass.LogUtil.APP.error("��ͼ����ʧ�ܣ��׳��쳣��鿴��־...", e);
				e.printStackTrace();
			}
			luckyclient.publicclass.LogUtil.APP
					.info("�ѶԵ�ǰ������н�ͼ��������ǰ���������ϲ鿴...��" + relativelyPath + "\\ScreenShot\\" + imgname + ".png��");
			return result;
			
		
		}
}
