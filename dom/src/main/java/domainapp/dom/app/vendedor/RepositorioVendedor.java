package domainapp.dom.app.vendedor;




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

import domainapp.dom.app.vendedor.Vendedor;



@DomainService(repositoryFor = Vendedor.class)
@DomainServiceLayout(menuOrder = "60", named="Vendedores")
public class RepositorioVendedor {
	@MemberOrder(sequence = "1")
	@ActionLayout(named="Crear nuevo Vendedor")
	public Vendedor createProveedor(
			@ParameterLayout(named="nombre")@Parameter(optionality=Optionality.OPTIONAL)String nombre,
			@ParameterLayout(named="codigo")@Parameter(optionality=Optionality.OPTIONAL)String codigo
			//@ParameterLayout(named="sucursal")@Parameter(optionality=Optionality.OPTIONAL)String sucursal
			){
		final Vendedor vendedor = container.newTransientInstance(Vendedor.class);
		vendedor.setNombre(nombre);
		vendedor.setCodigo(codigo);
		//vendedor.setSucursal(sucursal);
		container.persistIfNotAlready(vendedor);
		return vendedor;
	}

	@MemberOrder(sequence = "2")
	@ActionLayout(named = "Listar Todos")
	public List<Vendedor> listAll() {
		final List<Vendedor> listaVendedores = this.container
				.allMatches(new QueryDefault<Vendedor>(Vendedor.class,
						"ListarTodos"));
		if (listaVendedores.isEmpty()) {
			this.container.warnUser("No hay vendedores cargados en el sistema");
		}
		return listaVendedores;
	}
	
	@ActionLayout(named = "Buscar por Nombre")
	@MemberOrder(sequence = "4")
	public List<Vendedor> findByName(
			@ParameterLayout(named = "Nombre") @Parameter(optionality = Optionality.OPTIONAL) String nombre) {
		return container.allMatches(
				new QueryDefault<>(
						Vendedor.class,
						"findByName",
						"nombre", (nombre == null) ? "" : nombre));
	}

	@ActionLayout(named = "Buscar por Codigo")
	@MemberOrder(sequence = "5")
	public List<Vendedor> findByCode(
			@ParameterLayout(named = "Codigo") @Parameter(optionality = Optionality.OPTIONAL) String codigo) {
		return container.allMatches(
				new QueryDefault<>(
						Vendedor.class,
						"findByName",
						"nombre", (codigo == null) ? "" : codigo));
	}
	@javax.inject.Inject
    DomainObjectContainer container;
}