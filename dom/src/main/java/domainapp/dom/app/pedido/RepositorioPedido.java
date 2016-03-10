package domainapp.dom.app.pedido;

import java.util.Calendar;
import java.text.DateFormat;
import java.util.List;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.query.QueryDefault;
import domainapp.dom.app.pedido.Pedido;
import domainapp.dom.app.proveedor.Proveedor;
import domainapp.dom.app.servicios.E_estado;
import domainapp.dom.app.servicios.E_urgencia_pedido;
import domainapp.dom.app.sucursal.Sucursal;
import domainapp.dom.app.tipo.Tipo;
import domainapp.dom.app.vendedor.RepositorioVendedor;
import domainapp.dom.app.vendedor.Vendedor;

@DomainService(repositoryFor = Pedido.class)
@DomainServiceLayout(menuOrder = "60", named = "Pedidos")
public class RepositorioPedido {

	@MemberOrder(sequence = "1")
	@ActionLayout(named = "Crear nuevo Pedido")
	public Pedido createPedido(
			@ParameterLayout(named = "Tipo") @Parameter(optionality = Optionality.OPTIONAL) Tipo tipo,
			@ParameterLayout(named = "Numero de Venta") @Parameter(optionality = Optionality.OPTIONAL) String numeroVenta,
			@ParameterLayout(named = "Urgencia") @Parameter(optionality = Optionality.OPTIONAL) E_urgencia_pedido urgencia,
			@ParameterLayout(named = "Proveedor") @Parameter(optionality = Optionality.OPTIONAL) Proveedor proveedor,
			@ParameterLayout(named = "Vendedor") @Parameter(optionality = Optionality.OPTIONAL) Vendedor vendedor,
			@ParameterLayout(named = "Valor") @Parameter(optionality = Optionality.OPTIONAL) float valor, 
			@ParameterLayout(named = "Sucursal") Sucursal sucursal,
			@ParameterLayout(named = "Observaciones", multiLine = 15) String observacion){
		
		final Pedido Pedido = container.newTransientInstance(Pedido.class);
		Pedido.setTipo(tipo);
		Pedido.setNumeroVenta(numeroVenta);
		Pedido.setUrgencia(urgencia);
		Pedido.setProveedor(proveedor);
		Pedido.setVendedor(vendedor);
		Pedido.setValor(valor);
		Pedido.setEstado(E_estado.NUEVO);
		Pedido.setSucursal(sucursal);
		Pedido.setFechaHora(Calendar.getInstance());
		Pedido.setObservacion(observacion);
		Pedido.setActivo(true);
		Pedido.setTiempoEstimado("-");
		container.persistIfNotAlready(Pedido);
		return Pedido;

	}

	@MemberOrder(sequence = "2")
	@ActionLayout(named = "Crear nuevo Pedido")
	public Pedido createPedidoVendedores(
			
			@ParameterLayout(named = "Tipo") @Parameter(optionality = Optionality.OPTIONAL) Tipo tipo,
			@ParameterLayout(named = "Numero de Venta") @Parameter(optionality = Optionality.OPTIONAL) String numeroVenta,
			@ParameterLayout(named = "Urgencia") @Parameter(optionality = Optionality.OPTIONAL) E_urgencia_pedido urgencia,
			@ParameterLayout(named = "Proveedor") @Parameter(optionality = Optionality.OPTIONAL) Proveedor proveedor,
			@ParameterLayout(named = "Valor") @Parameter(optionality = Optionality.OPTIONAL) float valor, 
			@ParameterLayout(named = "Sucursal")  @Parameter(optionality = Optionality.OPTIONAL) Sucursal sucursal,
			@ParameterLayout(named = "Observaciones", multiLine = 15)  @Parameter(optionality = Optionality.OPTIONAL) String observacion
			) {
		final Pedido Pedido = container.newTransientInstance(Pedido.class);
		Pedido.setTipo(tipo);
		Pedido.setNumeroVenta(numeroVenta);
		Pedido.setUrgencia(urgencia);
		Pedido.setProveedor(proveedor);
		Vendedor oVendedor=new Vendedor();
		oVendedor= repositorioVendedor.findByUserCode( container.getUser().getName());
		Pedido.setVendedor(oVendedor);
		Pedido.setValor(valor);
		Pedido.setEstado(E_estado.NUEVO);
		Pedido.setSucursal(sucursal);
		Pedido.setFechaHora(Calendar.getInstance());
		Pedido.setObservacion(observacion);
		Pedido.setActivo(true);
		Pedido.setTiempoEstimado("-");
		container.persistIfNotAlready(Pedido);
		return Pedido;
	
	}
	
