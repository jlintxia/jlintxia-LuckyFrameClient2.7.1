package luckyclient.caserun.exwebdriver.ocr;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
public class Ocr {
	/**
	 * Ĭ�϶�ȡ���̸�Ŀ¼�µ��ļ�
	 */
	private static String readtextpath = System.getProperty("user.dir")+"\\CAPTCHA.txt";   
	/**
	 * Ĭ�ϰѽ�ͼ���ڹ��̸�Ŀ¼
	 */
	private static String screenshotpath = System.getProperty("user.dir")+"\\CAPTCHA.png"; 
	/**
	 * �������ļ�·��
	 */
	private static String cmdpath = System.getProperty("user.dir");  
	/**
	 * Ĭ�ϰѽ�ͼ����C�̸�Ŀ¼
	 */
	private static String cmdname = "handlingCAPTCHA.bat";  

	/**
	 * ��ȡ���ɵ�TXT�ļ��е���֤��
	 * @param filePath
	 */
	private static String readTextFile() {
		String lineTxt = "";
		try {
			String encoding = "GBK";
			File file = new File(readtextpath); 
			 // �ж��ļ��Ƿ����
			if (file.isFile() && file.exists()) {
				// ���ǵ������ʽ
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				while ((lineTxt = bufferedReader.readLine()) != null) {
					  return lineTxt;
				}
				read.close();
			} else {
				return "�Ҳ���ָ�����ļ�";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "��ȡ�ļ����ݳ���";
		}
		return lineTxt;
	}

	/**
	 * ��ȡ��֤��λ�õ�ͼƬ
	 * 
	 * @param 
	 */
	private static void screenShotForElement(WebDriver driver, WebElement element){
		driver = new Augmenter().augment(driver);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        
		try {
			Point p = element.getLocation();
			int width = element.getSize().getWidth();
			int height = element.getSize().getHeight();
			Rectangle rect = new Rectangle(width, height);
			BufferedImage img = ImageIO.read(scrFile);
			BufferedImage dest = img.getSubimage(p.getX()-9, p.getY()+1, rect.width+2, rect.height+2);
			ImageIO.write(dest, "png", scrFile);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileUtils.copyFile(scrFile, new File(screenshotpath));   
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String getCAPTCHA(WebDriver driver, WebElement element) {
		String code = "";
		screenShotForElement(driver, element);
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("cmd.exe /k start " + cmdname, null, new File(cmdpath));
			Thread.sleep(1000);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		code = readTextFile();
/*		if (new File(readtextpath).exists()) {
			new File(readtextpath).delete();
		}
		if (new File(screenshotpath).exists()) {
			new File(screenshotpath).delete();
		}*/
		return code;
	}
	
	

	public static void main(String[] args) throws IOException, InterruptedException {
	}
}