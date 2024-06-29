
package Simbolo;

import Abstracto.Instruccion;
import Funciones.DeclaracionStructs;
import Funciones.Errores;
import Funciones.FuncFunciones;
import Funciones.Metodos;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Arbol {
    
    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private TablaSimbolos tablaGlobal;
    public LinkedList<Errores> errores;
    private LinkedList<Instruccion> FuncYMetod;
    private LinkedList<Instruccion> Structs;

    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.consola = "";
        this.tablaGlobal = new TablaSimbolos();
        this.errores = new LinkedList<>();
        this.FuncYMetod = new LinkedList<>();
        this.Structs = new LinkedList<>();
    }

    public LinkedList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getConsola() {
        return consola;
    }

    public void setConsola(String consola) {
        this.consola = consola;
    }

    public TablaSimbolos getTablaGlobal() {
        return tablaGlobal;
    }

    public void setTablaGlobal(TablaSimbolos tablaGlobal) {
        this.tablaGlobal = tablaGlobal;
    }

    public LinkedList<Errores> getErrores() {
        return errores;
    }

    public void setErrores(LinkedList<Errores> errores) {
        this.errores = errores;
    }
    
    public void Print(String valor){
        this.consola += valor + "\n";
    }

    public LinkedList<Instruccion> getFuncYMetod() {
        return FuncYMetod;
    }

    public void setFuncYMetod(LinkedList<Instruccion> FuncYMetod) {
        this.FuncYMetod = FuncYMetod;
    }
    
    public void addFuncMetod(Instruccion funcmetod){
        if(funcmetod instanceof Metodos metodos){
            var encontro = this.getFuncMetod(metodos.ID);
            if(encontro != null){
                Errores error = new Errores("SEMANTICO", "El metodo con el nombre " + metodos.ID + " ya existe", metodos.linea, metodos.columna);
                this.errores.add(error);
            } else {
                this.FuncYMetod.add(funcmetod);
            }
        } else if(funcmetod instanceof FuncFunciones funcion){  
            var encontro = this.getFuncMetod(funcion.ID);
            if(encontro != null){
                Errores error = new Errores("SEMANTICO", "La funcion con el nombre " + funcion.ID + " ya existe", funcion.linea, funcion.columna);
                this.errores.add(error);
            } else {
                this.FuncYMetod.add(funcmetod);
            }
        }
    }
    
    public Instruccion getFuncMetod(String ID){
        for(var i : this.FuncYMetod){
            if(i instanceof Metodos metodo ){
                if(metodo.ID.equalsIgnoreCase(ID)){
                    return i;
                }
            } else if(i instanceof FuncFunciones funcion) {
                if(funcion.ID.equalsIgnoreCase(ID)){
                    return i;
                }
            }
        }
        return null;
    }

    public LinkedList<Instruccion> getStructs() {
        return Structs;
    }

    public void setStructs(LinkedList<Instruccion> Structs) {
        this.Structs = Structs;
    }
    
    public void addStruct(Instruccion struct){
        if(struct instanceof DeclaracionStructs stru){
            var encontro = this.getStruct(stru.ID);
            if(encontro != null){
                Errores error = new Errores("SEMANTICO", "El Struct con el nombre " + stru.ID + " ya existe", stru.linea, stru.columna);
                this.errores.add(error);
            } else {
                this.Structs.add(struct);
            }
        }
    }
    
    public Instruccion getStruct(String ID){
        for(var i: this.Structs){
           if(i instanceof DeclaracionStructs stru){
                if(stru.ID.equalsIgnoreCase(ID)){
                    return i;
                }
            } 
        }
        return null;
    }

}
