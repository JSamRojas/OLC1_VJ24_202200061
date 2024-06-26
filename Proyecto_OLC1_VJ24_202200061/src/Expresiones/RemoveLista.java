
package Expresiones;

import Abstracto.Instruccion;
import Funciones.Errores;
import Simbolo.Arbol;
import Simbolo.DatoNativo;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;
import java.util.ArrayList;

/**
 *
 * @author Rojas
 */
public class RemoveLista extends Instruccion {
    
    private String ID;
    private Instruccion posicion;

    public RemoveLista(String ID, Instruccion posicion, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.ID = ID;
        this.posicion = posicion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        var valor = tabla.getVariable(this.ID);
        if(valor == null){
            return new Errores("SEMANTICA", "La variable " + this.ID + " no existe",this.linea, this.columna);
        }
        
        if(valor.getTipoEstruct().equals("Lista Dinamica")){
            
            if(this.posicion == null){
                return new Errores("SEMANTICA", "El parametro de acceso debe ser de tipo ENTERO",this.linea, this.columna);
            }
            
            var pos = this.posicion.interpretar(arbol, tabla);
            
            if(pos instanceof Errores){
                return pos;
            }
            
            if(this.posicion.tipo.getTipo() != DatoNativo.ENTERO){
                return new Errores("SEMANTICA", "El parametro de acceso debe ser de tipo ENTERO",this.linea, this.columna);
            }
            
            var variable = valor.getValor();
            this.tipo.setTipo(valor.getTipo().getTipo());
            
            if((int) pos >= 0 && (int) pos < ((ArrayList) variable).size()){
                var arrTemp = ((ArrayList) variable);
                var elem = arrTemp.get((int) pos);
                arrTemp.remove((int) pos);
                valor.setValor(arrTemp);
                return elem;
            } else {
                return new Errores("SEMANTICA", "El parametro de acceso debe ser mayor a 0 y menor al size del vector",this.linea, this.columna); 
            }
            
        } else {
            return new Errores("SEMANTICA", "No se puede realizar un remove a una variable que no sea LISTA DINAMICA",this.linea, this.columna);
        }
    }
}
