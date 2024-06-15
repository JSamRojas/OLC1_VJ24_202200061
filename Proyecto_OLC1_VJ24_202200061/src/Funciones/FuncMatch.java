
package Funciones;

import Abstracto.Instruccion;
import java.util.LinkedList;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class FuncMatch extends Instruccion {
    
    private Instruccion condicion;
    private LinkedList<CasosMatch> Casos;

    public FuncMatch(Instruccion condicion, LinkedList<CasosMatch> Casos, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.condicion = condicion;
        this.Casos = Casos;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var Cond = this.condicion.interpretar(arbol, tabla);
        
        if(Cond instanceof Errores){
            return Cond;
        }
        
        var newTabla = new TablaSimbolos(tabla);
        
        for(var cases : this.Casos ){
            
            if(cases.EsCaso){
                
                var caso = cases.interpretar(arbol, tabla);
                
                if(this.condicion.tipo.getTipo() == cases.tipo.getTipo()){
                    
                    if(caso == Cond){
                    
                        for(var inst : cases.InstruccionesCaso){

                            var resultado = inst.interpretar(arbol, newTabla);

                            if(resultado instanceof Errores){
                                arbol.errores.add((Errores) resultado);
                            } 

                        }
                        return null;
                    
                    }
                    
                } else {
                    return new Errores("SEMANTICO", "LOS TIPOS DE LAS EXPRESIONES NO SON IGUALES", this.linea, this.columna);
                }

            } else {
                
                for(var inst : cases.InstruccionesCaso){
                    
                    var resultado = inst.interpretar(arbol, newTabla);
                    
                    if(resultado instanceof Errores){
                        arbol.errores.add((Errores) resultado);
                    } 
                    
                }
                
            }
        }
        
        return null;
    }

}
