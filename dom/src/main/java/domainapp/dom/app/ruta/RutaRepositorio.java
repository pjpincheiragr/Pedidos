package domainapp.dom.app.ruta;

import java.sql.Timestamp;
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

import domainapp.dom.app.cadete.Cadete;
import domainapp.dom.app.ruta.Ruta;
import domainapp.dom.app.proveedor.Proveedor;
import domainapp.dom.app.servicios.E_estado;
import domainapp.dom.app.sucursal.Sucursal;
import domainapp.dom.app.vendedor.Vendedor;

@DomainService(repositoryFor = Ruta.class)
@DomainServiceLayout(menuOrder = "60", named = "Rutas")
public class RutaRepositorio {
	@MemberOrder(sequence = "1")
	@ActionLayout(named = "Crear nuevo Ruta")
	public Ruta createRuta(
			@ParameterLayout(named = "Cadete") Cadete cadete
	) {
		final Ruta Ruta = container.newTransientInstance(Ruta.class);
		Ruta.setCadete(cadete);
	
		container.persistIfNotAlready(Ruta);
		return Ruta;
	}

	@MemberOrder(sequence = "1")
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

	@javax.inject.Inject
	DomainObjectContainer container;
}