
package Funciones;

import Abstracto.Instruccion;
import Simbolo.*;
import java.util.ArrayList;

/**
 *
 * @author Rojas
 */
public class ModificacionArr extends Instruccion {
    
    private String ID;
    private Instruccion valorVec;
    private Instruccion X;
    private Instruccion Y;

    public ModificacionArr(String ID, Instruccion valorVec, Instruccion X, Instruccion Y, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.ID = ID;
        this.valorVec = valorVec;
        this.X = X;
        this.Y = Y;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        // COMPRUEBA QUE LA VARIABLE EXISTA
        var variable = tabla.getVariable(this.ID);
        if(variable == null){
            return new Errores("SEMANTICO", "La variable con identificador " + this.ID + " no existe", this.linea, this.columna);
        }
        
        // VALIDAMOS QUE LA VARIABLE PUEDA SER MODIFICADA
        if(!variable.getMutabilidad()){
            return new Errores("SEMANTICO", "La variable " + this.ID + " es de tipo const, por lo que no se puede modificar", this.linea, this.columna);
        }
        
        if(variable.getTipoEstruct().equals("Arreglo (1 Dimension)") || variable.getTipoEstruct().equals("Lista Dinamica")){
            
            if(this.Y == null){
                
                if(this.X == null){
                    return new Errores("SEMANTICO", "La Posicion ingresada para acceder a un valor, debe ser de tipo ENTERO", this.linea, this.columna);
                }
                
                var Xvalor = this.X.interpretar(arbol, tabla);
            
                if(Xvalor instanceof Errores){
                    return Xvalor;
                }
                
                // VALIDAMOS QUE EL DATO INGRESADO PARA ACCEDER SEA DE TIPO ENTERO
                if(this.X.tipo.getTipo() != DatoNativo.ENTERO){
                    return new Errores("SEMANTICO", "La Posicion ingresada para acceder a un valor, debe ser de tipo ENTERO", this.linea, this.columna);
                }
                
                // INTERPRETAR EL NUEVO VALOR
                var nuevoValor = this.valorVec.interpretar(arbol, tabla);

                if(nuevoValor instanceof Errores){
                    return nuevoValor;
                }
                
                //SE VALIDA QUE EL TIPO DE LA EXPRESION SEA IGUAL AL TIPO DE LA VARIABLE
                if(variable.getTipo().getTipo() != this.valorVec.tipo.getTipo()){
                    return new Errores("SEMANTICO", "La variable tiene un tipo " + variable.getTipo().getTipo().toString() + 
                    " y se intento asignar un valor de tipo " + this.valorVec.tipo.getTipo().toString(), this.linea, this.columna);
                }
                
                if(variable.getValor() instanceof ArrayList arreglo){
                    if((int) Xvalor >= 0 && (int) Xvalor < arreglo.size()){                 
                        arreglo.set((int) Xvalor, nuevoValor);
                        variable.setValor(arreglo);
                    } else {
                        return new Errores("SEMANTICO", "La posicion " + Xvalor.toString() + " es mayor al tamaño del vector o menor a 0 " + ID , this.linea, this.columna);
                    }
                } 
                
            } else {
                return new Errores("SEMANTICA", "La variable " + this.ID + " es un vector de una dimension, por lo que solo se necesita un parametro",this.linea, this.columna);
            }
            
                
        } else if(variable.getTipoEstruct().equals("Arreglo (2 Dimensiones)")){
            
            if(this.X == null){
                return new Errores("SEMANTICO", "La Posicion ingresada para acceder a un valor, debe ser de tipo ENTERO", this.linea, this.columna);
            }
            
            if(this.Y == null){
                return new Errores("SEMANTICO", "La Posicion ingresada para acceder a un valor, debe ser de tipo ENTERO", this.linea, this.columna);
            }
            
            var Xvalor = this.X.interpretar(arbol, tabla);
            var Yvalor = this.Y.interpretar(arbol, tabla);

            if(Xvalor instanceof Errores){
                return Xvalor;
            }

            if(Yvalor instanceof Errores){
                return Yvalor;
            }
            
            // VALIDAMOS QUE LOS DATOS INGRESADOS PARA ACCEDER SEAN DE TIPO ENTERO
            if(this.X.tipo.getTipo() != DatoNativo.ENTERO){
                return new Errores("SEMANTICO", "La Posicion ingresada para acceder a un valor, debe ser de tipo ENTERO", this.linea, this.columna);
            }
            
            if(this.Y.tipo.getTipo() != DatoNativo.ENTERO){
                return new Errores("SEMANTICO", "La Posicion ingresada para acceder a un valor, debe ser de tipo ENTERO", this.linea, this.columna);
            }
            
            // INTERPRETAR EL NUEVO VALOR
            var nuevoValor = this.valorVec.interpretar(arbol, tabla);
            if(nuevoValor instanceof Errores){
                return nuevoValor;
            }
            
            //SE VALIDA QUE EL TIPO DE LA EXPRESION SEA IGUAL AL TIPO DE LA VARIABLE
            if(variable.getTipo().getTipo() != this.valorVec.tipo.getTipo()){
                return new Errores("SEMANTICO", "La variable tiene un tipo " + variable.getTipo().getTipo().toString() + 
                " y se intento asignar un valor de tipo " + this.valorVec.tipo.getTipo().toString(), this.linea, this.columna);
            }
            
            if(variable.getValor() instanceof ArrayList arreglo){
                if((int) Xvalor >= 0 && (int) Xvalor < arreglo.size()){
                    var listaTemp = arreglo.get((int) Xvalor);
                    if((int) Yvalor >= 0 && (int) Yvalor < ((ArrayList) listaTemp).size()){
                        if(listaTemp instanceof ArrayList arrTemp){
                            arrTemp.set((int) Yvalor, nuevoValor);
                            arreglo.set((int) Xvalor, arrTemp);
                            variable.setValor(arreglo);
                        }
                    } else {
                        return new Errores("SEMANTICO", "El parametro de acceso debe ser mayor a 0 y menor al size del vector" , this.linea, this.columna);
                    }     
                } else {
                    return new Errores("SEMANTICO", "El parametro de acceso debe ser mayor a 0 y menor al size del vector" + ID , this.linea, this.columna);
                }
            }

        } else {
            return new Errores("SEMANTICO", "El tipo de variable, debe ser vector" + ID , this.linea, this.columna);
        }
        return null;
    }
 
}
