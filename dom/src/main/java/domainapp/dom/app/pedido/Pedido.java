package domainapp.dom.app.pedido;



import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import domainapp.dom.app.marca.Marca;
import domainapp.dom.app.proveedor.Proveedor;
import domainapp.dom.app.servicios.E_estado;
import domainapp.dom.app.servicios.E_estado_item;
import domainapp.dom.app.sucursal.Sucursal;
import domainapp.dom.app.tipo.Tipo;
import domainapp.dom.app.vendedor.Vendedor;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import org.joda.time.LocalDate;
@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="Pedido_ID")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
	@javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.pedido.Pedido "
			+ " order by fecha "),
	@javax.jdo.annotations.Query(name = "ListarPendientes", language = "JDOQL", value = "SELECT "
					+ "FROM domainapp.dom.app.pedido.Pedido "
					+ "Where (estado==ASIGNADO) || (estado=EN_PROCESO)"
					+ " order by fecha "),
	@javax.jdo.annotations.Query(name = "findByDescription", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.pedido.Pedido "
			+ "WHERE ((:descripcion=='') || (descripcion.toLowerCase().indexOf(:descripcion) >= 0))"
			+ " order by fecha "),
	@javax.jdo.annotations.Query(name = "findByState", language = "JDOQL", value = "SELECT "
			+ "FROM domainapp.dom.app.pedido.Pedido "
			+ "WHERE (estado==:estado)"
			+ " order by fecha "),
	@javax.jdo.annotations.Query(name = "findBySeller", language = "JDOQL", value = "SELECT "
					+ "FROM domainapp.dom.app.pedido.Pedido "
					+ "WHERE (vendedor==:vendedor)"
					+ " order by fecha ")
})

@DomainObject(objectType = "PEDIDO",bounded=true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class Pedido {
	private Tipo tipo;
	private Proveedor proveedor;
	private LocalDate fecha;
	private int tiempo;
	private Vendedor vendedor;
	private float valor;
	private E_estado estado;
	private Sucursal sucursal;
	private String observacion;
	
	public String title() {		
		return  getFechaHora().toString()+" - "+getVendedor()+" - "+ getSucursal()   ;
	}

	public Pedido(Tipo tipo, Proveedor proveedor, LocalDate fecha, int tiempo,Vendedor vendedor,
			float valor, E_estado estado, Sucursal sucursal, String observacion) {
		super();
		this.tipo = tipo;
		this.proveedor = proveedor;
		this.tiempo=tiempo;
		this.vendedor = vendedor;
		this.valor=valor;
		this.estado = estado;
		this.sucursal = sucursal;
		this.fecha = fecha;
		this.observacion=observacion;
	}

	public Pedido() {
		super();
	}

	@MemberOrder(sequence="1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo=tipo;
	}

	@MemberOrder(sequence="2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	
	@MemberOrder(sequence="3")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public int getTiempo() {
		return tiempo;
	}

	public void setTiempo(int tiempo2) {
		this.tiempo=tiempo2;
	}

	
	
	@MemberOrder(sequence="4")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	
	@MemberOrder(sequence="5")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor=valor;
	}
	
	
	@MemberOrder(sequence="6")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public E_estado getEstado() {
		return estado;
	}

	public void setEstado(E_estado estado) {
		this.estado = estado;
	}


	@MemberOrder(sequence="7")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}
	
	// FechaHora
 	private LocalDate fechaHora; 	
 	 	 	
 	@MemberOrder( sequence = "4")
 	@Column(allowsNull = "true")
 	public LocalDate getFechaHora() {
 		return fechaHora;
 	}

 	public void setFechaHora(final LocalDate fechaHora) {
 		this.fechaHora = fechaHora;
 	}
	
	 //Agrego campo observación 
	 
	 @javax.jdo.annotations.Column(allowsNull="false", length = 600)
	 public String getObservacion(){
	       return observacion;
    }
	 public void setObservacion(final String observacion) {
				        this.observacion = observacion;
	 }      
				//Fin campo Descripcion del error
		
	 
		// {{ Pedido Item (Property)
		@Join
		@javax.jdo.annotations.Column(allowsNull="true")
		//@Persistent(mappedBy = "Pedido", dependentElement = "false")
		private List <PedidoItem> ListaPedidos=new ArrayList<PedidoItem>();

		//@Render(Type.EAGERLY)
		@MemberOrder(sequence = "1.5")
		public List <PedidoItem> getPedidoItem() {
			return ListaPedidos;
		}

		public void setPedidosItem(final List<PedidoItem> listaPedidos) {
			this.ListaPedidos = listaPedidos;
		}
		
		@ActionLayout(named = "Agregar Item")
		public void addPedidoItem(
			@ParameterLayout(named="Codigo")@Parameter(optionality=Optionality.OPTIONAL)String codigo,
			@ParameterLayout(named="Marca")@Parameter(optionality=Optionality.OPTIONAL)Marca marca,
			@ParameterLayout(named="Cantidad")@Parameter(optionality=Optionality.OPTIONAL)int cantidad,
			@ParameterLayout(named="Estado")@Parameter(optionality=Optionality.OPTIONAL)E_estado_item estado)
			{
		final PedidoItem PedidoItem = container.newTransientInstance(PedidoItem.class);
		PedidoItem.setCodigo(codigo);
		PedidoItem.setMarca(marca);
		PedidoItem.setCantidad(cantidad);
		PedidoItem.setEstado(estado);
		container.persistIfNotAlready(PedidoItem);	
			getPedidoItem().add(PedidoItem);
		}

	  //fin
	@javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    RepositorioPedidoItem repositorioPedidoItem;
}
