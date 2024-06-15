package Funciones;

import Simbolo.Tipo;

public class Simbolos {
    
    private String nombre;
    private Tipo tipo;
    private Object valor;
    private boolean mutabilidad;

    public Simbolos(String nombre, Tipo tipo, boolean mutabilidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.mutabilidad = mutabilidad;
    }

    public Simbolos(String nombre, Tipo tipo, Object valor, boolean mutabilidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.mutabilidad = mutabilidad;
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
    
    
    
}


