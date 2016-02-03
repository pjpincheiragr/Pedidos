package domainapp.dom.app.pedido;




import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;

import domainapp.dom.app.proveedor.Proveedor;
import domainapp.dom.app.servicios.E_estado;
import domainapp.dom.app.sucursal.Sucursal;
import domainapp.dom.app.vendedor.Vendedor;




@DomainService(repositoryFor = Pedido.class)
@DomainServiceLayout(menuOrder = "60", named="Pedidos")
public class RepositorioPedido {
	@MemberOrder(sequence = "1")
	@ActionLayout(named="Crear nuevo Pedido")
	public Pedido createPedido(
			@ParameterLayout(named="Descripcion")@Parameter(optionality=Optionality.OPTIONAL)String descripcion,
			@ParameterLayout(named="Lugar")@Parameter(optionality=Optionality.OPTIONAL)Proveedor proveedor,
			@ParameterLayout(named="Vendedor")@Parameter(optionality=Optionality.OPTIONAL)Vendedor vendedor,
			@ParameterLayout(named="Estado")E_estado estado,
			@ParameterLayout(named="Sucursal")Sucursal sucursal
			){
		final Pedido Pedido = container.newTransientInstance(Pedido.class);
		Pedido.setDescripcion(descripcion);
		Pedido.setProveedor(proveedor);
		Pedido.setVendedor(vendedor);		
		Pedido.setEstado(estado);
		Pedido.setSucursal(sucursal);
		container.persistIfNotAlready(Pedido);
		return Pedido;
	}

	
	@javax.inject.Inject
    DomainObjectContainer container;
}