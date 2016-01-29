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
public class Localidad {

	// {{ Nombre (property)
	private E_localidades nombre;

	@Column(allowsNull = "true")
	@MemberOrder(sequence = "1")
	@Persistent
	public E_localidades getNombre() {
		return nombre;
	}

	public void setNombre(final E_localidades nombre) {
		this.nombre = nombre;
	}
	// }}

	
	public enum E_localidades{	
		NEUQUEN, CIPOLLETTI, PLOTTIER, CENTENARIO, ROCA, ALLEN;
	}
	
	@Column(allowsNull = "true")
	public String title(){
		return nombre.toString();
	}

	@Override
	@Column(allowsNull = "true")
	public String toString() {
		return nombre.toString();
	}
	
	
	
}
