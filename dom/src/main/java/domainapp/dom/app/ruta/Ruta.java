package domainapp.dom.app.ruta;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;

import domainapp.dom.app.cadete.Cadete;
import domainapp.dom.app.pedido.Pedido;




@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Uniques({ @javax.jdo.annotations.Unique(name = "OrdenServicio_numero_must_be_unique", members = { "numero" }) })
@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.APPLICATION)
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Queries({
		@javax.jdo.annotations.Query(name = "ListarTodos", language = "JDOQL", value = "SELECT "
				+ "FROM domainapp.dom.app.ruta.Ruta ")
		})
@DomainObject(objectType = "ruta", bounded = true)
@DomainObjectLayout(bookmarking = BookmarkPolicy.AS_CHILD)



public class Ruta {
	private Cadete cadete;
	private List<Pedido> listaPedidos=new ArrayList<Pedido>();
	@PrimaryKey
	private long numero;
	
	public String title() {		
		return "Ruta: " + getNumero()   ;
	}

	public Ruta(Cadete cadete) {
		super();
		this.cadete = cadete;
	
	}

	public Ruta() {
		super();
	}

	@javax.jdo.annotations.Column(allowsNull = "false")
	@javax.jdo.annotations.PrimaryKey(column = "numero")
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT, sequence = "numero")
	@MemberOrder(name="Orden de Servicio",sequence = "1")
	public long getNumero() {
		return numero;
	}

	public void setNumero(final long numero) {
		this.numero = numero;
	}

	@MemberOrder(sequence = "2")
	@javax.jdo.annotations.Column(allowsNull = "false")
	@Property(editing = Editing.DISABLED)
	public Cadete getCadete() {
		return cadete;
	}

	public void setCadete(Cadete cadete) {
		this.cadete = cadete;
	}

	@MemberOrder(sequence = "3")
	@javax.jdo.annotations.Column(allowsNull = "true")
	@Property(editing = Editing.ENABLED)
	public List<Pedido> getListaPedidos() {
		return listaPedidos;
	}

	public void setListaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}

	@ActionLayout(named = "Agregar Pedido")
	public void addPedido(Pedido pedido) {
		getListaPedidos().add(pedido);
	}

	@javax.inject.Inject
	DomainObjectContainer container;
}