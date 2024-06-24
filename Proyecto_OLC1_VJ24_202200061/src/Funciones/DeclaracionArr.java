
package Funciones;

import Abstracto.Instruccion;
import Expresiones.TipoMutabilidad;
import GUI.VistaPrincipal;
import Simbolo.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Rojas
 */
public class DeclaracionArr extends Instruccion {
    
    public TipoMutabilidad Mutabilidad;
    public String ID;
    public String Entorno;
    public LinkedList<Instruccion> ValoresArr;
    public LinkedList<ArrayList<Instruccion>> Valores2D;

    public DeclaracionArr(TipoMutabilidad Mutabilidad, String ID, LinkedList<Instruccion> ValoresArr, LinkedList<ArrayList<Instruccion>> Valores2D, Tipo tipo, int linea, int columna) {
        super(tipo, linea, columna);
        this.Mutabilidad = Mutabilidad;
        this.ID = ID;
        this.Entorno = "";
        this.ValoresArr = ValoresArr;
        this.Valores2D = Valores2D;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        ArrayList<Object> Valores = new ArrayList<>();
        Simbolos newSimbolo = null;
        boolean mut;
        
        if(this.Mutabilidad == TipoMutabilidad.VAR){
            mut = true;        
        } else {
            mut = false;
        }
        
        if(this.Valores2D.isEmpty()){
            
            for(var i: this.ValoresArr){
            
                if(i.tipo.getTipo() == this.tipo.getTipo()){

                    var valor = i.interpretar(arbol, tabla);
                    if(valor instanceof Errores){
                        return valor;
                    }

                    Valores.add(valor);
                } else {
                    return new Errores("SEMANTICO", "Se declaro un arreglo de tipo " + this.tipo.getTipo().toString() + 
                    " y se le intento asignar un valor de tipo " + i.tipo.getTipo().toString(), this.linea, this.columna);
                }  
            }
            
            newSimbolo = new Simbolos(this.ID, this.tipo, Valores, mut, "Arreglo (1 Dimension)",  this.linea, this.columna);
            
        } else {
            
            for(var i: this.Valores2D){
                
                if(i.get(0) instanceof Instruccion && i.get(1) instanceof Instruccion){
                    var val1 = i.get(0);
                    var val2 = i.get(1);
                    ArrayList<Object> Valores2D = new ArrayList<>();
                    
                    if(val1.tipo.getTipo() == val2.tipo.getTipo()){
                        
                        var valor1 = val1.interpretar(arbol, tabla);
                        var valor2 = val2.interpretar(arbol, tabla);
                        
                        if(valor1 instanceof Errores){
                            return valor1;
                        }
                        
                        if(valor2 instanceof Errores){
                            return valor2;
                        }
                        
                        Valores2D.add(valor1);
                        Valores2D.add(valor2);
                        Valores.add(Valores2D);
                        
                    } else {
                        return new Errores("SEMANTICO", "Se declaro un arreglo de tipo " + this.tipo.getTipo().toString() + 
                        " y se le intento asignar un arreglo de un tipo distinto", this.linea, this.columna);
                    }
                }
                
            }
            
            newSimbolo = new Simbolos(this.ID, this.tipo, Valores, mut, "Arreglo (2 Dimensiones)",  this.linea, this.columna);
            
        }

        if(this.Entorno.equals("")){
            this.setEntorno("GLOBAL");
        }
        newSimbolo.setEntorno(this.Entorno);
        
        boolean creacion = tabla.setVariable(newSimbolo);
        
        if(!creacion){
            return new Errores("SEMANTICO", "La variable con el identificador " + this.ID + " ya existe", this.linea, this.columna);
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
