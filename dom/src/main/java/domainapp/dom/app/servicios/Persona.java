
package domainapp.dom.app.servicios;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.annotation.MemberOrder;
import org.joda.time.LocalDate;

import javax.jdo.annotations.InheritanceStrategy;


@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public class Persona {
	
	// {{ Dni (property)
	private int dni;

	@Persistent
	@MemberOrder(sequence = "3")
	@javax.jdo.annotations.Column(allowsNull="false")
	public int getDni() {
		return dni;
	}

	public void setDni(final int dni) {
		this.dni = dni;
	}
	// }}


	// {{ Apellido (property)
	private String apellido;

	@Persistent
	@MemberOrder(sequence = "1")
	@javax.jdo.annotations.Column(allowsNull="false")
	public String getApellido() {
		return apellido;
	}

	public void setApellido(final String apellido) {
		this.apellido = apellido;
	}
	// }}


	// {{ Nombre (property)
	private String nombre;

	@Persistent
	@MemberOrder(sequence = "2")
	@javax.jdo.annotations.Column(allowsNull="false")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}
	// }}


	// {{ Direccion (property)
	private Direccion direccion;

	//@Hidden(where = Where.ALL_TABLES)

	@Persistent
	@MemberOrder(sequence = "7")
	//@javax.jdo.annotations.Column(allowsNull="true", name = "DIRECCION_ID")
	@javax.jdo.annotations.Column(allowsNull="true")
	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(final Direccion direccion) {
		this.direccion = direccion;
	}
	// }}


	// {{ FechaNacimiento (property)
	private LocalDate fechaNacimiento;

	@Persistent
	@MemberOrder(sequence = "6")
	@javax.jdo.annotations.Column(allowsNull="true")
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(final LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	// }}

	
	// {{ Nacionalidad (property)
	private E_nacionalidad nacionalidad;

	@Persistent
	@MemberOrder(sequence = "5")
	@javax.jdo.annotations.Column(allowsNull="true")
	public E_nacionalidad getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(final E_nacionalidad nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	// }}


	// {{ Sexo (property)
	private E_sexo sexo;

	
	@Persistent
	@MemberOrder(sequence = "4")
	@javax.jdo.annotations.Column(allowsNull="true")
	//@Hidden(where = Where.ALL_TABLES)
	public E_sexo getSexo() {
		return sexo;
	}

	public void setSexo(final E_sexo sexo) {
		this.sexo = sexo;
	}
	// }}


	// {{ Telefono (property)
	private String telefono;

	@Persistent
	@MemberOrder(sequence = "8")
	@javax.jdo.annotations.Column(allowsNull="false")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(final String telefono) {
		this.telefono = telefono;
	}
	// }}
	
	// {{ EMAIL (property)
	private String email;

	@Persistent
	@MemberOrder(sequence = "9")
	@javax.jdo.annotations.Column(allowsNull="true")
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
	// }}


}
