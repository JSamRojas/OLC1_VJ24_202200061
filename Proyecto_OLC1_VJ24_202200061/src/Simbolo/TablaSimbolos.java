
package Simbolo;

import java.util.HashMap;

public class TablaSimbolos {
    
    private TablaSimbolos TablaAnterior;
    private HashMap<String, Object> TablaActual;
    private String nombre;

    public TablaSimbolos() {
        this.TablaActual = new HashMap<>();
        this.nombre = "";
    }

    public TablaSimbolos(TablaSimbolos TablaAnterior) {
        this.TablaAnterior = TablaAnterior;
        this.TablaActual = new HashMap<>();
        this.nombre = "";
    }

    public TablaSimbolos getTablaAnterior() {
        return TablaAnterior;
    }

    public void setTablaAnterior(TablaSimbolos TablaAnterior) {
        this.TablaAnterior = TablaAnterior;
    }

    public HashMap<String, Object> getTablaActual() {
        return TablaActual;
    }

    public void setTablaActual(HashMap<String, Object> tablaActual) {
        this.TablaActual = TablaActual;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
