
package Funciones;

import Abstracto.Instruccion;
import java.util.LinkedList;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class CasosMatch extends Instruccion {
    
    private Instruccion expresion;
    public LinkedList<Instruccion> InstruccionesCaso;
    public boolean EsCaso;

    public CasosMatch(Instruccion expresion, LinkedList<Instruccion> InstruccionesCaso, boolean EsCaso, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.expresion = expresion;
        this.InstruccionesCaso = InstruccionesCaso;
        this.EsCaso = EsCaso;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {

        var Caso = this.expresion.interpretar(arbol, tabla);
        
        if(Caso instanceof Errores){
            return Caso;
        }
        
        this.tipo.setTipo(this.expresion.tipo.getTipo());
        return Caso;  
    }

}
