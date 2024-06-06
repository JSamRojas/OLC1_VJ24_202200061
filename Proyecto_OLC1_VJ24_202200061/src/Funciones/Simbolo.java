package Funciones;

public class Simbolo {
    
    public static int contadora = 0;
    private int numero;
    private String nombre;
    private String tipo;
    private String valor;
    private int linea;
    private int columna;
    
    public Simbolo(String nombre, String tipo, String valor, int linea, int columna){
        
        this.numero = contadora++;
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
        
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getValor() {
        return valor;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }
    
    
    
}


