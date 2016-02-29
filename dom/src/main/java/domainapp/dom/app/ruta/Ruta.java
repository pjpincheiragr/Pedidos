package domainapp.dom.app.ruta;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.ActionLayout.Position;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Where;
import org.joda.time.LocalDate;

import domainapp.dom.app.cadete.Cadete;
import domainapp.dom.app.pedido.Pedido;
import domainapp.dom.app.pedido.PedidoHistorial;
import domainapp.dom.app.pedido.PedidoItem;
import domainapp.dom.app.pedido.RepositorioPedido;
import domainapp.dom.app.servicios.E_estado;
import domainapp.dom.app.servicios.E_urgencia_pedido;

@MemberGroupLayout(columnSpans = { 4, 0, 0, 8 }, left = "Detalles del Horario")
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Uniques({ @javax.jdo.annotations.Unique(name = "OrdenServicio_numero_must_be_unique", members = { "numero" }) })
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.APPLICATION)
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Queries({ @javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
		+ "FROM domainapp.dom.app.ruta.Ruta " + "WHERE activo == true") })
@DomainObject(objectType = "ruta", bounded = true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class Ruta {

	private Cadete cadete;
	private List<RutaItem> listaPedidos = new ArrayList<RutaItem>();
	private List<Pedido> listaPedidosUrgentes = new ArrayList<Pedido>();
	private List<Pedido> listaPedidosProgramables = new ArrayList<Pedido>();
	@PrimaryKey
	private long numero;
	private boolean activo;

	public String title() {
		return "Ruta: " + getNumero();
	}

	public Ruta(Cadete cadete, boolean activo) {
		super();
		this.cadete = cadete;
		this.activo = activo;
	}

	public Ruta() {
		super();
	}

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

	@MemberOrder(sequence = "2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public Cadete getCadete() {
		return cadete;
	}

	public void setCadete(Cadete cadete) {
		this.cadete = cadete;
	}

	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.ENABLED)
	@CollectionLayout(render = RenderType.EAGERLY)
	public List<RutaItem> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<RutaItem> listaPedidos) {
		this.listaPedidos = listaPedidos;
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
	public Ruta asignarPedidoUrgente(Pedido pedido) {
		final RutaItem oRutaItem = container
				.newTransientInstance(RutaItem.class);

		oRutaItem.setEstado(false);
		oRutaItem.setOrden(0);
		oRutaItem.setPedido(pedido);
		oRutaItem.setRuta(this);
		oRutaItem.setTiempo(0);
		container.persistIfNotAlready(oRutaItem);
		this.getListaPedidos().add(oRutaItem);

		pedido.setEstado(E_estado.ASIGNADO);
		final PedidoHistorial oPedidoHistorial = container
				.newTransientInstance(PedidoHistorial.class);
		//PedidoHistorial oPedidoHistorial = new PedidoHistorial();
		oPedidoHistorial.setPedido(pedido);
		oPedidoHistorial.setObservacion("Asignación automática");
		oPedidoHistorial.setFechaHora(LocalDate.now());
		oPedidoHistorial.setEstado(pedido.getEstado());

		container.persistIfNotAlready(oPedidoHistorial);
		return this;
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
	public Ruta asignarPedidoProgramable(Pedido pedido, 
			@ParameterLayout(named = "Orden") int orden, 
			@ParameterLayout(named = "Tiempo") int tiempo) {
		
		final RutaItem oRutaItem = container
				.newTransientInstance(RutaItem.class);

		oRutaItem.setEstado(false);
		oRutaItem.setOrden(orden);
		oRutaItem.setPedido(pedido);
		oRutaItem.setRuta(this);
		oRutaItem.setTiempo(tiempo);
		container.persistIfNotAlready(oRutaItem);
		this.getListaPedidos().add(oRutaItem);

		pedido.setEstado(E_estado.ASIGNADO);
		final PedidoHistorial oPedidoHistorial = container
				.newTransientInstance(PedidoHistorial.class);
				//PedidoHistorial oPedidoHistorial = new PedidoHistorial();
				oPedidoHistorial.setPedido(pedido);
				oPedidoHistorial.setObservacion("Asignación automática");
				oPedidoHistorial.setFechaHora(LocalDate.now());
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
	public Ruta deleteRuta() {
		this.setActivo(false);
		return this;
	}

	@Programmatic
	public boolean hideDeleteRuta() {
		if (!isActivo())
			return true;
		else
			return false;
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
	DomainObjectContainer container;

	@javax.inject.Inject
	RepositorioRutaItem repositorioRutaItem;

	@javax.inject.Inject
	RepositorioPedido repositorioPedido;
}