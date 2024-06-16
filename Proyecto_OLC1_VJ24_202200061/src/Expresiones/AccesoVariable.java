
package Expresiones;

import Abstracto.Instruccion;
import Funciones.Errores;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class AccesoVariable extends Instruccion{
    
    private String ID;

    public AccesoVariable(String ID, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.ID = ID;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var valor = tabla.getVariable(this.ID);
        if(valor == null){
            return new Errores("SEMANTICA", "La variable " + this.ID.toString() + " no existe",this.linea, this.columna);
        }
        this.tipo.setTipo(valor.getTipo().getTipo());
        return valor.getValor();
    }

}
