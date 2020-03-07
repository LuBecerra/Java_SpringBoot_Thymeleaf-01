
package edu.egg.libreria.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ModificacionServicio {
public void enviar(String cuerpo, String titulo, String mail){}
//    @Autowired    
//    private JavaMailSender mailSender;
//
//    
//    @Async
//    public void enviar(String cuerpo, String titulo, String mail){
//        SimpleMailMessage mensaje = new SimpleMailMessage();
//        mensaje.setTo(mail);
//        mensaje.setFrom("noreply@tinder-mascota.com");
//        mensaje.setSubject(titulo);
//        mensaje.setText(cuerpo);
//        
//        mailSender.send(mensaje);
//    }
}
//@Bean
//public JavaMailSender getJavaMailSender() {
//    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//    mailSender.setHost("smtp.gmail.com");
//    mailSender.setPort(587);
//      
//    mailSender.setUsername("my.gmail@gmail.com");
//    mailSender.setPassword("password");
//      
//    Properties props = mailSender.getJavaMailProperties();
//    props.put("mail.transport.protocol", "smtp");
//    props.put("mail.smtp.auth", "true");
//    props.put("mail.smtp.starttls.enable", "true");
//    props.put("mail.debug", "true");
//      
//    return mailSender;
//}