
package Funciones;

import Abstracto.Instruccion;
import Expresiones.Return;
import Expresiones.TipoMutabilidad;
import Simbolo.Arbol;
import Simbolo.DatoNativo;
import Simbolo.TablaSimbolos;
import Simbolo.Tipo;
import java.util.LinkedList;

/**
 *
 * @author Rojas
 */
public class FuncLlamada extends Instruccion {
    
    private String ID;
    private LinkedList<Instruccion> parametros;

    public FuncLlamada(String ID, LinkedList<Instruccion> parametros, int linea, int columna) {
        super(new Tipo(DatoNativo.VOID), linea, columna);
        this.ID = ID;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Arbol arbol, TablaSimbolos tabla) {
        var busqueda = arbol.getFuncMetod(this.ID);
        if(busqueda == null){
            return new Errores("SEMANTICO", "La funcion con el nombre " + this.ID + " no existe", this.linea, this.columna);
        }
        
        if(busqueda instanceof Metodos metodo){
            
            var newTabla = new TablaSimbolos(arbol.getTablaGlobal());
            newTabla.setNombre("GLOBAL - METODO " + this.ID);
            
            if(metodo.parametros.size() != this.parametros.size()){
                return new Errores("SEMANTICO", "La cantidad de parametros no coinciden", this.linea, this.columna);
            }
            
            for(int i = 0; i < this.parametros.size(); i++){
                var identificador = (String) metodo.parametros.get(i).get("id");
                var valor = this.parametros.get(i);
                var tipo2 = (Tipo) metodo.parametros.get(i).get("tipo");
                
                var DeclararParams = new DeclaracionVar(identificador, null, TipoMutabilidad.CONST, tipo2, this.linea, this.columna);
               
                var resultado = DeclararParams.interpretar(arbol, newTabla);
                
                if(resultado instanceof Errores){
                    return resultado;
                }
                
                var valorInterpretado = valor.interpretar(arbol, tabla);
                
                if(valorInterpretado instanceof Errores){
                    return valorInterpretado;
                }
                
                var variable = newTabla.getVariable(identificador);
                if(variable == null){
                    return new Errores("SEMANTICO", "La variable con el nombre " + identificador + " no existe", this.linea, this.columna);
                }
                
                if(variable.getTipo().getTipo() != valor.tipo.getTipo()){
                    return new Errores("SEMANTICO", "La variable es de tipo " + this.tipo.getTipo().toString() + " y el valor asignado es de tipo " + valor.tipo.getTipo().toString() , this.linea, this.columna);
                }
                
                variable.setValor(valorInterpretado);    
            }
            
            var resultadoFuncion = metodo.interpretar(arbol, newTabla);
            if(resultadoFuncion instanceof Errores){
                return resultadoFuncion;
            }
        } else if(busqueda instanceof FuncFunciones funcion){
            
            var newTabla = new TablaSimbolos(arbol.getTablaGlobal());
            newTabla.setNombre("GLOBAL - METODO " + this.ID);
            
            if(funcion.parametros.size() != this.parametros.size()){
                return new Errores("SEMANTICO", "La cantidad de parametros no coinciden", this.linea, this.columna);
            }
            
            for(int i = 0; i < this.parametros.size(); i++){
                var identificador = (String) funcion.parametros.get(i).get("id");
                var valor = this.parametros.get(i);
                var tipo2 = (Tipo) funcion.parametros.get(i).get("tipo");
                
                var DeclararParams = new DeclaracionVar(identificador, null, TipoMutabilidad.CONST, tipo2, this.linea, this.columna);
               
                var resultado = DeclararParams.interpretar(arbol, newTabla);
                
                if(resultado instanceof Errores){
                    return resultado;
                }
                
                var valorInterpretado = valor.interpretar(arbol, tabla);
                
                if(valorInterpretado instanceof Errores){
                    return valorInterpretado;
                }
                
                var variable = newTabla.getVariable(identificador);
                if(variable == null){
                    return new Errores("SEMANTICO", "La variable con el nombre " + identificador + " no existe", this.linea, this.columna);
                }
                
                if(variable.getTipo().getTipo() != valor.tipo.getTipo()){
                    return new Errores("SEMANTICO", "La variable es de tipo " + this.tipo.getTipo().toString() + " y el valor asignado es de tipo " + valor.tipo.getTipo().toString() , this.linea, this.columna);
                }
                
                variable.setValor(valorInterpretado);    
            }
            
            var resultadoFuncion = funcion.interpretar(arbol, newTabla);
            if(resultadoFuncion instanceof Errores){
                return resultadoFuncion;
            }
            
            if(resultadoFuncion instanceof Return ret){
                
                if(ret.expresion != null){
                    var valor = ret.interpretar(arbol, ret.tablaEntorno);
                    if(valor instanceof Errores){
                        return valor;
                    }

                    if(ret.tipo.getTipo() != funcion.tipo.getTipo()){
                        return new Errores("SEMANTICO", "La funcion es de tipo " + funcion.tipo.getTipo().toString() + " y el RETURN es de tipo " + 
                                               ret.tipo.getTipo().toString() , this.linea, this.columna);
                    }

                    this.tipo.setTipo(ret.tipo.getTipo());
                    return valor;
                } else {
                    return null;
                }

            }
            
        }
        return null;
    }
   
}
