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
 
 * @author： seagull
 * 
 * @date 2017年12月1日 上午9:29:40
 * 
 */
public class AppiumInitialization {
	/**
	 * 初始化AndroidAppium
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
		// 自动化测试服务
		capabilities.setCapability("automationName", properties.getProperty("automationName"));
		// 设备名称
		capabilities.setCapability("deviceName", properties.getProperty("deviceName"));
		// 平台类型
		capabilities.setCapability("platformName", properties.getProperty("platformName"));
		// 系统版本
		capabilities.setCapability("platformVersion", properties.getProperty("platformVersion"));
		// 模拟器上的ip地址
		capabilities.setCapability("udid", properties.getProperty("udid"));
		// Android应用的包名
		capabilities.setCapability("appPackage", properties.getProperty("appPackage"));
		// 启动的Android Activity
		capabilities.setCapability("appActivity", properties.getProperty("appActivity"));
		// 支持中文输入，会自动安装Unicode输入
		capabilities.setCapability("unicodeKeyboard", properties.getProperty("unicodeKeyboard"));
		// 重置输入法到原有状态
		capabilities.setCapability("resetKeyboard", properties.getProperty("resetKeyboard"));
		// 不重新签名apk
		capabilities.setCapability("noSign", properties.getProperty("noSign"));
		// 不重新安装apk
				capabilities.setCapability("noReset", properties.getProperty("noReset"));
		// 等待超时没接收到命令关闭appium
		capabilities.setCapability("newCommandTimeout", properties.getProperty("newCommandTimeout"));
		
//		appium = new AndroidDriver<AndroidElement>(
//				new URL("http://" + properties.getProperty("appiumsever") + "/wd/hub"), capabilities);
		appium = new AndroidDriver<AndroidElement>(builder.build(), capabilities);
		
		int waittime = Integer.valueOf(properties.getProperty("implicitlyWait"));
		appium.manage().timeouts().implicitlyWait(waittime, TimeUnit.SECONDS);
		return appium;
	}

	/**
	 * 初始化IOSAppium
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
		// 自动化测试服务
		capabilities.setCapability("automationName", properties.getProperty("automationName"));
		// 设备名称
		capabilities.setCapability("deviceName", properties.getProperty("deviceName"));
		// 平台类型
		capabilities.setCapability("platformName", properties.getProperty("platformName"));
		// 系统版本
		capabilities.setCapability("platformVersion", properties.getProperty("platformVersion"));
		// 模拟器上的ip地址
		capabilities.setCapability("udid", properties.getProperty("udid"));
		// 支持中文输入，会自动安装Unicode输入
		capabilities.setCapability("unicodeKeyboard", properties.getProperty("unicodeKeyboard"));
		// 重置输入法到原有状态
		capabilities.setCapability("resetKeyboard", properties.getProperty("resetKeyboard"));
		// 不重新签名apk
		capabilities.setCapability("noSign", properties.getProperty("noSign"));
		// 不重新安装apk
				capabilities.setCapability("noReset", properties.getProperty("noReset"));
		// 等待超时没接收到命令关闭appium
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
