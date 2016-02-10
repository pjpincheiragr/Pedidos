package domainapp.dom.app.pedido;

import java.sql.Time;
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

import domainapp.dom.app.proveedor.Proveedor;
import domainapp.dom.app.servicios.E_estado;
import domainapp.dom.app.sucursal.Sucursal;
import domainapp.dom.app.vendedor.Vendedor;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="Pedido_ID")
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
	private Proveedor proveedor;
	private Time tiempo;
	private Vendedor vendedor;
	private float valor;
	private E_estado estado;
	private Sucursal sucursal;

	public String title() {		
		return  getDescripcion()   ;
	}

	public Pedido(String descripcion, Proveedor proveedor, Time tiempo,Vendedor vendedor,
			float valor, E_estado estado, Sucursal sucursal) {
		super();
		this.descripcion = descripcion;
		this.proveedor = proveedor;
		this.tiempo=tiempo;
		this.vendedor = vendedor;
		this.valor=valor;
		this.estado = estado;
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
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	@MemberOrder(sequence="3")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public Time getTiempo() {
		return tiempo;
	}

	public void setTiempo(Time tiempo) {
		this.tiempo=tiempo;
	}

	
	
	@MemberOrder(sequence="4")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	
	@MemberOrder(sequence="5")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor=valor;
	}
	
	
	@MemberOrder(sequence="6")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public E_estado getEstado() {
		return estado;
	}

	public void setEstado(E_estado estado) {
		this.estado = estado;
	}


	@MemberOrder(sequence="7")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}


	@javax.inject.Inject
    DomainObjectContainer container;
}
