
package Expresiones;

import Abstracto.Instruccion;
import Funciones.Errores;
import Simbolo.Arbol;
import Simbolo.DatoNativo;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;

/**
 *
 * @author Rojas
 */
public class FuncRound extends Instruccion {
    
    private Instruccion expresion;

    public FuncRound(Instruccion expresion, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        if(this.expresion == null){
            return new Errores("SEMANTICO", "Debe ingresar un valor en la funcion ROUND", this.linea, this.columna);
        }
        
        var valor = this.expresion.interpretar(arbol, tabla);
        
        if(valor instanceof Errores){
            return valor;
        }
        
        if(this.expresion.tipo.getTipo() != DatoNativo.DECIMAL){
            return new Errores("SEMANTICO", "Se intento redondear un valor de tipo " + this.expresion.tipo.getTipo(), this.linea, this.columna);
        }
        
        this.tipo.setTipo(DatoNativo.ENTERO);
        return Math.round((double) valor);
        
    }
  
}