	@MemberOrder(sequence = "1")
	@ActionLayout(named = "Listar Todos")
	public List<Pedido> listAll() {
		final List<Pedido> listaPedidos = this.container
				.allMatches(new QueryDefault<Pedido>(Pedido.class,
						"ListarTodos"));
		if (listaPedidos.isEmpty()) {
			this.container.warnUser("No hay pedidos cargados en el sistema");
		}
		return listaPedidos;
	}
	
	/*
	 * Aqui comienza el método que permite a un vendedor visualizar sus pedidos
	 * */
	@MemberOrder(sequence = "2")
	@ActionLayout(named = "Listar Pedidos")
	
	public List<Pedido> listAllByVendor() {
		final List<Pedido> listaPedidos = this.container
				.allMatches(new QueryDefault<Pedido>(Pedido.class,
						"ListarTodosPorVendedor", "vendedor", repositorioVendedor.findByUserCode( container.getUser().getName()) ));
		if (listaPedidos.isEmpty()) {
			this.container.warnUser("No hay pedidos cargados en el sistema");
		}
		return listaPedidos;
	}

	//termina listar pedidos de VENDEDOR
	
	@MemberOrder(sequence = "3")
	@ActionLayout(named = "Listar Nuevos")
	public List<Pedido> listNew() {
		final List<Pedido> listaPedidos = this.container
				.allMatches(new QueryDefault<Pedido>(Pedido.class,
						"ListarNuevos", "estado",E_estado.NUEVO));
		if (listaPedidos.isEmpty()) {
			this.container.warnUser("No hay pedidos cargados en el sistema");
		}
		return listaPedidos;
	}

	@ActionLayout(named = "Buscar por Estado")
	@MemberOrder(sequence = "5")
	public List<Pedido> findByState(
			@ParameterLayout(named = "Estado") E_estado estado) {
		return container.allMatches(new QueryDefault<Pedido>(Pedido.class,
				"findByState", "estado", estado));
	}

	@ActionLayout(named = "Buscar por Vendedor")
	@MemberOrder(sequence = "6")
	public List<Pedido> findBySeller(
			@ParameterLayout(named = "Nombre") Vendedor vendedor) {
		return container.allMatches(new QueryDefault<Pedido>(Pedido.class,
				"findBySeller", "vendedor", vendedor));
	}

	// Obtener listado por Urgencia

	@MemberOrder(sequence = "8")
	@ActionLayout(named = "Listar Pedidos Por Urgencia")
	public List<Pedido> listAllByUrgency(
			@ParameterLayout(named = "Urgencia") @Parameter(optionality = Optionality.OPTIONAL) E_urgencia_pedido urgencia) {

		final List<Pedido> listaPedidos = this.container
				.allMatches(new QueryDefault<Pedido>(Pedido.class,
						"ListarTodosPorUrgencia", "urgencia", urgencia,"estado",E_estado.NUEVO));
		if (listaPedidos.isEmpty()) {
			this.container.warnUser("No hay rutas cargadas en el sistema");
		}
		return listaPedidos;
	}


	
	@javax.inject.Inject
	RepositorioVendedor repositorioVendedor;
	
	
	@javax.inject.Inject
	DomainObjectContainer container;

}