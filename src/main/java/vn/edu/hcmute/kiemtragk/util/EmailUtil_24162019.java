package vn.edu.hcmute.kiemtragk.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.*;
import vn.edu.hcmute.kiemtragk.config.LocalConfig_24162019;

public class EmailUtil_24162019 {
  public void sendOtp(String recipient, String otp) {
    Properties local = LocalConfig_24162019.load("mail-local.properties");
    String user = LocalConfig_24162019.value(local, "MAIL_USERNAME", "datcao19052006@gmail.com");
    String password =
        LocalConfig_24162019.value(local, "MAIL_APP_PASSWORD", "YOUR_GMAIL_APP_PASSWORD");
    if (password.isBlank() || password.equals("YOUR_GMAIL_APP_PASSWORD"))
      throw new IllegalArgumentException(
          "Chưa cấu hình Gmail App Password trong mail-local.properties.");
    Properties p = new Properties();
    p.setProperty("mail.smtp.host", "smtp.gmail.com");
    p.setProperty("mail.smtp.port", "587");
    p.setProperty("mail.smtp.auth", "true");
    p.setProperty("mail.smtp.starttls.enable", "true");
    p.setProperty("mail.smtp.starttls.required", "true");
    p.setProperty("mail.smtp.ssl.checkserveridentity", "true");
    p.setProperty("mail.smtp.connectiontimeout", "10000");
    p.setProperty("mail.smtp.timeout", "10000");
    p.setProperty("mail.smtp.writetimeout", "10000");
    Session session =
        Session.getInstance(
            p,
            new Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
              }
            });
    try {
      MimeMessage msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(user));
      msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
      msg.setSubject("Book Store - Mã xác thực đăng ký", "UTF-8");
      msg.setText(
          "Mã OTP của bạn: " + otp + "\nMã có hiệu lực 5 phút. Không chia sẻ mã này.", "UTF-8");
      Transport.send(msg);
    } catch (MessagingException e) {
      throw new IllegalArgumentException(
          "Không gửi được email OTP. Kiểm tra Gmail App Password và kết nối mạng.");
    }
  }
}
