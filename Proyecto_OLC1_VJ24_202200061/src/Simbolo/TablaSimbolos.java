
package Simbolo;

import java.util.HashMap;
import Funciones.Simbolos;

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
    
    public boolean setVariable(Simbolos simbolo){
        Simbolos busqueda = (Simbolos) this.TablaActual.get(simbolo.getNombre().toLowerCase());
        if(busqueda == null){
            this.TablaActual.put(simbolo.getNombre().toLowerCase(), simbolo);
            return true;
        }
        return false;
    }
    
    public Simbolos getVariable(String id){
        for(TablaSimbolos i = this; i != null; i = i.getTablaAnterior()){
            Simbolos busqueda = (Simbolos) i.TablaActual.get(id.toLowerCase());
            if(busqueda != null){
                return busqueda;
            }
        }
        return null;
    }
 
}
