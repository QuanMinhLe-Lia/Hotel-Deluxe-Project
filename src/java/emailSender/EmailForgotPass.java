    package emailSender;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import dal.AccountDAO;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


public class EmailForgotPass {

    public static void sendVerificationEmail(String email, String otp) {
        final String user = "hieunmhe161551@fpt.edu.vn";
        final String password = "argc iubf dtlu joga";
        

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.required", "true");
       props.put("mail.smtp.ssl.protocols", "TLSv1.2");
       
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("[DELUXE_HOTEL] FORGET PASSWORD OTP");
            message.setText("[DELUXE_HOTEL] Here is your Forget Passwor OTP .Your OTP for you to change password is: " + otp);

            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
     public static void main(String[] args) {
       EmailUtil.sendVerificationEmail("minhhieufvc1@gmail.com", "123456");
    }
}
