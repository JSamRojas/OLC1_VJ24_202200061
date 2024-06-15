
package Funciones;

import Abstracto.Instruccion;
import Simbolo.Arbol;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;
import Expresiones.TipoMutabilidad;

/**
 *
 * @author Rojas
 */
public class DeclaracionVar extends Instruccion {
    
    public String ID;
    public Instruccion value;
    public TipoMutabilidad Mutabilidad;

    public DeclaracionVar(String ID, Instruccion value, TipoMutabilidad Mutabilidad, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.ID = ID;
        this.value = value;
        this.Mutabilidad = Mutabilidad;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        Simbolos newSimbolo = null;
        boolean mut;
        
        if(this.Mutabilidad == TipoMutabilidad.VAR){
            mut = true;        
        } else {
            mut = false;
        }
        
        if(this.value == null){
            
            switch (this.tipo.getTipo()) {
                case ENTERO -> {
                    newSimbolo = new Simbolos(this.ID, this.tipo, 0, mut);
                } 
                case DECIMAL -> {
                    newSimbolo = new Simbolos(this.ID, this.tipo, 0.0, mut);
                }
                case CADENA -> {
                    newSimbolo = new Simbolos(this.ID, this.tipo, "",mut);
                }
                case CARACTER -> {
                    newSimbolo = new Simbolos(this.ID, this.tipo, '\u0000', mut);
                }      
                case BOOLEANO -> {
                    newSimbolo = new Simbolos(this.ID, this.tipo, true, mut);
                }
                default -> {
                    return new Errores("SEMANTICO", "LOS TIPOS DE LA VARIABLE Y EL VALOR SON DISTINTOS", this.linea, this.columna);
                }
            }
            
        } else {
            
            var ValorInterpretado = this.value.interpretar(arbol, tabla);
            
            if(ValorInterpretado instanceof Errores){
                return ValorInterpretado;
            }
            
            //SE VALIDA QUE LOS TIPOS SEAN IGUALES
        
            if(this.value.tipo.getTipo() != this.tipo.getTipo()){
                return new Errores("SEMANTICO", "LOS TIPOS DE LA VARIABLE Y EL VALOR SON DISTINTOS", this.linea, this.columna);
            }
            
            newSimbolo = new Simbolos(this.ID, this.tipo, ValorInterpretado, mut);
        }

        boolean creacion = tabla.setVariable(newSimbolo);
        
        if(!creacion){
            return new Errores("SEMANTICO", "NO SE PUDO CREAR ESTA VARIABLE PORQUE YA EXISTE", this.linea, this.columna);
        }
        
        return null;
    }

}