package domainapp.dom.app.tipo;

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
import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;

@DomainService(repositoryFor = Tipo.class)
@DomainServiceLayout(menuOrder = "1" , named="Tipo")

public class RepositorioTipo {
	
    //region > create (action)
    @MemberOrder(sequence = "1")
    public Tipo ingresarTipo(
    		final @ParameterLayout(named="Abreviatura") String abreviatura,
            final @ParameterLayout(named="Descripcion", multiLine=10) String descripcion
    		) {
        final Tipo obj = container.newTransientInstance(Tipo.class);
        obj.setAbreviatura(abreviatura);
        obj.setDescripcion(descripcion);  
        container.persistIfNotAlready(obj);
        container.informUser("La nueva marca a sido cargado correctamente");
        return obj;
    }

    //endregion
    
  //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public static List<Tipo> listarTodas() {
        return container.allInstances(Tipo.class);
    }
    //endregion

    //region > findByDescripcion (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "3")
    public List<Tipo> buscarPorDescripcion(
            @ParameterLayout(named="Descripcion") @Parameter(optionality=Optionality.OPTIONAL) String descripcion
     
    ) {
        return container.allMatches(
                new QueryDefault<>(
                        Tipo.class,
                        "findByDescripcion",
                        "descripcion", descripcion==null?"":descripcion));
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
	static 
    DomainObjectContainer container;

    //endregion

}
