package domainapp.dom.app.cadete;

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
import domainapp.dom.app.servicios.E_urgencia_pedido;


@DomainService(repositoryFor = CadeteItem.class)
@DomainServiceLayout(menuOrder = "60", named = "Cadetes")

public class RepositorioCadeteItem {
	/*
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
	
	*/

	@Programmatic 
	@MemberOrder(sequence = "2")
	@ActionLayout(named = "Listar Todos")
	public List<CadeteItem> listAllByUrgency(
			@ParameterLayout(named = "Urgencia") @Parameter(optionality = Optionality.OPTIONAL)  E_urgencia_pedido urgencia)  {	
		final List<CadeteItem> listaRutas = this.container
				.allMatches(new QueryDefault<CadeteItem>(CadeteItem.class,
						"ListarTodosPorUrgencia","urgencia",urgencia));
		/*if (listaRutas.isEmpty()) {
			this.container.warnUser("No hay rutas cargadas en el sistema");
		}*/
		return listaRutas;
	}

	@ActionLayout(named = "Update Estado")
	public CadeteItem updatePedido(
			@ParameterLayout(named = "Clave Pedido")long clavePedido
			) {
		final List<CadeteItem> listaPedidos = this.container.allMatches(new QueryDefault<CadeteItem>(CadeteItem.class,
						"BuscarPorClave","clavePedido",clavePedido));
		if (!listaPedidos.isEmpty()) {
			listaPedidos.get(0).setEstado(true);
		}
	
		 container.persistIfNotAlready(listaPedidos.get(0));
		 return listaPedidos.get(0);
	}
	
	@javax.inject.Inject
	DomainObjectContainer container;
}
