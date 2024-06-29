
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
public class DeclaracionStructs extends Instruccion {
    
    public String ID;
    public LinkedList<HashMap> Atributos;

    public DeclaracionStructs(String ID, LinkedList<HashMap> Atributos, int linea, int columna) {
        super(new Tipo(DatoNativo.STRUCT), linea, columna);
        this.ID = ID;
        this.Atributos = Atributos;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        
        for(int i = 0; i < this.Atributos.size(); i++){
            
            var busqueda = arbol.getStruct(this.Atributos.get(i).get("tipo").toString());
            if(busqueda != null){
                if(busqueda instanceof DeclaracionStructs struct){
                    var Atribstruct = struct.Atributos;
                    var hash = this.Atributos.get(i);
                    hash.put("valor", Atribstruct);
                }
            }  
        }
        return null;
    }
  
}
