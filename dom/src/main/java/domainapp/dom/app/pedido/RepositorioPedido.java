package domainapp.dom.app.pedido;

import org.joda.time.LocalDate;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import domainapp.dom.app.cadete.Cadete;
import domainapp.dom.app.pedido.Pedido;
import domainapp.dom.app.proveedor.Proveedor;
import domainapp.dom.app.ruta.RutaItem;
import domainapp.dom.app.servicios.E_estado;
import domainapp.dom.app.servicios.E_urgencia_pedido;
import domainapp.dom.app.sucursal.Sucursal;
import domainapp.dom.app.tipo.Tipo;
import domainapp.dom.app.vendedor.Vendedor;
import domainapp.dom.modules.security.Services;

@DomainService(repositoryFor = Pedido.class)
@DomainServiceLayout(menuOrder = "60", named = "Pedidos")
public class RepositorioPedido {

	@MemberOrder(sequence = "1")
	@ActionLayout(named = "Crear nuevo Pedido")
	public Pedido createPedido(
			@ParameterLayout(named = "Tipo") @Parameter(optionality = Optionality.OPTIONAL) Tipo tipo,
			@ParameterLayout(named = "Urgencia") @Parameter(optionality = Optionality.OPTIONAL) E_urgencia_pedido urgencia,
			@ParameterLayout(named = "Proveedor") @Parameter(optionality = Optionality.OPTIONAL) Proveedor proveedor,
			@ParameterLayout(named = "Vendedor") @Parameter(optionality = Optionality.OPTIONAL) Vendedor vendedor,
			//@ParameterLayout(named = "Tiempo") @Parameter(optionality = Optionality.OPTIONAL) int tiempo,
			@ParameterLayout(named = "Valor") @Parameter(optionality = Optionality.OPTIONAL) float valor,
			@ParameterLayout(named = "Estado") E_estado estado, @ParameterLayout(named = "Sucursal") Sucursal sucursal,
			@ParameterLayout(named = "Observaciones", multiLine = 15) String observacion) {

		final Pedido Pedido = container.newTransientInstance(Pedido.class);
		Pedido.setTipo(tipo);
		Pedido.setUrgencia(urgencia);
		Pedido.setProveedor(proveedor);
		if (new Services().isVendedor(container.getUser())) {

		}

		Pedido.setVendedor(vendedor);
		//Pedido.setTiempo(tiempo);
		Pedido.setValor(valor);
		Pedido.setEstado(estado);
		Pedido.setSucursal(sucursal);
		Pedido.setFechaHora(LocalDate.now());
		Pedido.setObservacion(observacion);
		Pedido.setActivo(true);

		container.persistIfNotAlready(Pedido);
		RutaItem oRutaItem = new RutaItem();
		oRutaItem.setEstado(false);
		oRutaItem.setOrden(0);
		oRutaItem.setPedido(Pedido);
		container.persistIfNotAlready(oRutaItem);
		return Pedido;

	}

	@MemberOrder(sequence = "1")
	@ActionLayout(named = "Listar Todos")
	public List<Pedido> listAll() {
		final List<Pedido> listaPedidos = this.container
				.allMatches(new QueryDefault<Pedido>(Pedido.class, "ListarTodos"));
		if (listaPedidos.isEmpty()) {
			this.container.warnUser("No hay pedidos cargados en el sistema");
		}
		return listaPedidos;
	}

	@MemberOrder(sequence = "2")
	@ActionLayout(named = "Listar Pendientes")
	public List<Pedido> listPending() {
		final List<Pedido> listaPedidos = this.container
				.allMatches(new QueryDefault<Pedido>(Pedido.class, "ListarPendientes"));
		if (listaPedidos.isEmpty()) {
			this.container.warnUser("No hay pedidos cargados en el sistema");
		}
		return listaPedidos;
	}

	@MemberOrder(sequence = "3")
	@ActionLayout(named = "Listar Nuevos")
	public List<Pedido> listNew() {
		final List<Pedido> listaPedidos = this.container
				.allMatches(new QueryDefault<Pedido>(Pedido.class, "ListarNuevos"));
		if (listaPedidos.isEmpty()) {
			this.container.warnUser("No hay pedidos cargados en el sistema");
		}
		return listaPedidos;
	}

	@ActionLayout(named = "Buscar por Descripcion")
	@MemberOrder(sequence = "4")
	public List<Pedido> findByName(
			@ParameterLayout(named = "Descripcion") @Parameter(optionality = Optionality.OPTIONAL) String descripcion) {
		return container.allMatches(new QueryDefault<>(Pedido.class, "findByDescription", "descripcion",
				(descripcion == null) ? "" : descripcion));
	}

	@ActionLayout(named = "Buscar por Estado")
	@MemberOrder(sequence = "5")
	public List<Pedido> findByState(@ParameterLayout(named = "Estado") E_estado estado) {
		return container.allMatches(new QueryDefault<>(Pedido.class, "findByState", "estado", estado));
	}

	@ActionLayout(named = "Buscar por Vendedor")
	@MemberOrder(sequence = "6")
	public List<Pedido> findBySeller(@ParameterLayout(named = "Nombre") Vendedor vendedor) {
		return container.allMatches(new QueryDefault<>(Pedido.class, "findBySeller", "vendedor", vendedor));
	}

	@ActionLayout(named = "Buscar por Cadete")
	@MemberOrder(sequence = "7")
	public List<Pedido> findByCadete(@ParameterLayout(named = "Nombre") Cadete cadete) {
		return container.allMatches(new QueryDefault<>(Pedido.class, "findByCadete", "cadete", cadete));
	}

	// Obtener listado por Urgencia

	@MemberOrder(sequence = "8")
	@ActionLayout(named = "Listar Pedidos Por Urgencia")
	public List<Pedido> listAllByUrgency(
			@ParameterLayout(named = "Urgencia") @Parameter(optionality = Optionality.OPTIONAL) E_urgencia_pedido urgencia) {

		final List<Pedido> listaPedidos = this.container
				.allMatches(new QueryDefault<Pedido>(Pedido.class, "ListarTodosPorUrgencia", "urgencia", urgencia));
		if (listaPedidos.isEmpty()) {
			this.container.warnUser("No hay rutas cargadas en el sistema");
		}
		return listaPedidos;
	}

	@javax.inject.Inject
	DomainObjectContainer container;

}