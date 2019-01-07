package luckyclient.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * =================================================================
 * ����һ�������Ƶ�������������������κ�δ�������ǰ���¶Գ����������޸ĺ�������ҵ��;��Ҳ������Գ�������޸ĺ����κ���ʽ�κ�Ŀ�ĵ��ٷ�����
 * Ϊ���������ߵ��Ͷ��ɹ���LuckyFrame�ؼ���Ȩ��Ϣ�Ͻ��۸�
 * ���κ����ʻ�ӭ��ϵ�������ۡ� QQ:1573584944  seagull1985
 * =================================================================
 *
 * @author�� seagull
 * @date 2017��12��1�� ����9:29:40
 */
public class SimpleMailSender {
    /**
     * ���ı���ʽ�����ʼ�
     *
     * @param mailInfo �����͵��ʼ�����Ϣ
     */
    public boolean sendTextMail(MailSenderInfo mailInfo) {
        // �ж��Ƿ���Ҫ�����֤
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (null == pro) return false;
        if (mailInfo.isValidate()) {
            // �����Ҫ�����֤���򴴽�һ��������֤��
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // ����session����һ���ʼ���Ϣ
            Message mailMessage = new MimeMessage(sendMailSession);
            // �����ʼ������ߵ�ַ
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // �����ʼ���Ϣ�ķ�����
            mailMessage.setFrom(from);
            // �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // �����ʼ���Ϣ������
            mailMessage.setSubject(mailInfo.getSubject());
            // �����ʼ���Ϣ���͵�ʱ��
            mailMessage.setSentDate(new Date());
            // �����ʼ���Ϣ����Ҫ����
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // �����ʼ�
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * ��HTML��ʽ�����ʼ�
     *
     * @param mailInfo �����͵��ʼ���Ϣ
     */
    public boolean sendHtmlMail(MailSenderInfo mailInfo) {
        // �ж��Ƿ���Ҫ�����֤
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (null == pro) return false;
        //�����Ҫ�����֤���򴴽�һ��������֤��
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // ����session����һ���ʼ���Ϣ
            Message mailMessage = new MimeMessage(sendMailSession);
            // �����ʼ������ߵ�ַ
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // �����ʼ���Ϣ�ķ�����
            mailMessage.setFrom(from);
            // �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��
            Address[] to = new InternetAddress[mailInfo.getToAddresses().length];
            // Ϊÿ���ʼ������ߴ���һ����ַ
            for (int i = 0; i < mailInfo.getToAddresses().length; i++) {
                to[i] = new InternetAddress(mailInfo.getToAddresses()[i]);
            }

            // Message.RecipientType.TO���Ա�ʾ�����ߵ�����ΪTO
            //�����ռ���    mailMessage.setRecipient(Message.RecipientType.TO,to);
            mailMessage.setRecipients(Message.RecipientType.TO, to);
            // �����ʼ���Ϣ������
            mailMessage.setSubject(mailInfo.getSubject());
            // �����ʼ���Ϣ���͵�ʱ��
            mailMessage.setSentDate(new Date());
            // MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���
            Multipart mainPart = new MimeMultipart();
            // ����һ������HTML���ݵ�MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // ����HTML����
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // ��MiniMultipart��������Ϊ�ʼ�����
            mailMessage.setContent(mainPart);
            // �����ʼ�
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            luckyclient.publicclass.LogUtil.APP.error(ex);
        }
        return false;
    }
}   