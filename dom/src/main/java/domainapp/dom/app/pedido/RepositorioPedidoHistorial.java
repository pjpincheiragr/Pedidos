package domainapp.dom.app.pedido;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(repositoryFor = PedidoHistorial.class)
@DomainServiceLayout(menuOrder = "60", named = "PedidosHistorial")
public class RepositorioPedidoHistorial {
/*
 * Se propone que el vendedor visualize si es necesario el historial de SUS pedidos en estado ACTIVO
 * */
	
	@MemberOrder(sequence = "1")
	@ActionLayout(named = "Listar Todos")
	public List<PedidoHistorial> listAll() {
		final List<PedidoHistorial> listaPedidos = this.container
				.allMatches(new QueryDefault<PedidoHistorial>(PedidoHistorial.class,
						"ListarTodos"));
		if (listaPedidos.isEmpty()) {
			this.container.warnUser("No hay pedidos cargados en el sistema");
		}
		return listaPedidos;
	}
	
	@javax.inject.Inject
	DomainObjectContainer container;
}
