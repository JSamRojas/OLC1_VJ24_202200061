
package Funciones;

import Abstracto.Instruccion;
import java.util.LinkedList;
import Simbolo.*;
import java.util.HashMap;

/**
 *
 * @author Rojas
 */
public class Metodos extends Instruccion {
    
    public String ID;
    public LinkedList<Instruccion> InstruccionesMetodo;
    public LinkedList<HashMap> parametros;

    public Metodos(String ID, LinkedList<HashMap> parametros,LinkedList<Instruccion> InstruccionesMetodo, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.ID = ID;
        this.parametros = parametros;
        this.InstruccionesMetodo = InstruccionesMetodo;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        for(var i : this.InstruccionesMetodo){

            var resultado = i.interpretar(arbol, tabla);
            
            if(resultado instanceof Errores){
                return resultado;
            }
        }
        return null;
    }

}
