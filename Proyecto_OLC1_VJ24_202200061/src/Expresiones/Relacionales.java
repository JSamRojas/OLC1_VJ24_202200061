
package Expresiones;

import Abstracto.Instruccion;
import Funciones.Errores;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class Relacionales extends Instruccion {
    
    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresRelacionales operacion;

    public Relacionales(Instruccion operando1, Instruccion operando2, OperadoresRelacionales operacion, int linea, int columna) {
        super(new Tipo(DatoNativo.BOOLEANO), linea, columna);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }
    
    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla){
        Object opIzq = null, opDer = null;
        opIzq = this.operando1.interpretar(arbol, tabla);
        if(opIzq instanceof Errores){
            return opIzq;
        }
        opDer = this.operando2.interpretar(arbol, tabla);
        if(opDer instanceof Errores){
            return opDer;
        }
        
        return switch(operacion){
            case IGUALACION ->
                this.igualacion(opIzq, opDer);
            case DIFERENCIA ->
                this.diferencia(opIzq, opDer);
            case MENORQUE ->
                this.menorque(opIzq, opDer);
            case MENORIGUAL ->
                this.menorigual(opIzq, opDer);
            case MAYORQUE -> 
                this.mayorque(opIzq, opDer);
            case MAYORIGUAL ->
                this.mayorigual(opIzq, opDer);
            default ->
                new Errores("SEMANTICO",  "El operador " + this.operacion.toString() + " no puede utilizar en operaciones relacionales", this.linea, this.columna);
        };
    }
    
    public Object igualacion(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1){
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((int) op1 == (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((int) op1 == (double) op2){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((int) op1 == AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una igualacion con el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((double) op1 == (int) op2){
                           return true;
                       } else {
                           return false;
                       } 
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((double) op1 == (double) op2){
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((double) op1 == AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una igualacion con el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipo2) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((boolean) op1 == (boolean) op2){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una igualacion con el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue == (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue == (double) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case CARACTER -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((char) op1 == (char) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una igualacion con el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CADENA -> {
                switch (tipo2) {
                    case CADENA -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if( op1.toString().equals(op2.toString())){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una igualacion con el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "No se puede realizar una igualacion con el tipo " + tipo1.toString(), this.linea, this.columna);
            }
        }
    } 
    
    public Object diferencia(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1){
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((int) op1 != (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((int) op1 != (double) op2){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((int) op1 != AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una diferencia entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((double) op1 != (int) op2){
                           return true;
                       } else {
                           return false;
                       } 
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((double) op1 != (double) op2){
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((double) op1 != AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una diferencia entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipo2) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((boolean) op1 != (boolean) op2){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una diferencia entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue != (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue != (double) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case CARACTER -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((char) op1 != (char) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una diferencia entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CADENA -> {
                switch (tipo2) {
                    case CADENA -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if( !op1.toString().equals(op2.toString())){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una diferencia entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "No se puede realizar una diferencia con el tipo " + tipo1.toString(), this.linea, this.columna);
            }
        }
    }
    
    public Object menorque(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1){
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((int) op1 < (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((int) op1 < (double) op2){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((int) op1 < AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((double) op1 < (int) op2){
                           return true;
                       } else {
                           return false;
                       } 
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((double) op1 < (double) op2){
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((double) op1 < AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipo2) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        boolean op1bool = (boolean) op1;
                        boolean op2bool = (boolean) op2;
                        int op1int = op1bool ? 1 : 0;
                        int op2int = op2bool ? 1 : 0;
                        if(op1int < op2int){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue < (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue < (double) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case CARACTER -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((char) op1 < (char) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CADENA -> {
                switch (tipo2) {
                    case CADENA -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if( op1.toString().length() < op2.toString().length()){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "No se puede realizar una comparacion con el tipo " + tipo1.toString(), this.linea, this.columna);
            }
        }
    }
    
    public Object menorigual(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1){
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((int) op1 <= (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((int) op1 <= (double) op2){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((int) op1 <= AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((double) op1 <= (int) op2){
                           return true;
                       } else {
                           return false;
                       } 
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((double) op1 <= (double) op2){
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((double) op1 <= AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipo2) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        boolean op1bool = (boolean) op1;
                        boolean op2bool = (boolean) op2;
                        int op1int = op1bool ? 1 : 0;
                        int op2int = op2bool ? 1 : 0;
                        if(op1int <= op2int){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue <= (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue <= (double) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case CARACTER -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((char) op1 <= (char) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CADENA -> {
                switch (tipo2) {
                    case CADENA -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if( op1.toString().length() <= op2.toString().length()){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "No se puede realizar una comparacion con el tipo " + tipo1.toString(), this.linea, this.columna);
            }
        }
    }
    
    public Object mayorque(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1){
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((int) op1 > (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((int) op1 > (double) op2){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((int) op1 > AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((double) op1 > (int) op2){
                           return true;
                       } else {
                           return false;
                       } 
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((double) op1 > (double) op2){
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((double) op1 > AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipo2) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        boolean op1bool = (boolean) op1;
                        boolean op2bool = (boolean) op2;
                        int op1int = op1bool ? 1 : 0;
                        int op2int = op2bool ? 1 : 0;
                        if(op1int > op2int){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue > (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue > (double) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case CARACTER -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((char) op1 > (char) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CADENA -> {
                switch (tipo2) {
                    case CADENA -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if( op1.toString().length() > op2.toString().length()){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "No se puede realizar una comparacion con el tipo " + tipo1.toString(), this.linea, this.columna);
            }
        }
    }
    
    public Object mayorigual(Object op1, Object op2){
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        switch (tipo1){
            case ENTERO -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((int) op1 >= (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((int) op1 >= (double) op2){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((int) op1 >= AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case DECIMAL -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((double) op1 >= (int) op2){
                           return true;
                       } else {
                           return false;
                       } 
                    }
                    case DECIMAL -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        if((double) op1 >= (double) op2){
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case CARACTER -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        int AsciiValue = (char) op2;
                        if((double) op1 >= AsciiValue){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case BOOLEANO -> {
                switch (tipo2) {
                    case BOOLEANO -> {
                        this.tipo.setTipo(DatoNativo.BOOLEANO);
                        boolean op1bool = (boolean) op1;
                        boolean op2bool = (boolean) op2;
                        int op1int = op1bool ? 1 : 0;
                        int op2int = op2bool ? 1 : 0;
                        if(op1int >= op2int){
                           return true;
                        } else {
                           return false;
                        }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CARACTER -> {
                switch (tipo2) {
                    case ENTERO -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue >= (int) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case DECIMAL -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       int AsciiValue = (char) op1;
                       if(AsciiValue >= (double) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    case CARACTER -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if((char) op1 >= (char) op2){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            case CADENA -> {
                switch (tipo2) {
                    case CADENA -> {
                       this.tipo.setTipo(DatoNativo.BOOLEANO);
                       if( op1.toString().length() >= op2.toString().length()){
                           return true;
                       } else {
                           return false;
                       }
                    }
                    default -> {
                        return new Errores("SEMANTICO", "No se puede realizar una comparacion entre el tipo " + tipo1.toString() + " y el tipo " + tipo2.toString(), this.linea, this.columna);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "No se puede realizar una comparacion con el tipo " + tipo1.toString(), this.linea, this.columna);
            }
        }
    }
}
