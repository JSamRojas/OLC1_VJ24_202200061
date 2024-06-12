
package Funciones;

public class Errores {
    
    public static int contadora = 1;
    private int numero;
    private String tipo;
    private String descripcion;
    private int linea;
    private int columna;

    public Errores(String tipo, String descripcion, int linea, int columna) {
        this.numero = contadora++;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public String toString() {
        return "Error numero=" + numero + ", tipo=" + tipo + ", descripcion=" + descripcion + ", linea=" + linea + ", columna=" + columna;
    }
    
    
}
