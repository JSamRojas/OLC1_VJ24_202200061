
package Funciones;

import Abstracto.Instruccion;
import Expresiones.Return;
import Simbolo.Arbol;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Rojas
 */
public class FuncFunciones extends Instruccion {
    
    public String ID;
    public LinkedList<Instruccion> InstruccionesFuncion;
    public LinkedList<HashMap> parametros;

    public FuncFunciones(String ID, LinkedList<Instruccion> InstruccionesFuncion, LinkedList<HashMap> parametros, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.ID = ID;
        this.InstruccionesFuncion = InstruccionesFuncion;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        for(var i : this.InstruccionesFuncion){
            
            if(i instanceof Return ret){
                if(ret.tablaEntorno == null){
                    ret.setTablaEntorno(tabla);
                }
                return ret;
            }
            
            var resultado = i.interpretar(arbol, tabla);
            
            if(resultado instanceof Errores){
                return resultado;
            }
            
            if(resultado instanceof Return){
                return resultado;
            }
            
        }
        return null;
    }

}
