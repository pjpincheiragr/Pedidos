package domainapp.dom.app.pedido;

import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.value.Blob;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Where;
import domainapp.dom.app.marca.Marca;
import domainapp.dom.app.proveedor.Proveedor;
import domainapp.dom.app.servicios.E_estado;
import domainapp.dom.app.servicios.E_estado_item;
import domainapp.dom.app.servicios.E_tieneMuestra;
import domainapp.dom.app.servicios.E_urgencia_pedido;
import domainapp.dom.app.sucursal.Sucursal;
import domainapp.dom.app.tipo.Tipo;
import domainapp.dom.app.vendedor.Vendedor;
import javax.jdo.annotations.Join;
import org.joda.time.DateTime;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
//@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.APPLICATION)

@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "Pedido_ID")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "ListarTodosPorUrgencia", language = "JDOQL", value = "SELECT  "
				+ " FROM domainapp.dom.app.ruta.RutaItem "
				+ " WHERE urgencia==:urgencia && estado==:estado && activo == true"),
		
		@javax.jdo.annotations.Query(name = "ListarTodosPorVendedor", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE activo == true && vendedor==:vendedor "),
		
		@javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE activo == true"),
		@javax.jdo.annotations.Query(name = "ListarNuevos", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "Where (estado==NUEVO) && activo == true && this.ListaPedidos != null "),
		@javax.jdo.annotations.Query(name = "findByDescription", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE ((:descripcion=='') || (descripcion.toLowerCase().indexOf(:descripcion) >= 0)) && activo == true && this.ListaPedidos != null "),
		@javax.jdo.annotations.Query(name = "findByState", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido"
				+ "WHERE (estado==:estado) && activo == true && this.ListaPedidos != null "),
		@javax.jdo.annotations.Query(name = "findBySeller", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE (vendedor==:vendedor) && activo == true"
				+ " && this.ListaPedidos != null ") })

