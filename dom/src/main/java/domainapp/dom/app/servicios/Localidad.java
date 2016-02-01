
package domainapp.dom.app.servicios;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.annotation.MemberOrder;

@PersistenceCapable
public class Localidad {

	// {{ Nombre (property)
	private E_localidades nombre;

	@Column(allowsNull = "true")
	@MemberOrder(sequence = "1")
	@Persistent
	public E_localidades getNombre() {
		return nombre;
	}

	public void setNombre(final E_localidades nombre) {
		this.nombre = nombre;
	}
	// }}

	
	public enum E_localidades{	
		NEUQUEN, CIPOLLETTI, PLOTTIER, CENTENARIO, ROCA, ALLEN;
	}
	
	@Column(allowsNull = "true")
	public String title(){
		return nombre.toString();
	}

	@Override
	@Column(allowsNull = "true")
	public String toString() {
		return nombre.toString();
	}
	
	
	
}
