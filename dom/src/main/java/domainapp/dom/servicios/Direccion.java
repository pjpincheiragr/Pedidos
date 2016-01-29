/*
 * This is software for administration and management of mobile equipment repair
 *
 * Copyright ( C ) 2015 , Pluscel
 *
 * This program is free software ; you can redistribute it and / or
 * Modify it under the terms of the GNU General Public License
 * As published by the Free Software Foundation ; either version 2
 * Of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * But WITHOUT ANY WARRANTY; without even the implied warranty
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. Boil
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * Along with this program ; if not, write to the Free Software
 *
 *
 * Foundation , Inc. , 51 Franklin Street, Fifth Floor , Boston, MA 02110-1301 , USA .
 */

package domainapp.dom.servicios;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.annotation.MemberOrder;

@PersistenceCapable
public class Direccion {

	
	@Column(allowsNull = "true")
	public String title(){
		return calle + " " + numero;
	}
	// {{ Calle (property)
	private String calle;

	//@Column(allowsNull = "true", name = "LOCALIDAD_ID")	
	@Column(allowsNull = "true")
	@Persistent
	@MemberOrder(sequence = "1")	
	public String getCalle() {
		return calle;
	}

	public void setCalle(final String calle) {
		this.calle = calle;
	}
	// }}

	// {{ Numero (property)
	private java.lang.Integer numero;

	@Column(allowsNull = "true")
	@Persistent
	@MemberOrder(sequence = "1.1")
	public java.lang.Integer getNumero() {
		return numero;
	}

	public void setNumero(final java.lang.Integer numero) {
		this.numero = numero;
	}
	// }}

	// {{ Piso (property)
	private String piso;

	@Column(allowsNull = "true")
	@Persistent
	@MemberOrder(sequence = "1.1.1")
	public String getPiso() {
		return piso;
	}

	public void setPiso(final String piso) {
		this.piso = piso;
	}
	// }}


	// {{ Departamento (property)
	private String departamento;

	@Column(allowsNull = "true")
	@Persistent
	@MemberOrder(sequence = "1.1.2")
	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(final String departamento) {
		this.departamento = departamento;
	}
	// }}
	
	
	// {{ Localidad (property)
	private Localidad localidad;

	@Column(allowsNull = "true")
	@Persistent
	@MemberOrder(sequence = "1.2")
	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(final Localidad localidad) {
		this.localidad = localidad;
	}
	// }}

}
