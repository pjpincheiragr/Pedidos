/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.app.homepage;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.HomePage;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.app.ruta.RepositorioRuta;
import domainapp.dom.app.ruta.Ruta;
import domainapp.dom.app.servicios.E_urgencia_pedido;
import domainapp.dom.app.pedido.Pedido;
import domainapp.dom.app.pedido.RepositorioPedido;
import domainapp.dom.app.vendedor.RepositorioVendedor;

@ViewModel
public class HomePageViewModel {

	//region > title
	public String title() {
		return ("Pedidos y Rutas pendientes");
	}
	// endregion
	
	 
	@javax.jdo.annotations.Column(allowsNull = "true")
	@MemberOrder(sequence = "1")
	@Property(editing = Editing.ENABLED)
	@CollectionLayout(render = RenderType.EAGERLY)
	@PropertyLayout(cssClass="my-special-auto-updating-entity")
	public List<Pedido> getListaPedidosUrgentes() {
	
		return repositorioPedido
				.listAllByUrgency(E_urgencia_pedido.RESPUESTA_RAPIDA);
	}

	@javax.jdo.annotations.Column(allowsNull = "true")
	@MemberOrder(sequence = "2")
	@Property(editing = Editing.ENABLED)
	@CollectionLayout(render = RenderType.EAGERLY)
	public List<Pedido> getListaPedidosProgramables() {
		return repositorioPedido
				.listAllByUrgency(E_urgencia_pedido.PROGRAMABLE);

	}
	


	@HomePage
	  @CollectionLayout(
	            render = RenderType.EAGERLY
	    )
	public List<Ruta> getlistRutas() {
		return RepositorioRuta.listAll();
		}



	/*@HomePage
	public List<Pedido> getlistPedidosVendedor1() {                                  //Ver este método
		Vendedor ObjetoVendedor1= new Vendedor();
		ObjetoVendedor1	=(repositorioVendedor.findByNameAuxiliar(""")).get(0);
		return repositorioPedido.findBySeller(ObjetoVendedor1);
	}
<<<<<<< HEAD
=======
	
	@HomePage

	public List<Cadete> getCadetes() {
		return repositorioCadete.listAll();	}
>>>>>>> e6078a5134d902acf586ae155ae6657cf6a23f4d


	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(describedAs = "El documento se almacenara en ReporteAlertas/AlertasMatafuego")
	public String downloadByPeriodMatafuego(@ParameterLayout(named = "Desde") Date desde, @ParameterLayout(named = "Hasta") Date hasta)
					throws JRException, IOException {
			return repositorioAlertaMatafuego.exportarPorPeriodo(desde, hasta);
	}

	@Action(semantics = SemanticsOf.SAFE)
	public String downloadAllVehiculo() throws JRException, IOException{
		return repositorioAlertaVehiculo.downloadAll();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(describedAs = "El documento se almacenara en ReporteAlertas/AlertasVehiculo")
	public String downloadByPeriodVehiculo(@ParameterLayout(named = "Desde") Date desde, @ParameterLayout(named = "Hasta") Date hasta)
					throws JRException, IOException {
		return repositorioAlertaVehiculo.exportarPorPeriodo(desde, hasta);
	}
*/
	@javax.inject.Inject
	DomainObjectContainer container;
	@javax.inject.Inject
	RepositorioPedido repositorioPedido;
	@javax.inject.Inject
	RepositorioVendedor repositorioVendedor;
	@javax.inject.Inject RepositorioRuta RepositorioRuta;
	



	// endregion
	/*
	// region > title
	public String title() {
		return (getAlertasMatafuego().size() + getAlertasVehiculo().size()) + " Alertas";
	}
	// endregion
	// region > object (collection)
	@HomePage
	public List<AlertaMatafuego> getAlertasMatafuego() {
		return repositorioAlertaMatafuego.listAllMatafuego();
	}

	@HomePage
	public List<AlertaVehiculo> getAlertasVehiculo() {
		return repositorioAlertaVehiculo.listAllVehiculo();
	}

	@Action(semantics = SemanticsOf.SAFE)
	public String downloadAllMatafuego() throws JRException, IOException{
		return repositorioAlertaMatafuego.downloadAll();

	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(describedAs = "El documento se almacenara en ReporteAlertas/AlertasMatafuego")
	public String downloadByPeriodMatafuego(@ParameterLayout(named = "Desde") Date desde, @ParameterLayout(named = "Hasta") Date hasta)
					throws JRException, IOException {
			return repositorioAlertaMatafuego.exportarPorPeriodo(desde, hasta);
	}

	@Action(semantics = SemanticsOf.SAFE)
	public String downloadAllVehiculo() throws JRException, IOException{
		return repositorioAlertaVehiculo.downloadAll();
	}

	@Action(semantics = SemanticsOf.SAFE)
	@ActionLayout(describedAs = "El documento se almacenara en ReporteAlertas/AlertasVehiculo")
	public String downloadByPeriodVehiculo(@ParameterLayout(named = "Desde") Date desde, @ParameterLayout(named = "Hasta") Date hasta)
					throws JRException, IOException {
		return repositorioAlertaVehiculo.exportarPorPeriodo(desde, hasta);
	}

	@javax.inject.Inject
	DomainObjectContainer container;
	@javax.inject.Inject
	RepositorioAlertaMatafuego repositorioAlertaMatafuego;
	@javax.inject.Inject
	RepositorioAlertaVehiculo repositorioAlertaVehiculo;

	// endregion
	 * 
	 * */

}
