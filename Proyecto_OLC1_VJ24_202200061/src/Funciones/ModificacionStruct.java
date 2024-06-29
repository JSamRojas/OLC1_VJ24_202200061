
package Funciones;

import Abstracto.Instruccion;
import Simbolo.Arbol;
import Simbolo.DatoNativo;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Rojas
 */
public class ModificacionStruct extends Instruccion {
    
    private String IDVar;
    private String IDStruct;
    private String IDStructInterno;
    private Instruccion expresion;

    public ModificacionStruct(String IDVar, String IDStruct, String IDStructInterno, Instruccion expresion, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.IDVar = IDVar;
        this.IDStruct = IDStruct;
        this.IDStructInterno = IDStructInterno;
        this.expresion = expresion;
    }

    public ModificacionStruct(String IDVar, String IDStruct, Instruccion expresion, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.IDVar = IDVar;
        this.IDStruct = IDStruct;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        var variable = tabla.getVariable(this.IDVar);
        
        if(variable == null){
            return new Errores("SEMANTICA", "La variable " + this.IDVar + " no existe",this.linea, this.columna);
        }
        
        var valor = this.expresion.interpretar(arbol, tabla);
        
        if(valor instanceof Errores){
            return valor;
        }
        
        var HashVariable = variable.getValor();

        if(HashVariable instanceof LinkedList lista){

            for(int i = 0; i < lista.size(); i++){

                var busqueda = lista.get(i);

                if(busqueda instanceof HashMap miniHash){

                    if(miniHash.get("id").toString().equals(this.IDStruct)){
                        
                        if(miniHash.get("valor") instanceof LinkedList listaInterna){

                            if(this.IDStructInterno == null){
                                return new Errores("SEMANTICA", "El atributo " + IDStruct + " es un STRUCT y no se ha proporcionado el atributo",this.linea, this.columna);
                            }
                            
                            for(int j = 0; j < listaInterna.size(); j++){
                                
                                var busqueda2 = listaInterna.get(j);
                                
                                if(busqueda2 instanceof HashMap miniHash2){
                                    
                                    if(miniHash2.get("id").toString().equals(this.IDStructInterno)){
                                        
                                        if(this.expresion.tipo.getTipo() != ((Tipo)miniHash2.get("tipo")).getTipo()){
                                            return new Errores("SEMANTICA", "Los tipos de las variables no coinciden",this.linea, this.columna);
                                        }
                                        
                                        miniHash2.put("valor", valor);
                                        
                                    }
                                    
                                }
                                
                            }
                            
                        } else {
                            
                            if(this.expresion.tipo.getTipo() != ((Tipo)miniHash.get("tipo")).getTipo()){
                                return new Errores("SEMANTICA", "Los tipos de las variables no coinciden",this.linea, this.columna);
                            }
                            
                            miniHash.put("valor", valor);
                            
                        }  
                    }
                }
            }
        } else {
            return new Errores("SEMANTICA", "La variable proporcionada no es de tipo STRUCT",this.linea, this.columna);
        }
        return null; 
    }

}
