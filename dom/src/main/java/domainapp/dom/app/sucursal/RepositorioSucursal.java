package domainapp.dom.app.sucursal;


	import java.util.List;

	import org.apache.isis.applib.DomainObjectContainer;
	import org.apache.isis.applib.annotation.ActionLayout;
	import org.apache.isis.applib.annotation.DomainService;
	import org.apache.isis.applib.annotation.DomainServiceLayout;
	import org.apache.isis.applib.annotation.MemberOrder;
	import org.apache.isis.applib.annotation.Optionality;
	import org.apache.isis.applib.annotation.Parameter;
	import org.apache.isis.applib.annotation.ParameterLayout;
	import org.apache.isis.applib.query.QueryDefault;
	import domainapp.dom.app.servicios.Direccion;
	import domainapp.dom.app.servicios.Localidad;
	import domainapp.dom.app.servicios.Localidad.E_localidades;

	@DomainService(repositoryFor = Sucursal.class)
	@DomainServiceLayout(menuOrder = "30", named = "Administración")
	public class RepositorioSucursal  {

		@MemberOrder(sequence = "1")
		@ActionLayout(named = "Crear Sucursal")
		public Sucursal createSucursal(
				final @ParameterLayout(named = "Codigo de Sucursal") String codigoSucursal,
				final @ParameterLayout(named = "Nombre") @Parameter(regexPattern = domainapp.dom.regex.validador.Validador.ValidacionAlfanumerico.ADMITIDOS, maxLength = 30) String nombre,
				final @ParameterLayout(named="Localidad") @Parameter(optionality=Optionality.OPTIONAL) E_localidades localidad,
				final @ParameterLayout(named="Calle") @Parameter(optionality=Optionality.OPTIONAL) String calle,
				final @ParameterLayout(named="Numero") @Parameter(optionality=Optionality.OPTIONAL)  java.lang.Integer numero,
				final @ParameterLayout(named="Piso") @Parameter(optionality=Optionality.OPTIONAL) String piso,
				final @ParameterLayout(named="Departamento") @Parameter(optionality=Optionality.OPTIONAL) String departamento,
				final @ParameterLayout(named="Teléfono") @Parameter(optionality=Optionality.OPTIONAL) String telefono)
		            {

			final Sucursal sucursal = container.newTransientInstance(Sucursal.class);
		    final Direccion dire = new Direccion();
		    final Localidad loca = new Localidad();
		    loca.setNombre(localidad);
		    if (calle != null && !calle.isEmpty()) { dire.setCalle(calle.toUpperCase());}
		    dire.setNumero(numero);
		    if (piso != null && !piso.isEmpty()) { dire.setPiso(piso);}
		    if (departamento != null && !departamento.isEmpty()) { dire.setDepartamento(departamento);}
		    if (loca != null) { dire.setLocalidad(loca);}	
			sucursal.setCodigoSucursal(codigoSucursal);
			sucursal.setNombre(nombre);
			sucursal.setDireccion(dire);
			container.persistIfNotAlready(sucursal);
			return sucursal;

		}

		@MemberOrder(sequence = "2")
		@ActionLayout(named = "Listar todos")
		public List<Sucursal> listAll() {
			final List<Sucursal> listaSucursal = this.container
					.allMatches(new QueryDefault<Sucursal>(Sucursal.class, "ListarTodos"));
			if (listaSucursal.isEmpty()) {
				//this.container.warnUser("No hay areas cargadas en el sistema");
			}
			return listaSucursal;
		}

		//@MemberOrder(sequence = "6", name = "Elementos Inactivos")
		@ActionLayout(named = "Sucursal")
		public List<Sucursal> listAllInactivos() {
			final List<Sucursal> listaInactivos = this.container
					.allMatches(new QueryDefault<Sucursal>(Sucursal.class, "ListarInactivos"));
			return listaInactivos;
		}

		@MemberOrder(sequence = "3")
		@ActionLayout(named = "Buscar por nombre")
	    public List<Sucursal> buscarPorNombre(
	            @ParameterLayout(named="Nombre") @Parameter(optionality=Optionality.OPTIONAL)   String nombre
	    ) {

	        return container.allMatches(
	                new QueryDefault<>(
	                        Sucursal.class,
	                        "findByName",
	                        "nombre", (nombre==null)?"":nombre));
	    }

		@javax.inject.Inject
		DomainObjectContainer container;
}
