package Funciones;

public class Simbolos {
    
    private String nombre;
    private String tipo;
    private Object valor;

    public Simbolos(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Simbolos(String nombre, String tipo, Object valor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
}


