
package domainapp.dom.modules.servicios;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.annotation.MemberOrder;



@PersistenceCapable
public class Estado {
	
	
	// {{ Nombre (property)
		private E_estados estado;

		@Column(allowsNull = "true")
		@MemberOrder(sequence = "1")
		@Persistent
		public E_estados getEstado() {
			return estado;
		}

		public void setEstado(final E_estados estado) {
			this.estado = estado;
		}
		

		public enum E_estados{	
			REPARADO,ENTREGADO,ENESPERA;
		}
		
		public String title(){
			return estado.toString();
		}

		@Override
		public String toString() {
			return estado.toString();
		}
		

}
