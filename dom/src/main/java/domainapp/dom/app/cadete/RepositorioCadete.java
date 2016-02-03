package domainapp.dom.app.cadete;

import java.util.ArrayList;
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
import domainapp.dom.app.cadete.Cadete;
import domainapp.dom.app.sucursal.Sucursal;

@DomainService(repositoryFor = Cadete.class)
@DomainServiceLayout(menuOrder = "60", named = "Cadetes")
public class RepositorioCadete {

	@MemberOrder(sequence = "1")
	@ActionLayout(named = "Crear nuevo cadete")
	public Cadete createcadete(
			@ParameterLayout(named = "nombre") @Parameter() String nombre,
			@ParameterLayout(named = "codigo") @Parameter() String codigo) {
		final Cadete cadete = container.newTransientInstance(Cadete.class);
		cadete.setNombre(nombre);
		cadete.setCodigo(codigo);
		container.persistIfNotAlready(cadete);
		return cadete;
	}

	@MemberOrder(sequence = "2")
	@ActionLayout(named = "Listar Todos")
	public List<Cadete> listAll() {
		final List<Cadete> listaCadetes = this.container
				.allMatches(new QueryDefault<Cadete>(Cadete.class,
						"ListarTodos"));
		if (listaCadetes.isEmpty()) {
			this.container.warnUser("No hay cadetes cargados en el sistema");
		}
		return listaCadetes;
	}

	/*
	 * public List<Sucursal> listAll() { final List<Sucursal> listaSucursal =
	 * this.container .allMatches(new QueryDefault<Sucursal>(Sucursal.class,
	 * "ListarTodos")); if (listaSucursal.isEmpty()) {
	 * this.container.warnUser("No hay areas cargadas en el sistema"); } return
	 * listaSucursal;
	 * 
	 * 
	 * @MemberOrder(sequence = "3")
	 * 
	 * @ActionLayout(named = "Buscar por nombre") public List<Sucursal>
	 * buscarPorApellidoNombre(
	 * 
	 * @ParameterLayout(named="Nombre")
	 * 
	 * @Parameter(optionality=Optionality.OPTIONAL) String nombre ) {
	 * 
	 * return container.allMatches( new QueryDefault<>( Sucursal.class,
	 * "findByName", "nombre", (nombre==null)?"":nombre)); } }
	 */

	@ActionLayout(named = "Buscar por Nombre")
	@MemberOrder(sequence = "4")
	public List<Cadete> findByName(
			@ParameterLayout(named = "Nombre") @Parameter(optionality = Optionality.OPTIONAL) String nombre) {
		return container.allMatches(
				new QueryDefault<>(
						Cadete.class,
						"findByName",
						"nombre", (nombre == null) ? "" : nombre));
	}

	@ActionLayout(named = "Buscar por Codigo")
	@MemberOrder(sequence = "5")
	public List<Cadete> findByCode(
			@ParameterLayout(named = "Codigo") @Parameter(optionality = Optionality.OPTIONAL) String codigo) {
		return container.allMatches(
				new QueryDefault<>(
						Cadete.class,
						"findByName",
						"nombre", (codigo == null) ? "" : codigo));
	}

	@javax.inject.Inject
	DomainObjectContainer container;
}