
package Funciones;

import Abstracto.Instruccion;
import Simbolo.Arbol;
import Simbolo.DatoNativo;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;
import java.util.ArrayList;

/**
 *
 * @author Rojas
 */
public class AppendLista extends Instruccion {
    
    private String ID;
    private Instruccion expresion;

    public AppendLista(String ID, Instruccion expresion, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.ID = ID;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        var valor = tabla.getVariable(this.ID);
        if(valor == null){
            return new Errores("SEMANTICA", "La variable " + this.ID + " no existe",this.linea, this.columna);
        }
        
        if(valor.getTipoEstruct().equals("Lista Dinamica")){
            
            // INTERPRETAR EL NUEVO VALOR
            var nuevoValor = this.expresion.interpretar(arbol, tabla);

            if(nuevoValor instanceof Errores){
                return nuevoValor;
            }
                
            //SE VALIDA QUE EL TIPO DE LA EXPRESION SEA IGUAL AL TIPO DE LA VARIABLE
            if(this.expresion.tipo.getTipo() != valor.getTipo().getTipo()){
                return new Errores("SEMANTICO", "La Lista tiene un tipo " + valor.getTipo().getTipo().toString() + 
                " y se intento asignar un valor de tipo " + this.expresion.tipo.getTipo().toString(), this.linea, this.columna);
            }
            
            var newArray = ((ArrayList) valor.getValor());
            newArray.add(nuevoValor);
            valor.setValor(newArray);
            
        } else {
            return new Errores("SEMANTICA", "El unico tipo de variable a la que se le puede realizar Append, es a las LISTAS",this.linea, this.columna);
        }
        
        return null;
    }
    
    
    
}
