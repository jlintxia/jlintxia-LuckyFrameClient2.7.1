package luckyclient.caserun.exwebdriver.extestlink;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestCaseStep;
import luckyclient.caserun.exinterface.testlink.TestLinkCaseExecution;
import luckyclient.caserun.exwebdriver.BaseWebDrive;
import luckyclient.caserun.exwebdriver.EncapsulateOperation;
import luckyclient.dblog.LogOperation;
import luckyclient.publicclass.DBOperation;
import luckyclient.testlinkapi.WebDriverAnalyticTestLinkCase;

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
public class WebCaseExecutionTestLink extends TestLinkCaseExecution{
	static Map<String, String> variable = new HashMap<String, String>();

	public static void caseExcution(String projectname, TestCase testcase, String taskid, WebDriver wd,LogOperation caselog)
			throws InterruptedException {
		// 0:�ɹ� 1:ʧ�� 2:���� ����������
		int setresult = 0; 
		String casenote = "��ע��ʼ��";
		String imagname = "";
		//���뿪ʼִ�е�����
		caselog.addCaseDetail(taskid, testcase.getFullExternalId(), testcase.getVersion().toString(), testcase.getName(), 4);       
		
		for (TestCaseStep step : testcase.getSteps()) {
			Map<String, String> params = WebDriverAnalyticTestLinkCase.analyticCaseStep(testcase, step.getNumber(), taskid,caselog);
			
			if(params.get("exception")!=null&&params.get("exception").toString().indexOf("�����쳣")>-1){
				setresult = 2;
				break;
			}
			
			String result = WebCaseExecutionTestLink.runStep(params, wd, taskid, testcase.getFullExternalId(), step.getNumber(), caselog);

			String expectedResults = params.get("ExpectedResults").toString();
			// ���н������
			if (result.indexOf("����") < 0 && result.indexOf("ʧ��") < 0) { 
				// ��ȡ�����ȴ�ʱ��
				int waitsec = Integer.parseInt(params.get("StepWait").toString()); 
				if (waitsec != 0) {
					luckyclient.publicclass.LogUtil.APP.info("�������ߡ�"+waitsec+"����");
					Thread.sleep(waitsec * 1000);
				}
				// ��Ԥ�ڽ��
				if (!"".equals(expectedResults)) { 
					// �жϴ���
					luckyclient.publicclass.LogUtil.APP.info("expectedResults=��"+expectedResults+"��");
					if (expectedResults.length() > 2 && expectedResults.substring(0, 2).indexOf("$=") > -1) {
						String expectedResultVariable = expectedResults.substring(2);
						variable.put(expectedResultVariable, result);
						continue;
					}

					// �ж�Ԥ�ڽ��-���ģʽ
					if (params.get("checkproperty") != null && params.get("checkproperty_value") != null) {
						String checkproperty = params.get("checkproperty").toString();
						String checkPropertyValue = params.get("checkproperty_value").toString();

						WebElement we = isElementExist(wd, checkproperty, checkPropertyValue);
						if (we != null) {
							luckyclient.publicclass.LogUtil.APP.info("������" + testcase.getFullExternalId() + " ��" + step.getNumber()
									+ "�����ڵ�ǰҳ�����ҵ�Ԥ�ڽ���ж��󡣵�ǰ����ִ�гɹ���");
							caselog.caseLogDetail(taskid, testcase.getFullExternalId(), "�ڵ�ǰҳ�����ҵ�Ԥ�ڽ���ж��󡣵�ǰ����ִ�гɹ���",
									"info", String.valueOf(step.getNumber()),"");
							continue;
						} else {
							casenote = "��" + step.getNumber() + "����û���ڵ�ǰҳ�����ҵ�Ԥ�ڽ���ж���ִ��ʧ�ܣ�";
							setresult = 1;
							java.text.DateFormat timeformat = new java.text.SimpleDateFormat("MMdd-hhmmss");
							imagname = timeformat.format(new Date());
							BaseWebDrive.webScreenShot(wd,imagname);
							luckyclient.publicclass.LogUtil.APP.error("������" + testcase.getFullExternalId() + " ��" + step.getNumber()
									+ "����û���ڵ�ǰҳ�����ҵ�Ԥ�ڽ���ж��󡣵�ǰ����ִ��ʧ�ܣ�");
							caselog.caseLogDetail(taskid, testcase.getFullExternalId(), "�ڵ�ǰҳ����û���ҵ�Ԥ�ڽ���ж��󡣵�ǰ����ִ��ʧ�ܣ�"
									+ "checkproperty��"+checkproperty+"��  checkproperty_value��"+checkPropertyValue+"��","error", String.valueOf(step.getNumber()),imagname);
							break;
						}

					}else{
						// ģ��ƥ��Ԥ�ڽ��ģʽ
						if (expectedResults.length()>2 && expectedResults.substring(0, 2).indexOf("%=")>-1) {
							if(result.indexOf(expectedResults.substring(2))>-1){
								luckyclient.publicclass.LogUtil.APP.info("������" + testcase.getFullExternalId() + " ��" + step.getNumber()
								+ "����ģ��ƥ��Ԥ�ڽ���ɹ���ִ�н����"+result);
						        caselog.caseLogDetail(taskid, testcase.getFullExternalId(), "����ģ��ƥ��Ԥ�ڽ���ɹ���",
								"info", String.valueOf(step.getNumber()),"");
						        continue;
							}else{
								casenote = "��" + step.getNumber() + "����ģ��ƥ��Ԥ�ڽ��ʧ�ܣ�";
								setresult = 1;
								java.text.DateFormat timeformat = new java.text.SimpleDateFormat("MMdd-hhmmss");
								imagname = timeformat.format(new Date());
								BaseWebDrive.webScreenShot(wd,imagname);
								luckyclient.publicclass.LogUtil.APP.error("������" + testcase.getFullExternalId() + " ��" + step.getNumber()
								+ "����ģ��ƥ��Ԥ�ڽ��ʧ�ܣ�ִ�н����"+result);
						        caselog.caseLogDetail(taskid, testcase.getFullExternalId(), "����ģ��ƥ��Ԥ�ڽ��ʧ�ܣ�ִ�н����"+result,
								"error", String.valueOf(step.getNumber()),imagname);
								break;
							}
							// ֱ��ƥ��Ԥ�ڽ��ģʽ
						}else if(expectedResults.equals(result)) {    
							luckyclient.publicclass.LogUtil.APP.info("������" + testcase.getFullExternalId() + " ��" + step.getNumber()
							+ "����ֱ��ƥ��Ԥ�ڽ���ɹ���ִ�н����"+result);
					        caselog.caseLogDetail(taskid, testcase.getFullExternalId(), "����ֱ��ƥ��Ԥ�ڽ���ɹ���",
							"info", String.valueOf(step.getNumber()),"");
					        continue;
						} else {
							casenote = "��" + step.getNumber() + "����ֱ��ƥ��Ԥ�ڽ��ʧ�ܣ�";
							setresult = 1;
							java.text.DateFormat timeformat = new java.text.SimpleDateFormat("MMdd-hhmmss");
							imagname = timeformat.format(new Date());
							BaseWebDrive.webScreenShot(wd,imagname);
							luckyclient.publicclass.LogUtil.APP.error("������" + testcase.getFullExternalId() + " ��" + step.getNumber()
							+ "����ֱ��ƥ��Ԥ�ڽ��ʧ�ܣ�ִ�н����"+result);
					        caselog.caseLogDetail(taskid, testcase.getFullExternalId(), "����ֱ��ƥ��Ԥ�ڽ��ʧ�ܣ�ִ�н����"+result,
							"error", String.valueOf(step.getNumber()),imagname);
							break;
						}
					}
				}

			} else {
				casenote = result;
				setresult = 2;
				java.text.DateFormat timeformat = new java.text.SimpleDateFormat("MMdd-hhmmss");
				imagname = timeformat.format(new Date());
				BaseWebDrive.webScreenShot(wd,imagname);
				luckyclient.publicclass.LogUtil.APP.error("������" + testcase.getFullExternalId() + " ��" + step.getNumber()	+ "����"+result);
		        caselog.caseLogDetail(taskid, testcase.getFullExternalId(), "��ǰ������ִ�й����н���|��λԪ��|��������ʧ�ܣ�"+result,
				"error", String.valueOf(step.getNumber()),imagname);
				break;
			}

		}

		variable.clear();
		caselog.updateCaseDetail(taskid, testcase.getFullExternalId(), setresult);
		if(setresult==0){
			luckyclient.publicclass.LogUtil.APP.info("������"+testcase.getFullExternalId()+"��ȫ������ִ�н���ɹ�...");
	        caselog.caseLogDetail(taskid, testcase.getFullExternalId(), "����ȫ������ִ�н���ɹ�","info", "ending","");
		}else{
			luckyclient.publicclass.LogUtil.APP.error("������"+testcase.getFullExternalId()+"������ִ�й�����ʧ�ܻ�������...��鿴����ԭ��"+casenote);
	        caselog.caseLogDetail(taskid, testcase.getFullExternalId(), "����ִ�й�����ʧ�ܻ�������"+casenote,"error", "ending","");
		}
		//LogOperation.UpdateTastdetail(taskid, 0);
	}

