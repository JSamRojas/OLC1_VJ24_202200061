
package Funciones;

import Abstracto.Instruccion;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class Break extends Instruccion {
    
    public Break(int linea, int columna){
        super(new Tipo(DatoNativo.VOID), linea, columna);
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        return null;
    }   
}