package domainapp.dom.app.ruta;


import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import domainapp.dom.app.pedido.Pedido;

@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="RutaItem_ID")
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

@DomainObject(objectType = "RUTAITEM",bounded=true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)
public class RutaItem {
	private Pedido pedido;
	private int orden ;
	private boolean estado;

	public String title() {		
		return  getPedido().toString() ;
	}

	public RutaItem(Pedido pedido, int orden, boolean estado) {
		super();
		this.pedido = pedido;
		this.orden = orden;
		this.estado = estado;
		
	}

	public RutaItem() {
		super();
	}

	@MemberOrder(sequence="1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@MemberOrder(sequence="2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing=Editing.DISABLED)
	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}


	@MemberOrder(sequence="4")
	@javax.jdo.annotations.Column(allowsNull = "false")
	public boolean getEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	


	@javax.inject.Inject
    DomainObjectContainer container;
}