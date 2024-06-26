
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
        newTabla.setNombre(tabla.getNombre() + " - MATCH");
        
        for(var cases : this.Casos ){
            
            if(cases.EsCaso){
                
                var caso = cases.interpretar(arbol, tabla);
                
                if(this.condicion.tipo.getTipo() == cases.tipo.getTipo()){
                    
                    if(caso == Cond){
                    
                        for(var inst : cases.InstruccionesCaso){
                            
                            if(inst instanceof DeclaracionVar){
                                ((DeclaracionVar) inst).setEntorno(newTabla.getNombre());
                            }
                
                            if(inst == null){
                                return null;
                            }

                            var resultado = inst.interpretar(arbol, newTabla);

                            if(resultado instanceof Errores){
                                return resultado;
                            } 

                        }
                        return null;
                    }
                    
                } else {
                    return new Errores("SEMANTICO", "la condicion del match es de tipo " + this.condicion.tipo.getTipo().toString() + " y el tipo del case es " + cases.tipo.getTipo().toString() , this.linea, this.columna);
                }

            } else {
                
                for(var inst : cases.InstruccionesCaso){
                    
                    if(inst instanceof DeclaracionVar){
                        ((DeclaracionVar) inst).setEntorno(newTabla.getNombre());
                    }
                
                    if(inst == null){
                        return null;
                    }
                    
                    var resultado = inst.interpretar(arbol, newTabla);
  
                    if(resultado instanceof Errores){
                        return resultado;
                    }   
                }                
            }
        }
        return null;
    }
}
