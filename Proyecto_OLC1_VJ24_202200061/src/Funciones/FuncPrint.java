
package Funciones;

import Abstracto.Instruccion;
import Simbolo.*;       

/**
 *
 * @author Rojas
 */
public class FuncPrint extends Instruccion {
    
    private Instruccion expresion;
    
    public FuncPrint(Instruccion expresion, int linea, int columna){
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.expresion = expresion;
    }
    
    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla){
        var resultado = this.expresion.interpretar(arbol, tabla);
        if(resultado instanceof Errores){
            return resultado;
        }
        String consola = resultado.toString();
        consola = consola.replace("\\n", "\n"); // reemplaza la secuencia de escape \n
        consola = consola.replace("\\\\", "\\"); // reemplaza la secuencia de escape \\
        consola = consola.replace("\\\"", "\""); // reemplaza la secuencia de escape \"
        consola = consola.replace("\\t", "\t"); // reemplaza la secuencia de escape \t
        consola = consola.replace("\\'", "'"); // reemplaza la secuencia de escape \'
        arbol.Print(consola);
        return null;
    }
    
}
