package domainapp.dom.modules.security;

import java.util.List;

import org.apache.isis.applib.security.RoleMemento;
import org.apache.isis.applib.security.UserMemento;

public class Services {
public boolean isVendedor(UserMemento user ){
	

	boolean isVendedor=false;
	
	final List<RoleMemento> roles = user.getRoles();
	for (RoleMemento role : roles) {
	    String roleName = role.getName();
	    if (roleName=="Vendedores")
	    		isVendedor=true;
	}
	return isVendedor;
}
}
