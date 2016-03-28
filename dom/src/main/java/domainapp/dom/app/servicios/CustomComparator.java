package domainapp.dom.app.servicios;

import java.util.Comparator;

import domainapp.dom.app.cadete.CadeteItem;

public class CustomComparator implements Comparator<CadeteItem> {
    @Override
    public int compare(CadeteItem o1, CadeteItem o2) {
        return Integer.compare(o1.getOrden(),o2.getOrden());
    }
}
