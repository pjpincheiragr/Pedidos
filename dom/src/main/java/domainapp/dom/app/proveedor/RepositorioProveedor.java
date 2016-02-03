package domainapp.dom.app.proveedor;




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


import domainapp.dom.app.proveedor.Proveedor;



@DomainService(repositoryFor = Proveedor.class)
@DomainServiceLayout(menuOrder = "60", named="Proveedores")
public class RepositorioProveedor {
	@MemberOrder(sequence = "1")
	@ActionLayout(named="Crear nuevo Proveedor")
	public Proveedor createProveedor(
			@ParameterLayout(named="nombre")@Parameter(optionality=Optionality.OPTIONAL)String nombre,
			@ParameterLayout(named="codigo")@Parameter(optionality=Optionality.OPTIONAL)String codigo,
			@ParameterLayout(named="direccion")@Parameter(optionality=Optionality.OPTIONAL)String direccion
			){
		final Proveedor proveedor = container.newTransientInstance(Proveedor.class);
		proveedor.setNombre(nombre);
		proveedor.setCodigo(codigo);
		proveedor.setDireccion(direccion);
		container.persistIfNotAlready(proveedor);
		return proveedor;
	}

	@MemberOrder(sequence = "2")
	@ActionLayout(named = "Listar todos")
	public List<Proveedor> listAll() {
		final List<Proveedor> listaProveedor = this.container
				.allMatches(new QueryDefault<Proveedor>(Proveedor.class, "ListarTodos"));
		if (listaProveedor.isEmpty()) {
			this.container.warnUser("No hay proveedores cargados en el sistema");
		}
		return listaProveedor;
	}
	
	@MemberOrder(sequence = "3")
	@ActionLayout(named = "Buscar por nombre")
    public List<Proveedor> findByName(
            @ParameterLayout(named="Nombre") @Parameter(optionality=Optionality.OPTIONAL)   String nombre
    ) {

        return container.allMatches(
                new QueryDefault<>(
                        Proveedor.class,
                        "findByName",
                        "nombre", (nombre==null)?"":nombre));
    }
	
	@ActionLayout(named = "Buscar por Codigo")
	@MemberOrder(sequence = "4")
	public List<Proveedor> findByCode(
			@ParameterLayout(named = "Codigo") @Parameter(optionality = Optionality.OPTIONAL) String codigo) {
		return container.allMatches(
				new QueryDefault<>(
						Proveedor.class,
						"findByName",
						"nombre", (codigo == null) ? "" : codigo));
	}
	@javax.inject.Inject
    DomainObjectContainer container;
}