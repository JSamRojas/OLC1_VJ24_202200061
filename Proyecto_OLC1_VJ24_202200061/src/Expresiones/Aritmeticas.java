
package Expresiones;

import Abstracto.Instruccion;
import Funciones.Errores;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class Aritmeticas extends Instruccion {
    
    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresAritmeticos operacion;
    private Instruccion operandoUnico;

    public Aritmeticas( Instruccion operandoUnico, OperadoresAritmeticos operacion, int linea, int columna) {
        super(new Tipo(DatoNativo.ENTERO), linea, columna);
        this.operacion = operacion;
        this.operandoUnico = operandoUnico;
    }

    public Aritmeticas(Instruccion operando1, Instruccion operando2, OperadoresAritmeticos operacion, int linea, int columna) {
        super(new Tipo(DatoNativo.ENTERO), linea, columna);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }
    
    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla){
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
        
        return switch (operacion){
            case SUMA ->
                this.suma(opIzq, opDer);
            case NEGACION ->
                this.negacion(Unico);
            case RESTA -> 
                this.resta(opIzq, opDer);
            case MULTIPLICACION ->
                this.multiplicacion(opIzq, opDer);
            case DIVISION -> 
                this.division(opIzq, opDer);
            case POTENCIA -> 
                this.potencia(opIzq, opDer);
            case MODULO -> 
                this.modulo(opIzq, opDer);
            default ->
                new Errores("SEMANTICO", "OPERADOR INVALIDO", this.linea, this.columna);
        };
        
    }
    
    public Object suma(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1){
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.ENTERO);
                        return (int) op1 + (int) op2;       
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return (int) op1 + (double) op2;  
                    }
                    case BOOLEANO -> {
                        return new Errores("SEMANTICO", "No se puede realizar una suma entre un entero y un booleano", this.linea, this.columna);
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.ENTERO);
                        int AsciiValue = (char) op2;
                        return (int) op1 + AsciiValue;
                    }
                    case CADENA -> {
                        this.tipo.setTipo(DatoNativo.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Suma erronea", this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return ((double) op1 + (int) op2);
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return (double) op1 + (double) op2;
                    }
                    case BOOLEANO -> {
                        return new Errores("SEMANTICO", "No se puede realizar una suma entre un decimal y un booleano", this.linea, this.columna);
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        int AsciiValue = (char) op2;
                        return (double) op1 + AsciiValue;
                    }
                    case CADENA -> {
                        this.tipo.setTipo(DatoNativo.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Suma Erronea", this.linea, this.columna);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipo2) {
                    case CADENA -> {
                        this.tipo.setTipo(DatoNativo.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una suma entre un booleano y otro tipo de dato", this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.ENTERO);
                        int AsciiValue = (char) op1;
                        return AsciiValue + (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        int AsciiValue = (char) op1;
                        return AsciiValue + (double) op2;
                    }
                    case BOOLEANO -> {
                        return new Errores("SEMANTICO", "No se puede realizar una suma entre un caracter y un booleano", this.linea, this.columna);
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    case CADENA -> {
                        this.tipo.setTipo(DatoNativo.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Suma Erronea", this.linea, this.columna);
                    }
                }
            }
            case CADENA -> {
                this.tipo.setTipo(DatoNativo.CADENA);
                return op1.toString() + op2.toString();
            }
            default -> {
                return new Errores("SEMANTICO", "Suma Erronea", this.linea, this.columna);
            }
        }      
    }
    
    public Object negacion(Object op1){
        var opU = this.operandoUnico.tipo.getTipo();
        switch (opU) {
            case ENTERO -> {
                this.tipo.setTipo(DatoNativo.ENTERO);
                return (int) op1 * -1;
            }
            case DECIMAL -> {
                this.tipo.setTipo(DatoNativo.DECIMAL);
                return (double) op1 * -1;
            }
            default -> {
                return new Errores("SEMANTICO", "Negacion Erronea", this.linea, this.columna);
            }
        }
    }
    
    public Object resta(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1) {
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.ENTERO);
                        return (int) op1 - (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return (int) op1 - (double) op2;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.ENTERO);
                        int AsciiValue = (char) op2;
                        return (int) op1 - AsciiValue;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Resta Erronea", this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return (double) op1 - (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return (double) op1 - (double) op2;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        int AsciiValue = (char) op2;
                        return (double) op1 - AsciiValue;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Resta Erronea", this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.ENTERO);
                        int AsciiValue = (char) op1;
                        return AsciiValue - (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        int AsciiValue = (char) op1;
                        return AsciiValue - (double) op2;
                    }
                    case CARACTER -> {
                        return new Errores("SEMANTICO", "Resta Erronea", this.linea, this.columna);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Resta Erronea", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Resta Erronea", this.linea, this.columna);
            }
        }  
    }
    
    public Object multiplicacion(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1) {
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.ENTERO);
                        return (int) op1 * (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return (int) op1 * (double) op2;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.ENTERO);
                        int AsciiValue = (char) op2;
                        return (int) op1 * AsciiValue;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Multiplicacion Erronea", this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return (double) op1 * (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return (double) op1 * (double) op2;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        int AsciiValue = (char) op2;
                        return (double) op1 * AsciiValue;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Multiplicacion Erronea", this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.ENTERO);
                        int AsciiValue = (char) op1;
                        return AsciiValue * (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        int AsciiValue = (char) op1;
                        return AsciiValue * (double) op2;
                    }
                    case CARACTER -> {
                        return new Errores("SEMANTICO", "Multiplicacion Erronea", this.linea, this.columna);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Multiplicacion Erronea", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Multiplicacion Erronea", this.linea, this.columna);
            }
        }
    }
    
    public Object division(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1) {
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        Integer op1int = (int) op1;
                        Double op1dec = op1int.doubleValue();
                        return (op1dec / (int) op2);
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return (int) op1 / (double) op2;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        int AsciiValue = (char) op2;
                        Integer op1int = (int) op1;
                        Double op1dec = op1int.doubleValue();
                        return op1dec / AsciiValue;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Division Erronea", this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return (double) op1 / (int) op2;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return (double) op1 / (double) op2;
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        int AsciiValue = (char) op2;
                        return (double) op1 / AsciiValue;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Division Erronea", this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        int AsciiValue = (char) op1;
                        Integer op2Int = (int) op2;
                        Double op2dec = op2Int.doubleValue();
                        return AsciiValue / op2dec;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        int AsciiValue = (char) op1;
                        return AsciiValue / (double) op2;
                    }
                    case CARACTER -> {
                        return new Errores("SEMANTICO", "Division Erronea", this.linea, this.columna);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Division Erronea", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Division Erronea", this.linea, this.columna);
            }
        }
    }
    
    public Object potencia(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1) {
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.ENTERO);
                        double resultdec = Math.pow((int) op1, (int) op2);
                        int resultadoint = (int) resultdec;
                        return resultadoint;
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return Math.pow((int) op1, (double) op2);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Potencia Erronea", this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return Math.pow((double) op1, (int) op2);
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return Math.pow((double) op1, (double) op2);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Potencia Erronea", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Potencia Erronea", this.linea, this.columna);
            }
        }
    }
    
    public Object modulo(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1) {
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        Integer op1int = (int) op1;
                        Double op1dec = op1int.doubleValue();
                        return (op1dec % (double) op2);
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return ((int) op1 % (double) op2);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Potencia Erronea", this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return ((double) op1 % (int) op2);
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.DECIMAL);
                        return ((double) op1 % (double) op2);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Potencia Erronea", this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Potencia Erronea", this.linea, this.columna);
            }
        }
    }
    
}
