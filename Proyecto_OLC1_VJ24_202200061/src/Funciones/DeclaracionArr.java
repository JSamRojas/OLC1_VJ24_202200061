
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
    public LinkedList<LinkedList<Instruccion>> Valores2D;

    public DeclaracionArr(TipoMutabilidad Mutabilidad, String ID, LinkedList<Instruccion> ValoresArr, LinkedList<LinkedList<Instruccion>> Valores2D, Tipo tipo, int linea, int columna) {
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

            for(int i = 0; i < this.Valores2D.size(); i++){
                ArrayList<Object> Valores2D = new ArrayList<>();
                var arreglo = this.Valores2D.get(i);
                for(int j = 0; j < this.Valores2D.get(i).size(); j++){
                    
                    var valor1 = ((Instruccion) arreglo.get(j)).interpretar(arbol, tabla);
                        
                    if(valor1 instanceof Errores){
                        return valor1;
                    }
                    
                    if(((Instruccion) arreglo.get(j)).tipo.getTipo() != this.tipo.getTipo()){
                        return new Errores("SEMANTICO", "Se declaro un arreglo de tipo " + this.tipo.getTipo().toString() + 
                        " y se le intento asignar un valor de tipo " + ((Instruccion) arreglo.get(j)).tipo.getTipo().toString() , this.linea, this.columna);
                    }  
                    Valores2D.add(valor1);    
                }
                
                if(!Valores.isEmpty()){
                    var vecAux = Valores.get(0);
                    if(((ArrayList) vecAux).size() != Valores2D.size()){
                        return new Errores("SEMANTICO", "El tamaño de los vectores internos son diferentes entre si", this.linea, this.columna);       
                    }
                }
                
                Valores.add(Valores2D);
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
