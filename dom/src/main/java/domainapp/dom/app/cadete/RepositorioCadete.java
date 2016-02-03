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



@DomainService(repositoryFor = Cadete.class)
@DomainServiceLayout(menuOrder = "60", named="Cadetes")
public class RepositorioCadete {
	@MemberOrder(sequence = "1")
	@ActionLayout(named="Crear nuevo cadete")
	public Cadete createcadete(
			@ParameterLayout(named="nombre")@Parameter(optionality=Optionality.OPTIONAL)String nombre,
			@ParameterLayout(named="codigo")@Parameter(optionality=Optionality.OPTIONAL)String codigo
			){
		final Cadete cadete = container.newTransientInstance(Cadete.class);
		cadete.setNombre(nombre);
		cadete.setCodigo(codigo);
		container.persistIfNotAlready(cadete);
		return cadete;
	}

	@MemberOrder(sequence ="2")
	@ActionLayout(named="Listar Todos")
	public List<Cadete> listAll(){
		return container.allMatches(new QueryDefault<Cadete>(Cadete.class, "ListarTodos"));
	}
	
	@ActionLayout(named="Buscar por Nombre")
	@MemberOrder(sequence="4")
	public List<Cadete> findByNombre(
			@ParameterLayout(named="Nombre") @Parameter(regexPattern=domainapp.dom.regex.validador.Validador.ValidacionAlfanumerico.ADMITIDOS) String nombre){
		final List<Cadete> listaCadete = listAll();
		final List<Cadete> lista = new ArrayList<Cadete>();
		for (Cadete a : listaCadete) {
			if (a.getNombre().toUpperCase().equals(nombre.toUpperCase())) {
				lista.add(a);
			}
		}

		if (lista.isEmpty()) {
			this.container.warnUser("No existe el Nombre buscado");
		}
		return lista;
	}

	@ActionLayout(named="Buscar por Codigo")
	@MemberOrder(sequence="5")
	public List<Cadete> findByCodigo(
			@ParameterLayout(named="Codigo") @Parameter
			(regexPattern = domainapp.dom.regex.validador.Validador.ValidacionAlfanumerico.ADMITIDOS) String codigo){
		final List<Cadete> listaCadete = listAll();
		final List<Cadete> lista = new ArrayList<Cadete>();
		for (Cadete a : listaCadete) {
			if (a.getCodigo().toUpperCase().equals(codigo.toUpperCase())) {
				lista.add(a);
			}
		}

		if (lista.isEmpty()) {
			this.container.warnUser("No existe el codigo buscado");
		}
		return lista;
	}
	
	@javax.inject.Inject
    DomainObjectContainer container;
}