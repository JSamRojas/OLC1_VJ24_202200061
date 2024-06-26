
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
public class AccesoVector extends Instruccion {
    
    private String ID;
    private Instruccion X;
    private Instruccion Y;

    public AccesoVector(String ID, Instruccion X, Instruccion Y, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.ID = ID;
        this.X = X;
        this.Y = Y;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        var valor = tabla.getVariable(this.ID);
        if(valor == null){
            return new Errores("SEMANTICA", "La variable " + this.ID + " no existe",this.linea, this.columna);
        }
        
        if(valor.getTipoEstruct().equals("Arreglo (1 Dimension)") || valor.getTipoEstruct().equals("Lista Dinamica")){
            
            if(this.Y == null){
                
                var valorX = this.X.interpretar(arbol, tabla);
                
                if(valorX instanceof Errores){
                    return valorX;
                }
                
                if(this.X.tipo.getTipo() != DatoNativo.ENTERO){
                    return new Errores("SEMANTICA", "El parametro de acceso debe ser de tipo ENTERO",this.linea, this.columna);
                }
                
                var variable = valor.getValor();
                this.tipo.setTipo(valor.getTipo().getTipo());
                
                if((int) valorX >= 0 && (int) valorX < ((ArrayList) variable).size()){ 
                    return ((ArrayList) variable).get((int) valorX);      
                } else {          
                    return new Errores("SEMANTICA", "El parametro de acceso debe ser mayor a 0 y menor al size del vector",this.linea, this.columna); 
                }
                
            } else {
                return new Errores("SEMANTICA", "La variable " + this.ID + " es un vector de una dimension, por lo que solo se necesita un parametro",this.linea, this.columna);
            }
            
        } else if(valor.getTipoEstruct().equals("Arreglo (2 Dimensiones)")){
            
            var valorX = this.X.interpretar(arbol, tabla);
            var valorY = this.Y.interpretar(arbol, tabla);
            
            if(valorX instanceof Errores){
                return valorX;
            }
            
            if(valorY instanceof Errores){
                return valorY;
            }
            
            if(this.X.tipo.getTipo() != DatoNativo.ENTERO){
                return new Errores("SEMANTICA", "El parametro de acceso debe ser de tipo ENTERO",this.linea, this.columna);
            }
            
            if(this.Y.tipo.getTipo() != DatoNativo.ENTERO){
                return new Errores("SEMANTICA", "El parametro de acceso debe ser de tipo ENTERO",this.linea, this.columna);
            }
            
            var variable = valor.getValor();
            this.tipo.setTipo(valor.getTipo().getTipo());
            
            if((int) valorX >= 0 && (int) valorX < ((ArrayList) variable).size()){
                var listatemp = ((ArrayList) variable).get((int) valorX);
                if((int) valorY >=0 && (int) valorY < ((ArrayList) listatemp).size()){
                    return ((ArrayList) listatemp).get((int) valorY);
                }    
            } else {          
                return new Errores("SEMANTICA", "El parametro de acceso debe ser mayor a 0 y menor al size del vector",this.linea, this.columna); 
            }
            
        } else {
            return new Errores("SEMANTICA", "La variable " + this.ID + " no es de tipo Vector",this.linea, this.columna);
        }
        return null;
    }
}
