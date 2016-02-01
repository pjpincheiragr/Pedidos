
package domainapp.dom.app.servicios;


import org.apache.isis.applib.ApplicationException;


public class CorreoException extends ApplicationException {

	public CorreoException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CorreoException(String msg) {
		super(msg);
	}

	public CorreoException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 1L;

}
