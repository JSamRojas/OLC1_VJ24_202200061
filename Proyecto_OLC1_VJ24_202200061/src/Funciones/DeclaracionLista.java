
package Funciones;

import Abstracto.Instruccion;
import GUI.VistaPrincipal;
import Simbolo.Arbol;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;
import java.util.ArrayList;

/**
 *
 * @author Rojas
 */
public class DeclaracionLista extends Instruccion {
    
    public String ID;
    public String Entorno;

    public DeclaracionLista(String ID, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.Entorno = "";
        this.ID = ID;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        Simbolos newSimbolo = new Simbolos(this.ID, this.tipo, new ArrayList<Object>(), true, "Lista Dinamica",  this.linea, this.columna);
        
        if(this.Entorno.equals("")){
            this.setEntorno("GLOBAL");
        }
        newSimbolo.setEntorno(this.Entorno);
        
        boolean creacion = tabla.setVariable(newSimbolo);
        
        if(!creacion){
            return new Errores("SEMANTICO", "La variable con el identificador " + this.ID + " ya existe", this.linea, this.columna);
        }
        
        VistaPrincipal.listaSimbolos.add(newSimbolo);

        return null;

    }
    
    public String getEntorno() {
        return Entorno;
    }

    public void setEntorno(String Entorno) {
        this.Entorno = Entorno;
    }

}
