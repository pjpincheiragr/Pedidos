package domainapp.dom.app.pedido;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;

@DomainService(repositoryFor = PedidoHistorial.class)
@DomainServiceLayout(menuOrder = "60", named = "PedidosHistorial")
public class RepositorioPedidoHistorial {


	
	/*@MemberOrder(sequence = "1")
	@ActionLayout(named = "VerHistorial")
	public List<PedidoHistorial> listAll() {
		final List<PedidoHistorial> listaPedidos = this.container
				.allMatches(new QueryDefault<PedidoHistorial>(PedidoHistorial.class,
						"VerHistorial","pedido",));
		if (listaPedidos.isEmpty()) {
			this.container.warnUser("No hay pedidos cargados en el sistema");
		}
		return listaPedidos;
	}*/
	
	@javax.inject.Inject
	DomainObjectContainer container;
}
