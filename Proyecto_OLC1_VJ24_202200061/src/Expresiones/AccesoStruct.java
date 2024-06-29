
package Expresiones;

import Abstracto.Instruccion;
import Funciones.Errores;
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
public class AccesoStruct extends Instruccion {
    
    private String IDVar;
    private String IDStruct;
    private String IDStructInterno;

    public AccesoStruct(String IDVar, String IDStruct, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.IDVar = IDVar;
        this.IDStruct = IDStruct;
    }

    public AccesoStruct(String IDVar, String IDStruct, String IDStructInterno, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.IDVar = IDVar;
        this.IDStruct = IDStruct;
        this.IDStructInterno = IDStructInterno;
    }
    
    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        var variable = tabla.getVariable(this.IDVar);
        Object valorFinal;
        
        if(variable == null){
            return new Errores("SEMANTICA", "La variable " + this.IDVar + " no existe",this.linea, this.columna);
        }
        
        var HashVariable = variable.getValor();

        if(HashVariable instanceof LinkedList lista){

            for(int i = 0; i < lista.size(); i++){

                var busqueda = lista.get(i);

                if(busqueda instanceof HashMap miniHash){

                    if(miniHash.get("id").toString().equals(this.IDStruct)){
                        
                        valorFinal = miniHash.get("valor");

                        if(valorFinal instanceof LinkedList listaInterna){

                            if(this.IDStructInterno == null){
                                return new Errores("SEMANTICA", "El atributo " + IDStruct + " es un STRUCT y no se ha proporcionado el atributo",this.linea, this.columna);
                            }
                            
                            for(int j = 0; j < listaInterna.size(); j++){
                                
                                var busqueda2 = listaInterna.get(j);
                                
                                if(busqueda2 instanceof HashMap miniHash2){
                                    
                                    if(miniHash2.get("id").toString().equals(this.IDStructInterno)){
                                        
                                        valorFinal = miniHash2.get("valor");
                                        this.tipo.setTipo(((Tipo) miniHash2.get("tipo")).getTipo());
                                        return valorFinal;
                                    }
                                    
                                }
                                
                            }
                            

                        } else {
                            this.tipo.setTipo(((Tipo) miniHash.get("tipo")).getTipo());
                            return valorFinal;
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
