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
import org.apache.isis.applib.value.DateTime;
import org.apache.isis.applib.value.TimeStamp;

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
	@javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.pedido.Pedido "
			+ " order by fecha "),
	@javax.jdo.annotations.Query(name = "ListarPendientes", language = "JDOQL", value = "SELECT "
					+ "FROM domainapp.dom.app.pedido.Pedido "
					+ "Where (estado==ASIGNADO) || (estado=EN_PROCESO)"
					+ " order by fecha "),
	@javax.jdo.annotations.Query(name = "findByDescription", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.pedido.Pedido "
			+ "WHERE ((:descripcion=='') || (descripcion.toLowerCase().indexOf(:descripcion) >= 0))"
			+ " order by fecha "),
	@javax.jdo.annotations.Query(name = "findByState", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.pedido.Pedido "
			+ "WHERE (estado==:estado)"
			+ " order by fecha "),
	@javax.jdo.annotations.Query(name = "findBySeller", language = "JDOQL", value = "SELECT "
					+ "FROM domainapp.dom.app.pedido.Pedido "
					+ "WHERE (vendedor==:vendedor)"
					+ " order by fecha ")
})

@DomainObject(objectType = "PEDIDO",bounded=true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class Pedido {
	private String descripcion;
	private Proveedor proveedor;
	private Timestamp fecha;
	private int tiempo;
	private Vendedor vendedor;
	private float valor;
	private E_estado estado;
	private Sucursal sucursal;

	public String title() {		
		return  getDescripcion()   ;
	}

	public Pedido(String descripcion, Proveedor proveedor, Timestamp fecha, int tiempo,Vendedor vendedor,
			float valor, E_estado estado, Sucursal sucursal) {
		super();
		this.descripcion = descripcion;
		this.proveedor = proveedor;
		this.tiempo=tiempo;
		this.vendedor = vendedor;
		this.valor=valor;
		this.estado = estado;
		this.sucursal = sucursal;
		this.fecha= fecha;
	
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
	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo2) {
		this.tiempo=tiempo2;
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
	
	@MemberOrder(sequence="8")
	@javax.jdo.annotations.Column(allowsNull = "false")
	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	
	@javax.inject.Inject
    DomainObjectContainer container;
}
