package domainapp.dom.app.pedido;

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

@DomainObject(objectType = "PEDIDO",bounded=true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class Pedido {
	private String descripcion;
	private String lugar;
	private String vendedor;
	private String urgencia;
	private String sucursal;

	

	public Pedido(String descripcion, String lugar, String vendedor,
			String urgencia, String sucursal) {
		super();
		this.descripcion = descripcion;
		this.lugar = lugar;
		this.vendedor = vendedor;
		this.urgencia = urgencia;
		this.sucursal = sucursal;
	}

	public Pedido() {
		super();
	}

	@MemberOrder(sequence="1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@MemberOrder(sequence="2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	@MemberOrder(sequence="3")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}

	@MemberOrder(sequence="4")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public String getUrgencia() {
		return urgencia;
	}

	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
	}


	@MemberOrder(sequence="4")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public String getSucursal() {
		return urgencia;
	}

	public void setSucursal(String urgencia) {
		this.urgencia = urgencia;
	}


	@javax.inject.Inject
    DomainObjectContainer container;
}
