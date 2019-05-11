package cn.itcast.store.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * JavaMail发送邮件:前提是QQ邮箱里帐号设置要开启POP3/SMTP协议
 */
public class MailUtils {
	
	public static void sendMail(String email, String emailMsg) throws Exception {
		Properties prop = new Properties();
		
		// 设置邮件服务器主机名
		prop.setProperty("mail.host", "smtp.qq.com");
		// 发送服务器需要身份验证
		prop.setProperty("mail.smtp.auth", "true");
		// 发送邮件协议名称
		prop.setProperty("mail.transport.protocol", "smtp");

		// 开启SSL加密，否则会失败
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.socketFactory", sf);
		
		// 创建session
		Session session = Session.getInstance(prop);
		// 通过session得到transport对象
		Transport ts = session.getTransport();
		// 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
		ts.connect("smtp.qq.com", "479372360", "gbzhvxetrcpkbibb");
		// 创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress("479372360@qq.com"));
		// 指明邮件的收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		// 邮件的标题
		message.setSubject("用户激活");
		// 邮件的文本内容
		String url="http://localhost:8080/MyTomcat/UserServlet?method=active&code=" + emailMsg;
		String content="<h1>来自购物天堂的激活邮件!激活请点击以下链接!</h1><h3><a href='"+url+"'>"+url+"</a></h3>";
		message.setContent(content, "text/html;charset=UTF-8");
		
		// 发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
		System.out.println("okok");

	}

	public static void main(String[] args) throws Exception {
		MailUtils.sendMail("m19959556401@163.com", "abcdefg");
	}	

	
}