
package Funciones;

import Abstracto.Instruccion;
import Simbolo.*;
import Expresiones.ModificadoresVar;
import Simbolo.DatoNativo;

/**
 *
 * @author Rojas
 */
public class ModificacionVar extends Instruccion {
    
    private String ID;
    private Instruccion expresion;
    private ModificadoresVar modificador;

    public ModificacionVar(String ID, Instruccion expresion, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.ID = ID;
        this.expresion = expresion;
    }

    public ModificacionVar(String ID, ModificadoresVar modificador, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.ID = ID;
        this.modificador = modificador;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        //COMPRUEBA QUE LA VARIABLE EXISTA
        var variable = tabla.getVariable(ID);
        if(variable == null){
            return new Errores("SEMANTICO", "LA VARIABLE NO EXISTE", this.linea, this.columna);
        }
        
        // IF PARA SABER SI LA MODIFICACION ES NORMAL, O ES DE AUMENTO - DECREMENTO
        if (this.expresion != null){
            
            //INTERPRETAR EL NUEVO VALOR
            var nuevoValor = this.expresion.interpretar(arbol, tabla);
            if(nuevoValor instanceof Errores){
                return nuevoValor;
            }

            if(!variable.getMutabilidad()){
                return new Errores("SEMANTICO", "LA VARIABLE NO PUEDE SER MODIFICADA", this.linea, this.columna);
            }

            //SE VALIDA QUE EL TIPO DE LA EXPRESION SEA IGUAL AL TIPO DE LA VARIABLE
            if(variable.getTipo().getTipo() != this.expresion.tipo.getTipo()){
                return new Errores("SEMANTICO", "LA VARIABLE NO EXISTE", this.linea, this.columna);
            }

            variable.setValor(nuevoValor);   
        } else {
            var newValor = variable.getValor();
            if(variable.getTipo().getTipo() == DatoNativo.ENTERO){
                
                switch(this.modificador){
                    case AUMENTO -> {
                        newValor = (int) newValor + 1;
                    } case DECREMENTO -> {
                        newValor = (int) newValor - 1;
                    } default -> {
                        return new Errores("SEMANTICO", "OPERACION INVALIDA", this.linea, this.columna);
                    }
                }
                
            } else if (variable.getTipo().getTipo() == DatoNativo.DECIMAL){
                
                switch(this.modificador){
                    case AUMENTO -> {
                        newValor = (double) newValor + 1;
                    } case DECREMENTO -> {
                        newValor = (double) newValor - 1;
                    } default -> {
                        return new Errores("SEMANTICO", "OPERACION INVALIDA", this.linea, this.columna);
                    }
                }
                
            } else {
                return new Errores("SEMANTICO", "No se puede aumentar o decrementar una variable que no sea int o double", this.linea, this.columna);
            }
            
            if(!variable.getMutabilidad()){
                return new Errores("SEMANTICO", "LA VARIABLE NO PUEDE SER MODIFICADA", this.linea, this.columna);
            }
            
            variable.setValor(newValor);
            
        }
         
        return null;
    }
 
}
