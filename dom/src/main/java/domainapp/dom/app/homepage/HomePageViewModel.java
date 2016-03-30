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
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.ViewModel;

import domainapp.dom.app.servicios.E_urgencia_pedido;
import domainapp.dom.app.pedido.Pedido;
import domainapp.dom.app.pedido.RepositorioPedido;
import domainapp.dom.app.vendedor.RepositorioVendedor;
import domainapp.dom.app.cadete.*;

@ViewModel
@MemberGroupLayout(
	     columnSpans={12,0,0,12}
	     )
public class HomePageViewModel {

	//region > title
	public String title() {
		return ("Pedidos y Rutas pendientes");
	}
	// endregion
	
	public String cssClass() {return "my-special-auto-updating-entity"; }
	
	@javax.jdo.annotations.Column(allowsNull = "true")
	@MemberOrder(sequence = "1")
	@Property(editing = Editing.ENABLED)
	@CollectionLayout(render = RenderType.EAGERLY)
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
	@MemberOrder(sequence = "3")
	@CollectionLayout(
	            render = RenderType.EAGERLY
	    )
	public List<Cadete> getlistCadetes() {
		return repositorioCadete.listAll();
	}

	@javax.inject.Inject
	DomainObjectContainer container;
	@javax.inject.Inject
	RepositorioPedido repositorioPedido;
	@javax.inject.Inject
	RepositorioVendedor repositorioVendedor;
	@javax.inject.Inject 
	RepositorioCadete repositorioCadete;
	



}
