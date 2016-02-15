package domainapp.dom.app.pedido;


import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.value.Blob;

import domainapp.dom.app.marca.Marca;

import domainapp.dom.app.servicios.E_estado_item;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="PedidoItem_ID")
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

@DomainObject(objectType = "PEDIDOITEM",bounded=true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class PedidoItem {
	private String codigo;
	private Marca marca;
	private int cantidad;
	private  E_estado_item estado;
	private Blob attachment;
	public String title() {		
		return  getCodigo() + " - "+ getMarca()   ;
	}

	public PedidoItem(String codigo, Marca marca, int cantidad, E_estado_item estado) {
		super();
		this.codigo = codigo;
		this.marca = marca;
		this.cantidad=cantidad;
		this.estado = estado;
		
	}

	public PedidoItem() {
		super();
	}

	@MemberOrder(sequence="1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@MemberOrder(sequence="2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	
	@MemberOrder(sequence="3")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad=cantidad;
	}

	
	
	@MemberOrder(sequence="4")
	@javax.jdo.annotations.Column(allowsNull = "false")
	public E_estado_item getEstado() {
		return estado;
	}

	public void setEstado(E_estado_item estado) {
		this.estado = estado;
	}
	

	 /*Esta porcion de codigo nos permite insertar documentos en nuestra clase.*/


	  @javax.jdo.annotations.Persistent(defaultFetchGroup="false")
	  @javax.jdo.annotations.Column(allowsNull="true")
	  public Blob getAttachment() {
	      return attachment;
	  }

	  public void setAttachment(final Blob attachment) {
	      this.attachment = attachment;
	  }
	  
	@javax.inject.Inject
    DomainObjectContainer container;
}
