package domainapp.dom.app.pedido;


import java.sql.Blob;
import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.query.QueryDefault;

import domainapp.dom.app.marca.Marca;
import domainapp.dom.app.servicios.E_estado_item;
import domainapp.dom.app.pedido.PedidoItem;

@DomainService(repositoryFor = PedidoItem.class)
@DomainServiceLayout(menuOrder = "60", named="PedidosItem")
public class RepositorioPedidoItem {
	/*
	@MemberOrder(sequence = "1")
	@ActionLayout(named="Crear nuevo Pedido Item")

	public PedidoItem createPedidoItem(
			@ParameterLayout(named="Codigo")@Parameter(optionality=Optionality.OPTIONAL)String codigo,
			@ParameterLayout(named="Marca")@Parameter(optionality=Optionality.OPTIONAL)Marca marca,
			@ParameterLayout(named="Cantidad")@Parameter(optionality=Optionality.OPTIONAL)int cantidad,
			final @ParameterLayout(named="Imagen") @Parameter(optionality=Optionality.OPTIONAL) Blob attachment,
			@ParameterLayout(named="Estado")@Parameter(optionality=Optionality.OPTIONAL)E_estado_item estado
			){
		final PedidoItem PedidoItem = container.newTransientInstance(PedidoItem.class);
		PedidoItem.setCodigo(codigo);
		PedidoItem.setMarca(marca);
		PedidoItem.setCantidad(cantidad);
		PedidoItem.setEstado(estado);
		container.persistIfNotAlready(PedidoItem);
		return PedidoItem;
	}


	 * Método comentado ya que PedidoItem no debe aparecer en el menú.
	 * 
	 * @MemberOrder(sequence = "1")
	@ActionLayout(named = "Listar Todos")
	public List<PedidoItem> listAll() {
		final List<PedidoItem> listaPedidos = this.container
				.allMatches(new QueryDefault<PedidoItem>(PedidoItem.class,
						"ListarTodos"));
		if (listaPedidos.isEmpty()) {
			this.container.warnUser("No hay pedidos cargados en el sistema");
		}
		return listaPedidos;
	}
	*/
	@javax.inject.Inject
    DomainObjectContainer container;

}
