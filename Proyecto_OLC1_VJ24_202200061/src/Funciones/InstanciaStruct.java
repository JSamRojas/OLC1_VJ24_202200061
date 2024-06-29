
package Funciones;

import Abstracto.Instruccion;
import Expresiones.TipoMutabilidad;
import GUI.VistaPrincipal;
import Simbolo.Arbol;
import Simbolo.DatoNativo;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author Rojas
 */
public class InstanciaStruct extends Instruccion {
    
    private String IDStruct;
    private String IDVar;
    public String Entorno;
    private HashMap<String,Instruccion> Valores;
    public TipoMutabilidad Mutabilidad;

    public InstanciaStruct(String IDStruct, String IDVar, HashMap<String, Instruccion> Valores, TipoMutabilidad Mutabilidad, int linea, int columna) {
        super(new Tipo(DatoNativo.STRUCT), linea, columna);
        this.IDStruct = IDStruct;
        this.IDVar = IDVar;
        this.Valores = Valores;
        this.Entorno = "";
        this.Mutabilidad = Mutabilidad;
    }

    public InstanciaStruct(HashMap<String, Instruccion> Valores, int linea, int columna) {
        super(new Tipo(DatoNativo.STRUCT), linea, columna);
        this.Entorno = "";
        this.Valores = Valores;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
         
        boolean mut;
        Simbolos newSimbolo = null;
        
        if(this.Mutabilidad != null){
            
            if(this.Mutabilidad == TipoMutabilidad.VAR){
                mut = true;        
            } else {
                mut = false;
            }

            var busqueda = arbol.getStruct(IDStruct);
            if(busqueda == null){
                return new Errores("SEMANTICO", "El struct con el nombre " + this.IDStruct + " no existe", this.linea, this.columna);
            }

            if(busqueda instanceof DeclaracionStructs struct){
                if(struct.Atributos.size() != this.Valores.size()){
                    return new Errores("SEMANTICO", "La cantidad de atributos no coinciden", this.linea, this.columna);
                }
                LinkedList<HashMap> ValorFinal = new LinkedList<>();
                
                for(HashMap<String, Instruccion> mapa: struct.Atributos){
                    HashMap<String, Instruccion> newMap = new HashMap<>(mapa);
                    ValorFinal.add(newMap);
                }
                
                Set<String> atributosInic = new HashSet<>();
                Set<String> atributosInst = this.Valores.keySet();

                for(var i: struct.Atributos){
                    atributosInic.add(i.get("id").toString());
                }

                if(!atributosInic.equals(atributosInst)){
                    return new Errores("SEMANTICO", "El nombre de los atributos no coinciden", this.linea, this.columna);
                }

                for(int i = 0; i < this.Valores.size(); i++){
                    var valor = this.Valores.get(struct.Atributos.get(i).get("id").toString()).interpretar(arbol, tabla);

                    if(valor instanceof Errores){
                        return valor;
                    }

                    if(valor instanceof LinkedList list){

                        var hash = ValorFinal.get(i);
                        var hash2 = hash.get("valor");
   
                        Set<String> AtstructInt = new HashSet<>();
                        Set<String> AtListInt = new HashSet<>();
                        
                        for(var j : list){
                            if(j instanceof HashMap hashito){
                                AtListInt.add(hashito.get("id").toString());
                            }   
                        }
                        
                        for(var k : (LinkedList) hash2){
                            if(k instanceof HashMap hashito){
                                AtstructInt.add(hashito.get("id").toString());
                            }   
                        }

                        if(!AtstructInt.equals(AtListInt)){
                            return new Errores("SEMANTICO", "El nombre de los atributos no coinciden", this.linea, this.columna);
                        }

                        hash.put("valor", valor);
   
                    } else if(valor instanceof HashMap miniHash){
                        
                        var StructInterno = arbol.getStruct(struct.Atributos.get(i).get("tipo").toString());
                        
                        if(StructInterno instanceof DeclaracionStructs MiniStruct){
                            
                            if(MiniStruct.Atributos.size() != miniHash.size()){
                                return new Errores("SEMANTICO", "La cantidad de atributos no coinciden", this.linea, this.columna);
                            }
                            
                            LinkedList<HashMap> ValorSemiFinal = new LinkedList<>();
                            
                            for(HashMap<String, Instruccion> mapa: MiniStruct.Atributos){
                                HashMap<String, Instruccion> newMap = new HashMap<>(mapa);
                                ValorSemiFinal.add(newMap);
                            }
                            
                            Set<String> AtributosPadre = new HashSet<>();
                            Set<String> AtributosHijo = miniHash.keySet();

                            for(var j: MiniStruct.Atributos){
                                AtributosPadre.add(j.get("id").toString());
                            }

                            if(!AtributosPadre.equals(AtributosHijo)){
                                return new Errores("SEMANTICO", "El nombre de los atributos no coinciden", this.linea, this.columna);
                            }
                            
                            for(int k = 0; k < miniHash.size(); k++){
                                var valorHijo = miniHash.get(MiniStruct.Atributos.get(k).get("id").toString());
                                valorHijo = ((Instruccion) valorHijo).interpretar(arbol, tabla);
                                
                                if(valorHijo instanceof Errores){
                                    return valorHijo;
                                }
                                
                                var tipoValor = ((Instruccion) miniHash.get(MiniStruct.Atributos.get(k).get("id").toString())).tipo.getTipo();
                                
                                if(((Tipo) MiniStruct.Atributos.get(k).get("tipo")).getTipo() != tipoValor){
                                    return new Errores("SEMANTICO", "El parametro es de tipo " + MiniStruct.Atributos.get(k).get("tipo").toString() + 
                                               " y el valor asignado es de tipo " + tipoValor.toString() , this.linea, this.columna);
                                }
                                
                                var hashSemi = ValorSemiFinal.get(k);
                                hashSemi.put("valor", valorHijo);
                            }
                            
                            var hash = ValorFinal.get(i);
                            hash.put("valor", ValorSemiFinal);
                            
                        }
                        
                    } else {

                        if( ((Tipo) struct.Atributos.get(i).get("tipo")).getTipo() != this.Valores.get(struct.Atributos.get(i).get("id").toString()).tipo.getTipo()){
                        return new Errores("SEMANTICO", "El parametro es de tipo " + struct.Atributos.get(i).get("tipo").toString() + 
                                               " y el valor asignado es de tipo " + this.Valores.get(struct.Atributos.get(i).get("id").toString()).tipo.getTipo().toString()
                                               , this.linea, this.columna);
                        }

                        var hash = ValorFinal.get(i);
                        hash.put("valor", valor);
                    }

                }

                newSimbolo = new Simbolos(this.IDVar, this.tipo, ValorFinal, mut, this.IDStruct,  this.linea, this.columna);
            }
            
        } else {  
            return this.Valores;
        }
 
        if(this.Entorno.equals("")){
            this.setEntorno("GLOBAL");
        }
        newSimbolo.setEntorno(this.Entorno);
        
        boolean creacion = tabla.setVariable(newSimbolo);
        
        if(!creacion){
            return new Errores("SEMANTICO", "La variable con el identificador " + this.IDVar + " ya existe", this.linea, this.columna);
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
