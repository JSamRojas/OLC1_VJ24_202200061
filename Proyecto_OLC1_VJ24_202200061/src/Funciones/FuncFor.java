
package Funciones;

import Abstracto.Instruccion;
import java.util.LinkedList;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class FuncFor extends Instruccion {
    
    private Instruccion asignacion;
    private Instruccion condicion;
    private Instruccion actualizacion;
    private LinkedList<Instruccion> instruccionesFOR;

    public FuncFor(Instruccion asignacion, Instruccion condicion, Instruccion actualizacion, LinkedList<Instruccion> instruccionesFOR, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.asignacion = asignacion;
        this.condicion = condicion;
        this.actualizacion = actualizacion;
        this.instruccionesFOR = instruccionesFOR;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        var newTabla = new TablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre() + "-FOR");
        
        var par1 = this.asignacion.interpretar(arbol, newTabla);
        if(par1 instanceof Errores){
            return par1;
        }
        
        var cond = this.condicion.interpretar(arbol, newTabla);
        if(cond instanceof Errores){
            return cond;
        }
        
        if(this.condicion.tipo.getTipo() != DatoNativo.BOOLEANO){
            return new Errores("SEMANTICO", "LA CONDICION DEBE SER BOOLEANA", this.linea, this.columna);
        }
        
        while((boolean) this.condicion.interpretar(arbol, newTabla)){
            
            var newTabla2 = new TablaSimbolos(newTabla);
            newTabla2.setNombre(tabla.getNombre() + "-FOR");
            
            for(var i : this.instruccionesFOR){
                
                if (i instanceof Break) {
                    return null;
                }
                
                var resIns = i.interpretar(arbol, newTabla2);
                
                if(resIns instanceof Errores){
                    return resIns;
                }
                
                if (resIns instanceof Break) {
                    return null;
                }
                
            }
            
            var act = this.actualizacion.interpretar(arbol, newTabla);
            if(act instanceof Errores){
                return act;
            }
        }
        return null;
    }
    
    
    
}