@DomainObject(objectType = "PEDIDO", bounded = true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class Pedido {

	//@PrimaryKey
	//private long numero;
	private Tipo tipo;
	private Proveedor proveedor;
	private DateTime fechaHora;
	private Vendedor vendedor;
	private float valor;
	private E_estado estado;
	private Sucursal sucursal;
	private String observacion;
	private boolean activo;
	private E_urgencia_pedido urgencia;

	public String title() {
		return  getVendedor().getNombre()
				+ " - " + getSucursal() + "-" + this.getFechaHora() ;
	}

	public Pedido(Tipo tipo, Proveedor proveedor, DateTime fechaHora,
			Vendedor vendedor, float valor, E_estado estado, Sucursal sucursal,
			String observacion, boolean activo, E_urgencia_pedido urgencia) {
		super();
		this.tipo = tipo;
		this.proveedor = proveedor;
		this.vendedor = vendedor;
		this.valor = valor;
		this.estado = estado;
		this.sucursal = sucursal;
		this.fechaHora = fechaHora;
		this.observacion = observacion;
		this.activo = activo;
		this.urgencia = urgencia;
	}

	public Pedido() {
		super();
	}

	/*
	 * Orden es responsabilidad de RutaItem
	 * 
	 * @MemberOrder(sequence = "1")
	 * 
	 * @javax.jdo.annotations.Column(allowsNull = "true")
	 * 
	 * @Property(editing = Editing.ENABLED) public void setOrden(int orden) {
	 * this.orden = orden; }
	 * 
	 * public int getOrden() { return this.orden; }
	

	@javax.jdo.annotations.Column(allowsNull = "false")
	@javax.jdo.annotations.PrimaryKey(column = "numero")
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "numero")
	@MemberOrder(name = "Numero", sequence = "1")
	public long getNumero() {
		return numero;
	}

	public void setNumero(final long numero) {
		this.numero = numero;
	}
	 */
	@MemberOrder(sequence = "1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@MemberOrder(sequence = "2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	/*
	 * Tiempo es responsabilidad de RutaItem
	 * 
	 * @MemberOrder(sequence = "3")
	 * 
	 * @javax.jdo.annotations.Column(allowsNull = "false")
	 * 
	 * @Property(editing = Editing.DISABLED) public int getTiempo() { return
	 * tiempo; }
	 * 
	 * public void setTiempo(int tiempo2) { this.tiempo = tiempo2; }
	 */
	@MemberOrder(sequence = "4")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	@MemberOrder(sequence = "5")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	@MemberOrder(sequence = "6")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public E_estado getEstado() {
		return estado;
	}

	/*
	 * Se agrega en setEstado la creación de un nuevo Historial de pedido, con
	 * la información del estado actual antes de que este cambie. Se propone que
	 * el vendedor pueda visualizar el historial de Sus pedidos ACTIVOS
	 */
	public void setEstado(E_estado estado) {
		this.estado = estado;
	}

	@MemberOrder(sequence = "7")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setFechaHora(DateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	@javax.jdo.annotations.Column(allowsNull = "true")
	public DateTime getFechaHora() {
		return this.fechaHora;

	}

	// Agrego campo observaciÃ³n

	@javax.jdo.annotations.Column(allowsNull = "true", length = 600)
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(final String observacion) {
		this.observacion = observacion;
	}

	// Fin campo observaciÃ³n

	// {{ Pedido Item (Property)
	@Join
	@javax.jdo.annotations.Column(allowsNull = "true")
	// @Persistent(mappedBy = "Pedido", dependentElement = "false")
	private List<PedidoItem> ListaPedidos = new ArrayList<PedidoItem>();

	// @Render(Type.EAGERLY)
	@MemberOrder(sequence = "1.5")
	@CollectionLayout(render = RenderType.EAGERLY)
	public List<PedidoItem> getPedidoItem() {
		return this.ListaPedidos;
	}

	public void setPedidosItem(final List<PedidoItem> listaPedidos) {
		this.ListaPedidos = listaPedidos;
	}

	@ActionLayout(named = "Agregar Item")
	public Pedido addPedidoItem(
			@ParameterLayout(named = "Muestra") @Parameter(optionality = Optionality.OPTIONAL) E_tieneMuestra muestra,
			@ParameterLayout(named = "Codigo") @Parameter(optionality = Optionality.OPTIONAL) String codigo,
			@ParameterLayout(named = "Marca") @Parameter(optionality = Optionality.OPTIONAL) Marca marca,
			@ParameterLayout(named = "Cantidad") @Parameter(optionality = Optionality.OPTIONAL) int cantidad,
			//@ParameterLayout(named = "Estado") @Parameter(optionality = Optionality.OPTIONAL) E_estado_item estado,
			@ParameterLayout(named = "Observación", multiLine = 10) @Parameter(optionality = Optionality.OPTIONAL) String observacion,
			final @ParameterLayout(named = "Imagen") @Parameter(optionality = Optionality.OPTIONAL) Blob attachment) {

		final PedidoItem PedidoItem = container
				.newTransientInstance(PedidoItem.class);

		PedidoItem.setMuestra(muestra);
		PedidoItem.setMarca(marca);
		PedidoItem.setCodigo(codigo);
		PedidoItem.setCantidad(cantidad);
		PedidoItem.setEstado(E_estado_item.ACTIVO);
		PedidoItem.setObservacion(observacion);
		PedidoItem.setAttachment(attachment);
		PedidoItem.setActivo(true);
		container.persistIfNotAlready(PedidoItem);
		getPedidoItem().add(PedidoItem);
		return this;
	}

	// fin

	// correspondiente a la clasificaciÃ³n de urgencia del pedido

	@MemberOrder(sequence = "8")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public E_urgencia_pedido getUrgencia() {
		return urgencia;
	}

	public void setUrgencia(E_urgencia_pedido urgencia) {
		this.urgencia = urgencia;
	}

	// urgencia de Pedido

	@Property(hidden = Where.EVERYWHERE)
	@MemberOrder(sequence = "9")
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@ActionLayout(named = "Eliminar Pedido")
	public Pedido deletePedido() {
		this.setActivo(false);
		return this;
	}

	@ActionLayout(named = "Ver Historial")
	public List<PedidoHistorial> viewHistory() {
		
		final List<PedidoHistorial> historial = this.container
				.allMatches(new QueryDefault<PedidoHistorial>(PedidoHistorial.class,
						"VerHistorial"));
		if (historial.isEmpty()) {
			this.container.warnUser("No hay pedidos cargados en el sistema");
		}
		return historial;
	}
	
	/*
	 * @Programmatic public boolean hideDeletePedido() { if (!isActivo()) return
	 * true; else return false; }
	*/
	
	@javax.inject.Inject
	DomainObjectContainer container;
	@javax.inject.Inject
	RepositorioPedidoItem repositorioPedidoItem;
}