
package Funciones;

import Abstracto.Instruccion;
import java.util.LinkedList;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class FuncIf extends Instruccion {
    
    private Instruccion condicion;
    private LinkedList<Instruccion> InstruccionesIF;
    private LinkedList<Instruccion> InstruccionesELSE;

    public FuncIf(Instruccion condicion, LinkedList<Instruccion> InstruccionesIF, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.condicion = condicion;
        this.InstruccionesIF = InstruccionesIF;
    }

    public FuncIf(Instruccion condicion, LinkedList<Instruccion> InstruccionesIF, LinkedList<Instruccion> InstruccionesELSE, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.condicion = condicion;
        this.InstruccionesIF = InstruccionesIF;
        this.InstruccionesELSE = InstruccionesELSE;
    }
    
    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var cond = this.condicion.interpretar(arbol, tabla);
        if(cond instanceof Errores){
            return cond;
        }
        
        if(this.condicion.tipo.getTipo() != DatoNativo.BOOLEANO){
            return new Errores("SEMANTICO", "LA CONDICION DEL IF DEBE SER DE TIPO BOOLEANO",this.linea, this.columna);
        }
        
        var newTabla = new TablaSimbolos(tabla);
        if((boolean) cond){
            for (var i : this.InstruccionesIF){
                
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
        } else {
            
            if(this.InstruccionesELSE != null){
                
                for (var i : this.InstruccionesELSE){
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
            }
            
        }
        return null;
    } 
}
