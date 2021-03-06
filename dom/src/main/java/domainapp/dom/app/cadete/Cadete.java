package domainapp.dom.app.cadete;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

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
import org.apache.isis.applib.query.QueryDefault;
import org.joda.time.DateTime;

import domainapp.dom.app.pedido.Pedido;
import domainapp.dom.app.pedido.PedidoHistorial;
import domainapp.dom.app.pedido.PedidoItem;
import domainapp.dom.app.pedido.RepositorioPedido;
import domainapp.dom.app.servicios.CustomComparator;
import domainapp.dom.app.servicios.E_estado;
import domainapp.dom.app.servicios.E_estado_item;
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
	private String recorrido;

	private List<CadeteItem> listaPedidos = new ArrayList<CadeteItem>();
	private List<Pedido> listaPedidosUrgentes = new ArrayList<Pedido>();
	private List<Pedido> listaPedidosProgramables = new ArrayList<Pedido>();

	private boolean activo;

	public String title() {
		return getNombre();
	}

	public Cadete(String nombre, String codigo, boolean activo) {
		super();
		this.nombre = nombre;
		this.codigo = codigo;
		this.activo = activo;
		this.recorrido = "->";
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

	// FALTA VALIDAR EL CODIGO!
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@MemberOrder(sequence = "2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public String getRecorrido() {
		return this.recorrido;
	}

	public void setRecorrido(String recorrido) {
		this.recorrido = recorrido;
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
	public Cadete quitarPedido(CadeteItem oCadeteItem) {
		final Pedido pedido = oCadeteItem.getPedido();
		this.getListaPedidos().remove(oCadeteItem);
		actualizarRecorrido();
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
	public List<CadeteItem> choices0QuitarPedido(final CadeteItem oCadeteItem) {
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
	public Cadete asignarPedidoUrgente(
			Pedido pedido,
			@ParameterLayout(named = "Orden") @Parameter(optionality = Optionality.OPTIONAL) int orden,
			@ParameterLayout(named = "Tiempo") @Parameter(optionality = Optionality.OPTIONAL) int tiempo) {
		this.agregarPedidoGenerico(pedido, orden, tiempo);
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

		this.agregarPedidoGenerico(pedido, orden, tiempo);

		return this;
	}

	@Programmatic
	public List<Pedido> choices0AsignarPedidoProgramable(final Pedido pedido) {
		return repositorioPedido
				.listAllByUrgency(E_urgencia_pedido.PROGRAMABLE);
	}

	@Programmatic
	public void agregarPedidoGenerico(Pedido pedido, int orden, int tiempo) {

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
		agregarCadeteItem(oCadeteItem);
		actualizarRecorrido();
		pedido.setEstado(E_estado.ASIGNADO);
		final PedidoHistorial oPedidoHistorial = container
				.newTransientInstance(PedidoHistorial.class);
		oPedidoHistorial.setPedido(pedido);
		oPedidoHistorial.setObservacion("Asignado a Cadete: "
				+ this.getNombre());
		oPedidoHistorial.setFechaHora(DateTime.now());
		oPedidoHistorial.setEstado(pedido.getEstado());
		container.persistIfNotAlready(oPedidoHistorial);

	}

	private void agregarCadeteItem(CadeteItem oCadeteItem) {

		for (int i = 0; i < this.listaPedidos.size(); i++) {
			if (this.listaPedidos.get(i).getProveedor().getNombre()
					.equalsIgnoreCase(oCadeteItem.getProveedor().getNombre())) {
				oCadeteItem.setOrden(this.listaPedidos.get(i).getOrden());
			}
		}
		this.listaPedidos.add(oCadeteItem);
		Collections.sort(this.listaPedidos, new CustomComparator());
		actualizarRecorrido();
	}

	@Programmatic
	public void actualizarRecorrido() {
		this.setRecorrido("->");
		if (!(this.listaPedidos == null) && !this.listaPedidos.isEmpty()) {
			for (int i = 0; i < this.listaPedidos.size(); i++) {

				if (!(this.recorrido.contains(this.listaPedidos.get(i)
						.getProveedor().getNombre()))) {
					this.setRecorrido(this.recorrido
							+ this.listaPedidos.get(i).getProveedor()
									.getNombre() + " / ");
				}
			}
		}
	}

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
	RepositorioCadeteItem repositorioCadeteItem;

	@javax.inject.Inject
	DomainObjectContainer container;
}
