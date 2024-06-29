
package Expresiones;

import Abstracto.Instruccion;
import Funciones.Errores;
import Simbolo.Arbol;
import Simbolo.DatoNativo;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;

/**
 *
 * @author Rojas
 */
public class Return extends Instruccion {
    
    public Instruccion expresion;
    public TablaSimbolos tablaEntorno;

    public Return(Instruccion expresion, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.expresion = expresion;
        this.tablaEntorno = null;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        if(this.expresion == null){
            return null;
        }
        
        if(this.tablaEntorno == null){
            this.setTablaEntorno(tabla);
        }
        
        var valor = this.expresion.interpretar(arbol, this.tablaEntorno);
        
        if(valor instanceof Errores){
            return valor;
        }
        
        this.tipo.setTipo(this.expresion.tipo.getTipo());
        return valor;
    }

    public TablaSimbolos getTablaEntorno() {
        return tablaEntorno;
    }

    public void setTablaEntorno(TablaSimbolos tablaEntorno) {
        this.tablaEntorno = tablaEntorno;
    }
    
    

}
