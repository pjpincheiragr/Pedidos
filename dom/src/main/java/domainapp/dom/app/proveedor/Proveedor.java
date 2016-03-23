package domainapp.dom.app.proveedor;



import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;

import domainapp.dom.app.servicios.Direccion;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="Proveedor_ID")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.proveedor.Proveedor "),
	@javax.jdo.annotations.Query(name = "findByName", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.proveedor.Proveedor "
			+ "WHERE ((:nombre=='') || (nombre.toLowerCase().indexOf(:nombre) >= 0))"
			+ " order by nombre "),
	@javax.jdo.annotations.Query(name = "findByCode", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.proveedor.Proveedor "
			+ "WHERE ((:codigo=='') || (codigo.toLowerCase().indexOf(:codigo) >= 0))"
			+ " order by codigo ") })

@DomainObject(objectType = "PROVEEDOR",bounded=true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class Proveedor {
	private String nombre;
	private String codigo;
	private Direccion direccion;
	
	public String title() {		
		return getNombre() + ' '+ getCodigo()   ;
	}

	public Proveedor(String nombre, String codigo, Direccion direccion) {
		super();
		this.nombre = nombre;
		this.codigo = codigo;
		this.direccion = direccion;

	}

	public Proveedor() {
		super();
	}

	@MemberOrder(sequence="1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@MemberOrder(sequence="2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@MemberOrder(sequence="3")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

		@javax.inject.Inject
    DomainObjectContainer container;
}
