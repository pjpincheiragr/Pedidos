package domainapp.dom.app.pedido;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.value.Blob;
import domainapp.dom.app.marca.Marca;
import domainapp.dom.app.servicios.E_estado_item;
import domainapp.dom.app.servicios.E_tieneMuestra;





@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "PedidoItem_ID")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.APPLICATION)
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido " + "WHERE activo == true"),
		@javax.jdo.annotations.Query(name = "ListarPendientes", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "Where (estado==ASIGNADO) || (estado=EN_PROCESO) && activo == true"),
		@javax.jdo.annotations.Query(name = "findByDescription", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE ((:descripcion=='') || (descripcion.toLowerCase().indexOf(:descripcion) >= 0)) && activo == true"),
		@javax.jdo.annotations.Query(name = "findByState", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE (estado==:estado) && activo == true"),
		@javax.jdo.annotations.Query(name = "findBySeller", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE (vendedor==:vendedor) && activo == true"),
				@javax.jdo.annotations.Query(name = "BuscarPorClave", language = "JDOQL", value = "SELECT "
						+ "FROM domainapp.dom.app.pedido.PedidoItem "
						+ "WHERE (clave == :clave) && activo == true")
})

@DomainObject(objectType = "PEDIDOITEM", bounded = true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class PedidoItem {

	private E_tieneMuestra muestra;
	private String codigo;
	private Marca marca;
	private int cantidad;
	private E_estado_item estado;
	private Blob attachment;
	private String observacion;
	private boolean activo;

	public String title() {
		return getCodigo() + " - " + getMarca().getDescripcion() + "Tiene Muestra: " + this.getMuestra();
	}

	public PedidoItem(E_tieneMuestra tieneMuestra, String codigo, Marca marca,
			int cantidad, E_estado_item estado, String observacion,
			boolean activo) {
		super();
		this.muestra = tieneMuestra;
		this.codigo = codigo;
		this.marca = marca;
		this.cantidad = cantidad;
		this.estado = estado;
		this.observacion = observacion;
		this.activo = activo;
	}

	public PedidoItem() {
		super();
	}

	
	@PrimaryKey
	private long clave;

	@javax.jdo.annotations.Column(allowsNull = "false")
	@javax.jdo.annotations.PrimaryKey(column = "clave")
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY, sequence = "clave")
	@MemberOrder(sequence = "1")
	public long getClave() {
		return clave;
	}

	public void setClave(final long clave) {
		this.clave = clave;
	}
	
	@MemberOrder(sequence = "2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public E_tieneMuestra getMuestra() {
		return muestra;
	}

	public void setMuestra(E_tieneMuestra tieneMuestra) {
		this.muestra = tieneMuestra;
	}

	//
	/*
	 * public boolean hideCodigo() { return tieneMuestra(); }
	 */

	@MemberOrder(sequence = "3")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@MemberOrder(sequence = "4")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	@MemberOrder(sequence = "5")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@MemberOrder(sequence = "6")
	@javax.jdo.annotations.Column(allowsNull = "false")
	public E_estado_item getEstado() {
		return estado;
	}

	public void setEstado(E_estado_item estado) {
		this.estado = estado;
	}

	// Agrego campo observaciÃ³n
	@MemberOrder(sequence = "7")
	@javax.jdo.annotations.Column(allowsNull = "true", length = 600)
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(final String observacion) {
		this.observacion = observacion;
	}

	// Fin campo observaciÃ³n

	/* Esta porcion de codigo nos permite insertar documentos en nuestra clase. */

	@javax.jdo.annotations.Persistent(defaultFetchGroup = "false")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Blob getAttachment() {
		return attachment;
	}

	public void setAttachment(final Blob attachment) {
		this.attachment = attachment;
	}

	// SecciÃ³n de Borrar Pedido

	@Property(hidden = Where.EVERYWHERE)
	@MemberOrder(sequence = "8")
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@ActionLayout(named = "Eliminar Item")
	public PedidoItem deletePedidoItem() {
		/*
		 * boolean band = true; List<Empleado> lista = this.container
		 * .allMatches(new QueryDefault<Empleado>(Empleado.class,
		 * "ListarTodos")); for (Empleado e : lista) { if
		 * (e.getArea().equals(this)) { band = false; } } if (band == true) {
		 * this.setActivo(false); this.container
		 * .informUser("El area ha sido eliminado de manera exitosa"); } else {
		 * this.container .warnUser(
		 * "No se pudo realizar esta acciÃ³n. El objeto que intenta eliminar esta asignado"
		 * ); }
		 */
		this.setActivo(false);
		return this;
	}

	// Fin secciÃ³n borrar pedido

	@javax.inject.Inject
	DomainObjectContainer container;
	
	
}