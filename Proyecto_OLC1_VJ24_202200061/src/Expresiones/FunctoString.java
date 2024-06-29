
package Expresiones;

import Abstracto.Instruccion;
import Funciones.Errores;
import Simbolo.Arbol;
import Simbolo.DatoNativo;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Rojas
 */
public class FunctoString extends Instruccion {
    
    private Instruccion expresion;

    public FunctoString(Instruccion expresion, int linea, int columna) {
        super(new Tipo(DatoNativo.CADENA), linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        String nombreStruct = "";
        
        if(this.expresion == null){
            return new Errores("SEMANTICO", "Debe ingresar un valor en la funcion toString", this.linea, this.columna);
        }
        
        var valor = this.expresion.interpretar(arbol, tabla);
        
        if(valor instanceof Errores){
            return valor;
        }
        
        if(this.expresion instanceof AccesoVariable Acces){
            nombreStruct = Acces.ID;
        }
        
        var variable = tabla.getVariable(nombreStruct);
        
        switch(this.expresion.tipo.getTipo()) {
            
            case ENTERO -> {
                return valor.toString();
            }
            case DECIMAL -> {
                return valor.toString();
            }
            case CARACTER -> {
                return valor.toString();
            }
            case BOOLEANO -> {
                return valor.toString();
            }
            case STRUCT -> {
                
                String cadena = "";
                cadena += variable.getTipoEstruct().toString() + " { ";
                
                if(valor instanceof LinkedList lista){
                    
                    for(int i = 0; i < lista.size(); i++){
                        
                        if(i != 0){
                            cadena += ",";
                        }
                        
                        var hash = lista.get(i);
                        
                        if(hash instanceof HashMap hashito){
                            
                            cadena += hashito.get("id").toString() + ":" + hashito.get("valor").toString();               
                        }  
                    }    
                     cadena += " }";   
                }
                return cadena;
            }
            default -> {
                return new Errores("SEMANTICO", "No se puede realizar la funcion toString a una CADENA", this.linea, this.columna);
            }
        } 
    }
}
