
package domainapp.dom.app.servicios;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.AbstractFactoryAndRepository;

//import domainapp.dom.modules.servicios.CorreoException;

@DomainService

public class EnvioCorreo extends AbstractFactoryAndRepository {
	
	public static String send(String destinatarios, String asunto, String mensaje) {

		try {
			Email email = new SimpleEmail();
			email.setSmtpPort(587);
			email.setHostName("smtp.gmail.com");		
			email.setAuthentication("ndinolfo@gisbertrepuestos.com.ar", "nicolete@123");
			email.setStartTLSEnabled(true);
			email.setSSLOnConnect(true);
			email.setFrom("ndinolfo@gisbertrepuestos.com.ar", "Logística");
			email.setSubject("Prueba1");
			email.setMsg("ANDAN LOS EMAILS!");			
			
			email.addTo("ppincheira@gisbertrepuestos.com.ar");/*
			
			
			Email email = new SimpleEmail();
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("ndinolfo@gisbertrepuestos.com.ar", ""));
			email.setDebug(true);
			email.setHostName("smtp.gmail.com");
			email.getMailSession().getProperties().put("mail.smtps.auth", "true");
			email.getMailSession().getProperties().put("mail.debug", "true");
			email.getMailSession().getProperties().put("mail.smtps.port", "587");
			email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
			email.getMailSession().getProperties().put("mail.smtps.socketFactory.class",   "javax.net.ssl.SSLSocketFactory");
			email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
			email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
			email.setFrom("ndinolfo@gisbertrepuestos.com.ar", "Nicolas Dinolfo");
			email.setSubject("TestMail");
			email.setMsg("This is a test mail?");
			email.addTo("nicodinolfo@gmail.com", "Nico");
			//email.setTLS(true);
			email.setStartTLSEnabled(true);

			email.send();*/
			/*Email email = new SimpleEmail();
String authuser = "...@gmail.com";
String authpwd = "xxxx";
email.setSmtpPort(587);
email.setAuthenticator(new DefaultAuthenticator(authuser, authpwd));
email.setDebug(true);
email.setHostName("smtp.gmail.com");
email.getMailSession().getProperties().put("mail.smtps.auth", "true");
email.getMailSession().getProperties().put("mail.debug", "true");
email.getMailSession().getProperties().put("mail.smtps.port", "587");
email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
email.getMailSession().getProperties().put("mail.smtps.socketFactory.class",   "javax.net.ssl.SSLSocketFactory");
email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");
email.setFrom("........@gmail.com", "SenderName");
email.setSubject("TestMail");
email.setMsg("This is a test mail?");
email.addTo("xxxx@gmail.com", "ToName");
email.setTLS(true);
email.send();*/
			return email.send();
		} catch (EmailException e) {
			throw new CorreoException(e.getMessage(), e);
		}
			
	}
}
