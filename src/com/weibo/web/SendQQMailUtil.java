package com.weibo.web;

/**
 * @author CasterWx  AntzUhl
 * @site https://github.com/CasterWx
 * @company Henu
 * @create 2019-01-24-21:31
 */
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendQQMailUtil {
    public static void Send(String myMailAddress,String mailAddress,String stmpPassword,WebBean webBean) throws AddressException,MessagingException {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// ����Э��
        properties.put("mail.smtp.host", "smtp.qq.com");// ������
        properties.put("mail.smtp.port", 465);// �˿ں�
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// �����Ƿ�ʹ��ssl��ȫ���� ---һ�㶼ʹ��
        properties.put("mail.debug", "true");// �����Ƿ���ʾdebug��Ϣ true ���ڿ���̨��ʾ�����Ϣ
        // �õ��ػ�����
        Session session = Session.getInstance(properties);
        // ��ȡ�ʼ�����
        Message message = new MimeMessage(session);
        // ���÷����������ַ
        message.setFrom(new InternetAddress(myMailAddress));
        // �����ռ��������ַ
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(mailAddress)});
        //message.setRecipient(Message.RecipientType.TO, new InternetAddress("xxx@qq.com"));//һ���ռ���
        // �����ʼ�����
        message.setSubject(webBean.getTitle());
        // �����ʼ�����
        message.setText("�����ĵĲ���������!!!\n" +
                "���⣺"+webBean.getTitle()+"\n" +
                "ժҪ��"+webBean.getDesc()+"\n" +
                "ʱ�䣺"+webBean.getTime()+"\n" +
                "URL��"+webBean.getUrl()+"\n" +
                "\n�뼰ʱ�鿴Ŷ~~~");
        // �õ��ʲ����
        Transport transport = session.getTransport();
        // �����Լ��������˻�
        transport.connect(myMailAddress, stmpPassword);// ����ΪQQ���俪ͨ��stmp�����õ��Ŀͻ�����Ȩ��
        // �����ʼ�
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
