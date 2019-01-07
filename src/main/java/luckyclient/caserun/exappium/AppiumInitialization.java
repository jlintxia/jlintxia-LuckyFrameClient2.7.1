package luckyclient.caserun.exappium;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;

/**
 
 * @author�� seagull
 * 
 * @date 2017��12��1�� ����9:29:40
 * 
 */
public class AppiumInitialization {
	/**
	 * ��ʼ��AndroidAppium
	 * 
	 * @throws IOException
	 */
	public static AndroidDriver<AndroidElement> setAndroidAppium() throws IOException {
		AndroidDriver<AndroidElement> appium = null;
		Properties properties = luckyclient.publicclass.AppiumConfig.getConfiguration();
		
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withAppiumJS(new File(properties.getProperty("appiumjs"))).usingDriverExecutable(new File(properties.getProperty("node"))).withIPAddress("127.0.0.1")
				.usingPort(new RandomTest().port())
				.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, new RandomTest().bootstrap());
		
		
		
	
		DesiredCapabilities capabilities = new DesiredCapabilities();
		File directory = new File("");
		File app = new File(directory.getCanonicalPath() + File.separator + properties.getProperty("appname"));
		capabilities.setCapability("app", app.getAbsolutePath());
		// �Զ������Է���
		capabilities.setCapability("automationName", properties.getProperty("automationName"));
		// �豸����
		capabilities.setCapability("deviceName", properties.getProperty("deviceName"));
		// ƽ̨����
		capabilities.setCapability("platformName", properties.getProperty("platformName"));
		// ϵͳ�汾
		capabilities.setCapability("platformVersion", properties.getProperty("platformVersion"));
		// ģ�����ϵ�ip��ַ
		capabilities.setCapability("udid", properties.getProperty("udid"));
		// AndroidӦ�õİ���
		capabilities.setCapability("appPackage", properties.getProperty("appPackage"));
		// ������Android Activity
		capabilities.setCapability("appActivity", properties.getProperty("appActivity"));
		// ֧���������룬���Զ���װUnicode����
		capabilities.setCapability("unicodeKeyboard", properties.getProperty("unicodeKeyboard"));
		// �������뷨��ԭ��״̬
		capabilities.setCapability("resetKeyboard", properties.getProperty("resetKeyboard"));
		// ������ǩ��apk
		capabilities.setCapability("noSign", properties.getProperty("noSign"));
		// �����°�װapk
				capabilities.setCapability("noReset", properties.getProperty("noReset"));
		// �ȴ���ʱû���յ�����ر�appium
		capabilities.setCapability("newCommandTimeout", properties.getProperty("newCommandTimeout"));
		
//		appium = new AndroidDriver<AndroidElement>(
//				new URL("http://" + properties.getProperty("appiumsever") + "/wd/hub"), capabilities);
		appium = new AndroidDriver<AndroidElement>(builder.build(), capabilities);
		
		int waittime = Integer.valueOf(properties.getProperty("implicitlyWait"));
		appium.manage().timeouts().implicitlyWait(waittime, TimeUnit.SECONDS);
		return appium;
	}

	/**
	 * ��ʼ��IOSAppium
	 * 
	 * @throws IOException
	 */
	public static IOSDriver<IOSElement> setIosAppium() throws IOException {
		IOSDriver<IOSElement> appium = null;
		Properties properties = luckyclient.publicclass.AppiumConfig.getConfiguration();
		
		AppiumServiceBuilder builder = new AppiumServiceBuilder();
		builder.withAppiumJS(new File(properties.getProperty("appiumjs"))).usingDriverExecutable(new File(properties.getProperty("node"))).withIPAddress("127.0.0.1")
				.usingPort(new RandomTest().port())
				.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, new RandomTest().bootstrap());
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		File directory = new File("");
		File app = new File(directory.getCanonicalPath() + File.separator + properties.getProperty("appname"));
		capabilities.setCapability("app", app.getAbsolutePath());
		// �Զ������Է���
		capabilities.setCapability("automationName", properties.getProperty("automationName"));
		// �豸����
		capabilities.setCapability("deviceName", properties.getProperty("deviceName"));
		// ƽ̨����
		capabilities.setCapability("platformName", properties.getProperty("platformName"));
		// ϵͳ�汾
		capabilities.setCapability("platformVersion", properties.getProperty("platformVersion"));
		// ģ�����ϵ�ip��ַ
		capabilities.setCapability("udid", properties.getProperty("udid"));
		// ֧���������룬���Զ���װUnicode����
		capabilities.setCapability("unicodeKeyboard", properties.getProperty("unicodeKeyboard"));
		// �������뷨��ԭ��״̬
		capabilities.setCapability("resetKeyboard", properties.getProperty("resetKeyboard"));
		// ������ǩ��apk
		capabilities.setCapability("noSign", properties.getProperty("noSign"));
		// �����°�װapk
				capabilities.setCapability("noReset", properties.getProperty("noReset"));
		// �ȴ���ʱû���յ�����ر�appium
		capabilities.setCapability("newCommandTimeout", properties.getProperty("newCommandTimeout"));
		
//		appium = new IOSDriver<IOSElement>(new URL("http://" + properties.getProperty("appiumsever") + "/wd/hub"),
//				capabilities);
		
	
		appium = new IOSDriver<IOSElement>(builder.build(), capabilities);
		
		int waittime = Integer.valueOf(properties.getProperty("implicitlyWait"));
		appium.manage().timeouts().implicitlyWait(waittime, TimeUnit.SECONDS);
		return appium;
	}

	public static void main(String args[]) throws IOException, InterruptedException {

	}
}
