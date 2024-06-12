
package Expresiones;

import Abstracto.Instruccion;
import Simbolo.Arbol;
import Simbolo.Tipo;
import Simbolo.TablaSimbolos;

/**
 *
 * @author Rojas
 */
public class Nativo extends Instruccion{
    
    public Object valor;
    
    public Nativo(Object valor, Tipo tipo, int linea, int columna){
        super(tipo, linea, columna);
        this.valor = valor;
    }
    
    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla){
        return this.valor;      
    }
    
}
