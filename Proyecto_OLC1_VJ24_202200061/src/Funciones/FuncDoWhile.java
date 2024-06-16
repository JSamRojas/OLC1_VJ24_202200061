
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
            return new Errores("SEMANTICO", "La condicion del do while debe ser tipo booleano, no de tipo " + this.expresion.tipo.getTipo().toString(),this.linea, this.columna);
        }
        
        var newTabla = new TablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre() + " - DO WHILE");
        
        do {
            
            for (var i : this.InstruccionesWHILE){
                
                if(i instanceof DeclaracionVar){
                    ((DeclaracionVar) i).setEntorno(newTabla.getNombre());
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
                
                var resultado = i.interpretar(arbol, newTabla);
                
                if(resultado instanceof Errores){
                    return resultado;
                }
                
                if (resultado instanceof Break) {
                    return null;
                }
                
                if(resultado instanceof Continue){
                    break;
                }
                
            }
            
        } while((boolean) this.expresion.interpretar(arbol, newTabla));

        return null;
    }
    
}
