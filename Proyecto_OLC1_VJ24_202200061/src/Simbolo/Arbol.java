
package Simbolo;

import Abstracto.Instruccion;
import Funciones.Errores;
import Funciones.Metodos;
import java.util.LinkedList;

public class Arbol {
    
    private LinkedList<Instruccion> instrucciones;
    private String consola;
    private TablaSimbolos tablaGlobal;
    public LinkedList<Errores> errores;
    private LinkedList<Instruccion> FuncYMetod;

    public Arbol(LinkedList<Instruccion> instrucciones) {
        this.instrucciones = instrucciones;
        this.consola = "";
        this.tablaGlobal = new TablaSimbolos();
        this.errores = new LinkedList<>();
        this.FuncYMetod = new LinkedList<>();
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
        }  
    }
    
    public Instruccion getFuncMetod(String ID){
        for(var i : this.FuncYMetod){
            if(i instanceof Metodos metodo ){
                if(metodo.ID.equalsIgnoreCase(ID)){
                    return i;
                }
            }
        }
        return null;
    }
    
}
