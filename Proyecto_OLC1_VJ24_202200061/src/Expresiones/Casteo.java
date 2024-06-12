
package Expresiones;

import Abstracto.Instruccion;
import Funciones.Errores;
import Simbolo.*;

/**
 *
 * @author Rojas
 */
public class Casteo extends Instruccion {
    
    private DatoNativo TipoDatoCasteo;
    private Instruccion operando1;

    public Casteo(Instruccion operando1, DatoNativo TipoDatoCasteo, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.TipoDatoCasteo = TipoDatoCasteo;
        this.operando1 = operando1;
    }
    
    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla){
        Object opU = null;
        opU = this.operando1.interpretar(arbol, tabla);
        if(opU instanceof Errores){
            return opU;
        }
        
        return switch (TipoDatoCasteo){
            case ENTERO ->
                this.entero(opU);
            case DECIMAL ->
                this.decimal(opU);
            case CARACTER -> 
                this.caracter(opU);
            default ->
                new Errores("SEMANTICO", "OPERADOR INVALIDO", this.linea, this.columna);
        };
    }
    
    public Object entero(Object opU){
        var tipoUnico = this.operando1.tipo.getTipo();
        
        switch(tipoUnico) {
            case DECIMAL -> {
                this.tipo.setTipo(DatoNativo.ENTERO);
                double opUdec = (double) opU;
                int opUint = (int) Math.floor(opUdec);
                return opUint;
            }
            case CARACTER -> {
                this.tipo.setTipo(DatoNativo.ENTERO);
                int AsciiValue = (char) opU;
                return AsciiValue;
            }
            default -> {
                return new Errores("SEMANTICO", "Casteo Erroneo", this.linea, this.columna);
            }
        }
    }
    
    public Object decimal(Object opU){
        var tipoUnico = this.operando1.tipo.getTipo();
        
        switch(tipoUnico) {
            case ENTERO -> {
                this.tipo.setTipo(DatoNativo.DECIMAL);
                double opUdec = (int) opU;
                return opUdec;
            }
            case CARACTER -> {
                this.tipo.setTipo(DatoNativo.DECIMAL);
                int AsciiValue = (char) opU;
                Integer Asciidec = AsciiValue;
                Double opUdec = Asciidec.doubleValue();
                return opUdec;
            } 
            default -> {
                return new Errores("SEMANTICO", "Casteo Erroneo", this.linea, this.columna);
            }
        }
    }
    
    public Object caracter(Object opU){
        var tipoUnico = this.operando1.tipo.getTipo();
        
        switch(tipoUnico) {
            case ENTERO -> {
                this.tipo.setTipo(DatoNativo.CARACTER);
                int AsciiValue = (int) opU;
                char opUchar = (char) AsciiValue;
                return opUchar;
            }
            default -> {
                return new Errores("SEMANTICO", "Casteo Erroneo", this.linea, this.columna);
            }
        }
    }
}
