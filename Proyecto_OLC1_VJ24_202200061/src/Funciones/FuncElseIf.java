
package Funciones;

import Abstracto.Instruccion;
import java.util.LinkedList;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class FuncElseIf extends Instruccion {
    
    private Instruccion condicion;
    private Instruccion NextIf;
    private LinkedList<Instruccion> InstruccionesIF;

    public FuncElseIf(Instruccion condicion, Instruccion NextIf, LinkedList<Instruccion> InstruccionesIF, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.condicion = condicion;
        this.NextIf = NextIf;
        this.InstruccionesIF = InstruccionesIF;
    }
    
    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var cond = this.condicion.interpretar(arbol, tabla);
        if(cond instanceof Errores){
            return cond;
        }
        
        if(this.condicion.tipo.getTipo() != DatoNativo.BOOLEANO){
            return new Errores("SEMANTICO", "La condicion del if debe ser tipo booleano, no de tipo " + this.condicion.tipo.getTipo().toString(),this.linea, this.columna);
        }
        
        var newTabla = new TablaSimbolos(tabla);
        if((boolean) cond){
            for (var i : this.InstruccionesIF){
                
                if(i instanceof DeclaracionVar){
                    ((DeclaracionVar) i).setEntorno(newTabla.getNombre());
                }
                
                if(i == null){
                    return null;
                }
                
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
            this.NextIf.interpretar(arbol, tabla);     
        }
        return null;
    }
    
}
