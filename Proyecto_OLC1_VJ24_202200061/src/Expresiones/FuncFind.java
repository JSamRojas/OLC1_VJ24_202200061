
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
public class FuncFind extends Instruccion {
    
    private String ID;
    private Instruccion expresion;

    public FuncFind(String ID, Instruccion expresion, int linea, int columna) {
        super(new Tipo(DatoNativo.BOOLEANO), linea, columna);
        this.ID = ID;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        var variable = tabla.getVariable(ID);
        var valorBuscado = this.expresion.interpretar(arbol, tabla);
        
        if(variable == null){
            return new Errores("SEMANTICO", "La variable con el nombre " + ID + " no existe", this.linea, this.columna);
        }
        
        if(valorBuscado instanceof Errores){
            return valorBuscado;
        }  
        
        if(variable.getTipo().getTipo() != expresion.tipo.getTipo()){
            return new Errores("SEMANTICO", "La variable es de tipo " + variable.getTipo().getTipo().toString() + 
                                   " y el valor buscado es de tipo " + this.expresion.tipo.getTipo().toString() , this.linea, this.columna);   
        }
        
        if(variable.getTipoEstruct().equals("Arreglo (1 Dimension)") || variable.getTipoEstruct().equals("Lista Dinamica")){
            if(variable.getValor() instanceof ArrayList array){
                return array.contains(valorBuscado);
            } else {
                return new Errores("SEMANTICO", "La variable solamente puede ser un vector o una lista " , this.linea, this.columna); 
            } 
        } else {
            return new Errores("SEMANTICO", "La variable debe ser un vector de 1 dimension o una lista dinamica", this.linea, this.columna);
        }
        
    }
}
