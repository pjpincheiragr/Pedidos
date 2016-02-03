package domainapp.dom.app.proveedor;




import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;



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

	
	@javax.inject.Inject
    DomainObjectContainer container;
}