
package Expresiones;

import Abstracto.Instruccion;
import Funciones.Errores;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class Logicas extends Instruccion {
    
    private Instruccion operando1;
    private Instruccion operando2;
    private Instruccion operandoUnico;
    private OperadoresLogicos operacion;

    public Logicas(Instruccion operandoUnico, OperadoresLogicos operacion, int linea, int columna) {
        super(new Tipo(DatoNativo.BOOLEANO), linea, columna);
        this.operandoUnico = operandoUnico;
        this.operacion = operacion;
    }
    
    public Logicas(Instruccion operando1, Instruccion operando2, OperadoresLogicos operacion, int linea, int columna) {
        super(new Tipo(DatoNativo.BOOLEANO), linea, columna);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }
    
    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        Object opIzq = null, opDer = null, Unico = null;
        if(this.operandoUnico != null){
            Unico = this.operandoUnico.interpretar(arbol, tabla);
            if(Unico instanceof Errores){
                return Unico;
            }
        } else {  
            opIzq = this.operando1.interpretar(arbol, tabla);
            if(opIzq instanceof Errores){
                return opIzq;
            }
            opDer = this.operando2.interpretar(arbol, tabla);
            if(opDer instanceof Errores){
                return opDer;
            }
        }
        
        return switch(operacion) {
            case OR -> 
                this.or(opIzq, opDer);
            case AND ->
                this.and(opIzq,opDer);
            case XOR ->
                this.xor(opIzq, opDer);
            case NOT ->
                this.not(Unico);
            default -> 
                new Errores("SEMANTICO", "OPERADOR INVALIDO", this.linea, this.columna);
        };
    }
    
    public Object or(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case BOOLEANO -> {
                switch (tipo2) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((boolean) op1 || (boolean) op2){
                            return true;
                        } else {
                            return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Comparacion Erronea", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Comparacion Erronea", this.linea, this.columna);
            }
        }
    }
    
    public Object and(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case BOOLEANO -> {
                switch (tipo2) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((boolean) op1 && (boolean) op2){
                            return true;
                        } else {
                            return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Comparacion Erronea", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Comparacion Erronea", this.linea, this.columna);
            }
        }
    }
    
    public Object xor(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case BOOLEANO -> {
                switch (tipo2) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((boolean) op1 || (boolean) op2){
                            return false;
                        } else {
                            return true;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Comparacion Erronea", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Comparacion Erronea", this.linea, this.columna);
            }
        }
    }
    
    public Object not(Object op1){
        var opU = this.operandoUnico.tipo.getTipo();
        
        switch (opU) {
            case BOOLEANO -> {
                this.tipo.setTipo(DatoNativo.BOOLEANO);
                if((boolean) op1 == true){
                    return false;
                } else {
                    return true;
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Comparacion Erronea", this.linea, this.columna);
            }
        }
    }
}
