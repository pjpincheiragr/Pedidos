/*
 *  SIGAFV is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * SIGAFV is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with SIGAFV.  If not, see <http://www.gnu.org/licenses/>.
 */
    
package domainapp.fixture;

import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class VaciarTablaFixture extends Fixture{

	private String tabla="";

	public VaciarTablaFixture(final String tabla){
		withDiscoverability(Discoverability.DISCOVERABLE);
		this.tabla=tabla;
	}

	@Override
	protected void execute(ExecutionContext executionContext) {
		if (tabla!="")
			isisJdoSupport.executeUpdate("TRUNCATE \""+tabla+"\"CASCADE");
	}

	@javax.inject.Inject
	private IsisJdoSupport isisJdoSupport;
}
