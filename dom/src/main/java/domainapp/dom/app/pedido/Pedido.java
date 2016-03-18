package domainapp.dom.app.pedido;

import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.value.Blob;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
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
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.annotation.ActionLayout.Position;

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

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "Pedido_ID")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Uniques({ @javax.jdo.annotations.Unique(name = "Pedido_clave_must_be_unique", members = { "clave" }) })
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.APPLICATION)
@javax.jdo.annotations.Queries({

		@javax.jdo.annotations.Query(name = "BuscarPorClave", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.pedido.Pedido "
			+ "WHERE clave == :clave && activo == true "),

		@javax.jdo.annotations.Query(name = "ListarTodosPorUrgencia", language = "JDOQL", value = "SELECT  "
				+ " FROM domainapp.dom.app.ruta.RutaItem "
				+ " WHERE urgencia == :urgencia && estado == :estado && activo == true"),

		@javax.jdo.annotations.Query(name = "ListarTodosPorVendedor", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE activo == true && vendedor == :vendedor "),

		@javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE activo == true"),

		@javax.jdo.annotations.Query(name = "ListarNuevos", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE estado == :estado && activo == true"),

		@javax.jdo.annotations.Query(name = "findByState", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE estado == :estado && activo == true"),

		@javax.jdo.annotations.Query(name = "findBySeller", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.pedido.Pedido "
				+ "WHERE vendedor == :vendedor && activo == true") })

@DomainObject(objectType = "PEDIDO", bounded = true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class Pedido {

	private Tipo tipo;
	private Proveedor proveedor;
	private Calendar fechaHora;
	private Vendedor vendedor;
	private float valor;
	private E_estado estado;
	private Sucursal sucursal;
	private String observacion;
	private boolean activo;
	private E_urgencia_pedido urgencia;
	private List<PedidoItem> ListaPedidos = new ArrayList<PedidoItem>();
	private String numeroVenta;
	private String tiempoEstimado;
	private boolean confirmado;

	public String title() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM");

		return getVendedor().getNombre() + " - "
				+ "Sucursal: " + getSucursal().getCodigoSucursal() + " - "
				+ dateFormat.format(this.fechaHora.getTime());
	}

	public Pedido(Tipo tipo, Proveedor proveedor, Calendar fechaHora,
			Vendedor vendedor, float valor, E_estado estado, Sucursal sucursal,
			String observacion, boolean activo, E_urgencia_pedido urgencia,
			String numeroVenta) {
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
		this.numeroVenta = numeroVenta;
	}

	public Pedido() {
		super();
	}

	
	@ActionLayout(named = "Confirmar", position = Position.BELOW)
	public Pedido confirmar(){
		this.setConfirmado(true);
		return this;	
	}
	
	public String disableConfirmar(){
		return getConfirmado() ? "El pedido ya fue confirmado" : null;
	}
	

	@ActionLayout(named = "Actualizar T.E.", position = Position.BELOW)
	public Pedido actualizarTiempo(String tEstimado){
		this.setTiempoEstimado(tEstimado);
		//this.repositorioPedido.enviarSMS();
		return this;	
	}
	
	
	@MemberOrder(name = "Estado del Pedido", sequence = "1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	public boolean getConfirmado() {
		return this.confirmado;
	}

	public void setConfirmado(boolean confirmado) {
		this.confirmado = confirmado;;

	}
	
	@MemberOrder(name = "Estado del Pedido", sequence = "2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	public E_estado getEstado() {
		return estado;
	}

	public void setEstado(E_estado estado) {
		this.estado = estado;
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
	@Property(editing = Editing.ENABLED)
	@javax.jdo.annotations.Column(allowsNull = "false")
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;

	}

	@MemberOrder(sequence = "2")
	@Property(editing = Editing.ENABLED)
	@javax.jdo.annotations.Column(allowsNull = "true")
	public String getNumeroVenta() {
		return this.numeroVenta;
	}

	public void setNumeroVenta(String numeroVenta) {
		this.numeroVenta = numeroVenta;
	}

	@MemberOrder(sequence = "2")
	@Property(editing = Editing.DISABLED)
	@javax.jdo.annotations.Column(allowsNull = "false")
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	@MemberOrder(sequence = "4")
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.DISABLED)
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

	

	@MemberOrder(sequence = "7")
	@Property(editing = Editing.DISABLED)
	@javax.jdo.annotations.Column(allowsNull = "false")
	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	
	
	@Property(editing = Editing.DISABLED)
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Programmatic
	public Calendar getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(Calendar fechaHora) {
		this.fechaHora = fechaHora;
	}

	// COMIENZA EL CAMPO OBSERVACION


	@Property(editing = Editing.ENABLED)
	@javax.jdo.annotations.Column(allowsNull = "true", length = 600)
	public String getObservacion() {
		return observacion;
	}
	
	public void setObservacion(final String observacion) {
		this.observacion = observacion;
	}

	// Fin campo observacion

	@MemberOrder(sequence = "8")
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.ENABLED)
	public String getTiempoEstimado() {

		return this.tiempoEstimado;
	}

	public void setTiempoEstimado(String tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;

	}

	// {{ Pedido Item (Property)
	@Join
	@javax.jdo.annotations.Column(allowsNull = "true")
	// @Persistent(mappedBy = "Pedido", dependentElement = "false")
	// @Render(Type.EAGERLY)
	@MemberOrder(sequence = "1.5")
	@CollectionLayout(render = RenderType.EAGERLY)
	public List<PedidoItem> getPedidoItem() {
		return this.ListaPedidos;
	}

	public void setPedidoItem(final List<PedidoItem> listaPedidos) {
		this.ListaPedidos = listaPedidos;
	}

	@MemberOrder(sequence = "1", name = "PedidoItem")
	@ActionLayout(named = "Agregar Item", position = Position.PANEL)
	public Pedido addPedidoItem(
			@ParameterLayout(named = "Muestra") @Parameter(optionality = Optionality.OPTIONAL) E_tieneMuestra muestra,
			@ParameterLayout(named = "Codigo") @Parameter(optionality = Optionality.OPTIONAL) String codigo,
			@ParameterLayout(named = "Marca") @Parameter(optionality = Optionality.OPTIONAL) Marca marca,
			@ParameterLayout(named = "Cantidad") @Parameter(optionality = Optionality.OPTIONAL) int cantidad,
			// @ParameterLayout(named = "Estado") @Parameter(optionality =
			// Optionality.OPTIONAL) E_estado_item estado,
			@ParameterLayout(named = "Observación", multiLine = 10) @Parameter(optionality = Optionality.OPTIONAL) String observacion,
			final @ParameterLayout(named = "Imagen") @Parameter(optionality = Optionality.OPTIONAL) Blob attachment) {

		final PedidoItem PedidoItem = container
				.newTransientInstance(PedidoItem.class);

		PedidoItem.setMuestra(muestra);
		PedidoItem.setMarca(marca);
		PedidoItem.setCodigo(codigo);
		PedidoItem.setCantidad(cantidad);
		PedidoItem.setEstado(E_estado_item.PENDIENTE);
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
				.allMatches(new QueryDefault<PedidoHistorial>(
						PedidoHistorial.class, "VerHistorial"));
		if (historial.isEmpty()) {
			this.container.warnUser("No hay pedidos cargados en el sistema");
		}
		return historial;
	}

	
	
	@javax.inject.Inject
	DomainObjectContainer container;
	
	@javax.inject.Inject
	RepositorioPedidoItem repositorioPedidoItem;
	
	@javax.inject.Inject
	RepositorioPedido repositorioPedido;
}