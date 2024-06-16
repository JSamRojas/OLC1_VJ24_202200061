package Funciones;

import Simbolo.Tipo;

public class Simbolos {
    
    private String nombre;
    private Tipo tipo;
    private Object valor;
    private boolean mutabilidad;
    private String TipoEstruct;
    private String Entorno;
    private int linea;
    private int columna;

    public Simbolos(String nombre, Tipo tipo, boolean mutabilidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.mutabilidad = mutabilidad;
    }

    public Simbolos(String nombre, Tipo tipo, Object valor, boolean mutabilidad, int linea, int columna) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.mutabilidad = mutabilidad;
        this.TipoEstruct = "Variable";
        this.Entorno = "";
        this.linea = linea;
        this.columna = columna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public boolean getMutabilidad() {
        return mutabilidad;
    }

    public void setMutabilidad(boolean mutabilidad) {
        this.mutabilidad = mutabilidad;
    }

    public String getTipoEstruct() {
        return TipoEstruct;
    }

    public void setTipoEstruct(String TipoEstruct) {
        this.TipoEstruct = TipoEstruct;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getEntorno() {
        return Entorno;
    }

    public void setEntorno(String Entorno) {
        this.Entorno = Entorno;
    }
 
}


