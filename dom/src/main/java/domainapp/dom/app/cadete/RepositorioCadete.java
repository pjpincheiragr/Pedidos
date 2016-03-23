package domainapp.dom.app.cadete;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.query.QueryDefault;
import domainapp.dom.app.cadete.Cadete;
@DomainService(repositoryFor = Cadete.class)
@DomainServiceLayout(menuOrder = "60", named = "Administración")
public class RepositorioCadete {

	@MemberOrder(sequence = "1")
	@ActionLayout(named = "Crear Nuevo Cadete")
	public Cadete createcadete(
			@ParameterLayout(named = "nombre") @Parameter() String nombre,
			@ParameterLayout(named = "codigo") @Parameter() String codigo) {
		if(this.validateCreateCadete(nombre,codigo)==null){
		final Cadete cadete = container.newTransientInstance(Cadete.class);
		cadete.setCodigo(codigo);
		cadete.setNombre(nombre);
		container.persistIfNotAlready(cadete);
		return cadete;
		}else{
			List <Cadete> cadetes=container.allMatches(
					new QueryDefault<Cadete>(Cadete.class, "findByCode",
							"codigo", codigo));
			return cadetes.get(0);
		}
		}
	
	@Programmatic
	public String validateCreateCadete(String nombre, String codigo) {
		if (!container.allMatches(
				new QueryDefault<Cadete>(Cadete.class, "findByCode",
						"codigo", codigo)).isEmpty()) {
			container
			.informUser( "El codigo ya se asocia a un cadete.");
			return "error";
		}
		return null;
	}

	@MemberOrder(sequence = "2")
	@ActionLayout(named = "Listar Todos los Cadetes")
	public List<Cadete> listAll() {
		final List<Cadete> listaCadetes = this.container
				.allMatches(new QueryDefault<Cadete>(Cadete.class,
						"ListarTodos"));
		if (listaCadetes.isEmpty()) {
			this.container.warnUser("No hay cadetes cargados en el sistema");
		}
		return listaCadetes;
	}


	@ActionLayout(named = "Buscar Cadete por Nombre")
	@MemberOrder(sequence = "4")
	public List<Cadete> findByName(
			@ParameterLayout(named = "Nombre") @Parameter(optionality = Optionality.OPTIONAL) String nombre) {
		return container.allMatches(
				new QueryDefault<>(
						Cadete.class,
						"findByName",
						"nombre", (nombre == null) ? "" : nombre));
	}
	

	@ActionLayout(named = "Buscar Cadete por Codigo")
	@MemberOrder(sequence = "5")
	public List<Cadete> findByCode(
			@ParameterLayout(named = "Codigo") @Parameter(optionality = Optionality.OPTIONAL) String codigo) {
		return container.allMatches(
				new QueryDefault<>(
						Cadete.class,
						"findByCode",
						"codigo", (codigo == null) ? "" : codigo));
	}
	

	@javax.inject.Inject
	DomainObjectContainer container;
}