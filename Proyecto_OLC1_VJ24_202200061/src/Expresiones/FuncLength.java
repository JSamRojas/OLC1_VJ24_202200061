
package Expresiones;

import Abstracto.Instruccion;
import Funciones.Errores;
import Simbolo.Arbol;
import Simbolo.DatoNativo;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;
import java.util.ArrayList;

/**
 *
 * @author Rojas
 */
public class FuncLength extends Instruccion {
    
    private Instruccion expresion;

    public FuncLength(Instruccion expresion, int linea, int columna) {
        super(new Tipo(DatoNativo.ENTERO), linea, columna);
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
        
        if(valor instanceof ArrayList array){
            return array.size();     
        } else if (this.expresion.tipo.getTipo() == DatoNativo.CADENA) {
            return valor.toString().length();
        } else {
            return new Errores("SEMANTICO", "La funcion LENGTH solamente funciona con vectores, listas y CADENAS", this.linea, this.columna);
        }  
    }
}
