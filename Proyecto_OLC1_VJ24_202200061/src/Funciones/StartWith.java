
package Funciones;

import Abstracto.Instruccion;
import Expresiones.TipoMutabilidad;
import Simbolo.Arbol;
import Simbolo.*;
import java.util.LinkedList;

/**
 *
 * @author Rojas
 */
public class StartWith extends Instruccion {
    
    private String ID;
    private LinkedList<Instruccion> parametros;

    public StartWith(String ID, LinkedList<Instruccion> parametros, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.ID = ID;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var busqueda = arbol.getFuncMetod(ID);
        
        if(busqueda == null){
            return new Errores("SEMANTICO", "La funcion con el nombre " + this.ID + " no existe", this.linea, this.columna);
        }
        
        if(busqueda instanceof Metodos metodo){
            var newTabla = new TablaSimbolos(arbol.getTablaGlobal());
            newTabla.setNombre("GLOBAL - METODO");
            
            if(metodo.parametros.size() != this.parametros.size()){
                return new Errores("SEMANTICO", "La cantidad de parametros no coinciden", this.linea, this.columna);
            }
            
            for(int i = 0; i < this.parametros.size(); i++){
                var identificador = (String) metodo.parametros.get(i).get("id");
                var valor = this.parametros.get(i);
                var tipo2 = (Tipo) metodo.parametros.get(i).get("tipo");
                
                var DeclararParams = new DeclaracionVar(identificador, valor, TipoMutabilidad.CONST, tipo2, this.linea, this.columna);
                
                var declaro = DeclararParams.interpretar(arbol, newTabla);
                
                if(declaro instanceof Errores){
                    return declaro;
                }

            }
            
            var resultadoFuncion = metodo.interpretar(arbol, newTabla);
                if(resultadoFuncion instanceof Errores){
                    return resultadoFuncion;
            }  
        }
        return null;    
    }
}
