package domainapp.dom.app.sucursal;


	import java.io.File;
	import java.io.FileInputStream;
	import java.io.IOException;
	import java.sql.Timestamp;
	import java.text.DateFormat;
	import java.util.ArrayList;
	import java.util.Date;
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
	import net.sf.jasperreports.engine.JRException;
	import net.sf.jasperreports.engine.JasperCompileManager;
	import net.sf.jasperreports.engine.JasperFillManager;
	import net.sf.jasperreports.engine.JasperPrint;
	import net.sf.jasperreports.engine.JasperReport;
	import net.sf.jasperreports.engine.design.JasperDesign;
	import net.sf.jasperreports.engine.xml.JRXmlLoader;
	import net.sf.jasperreports.view.JasperViewer;

	@DomainService(repositoryFor = Sucursal.class)
	@DomainServiceLayout(menuOrder = "30", named = "Sucursal")
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
			Date date = new Date();
			Timestamp fecha = new Timestamp(date.getTime());
			sucursal.setCodigoSucursal(codigoSucursal);
			sucursal.setNombre(nombre);
			sucursal.setDireccion(dire);
			sucursal.setActivo(true);
			container.persistIfNotAlready(sucursal);
			return sucursal;

		}
/*
		// Validar Codigo de Area y Nombre
			public String validateCreateArea(String codigoArea, String nombre,String descripcion) {
				if (!container.allMatches(
						new QueryDefault<Area>(Area.class, "buscarPorNombre",
								"nombre", nombre.toUpperCase())).isEmpty()) {
					return "El nombre ya Existe. Por favor vericar los datos ingresados.";
				}
				if (!container.allMatches(
						new QueryDefault<Area>(Area.class, "buscarPorCodigo",
								"codigoArea", codigoArea.toUpperCase())).isEmpty()) {
					return "El Codigo de Area ya Existe. Por favor vericar los datos ingresados.";
				}
				return null;
			}
*/
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

		/*
		@MemberOrder(sequence="5")
		@ActionLayout(named="Exportar Areas")
		public String downloadAll() throws JRException, IOException {
			AreaDataSource datasource = new AreaDataSource();
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
			for (Area a : listAll()) {
				ReporteArea area = new ReporteArea();
				area.setCodigoArea(a.getCodigoArea());
				area.setNombre(a.getNombre());
				area.setDescripcion(a.getDescripcion());
				area.setFechaAlta(df.format(a.getFechaAlta()));
				area.setActivo(Boolean.toString(a.isActivo()));
				datasource.addParticipante(area);
			}
			File file = new File("Area.jrxml");
			FileInputStream input = null;
			try {
				input = new FileInputStream(file);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			JasperDesign jd = JRXmlLoader.load(input);
			JasperReport reporte = JasperCompileManager.compileReport(jd);
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null, datasource);
			JasperViewer.viewReport(jasperPrint,false);
			return "Reporte Generado";
		}
*/
		@javax.inject.Inject
		DomainObjectContainer container;
}
