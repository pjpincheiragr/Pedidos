package domainapp.dom.app.estadoelemento;

import java.sql.Timestamp;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Programmatic;

import domainapp.dom.app.empleado.Empleado;
import domainapp.dom.app.gps.Gps;
import domainapp.dom.app.matafuego.Matafuego;
import domainapp.dom.app.vehiculo.Vehiculo;


@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="Activo_ID")
@javax.jdo.annotations.Version(
        strategy=VersionStrategy.VERSION_NUMBER, 
        column="version")

@DomainObject(
        objectType = "ACTIVO"
)

@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_CHILD
)
public class Activo extends Estado {

	public Activo(Timestamp fechaCambio, Motivo motivo) {
		super(fechaCambio, motivo);
	}

	public Activo() {
		super();
	}

	@Override
	public String toString() {
		return "Activo";
	}

	/**********************************
	 * Desactivacion de los elementos.*
	 **********************************/

	@Override
	@Programmatic
	public void desactivarGps(Gps gps, Motivo motivo, Timestamp fecha) {
		Estado estado = nuevoEstadoInactivo(fecha, motivo);
		actualizarGps(gps, estado);
		container.informUser("Se desactivo el Gps de manera exitosa.");
	}

	@Override
	@Programmatic
	public void desactivarMatafuego(Matafuego matafuego, Motivo motivo, Timestamp fecha) {
		Estado estado = nuevoEstadoInactivo(fecha, motivo);
		actualizarMatafuego(matafuego, estado);
		container.informUser("Se desactivo el Matafuego de manera exitosa.");
	}

	@Override
	@Programmatic
	public void desactivarVehiculo(Vehiculo vehiculo, Motivo motivo, Timestamp fecha) {
		Estado estado = nuevoEstadoInactivo(fecha, motivo);
		actualizarVehiculo(vehiculo, estado);
		container.informUser("Se desactivo el Vehiculo de manera exitosa.");
	}

	/*********************************
	 * Reactivacion de los elementos.*
	 *********************************/

	@Override
	@Programmatic
	public void reactivarGps(Gps gps) {
		container.warnUser("El Gps seleccionado, ya se encuentra en estado Activo.");
	}

	@Override
	@Programmatic
	public void reactivarMatafuego(Matafuego matafuego) {
		container.warnUser("El Matafuego seleccionado, ya se encuentra en estado Activo.");
	}

	@Override
	@Programmatic
	public void reactivarVehiculo(Vehiculo vehiculo) {
		container.warnUser("El Vehiculo seleccionado, ya se encuentra en estado Activo.");
	}

	/*******************************
	 * Asignacion de los elementos.*
	 *******************************/

	@Override
	@Programmatic
	public void asignarGps(Gps gps) {
		Estado estado = new Asignado(gps.getEstado().getFechaCambio(), gps.getEstado().getMotivo());
		actualizarGps(gps, estado);
	}

	@Override
	@Programmatic
	public void asignarMatafuego(Matafuego matafuego) {
		Estado estado = new Asignado(matafuego.getEstado().getFechaCambio(), matafuego.getEstado().getMotivo());
		actualizarMatafuego(matafuego, estado);
	}

	@Override
	@Programmatic
	public void asignarVehiculo(Vehiculo vehiculo) {
		Estado estado = new Asignado(vehiculo.getEstado().getFechaCambio(), vehiculo.getEstado().getMotivo());
		actualizarVehiculo(vehiculo, estado);
	}

	/**********************************
	 * Desasignacion de los elementos.*
	 **********************************/

	@Override
	public void desasignarGps(Vehiculo vehiculo) {
		container.warnUser("El Gps, debe encontrarse en estado Asignado para poder ser Desasignado.");
	}

	@Override
	public void desasignarMatafuego(Vehiculo vehiculo) {
		container.warnUser("El Matafuego, debe encontrarse en estado Asignado para poder ser Desasignado.");
	}

	@Override
	public void desasignarVehiculo(Empleado empleado) {
		container.warnUser("El Vehiculo, debe encontrarse en estado Asignado para poder ser Desasignado.");
	}

	@javax.inject.Inject
	DomainObjectContainer container;
}