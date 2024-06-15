
package Funciones;

import Abstracto.Instruccion;
import java.util.LinkedList;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class FuncDoWhile extends Instruccion {
    
    private Instruccion expresion;
    private LinkedList<Instruccion> InstruccionesWHILE;

    public FuncDoWhile(Instruccion expresion, LinkedList<Instruccion> InstruccionesWHILE, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.expresion = expresion;
        this.InstruccionesWHILE = InstruccionesWHILE;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var cond = this.expresion.interpretar(arbol, tabla);
        if(cond instanceof Errores){
            return cond;
        }
        
        if(this.expresion.tipo.getTipo() != DatoNativo.BOOLEANO){
            return new Errores("SEMANTICO", "LA CONDICION DEL IF DEBE SER DE TIPO BOOLEANO",this.linea, this.columna);
        }
        
        var newTabla = new TablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre() + " - DO WHILE");
        
        do {
            
            for (var i : this.InstruccionesWHILE){
                
                if (i instanceof Break) {
                    return i;
                }
                
                var resultado = i.interpretar(arbol, newTabla);
                
                if(resultado instanceof Errores){
                    arbol.errores.add((Errores) resultado);
                }
                
                if (resultado instanceof Break) {
                    return resultado;
                }
                
            }
            
        } while((boolean) this.expresion.interpretar(arbol, newTabla));

        return null;
    }
    
}
