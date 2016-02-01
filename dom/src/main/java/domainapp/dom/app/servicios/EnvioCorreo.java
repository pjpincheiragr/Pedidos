
package domainapp.dom.app.servicios;
/*
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.AbstractFactoryAndRepository;

import domainapp.dom.modules.servicios.CorreoException;

@DomainService

public class EnvioCorreo extends AbstractFactoryAndRepository {
	
	public static String send(String destinatarios, String asunto, String mensaje) {

		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthentication("ppincheira@gisbertrepuestos.com.ar", "sistemas");
			email.setSSLOnConnect(true);
			email.setFrom("ppincheira@gisbertrepuestos.com.ar", "Sistema");
			email.setSubject(asunto);
			email.setMsg(mensaje);			
			
			email.addTo(destinatarios);
			return email.send();
		} catch (EmailException e) {
			throw new CorreoException(e.getMessage(), e);
		}
			
	}
}*/
