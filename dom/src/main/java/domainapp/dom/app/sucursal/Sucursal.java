package domainapp.dom.app.sucursal;



import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import domainapp.dom.app.servicios.Direccion;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "Sucursal_ID")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.sucursal.Sucursal "),
		@javax.jdo.annotations.Query(name = "ListarInactivos", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.area.Area " + "WHERE activo == false"),
		@javax.jdo.annotations.Query(name = "findByName", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.sucursal.Sucursal "
				+ "WHERE ((:nombre=='') || (nombre.toLowerCase().indexOf(:nombre) >= 0))"
				+ " order by nombre ") })
@DomainObject(objectType = "SUCURSAL", bounded = true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class Sucursal {

	private String codigoSucursal;
	private String nombre;
	private Direccion direccion;

	public String title() {
		return "Sucursal:" + " " + getNombre();
	}

	public Sucursal(String codigoSucursal, String nombre, Direccion direccion
			) {
		super();
		this.codigoSucursal = codigoSucursal;
		this.nombre = nombre;
		this.direccion = direccion;
	}

	public Sucursal() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Persistent
	@MemberOrder(sequence = "1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	public String getCodigoSucursal() {
		return codigoSucursal;
	}

	public void setCodigoSucursal(String codigoSucursal) {
		this.codigoSucursal = codigoSucursal;
	}

	@Persistent
	@Property(editing = Editing.DISABLED)
	@MemberOrder(sequence = "2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Persistent
	@MemberOrder(sequence = "3")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}


	@javax.inject.Inject
	DomainObjectContainer container;

	@Override
	public String toString() {
		return "Sucursal " + nombre;
	}

}
