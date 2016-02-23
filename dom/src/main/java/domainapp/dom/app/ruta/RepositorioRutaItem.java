package domainapp.dom.app.ruta;

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
import domainapp.dom.app.ruta.RutaItem;
import domainapp.dom.app.servicios.E_urgencia_pedido;


@DomainService(repositoryFor = RutaItem.class)
@DomainServiceLayout(menuOrder = "60", named = "Rutas")

public class RepositorioRutaItem {
	
	@MemberOrder(sequence = "1")
	@ActionLayout(named = "Crear nueva Ruta Item")
	
	public RutaItem createRutaItem(
			@ParameterLayout(named = "Estado") boolean estado,
			@ParameterLayout(named = "Orden") int orden,
			@ParameterLayout(named = "Pedido") Pedido pedido,
			@ParameterLayout(named = "Estado") Ruta ruta
			) {
		final RutaItem RutaItem = container.newTransientInstance(RutaItem.class);
		RutaItem.setEstado(false);
		RutaItem.setOrden(0);
		RutaItem.setPedido(null);
		RutaItem.setRuta(null);
		container.persistIfNotAlready(RutaItem);
		return RutaItem;
	}
	
	

	@MemberOrder(sequence = "2")
	@ActionLayout(named = "Listar Todos")
	public List<RutaItem> listAllByUrgency(
			@ParameterLayout(named = "Urgencia") @Parameter(optionality = Optionality.OPTIONAL)  E_urgencia_pedido urgencia)  {
		
		
		final List<RutaItem> listaRutas = this.container
				.allMatches(new QueryDefault<RutaItem>(RutaItem.class,
						"ListarTodosPorUrgencia",urgencia));
		if (listaRutas.isEmpty()) {
			this.container.warnUser("No hay rutas cargadas en el sistema");
		}
		return listaRutas;
	}
	
	@javax.inject.Inject
	DomainObjectContainer container;
}
