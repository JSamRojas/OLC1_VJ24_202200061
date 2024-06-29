
package Funciones;

import Abstracto.Instruccion;
import Expresiones.Return;
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
            return new Errores("SEMANTICO", "La condicion del do while debe ser tipo booleano, no de tipo " + this.expresion.tipo.getTipo().toString(),this.linea, this.columna);
        }
        
        var newTabla = new TablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre() + " - DO WHILE");
        
        do {
            
            var newTabla2 = new TablaSimbolos(newTabla);
            newTabla2.setNombre(newTabla.getNombre() + " - DO WHILE");
            
            for (var i : this.InstruccionesWHILE){
                
                if(i instanceof DeclaracionVar){
                    ((DeclaracionVar) i).setEntorno(newTabla2.getNombre());
                }
                
                if(i == null){
                    return null;
                }
                
                if (i instanceof Break) {
                    return null;
                }
                
                if (i instanceof Continue) {
                    break;
                }
                
                if(i instanceof Return ret){
                    ret.setTablaEntorno(newTabla2);
                    return i;
                }
                
                var resultado = i.interpretar(arbol, newTabla2);
                
                if(resultado instanceof Errores){
                    return resultado;
                }
                
                if (resultado instanceof Break) {
                    return null;
                }
                
                if(resultado instanceof Continue){
                    break;
                }
                
                if(resultado instanceof Return){
                    return resultado;
                }
                
            }
            
        } while((boolean) this.expresion.interpretar(arbol, newTabla));

        return null;
    }
    
}
