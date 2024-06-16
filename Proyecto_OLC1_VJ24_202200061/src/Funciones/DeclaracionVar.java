
package Funciones;

import Abstracto.Instruccion;
import Simbolo.Arbol;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;
import Expresiones.TipoMutabilidad;
import GUI.VistaPrincipal;

/**
 *
 * @author Rojas
 */
public class DeclaracionVar extends Instruccion {
    
    public String ID;
    public Instruccion value;
    public TipoMutabilidad Mutabilidad;
    public String Entorno;

    public DeclaracionVar(String ID, Instruccion value, TipoMutabilidad Mutabilidad, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.ID = ID;
        this.value = value;
        this.Mutabilidad = Mutabilidad;
        this.Entorno = "";
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
                    newSimbolo = new Simbolos(this.ID, this.tipo, 0, mut, this.linea, this.columna);
                } 
                case DECIMAL -> {
                    newSimbolo = new Simbolos(this.ID, this.tipo, 0.0, mut,  this.linea, this.columna);
                }
                case CADENA -> {
                    newSimbolo = new Simbolos(this.ID, this.tipo, "",mut,  this.linea, this.columna);
                }
                case CARACTER -> {
                    newSimbolo = new Simbolos(this.ID, this.tipo, '\u0000', mut,  this.linea, this.columna);
                }      
                case BOOLEANO -> {
                    newSimbolo = new Simbolos(this.ID, this.tipo, true, mut,  this.linea, this.columna);
                }
                default -> {
                    return new Errores("SEMANTICO", "El tipo de variable declarado es diferente al tipo asignado", this.linea, this.columna);
                }
            }
            
        } else {
            
            var ValorInterpretado = this.value.interpretar(arbol, tabla);
            
            if(ValorInterpretado instanceof Errores){
                return ValorInterpretado;
            }
            
            //SE VALIDA QUE LOS TIPOS SEAN IGUALES
        
            if(this.value.tipo.getTipo() != this.tipo.getTipo()){
                return new Errores("SEMANTICO", "La variable es de tipo " + this.tipo.getTipo().toString() + " y el valor asignado es de tipo " + this.value.tipo.getTipo().toString() , this.linea, this.columna);
            }
            
            newSimbolo = new Simbolos(this.ID, this.tipo, ValorInterpretado, mut,  this.linea, this.columna);
        }
        
        if(this.Entorno.equals("")){
            this.setEntorno("GLOBAL");
        }
        newSimbolo.setEntorno(this.Entorno);
        
        boolean creacion = tabla.setVariable(newSimbolo);
        
        if(!creacion){
            return new Errores("SEMANTICO", "La variable con el identificador " + this.ID.toString() + " ya existe", this.linea, this.columna);
        }
        
        VistaPrincipal.listaSimbolos.add(newSimbolo);
        
        return null;
    }

    public String getEntorno() {
        return Entorno;
    }

    public void setEntorno(String Entorno) {
        this.Entorno = Entorno;
    }

}
