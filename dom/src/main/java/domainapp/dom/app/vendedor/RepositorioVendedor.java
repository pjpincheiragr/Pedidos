package domainapp.dom.app.vendedor;




import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;



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

	
	@javax.inject.Inject
    DomainObjectContainer container;
}