package domainapp.dom.app.cadete;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;

import domainapp.dom.app.pedido.Pedido;
import domainapp.dom.app.proveedor.Proveedor;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "CadeteItem_ID")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "ListarTodosPorUrgencia", language = "JDOQL", value = "SELECT  "
				+ " FROM domainapp.dom.app.ruta.CadeteItem "
				+ " WHERE pedido.urgencia==:urgencia && activo == true"
				+ " order by orden "),
		@javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.ruta.CadeteItem " + " order by orden "),
		@javax.jdo.annotations.Query(name = "ListarPendientes", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "Where (estado==ASIGNADO) || (estado=EN_PROCESO) && activo == true"
				+ " order by orden "),
		@javax.jdo.annotations.Query(name = "findByDescription", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE ((:descripcion=='') || (descripcion.toLowerCase().indexOf(:descripcion) >= 0)) && activo == true"
				+ " order by orden "),
		@javax.jdo.annotations.Query(name = "findByState", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE (estado==:estado) && activo == true"
				+ " order by orden "),
		@javax.jdo.annotations.Query(name = "findBySeller", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE (vendedor==:vendedor && activo == true)"
				+ " order by orden "),
		@javax.jdo.annotations.Query(name = "BuscarPorClave", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.ruta.CadeteItem "
				+ "WHERE clavePedido == :clavePedido "),

})
@DomainObject(objectType = "CADETEITEM", bounded = true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class CadeteItem {

	private Pedido pedido;
	private Proveedor proveedor;
	private long clavePedido;
	private int orden;
	private boolean estado;
	private Cadete cadete;
	private int tiempo;

	public String title() {
		return this.pedido.getVendedor().getNombre()+ " " +this.clavePedido+" "+ this.pedido.getProveedor().getNombre() ;
	}

	public CadeteItem(Pedido pedido, long clavePedido, Proveedor proveedor,
			int orden, boolean estado, Cadete cadete, int tiempo) {
		super();
		this.pedido = pedido;
		this.clavePedido = clavePedido;
		this.proveedor = proveedor;
		this.orden = orden;
		this.estado = estado;
		this.cadete = cadete;
		this.tiempo = tiempo;
	}

	public CadeteItem() {
		super();
	}

	@MemberOrder(sequence = "1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	//@MemberOrder(sequence = "1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	@Programmatic
	public long getClavePedido() {
		return this.clavePedido;
	}

	public void setClavePedido(long clavePedido) {
		this.clavePedido = clavePedido;
	}

	@MemberOrder(sequence = "1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	
	@MemberOrder(sequence = "2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.ENABLED)
	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	@MemberOrder(sequence = "3")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.ENABLED)
	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo2) {
		this.tiempo = tiempo2;
	}

	@MemberOrder(sequence = "4")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@MemberOrder(sequence = "5")
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
	public Cadete getCadete() {
		return cadete;
	}

	public void setCadete(Cadete cadete) {
		this.cadete = cadete;
	}

	@javax.inject.Inject
	DomainObjectContainer container;
}
