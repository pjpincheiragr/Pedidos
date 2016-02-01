package domainapp.dom.app.pedido;




import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;



@DomainService(repositoryFor = Pedido.class)
@DomainServiceLayout(menuOrder = "60", named="Pedidos")
public class RepositorioPedido {
	@MemberOrder(sequence = "1")
	@ActionLayout(named="Crear nuevo Pedido")
	public Pedido createPedido(
			@ParameterLayout(named="descripcion")@Parameter(optionality=Optionality.OPTIONAL)String descripcion,
			@ParameterLayout(named="lugar")@Parameter(optionality=Optionality.OPTIONAL)String lugar,
			@ParameterLayout(named="vendedor")@Parameter(optionality=Optionality.OPTIONAL)String vendedor,
			@ParameterLayout(named="urgencia")@Parameter(optionality=Optionality.OPTIONAL)String urgencia,
			@ParameterLayout(named="sucursal")String sucursal
			){
		final Pedido Pedido = container.newTransientInstance(Pedido.class);
		Pedido.setDescripcion(descripcion);
		Pedido.setLugar(lugar);
		Pedido.setVendedor(vendedor);		
		Pedido.setUrgencia(urgencia);
		Pedido.setSucursal(sucursal);
		container.persistIfNotAlready(Pedido);
		return Pedido;
	}

	
	@javax.inject.Inject
    DomainObjectContainer container;
}