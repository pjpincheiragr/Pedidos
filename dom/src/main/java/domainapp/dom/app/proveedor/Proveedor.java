package domainapp.dom.app.proveedor;

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
         column="Aceite_ID")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(
				name = "ListarTodos", language = "JDOQL",
				value = "SELECT "
				+ "FROM domainapp.dom.app.aceite.Aceite "
				+ "WHERE activo == true"),
		@javax.jdo.annotations.Query(
				name = "Buscar_Nombre", language = "JDOQL",
				value = "SELECT "
				+ "FROM domainapp.dom.app.Aceite "
				+ "WHERE nombre.indexOf(:nombre) >= 0 && activo == true"),
		@javax.jdo.annotations.Query(
				name = "Buscar_Marca", language = "JDOQL",
				value = "SELECT "
				+ "FROM domainapp.dom.app.Aceite "
				+ "WHERE marca.indexOf(:marca) >= 0 && activo == true"),
		@javax.jdo.annotations.Query(
				name = "Buscar_Codigo", language = "JDOQL",
				value = "SELECT "
				+ "FROM domainapp.dom.app.Aceite "
				+ "WHERE codigo.indexOf(:codigo) >= 0 && activo == true"),
		@javax.jdo.annotations.Query(
				name = "Buscar_Tipo", language = "JDOQL",
				value = "SELECT "
				+ "FROM domainapp.dom.app.Aceite "
				+ "WHERE tipoAceite.indexOf(:tipoAceite) >= 0 && activo == true")
})

@DomainObject(objectType = "PROVEEDOR",bounded=true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class Proveedor {
	private String nombre;
	private String codigo;
	private String direccion;
	

	public Proveedor(String nombre, String codigo, String direccion) {
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
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

		@javax.inject.Inject
    DomainObjectContainer container;
}
