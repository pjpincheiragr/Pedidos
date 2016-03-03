package domainapp.dom.app.ruta;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.query.QueryDefault;

import domainapp.dom.app.cadete.Cadete;
import domainapp.dom.app.ruta.Ruta;


@DomainService(repositoryFor = Ruta.class)
@DomainServiceLayout(menuOrder = "60", named = "Rutas")

public class RepositorioRuta {
	
	@MemberOrder(sequence = "1")
	@ActionLayout(named = "Crear nueva Ruta")
	
	public Ruta createRuta(
			@ParameterLayout(named = "Cadete") Cadete cadete
			) {
		final Ruta Ruta = container.newTransientInstance(Ruta.class);
		Ruta.setCadete(cadete);
		Ruta.setActivo(true);
		container.persistIfNotAlready(Ruta);
		return Ruta;
	}
	
	

	@MemberOrder(sequence = "2")
	@ActionLayout(named = "Listar Todos")
	public List<Ruta> listAll() {
		final List<Ruta> listaRutas = this.container
				.allMatches(new QueryDefault<Ruta>(Ruta.class,
						"ListarTodos"));
		if (listaRutas.isEmpty()) {
			this.container.warnUser("No hay rutas cargadas en el sistema");
		}
		return listaRutas;
	}
	
	@MemberOrder(sequence = "3")
	@ActionLayout(named = "Listar por Cadete")
	public List<Ruta> filterdByCadete(
			@ParameterLayout(named = "Cadete") Cadete cadete
			) {
		final List<Ruta> listaRutas = this.container
				.allMatches(new QueryDefault<Ruta>(Ruta.class,
						"ListarPorCaadete","cadete",cadete));
		if (listaRutas.isEmpty()) {
			this.container.warnUser("No hay rutas cargadas en el sistema");
		}
		return listaRutas;
	}
	
	
	@javax.inject.Inject
	DomainObjectContainer container;
}