title ��������ִ�С�%1�� 
set classpath=%CLASSPATH%;.\luckyclient;
@echo ����ִ������
@echo ����˵�� ����Ϊ����Ŀ���� tastId ������ 
java -Djava.ext.dirs=./lib;.%3 luckyclient.caserun.BatchCaseExecute %1 %2
@echo ��ǰ��������ִ�д��ڽ���90����˳�
ping 127.0.0.1 -n 90 >nul
exit
