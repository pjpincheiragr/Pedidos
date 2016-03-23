package domainapp.dom.app.cadete;

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
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.annotation.ActionLayout.Position;
import org.joda.time.DateTime;

import domainapp.dom.app.pedido.Pedido;
import domainapp.dom.app.pedido.PedidoHistorial;
import domainapp.dom.app.pedido.RepositorioPedido;
import domainapp.dom.app.servicios.E_estado;
import domainapp.dom.app.servicios.E_urgencia_pedido;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "Cadete_ID")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.cadete.Cadete "),
		@javax.jdo.annotations.Query(name = "findByName", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.cadete.Cadete "
				+ "WHERE ((:nombre=='') || (nombre.toLowerCase().indexOf(:nombre) >= 0))"
				+ " order by nombre "),
		@javax.jdo.annotations.Query(name = "findByCode", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.cadete.Cadete "
				+ "WHERE ((:codigo=='') || (codigo.toLowerCase().indexOf(:codigo) >= 0))"
				+ " order by codigo ") })

@DomainObject(objectType = "cadete", bounded = true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class Cadete {
	private String nombre;
	private String codigo;
	private List<CadeteItem> listaPedidos = new ArrayList<CadeteItem>();
	private List<Pedido> listaPedidosUrgentes = new ArrayList<Pedido>();
	private List<Pedido> listaPedidosProgramables = new ArrayList<Pedido>();
	private boolean activo;
	
	public String title() {		
		return  getNombre();
	}

	public Cadete(String nombre, String codigo,boolean activo) {
		super();
		this.nombre = nombre;
		this.codigo = codigo;
		this.activo = activo;
	}

	public Cadete() {
		super();
	}
	
	@MemberOrder(sequence = "1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Programmatic
	@MemberOrder(sequence = "2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public String getCodigo() {
		return codigo;
	}

	//FALTA VALIDAR EL CODIGO! 
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.ENABLED)
	@CollectionLayout(render = RenderType.EAGERLY)
	public List<CadeteItem> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<CadeteItem> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	@MemberOrder(sequence = "1", name = "ListaPedidos")
	@ActionLayout(named = "Quitar", position = Position.PANEL)
	public Cadete quitarPedido(CadeteItem cadeteItem) {
		final Pedido pedido = cadeteItem.getPedido();
		this.getListaPedidos().remove(cadeteItem);
		pedido.setEstado(E_estado.NUEVO);
		final PedidoHistorial oPedidoHistorial = container
				.newTransientInstance(PedidoHistorial.class);
		oPedidoHistorial.setPedido(pedido);
		oPedidoHistorial.setObservacion("Quitado de la ruta: ");
		oPedidoHistorial.setFechaHora(DateTime.now());
		oPedidoHistorial.setEstado(pedido.getEstado());

		container.persistIfNotAlready(oPedidoHistorial);
		return this;
	}

	@Programmatic
	public List<CadeteItem> choices0QuitarPedido(final CadeteItem rutaItem) {
		return this.getListaPedidos();
	}

	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.ENABLED)
	@CollectionLayout(render = RenderType.EAGERLY)
	public List<Pedido> getListaPedidosUrgentes() {
		return repositorioPedido
				.listAllByUrgency(E_urgencia_pedido.RESPUESTA_RAPIDA);
	}

	public void setListaPedidosUrgentes(List<Pedido> listaPedidosUrgentes) {
		this.listaPedidosUrgentes = listaPedidosUrgentes;
	}

	@MemberOrder(sequence = "1", name = "ListaPedidosUrgentes")
	@ActionLayout(named = "Agregar", position = Position.PANEL)
	public Cadete asignarPedidoUrgente(Pedido pedido,
			@ParameterLayout(named = "Orden") int orden,
			@ParameterLayout(named = "Tiempo") int tiempo) {
		final CadeteItem oCadeteItem = container
				.newTransientInstance(CadeteItem.class);
		oCadeteItem.setEstado(false);
		this.ordenarItems(orden);
		oCadeteItem.setOrden(orden);
		oCadeteItem.setPedido(pedido);
		oCadeteItem.setClavePedido(pedido.getClave());
		oCadeteItem.setProveedor(pedido.getProveedor());
		oCadeteItem.setCadete(this);
		oCadeteItem.setTiempo(tiempo);
		container.persistIfNotAlready(oCadeteItem);
		this.getListaPedidos().add(oCadeteItem);
		pedido.setEstado(E_estado.ASIGNADO);
		final PedidoHistorial oPedidoHistorial = container
				.newTransientInstance(PedidoHistorial.class);
		
		oPedidoHistorial.setPedido(pedido);
		oPedidoHistorial.setObservacion("Asignado a Cadete: " + this.getNombre());
		oPedidoHistorial.setFechaHora(DateTime.now());
		oPedidoHistorial.setEstado(pedido.getEstado());
		container.persistIfNotAlready(oPedidoHistorial);
		return this;
	}

	@Programmatic
	private void ordenarItems(int orden) {
		if (!(this.getListaPedidos().isEmpty())) {
			List<CadeteItem> items = this.getListaPedidos();

			int j;
			int ordenItem = 0;

			for (j = 0; j < items.size(); j++) {
				ordenItem = items.get(j).getOrden();
				if (ordenItem >= orden)
					items.get(j).setOrden(ordenItem + 1);
			}

		}
	}

	@Programmatic
	public List<Pedido> choices0AsignarPedidoUrgente(final Pedido pedido) {
		return repositorioPedido
				.listAllByUrgency(E_urgencia_pedido.RESPUESTA_RAPIDA);
	}

	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.ENABLED)
	@CollectionLayout(render = RenderType.EAGERLY)
	public List<Pedido> getListaPedidosProgramables() {
		return repositorioPedido
				.listAllByUrgency(E_urgencia_pedido.PROGRAMABLE);

	}

	public void setListaPedidosProgramables(
			List<Pedido> listaPedidosProgramables) {
		this.listaPedidosProgramables = listaPedidosProgramables;
	}

	@MemberOrder(sequence = "1", name = "ListaPedidosProgramables")
	@ActionLayout(named = "Agregar", position = Position.PANEL)
	public Cadete asignarPedidoProgramable(Pedido pedido,
			@ParameterLayout(named = "Orden") int orden,
			@ParameterLayout(named = "Tiempo") int tiempo) {

		final CadeteItem oRutaItem = container
				.newTransientInstance(CadeteItem.class);

		oRutaItem.setEstado(false);
		this.ordenarItems(orden);
		oRutaItem.setOrden(orden);
		oRutaItem.setPedido(pedido);
		oRutaItem.setClavePedido(pedido.getClave());
		oRutaItem.setProveedor(pedido.getProveedor());
		//oRutaItem.setRuta(this);
		oRutaItem.setTiempo(tiempo);
		container.persistIfNotAlready(oRutaItem);
		this.getListaPedidos().add(oRutaItem);

		pedido.setEstado(E_estado.ASIGNADO);
		final PedidoHistorial oPedidoHistorial = container
				.newTransientInstance(PedidoHistorial.class);
		// PedidoHistorial oPedidoHistorial = new PedidoHistorial();
		oPedidoHistorial.setPedido(pedido);
		//oPedidoHistorial.setObservacion("Asignado a Ruta: " + this.getNumero());
		oPedidoHistorial.setFechaHora(DateTime.now());
		oPedidoHistorial.setEstado(pedido.getEstado());

		container.persistIfNotAlready(oPedidoHistorial);
		return this;
	}

	@Programmatic
	public List<Pedido> choices0AsignarPedidoProgramable(final Pedido pedido) {
		return repositorioPedido
				.listAllByUrgency(E_urgencia_pedido.PROGRAMABLE);
	}

	/*
	 * @ActionLayout(named = "Agregar Pedido") public Ruta addPedido( Pedido
	 * pedido ) { pedido.setEstado(E_estado.ASIGNADO);
	 * getListaPedidos().add(pedido); return this; }
	 */
	@ActionLayout(named = "Eliminar Ruta")
	public Cadete deleteRuta() {
		this.setActivo(false);
		return this;
	}

	@Programmatic
	public boolean hideDeleteRuta() {
		return isActivo() ? false : true;
		}

	@Property(hidden = Where.EVERYWHERE)
	@MemberOrder(sequence = "5")
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}


	@javax.inject.Inject
	RepositorioPedido repositorioPedido;
	
	@javax.inject.Inject
	RepositorioCadeteItem repositorioRutaItem;
	@javax.inject.Inject
	DomainObjectContainer container;
}