	private static String runStep(Map<String, String> params, WebDriver wd,String taskid,String casenum,int stepno,LogOperation caselog) {
		String result = "";
		String property;
		String propertyValue;
		String operation;
		String operationValue;

		try {
			property = params.get("property");
			propertyValue = params.get("property_value");
			operation = params.get("operation");
			operationValue = params.get("operation_value");

			// �������ƽ��������쳣���ǵ���������������쳣
			if (propertyValue != null && property.indexOf("�����쳣") > -1) {
				luckyclient.publicclass.LogUtil.APP.error("��ǰ������������쳣���Ƕ���Ϊ�գ�---"+property);
				return "����������������";
			}

			// ����ֵ����
			if (propertyValue != null && propertyValue.indexOf("@") > -1 && propertyValue.indexOf("[@") < 0 
					&& propertyValue.indexOf("@@") < 0) {
				propertyValue = settingParameter(propertyValue);
				// �жϴ����Ƿ��������
				if (propertyValue.indexOf("Set parameter error") > -1) {
					caselog.caseLogDetail(taskid, casenum, "��ǰ������������쳣���Ƕ���Ϊ�գ�---"+property,
							"error", String.valueOf(stepno),"");
					return "�����ι��̳���" + propertyValue;
				}
			}else if(propertyValue != null && (propertyValue.indexOf("&quot;")>-1 
					|| propertyValue.indexOf("&#39;")>-1 || propertyValue.indexOf("@@")>-1)){
				propertyValue = propertyValue.replaceAll("&quot;", "\"");
				propertyValue = propertyValue.replaceAll("&#39;", "\'");
				propertyValue = propertyValue.replaceAll("@@", "@");
			}
			
			if (operationValue != null && operationValue.indexOf("@") > -1 && operationValue.indexOf("@@") < 0) {
				operationValue = settingParameter(operationValue);
				if (operationValue.indexOf("Set parameter error") > -1) {
					return "�����ι��̳���" + propertyValue;
				}
			}else if(operationValue != null && (operationValue.indexOf("&quot;")>-1 
					|| operationValue.indexOf("&#39;")>-1 || operationValue.indexOf("@@")>-1)){
				operationValue = operationValue.replaceAll("&quot;", "\"");
				operationValue = operationValue.replaceAll("&#39;", "\'");
				operationValue = operationValue.replaceAll("@@", "@");
			}
			
			luckyclient.publicclass.LogUtil.APP.info("���ν�������������ɣ��ȴ����ж������......");
			caselog.caseLogDetail(taskid, casenum, "�������:"+operation+"; ����ֵ:"+operationValue,"info", String.valueOf(stepno),"");

		} catch (Exception e) {
			e.printStackTrace();
			luckyclient.publicclass.LogUtil.APP.error("���ν������������׳��쳣��---"+e.getMessage());
			return "��������ʧ��!";
		}

		try {		
			//���ýӿ�����
			if(operation!=null&&operationValue!=null&&"runcase".equals(operation)){
				String[] temp=operationValue.split(",",-1);
				String ex = TestLinkCaseExecution.oneCaseExecuteForWebDriver(temp[0], Integer.valueOf(temp[1]),taskid,caselog);
				if(ex.indexOf("CallCase���ó���")<=-1&&ex.indexOf("������������")<=-1&&ex.indexOf("ƥ��ʧ��")<=-1){
					return ex;
				}else{
					return "���ýӿ���������ʧ��";
				}
			}
			
			WebElement we = null;
			// ҳ��Ԫ�ز�
			if (property != null && propertyValue != null) { 
				we = isElementExist(wd, property, propertyValue);
				// �жϴ�Ԫ���Ƿ����
				if (we == null) {
					luckyclient.publicclass.LogUtil.APP.error("��λ����ʧ�ܣ�isElementExistΪnull!");
					return "isElementExist��λԪ�ع���ʧ�ܣ�";
				}

				if (operation.indexOf("select") > -1) {
					result = EncapsulateOperation.selectOperation(we, operation, operationValue);
				} else if (operation.indexOf("get") > -1){
					result = EncapsulateOperation.getOperation(wd, we, operation,operationValue);
				} else if (operation.indexOf("mouse") > -1){
					result = EncapsulateOperation.actionWeOperation(wd, we, operation, operationValue, property, propertyValue);
				} else {
					result = EncapsulateOperation.objectOperation(wd, we, operation, operationValue, property, propertyValue);
				}
				// Driver�����		
			} else if (property == null && operation != null) { 		
				// ���������¼�
				if (operation.indexOf("alert") > -1){
					result = EncapsulateOperation.alertOperation(wd, operation);
				}else if(operation.indexOf("mouse") > -1){
					result = EncapsulateOperation.actionOperation(wd, operation, operationValue);
				}else{
					result = EncapsulateOperation.driverOperation(wd, operation, operationValue);
				} 				
			}else{
				luckyclient.publicclass.LogUtil.APP.error("Ԫ�ز�������ʧ�ܣ�");
				result =  "Ԫ�ز�������ʧ�ܣ�";
			}
		} catch (Exception e) {
			luckyclient.publicclass.LogUtil.APP.error("Ԫ�ض�λ���̻��ǲ�������ʧ�ܻ��쳣��"+e.getMessage());
			return "Ԫ�ض�λ���̻��ǲ�������ʧ�ܻ��쳣��" + e.getMessage();
		}
		caselog.caseLogDetail(taskid, casenum, result,"info", String.valueOf(stepno),"");
		
		if(result.indexOf("��ȡ����ֵ�ǡ�")>-1&&result.indexOf("��")>-1){
			result = result.substring(7, result.length()-1);
		}
		return result;

	}

