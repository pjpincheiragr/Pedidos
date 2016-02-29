package domainapp.dom.app.pedido;


import org.joda.time.LocalDate;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import domainapp.dom.app.servicios.E_estado;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "PedidoHistorial_ID")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Queries({ @javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
		+ "FROM domainapp.dom.app.pedido.PedidoHistorial")
})
@DomainObject(objectType = "PEDIDOHISTORIAL", bounded = true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)

public class PedidoHistorial {

	private Pedido pedido;
	private String observacion;
	private E_estado estado;
	private LocalDate fechaHora;
	

	public String title() {
		return "Historial de " + pedido.title();
	}

	public PedidoHistorial(Pedido pedido, String observacion, E_estado estado,
			LocalDate fecha) {

		super();
		this.pedido = pedido;		
		this.observacion = observacion;
		this.estado = estado;
		this.fechaHora = fecha;
	}

	public PedidoHistorial() {
		super();
	}

	@MemberOrder(sequence = "1")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@MemberOrder(sequence = "2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@MemberOrder(sequence = "3")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public E_estado getEstado() {
		return estado;
	}

	public void setEstado(E_estado estado) {
		this.estado = estado;
	}

	@MemberOrder(sequence = "4")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public LocalDate getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDate fechaHora) {
		this.fechaHora = fechaHora;
	}

	@javax.inject.Inject
	DomainObjectContainer container;
}
