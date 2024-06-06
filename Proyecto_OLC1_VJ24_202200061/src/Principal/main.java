/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package Principal;
import GUI.VistaPrincipal;
import java.io.StringReader;

public class main {

    public static void main(String[] args) {
        
        VistaPrincipal p = new VistaPrincipal();
        p.setVisible(true);
        
        analizadores("src/Compiladores/","Lexico.jflex","Sintactico.cup");
    }
    
    public static void analizadores(String ruta, String jflexFile, String cupFile){
        try {
            String opcionesJflex[] =  {ruta+jflexFile,"-d",ruta};
            jflex.Main.generate(opcionesJflex);

            String opcionesCup[] =  {"-destdir", ruta,"-parser","Parser",ruta+cupFile};
            java_cup.Main.main(opcionesCup);
            
        } catch (Exception e) {
            System.out.println("No se ha podido generar los analizadores");
            System.out.println(e);
        }
    }
    
        // Realizar Analisis
    
    public static void analizar (String entrada){
        try {
            Compiladores.Lexer lexer = new Compiladores.Lexer(new StringReader(entrada)); 
            Compiladores.Parser parser = new Compiladores.Parser(lexer);
            parser.parse();
        } catch (Exception e) {
            System.out.println("Error fatal en compilación de entrada.");
            System.out.println(e);
        } 
    }

    
    
}
