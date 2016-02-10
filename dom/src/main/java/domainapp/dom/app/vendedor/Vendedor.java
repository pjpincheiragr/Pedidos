package domainapp.dom.app.vendedor;

import java.sql.Timestamp;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="Vendedor_ID")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.vendedor.Vendedor "),
	@javax.jdo.annotations.Query(name = "findByName", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.vendedor.Vendedor "
			+ "WHERE ((:nombre=='') || (nombre.toLowerCase().indexOf(:nombre) >= 0))"
			+ " order by nombre "),
	@javax.jdo.annotations.Query(name = "findByNameAuxiliar", language = "JDOQL", value = "SELECT "
					+ "FROM domainapp.dom.app.vendedor.Vendedor "
					+ "WHERE (nombre==nombre))"),
	@javax.jdo.annotations.Query(name = "findByCode", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.vendedor.Vendedor "
			+ "WHERE ((:codigo=='') || (codigo.toLowerCase().indexOf(:codigo) >= 0))"
			+ " order by codigo ") })

@DomainObject(objectType = "VENDEDOR",bounded=true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class Vendedor {
	private String nombre;
	private String codigo;
	
	public String title() {		
		return  getNombre()   ;
	}
	public Vendedor(String nombre, String codigo) {
		super();
		this.nombre = nombre;
		this.codigo = codigo;
		

	}

	public Vendedor() {
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

	@javax.inject.Inject
    DomainObjectContainer container;
}
