
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
            return new Errores("SEMANTICO", "La condicion del if debe ser tipo booleano, no de tipo " + this.condicion.tipo.getTipo().toString(),this.linea, this.columna);
        }
        
        var newTabla = new TablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre() + "- IF");
        if((boolean) cond){
            for (var i : this.InstruccionesIF){
                
                if(i instanceof DeclaracionVar){
                    ((DeclaracionVar) i).setEntorno(newTabla.getNombre());
                }
                
                if(i == null){
                    return null;
                }
                
                if (i instanceof Continue) {
                    return i;
                }
                
                if (i instanceof Break) {
                    return i;
                }
                
                var resultado = i.interpretar(arbol, newTabla);
                
                if(resultado instanceof Errores){
                    return resultado;
                }
                
                if (resultado instanceof Break) {
                    return resultado;
                }
                
                if (resultado instanceof Continue) {
                    return resultado;
                }
                
            }
        } else {
            
            if(this.InstruccionesELSE != null){
                
                for (var i : this.InstruccionesELSE){
                    
                    if(i instanceof DeclaracionVar){
                        ((DeclaracionVar) i).setEntorno(newTabla.getNombre());
                    }
                
                    if(i == null){
                        return null;
                    }
                    
                    if (i instanceof Break) {
                        return i;
                    }
                    
                    if(i instanceof Continue){
                        return i;
                    }
                
                    var resultado = i.interpretar(arbol, newTabla);
                    
                    if(resultado == null){
                        return null;
                    }

                    if(resultado instanceof Errores){
                        return resultado;
                    }

                    if (resultado instanceof Break) {
                        return resultado;
                    }
                    
                    if (resultado instanceof Continue) {
                        return resultado;
                    }
                }
            }
            
        }
        return null;
    } 
}