	private static String settingParameter(String parameter) {
		int keyexistidentity = 0;
		if (parameter.indexOf("&quot;") > -1 || parameter.indexOf("&#39;") > -1) { 
			parameter = parameter.replaceAll("&quot;", "\"");
			parameter = parameter.replaceAll("&#39;", "\'");
		}
		//��������ַ����д�@�����
		if(parameter.indexOf("\\@")>-1){
			return parameter.replace("\\@", "@");
		}
		
		// ȡ�������������ñ�������
		int sumvariable = DBOperation.sumString(parameter, "@");
		String uservariable = null;
		String uservariable1 = null;
		String uservariable2 = null;

		if (sumvariable == 1) {
			uservariable = parameter.substring(parameter.indexOf("@") + 1);
		} else if (sumvariable == 2) { 
			uservariable = parameter.substring(parameter.indexOf("@") + 1, parameter.lastIndexOf("@"));
			uservariable1 = parameter.substring(parameter.lastIndexOf("@") + 1);
		} else if (sumvariable == 3) {
			String temp = parameter.substring(parameter.indexOf("@") + 1, parameter.lastIndexOf("@"));
			uservariable1 = temp.substring(temp.indexOf("@") + 1);
			uservariable2 = parameter.substring(parameter.lastIndexOf("@") + 1);
			uservariable = parameter.substring(parameter.indexOf("@") + 1, parameter.indexOf(uservariable1) - 1);
		} else {
			luckyclient.publicclass.LogUtil.APP.error("�������һ�������������˳���3�����ϵı���Ŷ���Ҵ�����������");
			return "�������һ�������������˳���3�����ϵı���Ŷ���Ҵ�������������Set parameter error��";
		}

		@SuppressWarnings("rawtypes")
		Iterator keys = variable.keySet().iterator();
		String key = null;
		while (keys.hasNext()) {
			key = (String) keys.next();
			if (uservariable.indexOf(key) > -1) {
				keyexistidentity = 1;
				uservariable = key;
				break;
			}
		}
		if (sumvariable == 2 || sumvariable == 3) { 
			keys = variable.keySet().iterator();
			while (keys.hasNext()) {
				keyexistidentity = 0;
				key = (String) keys.next();
				if (uservariable1.indexOf(key) > -1) {
					keyexistidentity = 1;
					uservariable1 = key;
					break;
				}
			}
		}
		if (sumvariable == 3) { 
			keys = variable.keySet().iterator();
			while (keys.hasNext()) {
				keyexistidentity = 0;
				key = (String) keys.next();
				if (uservariable2.indexOf(key) > -1) {
					keyexistidentity = 1;
					uservariable2 = key;
					break;
				}
			}
		}
		if (keyexistidentity == 1) {
			// ƴװ����������+ԭ���ַ�����
			String parameterValues = parameter.replaceAll("@" + uservariable, variable.get(uservariable).toString());
			// ����ڶ�������
			if (sumvariable == 2 || sumvariable == 3) {
				parameterValues = parameterValues.replaceAll("@" + uservariable1,
						variable.get(uservariable1).toString());
			}
			// �������������
			if (sumvariable == 3) {
				parameterValues = parameterValues.replaceAll("@" + uservariable2,
						variable.get(uservariable2).toString());
			}

			return parameterValues;
		} else {
			luckyclient.publicclass.LogUtil.APP.error("û���ҵ���Ҫ�ı���Ŷ�������°ɣ���һ�����������ǣ�" + uservariable + "����" + "�������������ǣ�" + uservariable1
					+ "�����������������ǣ�" + uservariable2);
			return "��Set parameter error��û���ҵ���Ҫ�ı���Ŷ�������°ɣ���һ�����������ǣ�" + uservariable + "����" + "�������������ǣ�" + uservariable1
					+ "�����������������ǣ�" + uservariable2;
		}
	}

	public static WebElement isElementExist(WebDriver wd, String property, String propertyValue) {
		try {
			WebElement we = null;

			// ����WebElement����λ
			switch (property) {
			case "id":
				we = wd.findElement(By.id(propertyValue));
				break;
			case "name":
				we = wd.findElement(By.name(propertyValue));
				break;
			case "xpath":
				we = wd.findElement(By.xpath(propertyValue));
				break;
			case "linktext":
				we = wd.findElement(By.linkText(propertyValue));
				break;
			case "tagname":
				we = wd.findElement(By.tagName(propertyValue));
				break;
			case "cssselector":
				we = wd.findElement(By.cssSelector(propertyValue));
				break;
			default:
				break;
			}

			return we;

		} catch (Exception e) {
			luckyclient.publicclass.LogUtil.APP.error("��ǰ����λʧ�ܣ�"+e.getMessage());
			return null;
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
