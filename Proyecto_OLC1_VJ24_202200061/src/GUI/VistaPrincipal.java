
package GUI;

import Abstracto.Instruccion;
import Simbolo.Arbol;
import Simbolo.TablaSimbolos;
import Funciones.Errores;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;


public class VistaPrincipal extends javax.swing.JFrame {

    public static JButton botonJtabbed = new JButton("+");
    public static HashMap<String, JTextArea> Ventanas = new HashMap<>();
    public static int indice = 1;
    public static LinkedList<Errores> lista = new LinkedList<>();
    
    public VistaPrincipal() {
        
        initComponents();
        
        botonJtabbed.setBorder(null);
        botonJtabbed.setFocusPainted(false);
        botonJtabbed.setContentAreaFilled(false);
        botonJtabbed.setPreferredSize(new Dimension(20,20));
        
        jTabbedPane1.addTab("",null);
        jTabbedPane1.setTabComponentAt(0,botonJtabbed);
        
        botonJtabbed.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                AgregarVentana();
 
            }
    
        });
        
        jTabbedPane1.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                
                if (SwingUtilities.isRightMouseButton(e)){
                    
                    String nombreEliminar;
                    
                    int index = jTabbedPane1.getSelectedIndex();
                    nombreEliminar = jTabbedPane1.getTitleAt(index);
                    
                    if(index != 0){

                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem delete = new JMenuItem("Eliminar");
                        delete.addActionListener(new ActionListener(){

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                
                                jTabbedPane1.remove(index);
                                
                                Ventanas.remove(nombreEliminar);

                        }
                        });

                        popupMenu.add(delete);
                        popupMenu.show(jTabbedPane1, e.getX(), e.getY());

                    }
                    
                }
                  
            }

            @Override
            public void mouseReleased(MouseEvent e) {
           }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
           }

        });
           
    }
  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Analizador de Codigo");
        setBackground(new java.awt.Color(102, 102, 255));
        setForeground(new java.awt.Color(102, 102, 255));
        setName("FramePrincipal"); // NOI18N

        jLabel1.setText("Consola");

        jLabel2.setText("Ventanas");

        jTabbedPane1.setBackground(new java.awt.Color(51, 102, 255));
        jScrollPane2.setViewportView(jTabbedPane1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jScrollPane4.setViewportView(jScrollPane1);

        jMenuBar1.setBackground(new java.awt.Color(153, 153, 255));

        jMenu2.setText("Archivo");

        jMenuItem1.setText("Nuevo Archivo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Abrir Archivo");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Guardar Archivo");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Ejecutar");

        jMenuItem7.setText("Iniciar");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Reportes");

        jMenuItem4.setText("Reporte de Errores");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem5.setText("Generar AST");
        jMenu3.add(jMenuItem5);

        jMenuItem6.setText("Reporte de Tabla de Simbolos");
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    // AGFREGAR VENTANA DESDE MENU
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
        AgregarVentana();
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        
        JFileChooser FileChoose = new JFileChooser("D:\\USAC\\VACACIONES JUNIO 2024\\LAB COMPI 1 VACIONES 2024\\OLC1_VJ24_202200061");
        
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.JC","jc");
        
        FileChoose.setFileFilter(filtro);
        
        int selec = FileChoose.showOpenDialog(this);
        
        if (selec == JFileChooser.APPROVE_OPTION) {
            
            File Archivo = FileChoose.getSelectedFile();
            
            try(FileReader fr = new FileReader(Archivo)){
                
                String cadena = "";
                int valor = fr.read();
                
                while(valor != -1){
                    
                    cadena = cadena + (char) valor;
                    valor = fr.read();
                    
                }
                
                JLabel TituloTab = new JLabel(Archivo.getName());
                JTextArea textArea = new JTextArea();
                textArea.setText(cadena);
                
                jTabbedPane1.addTab(TituloTab.getText(), textArea);
                jTabbedPane1.setTabComponentAt(jTabbedPane1.getTabCount()- 1 ,TituloTab);
                Ventanas.put(TituloTab.getText(), textArea);
    
            }catch(IOException e){
                
             e.printStackTrace();
             
        }
                
            
        }
        
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // FUNCION REALIZAR COMPILACION
    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        
        int index2 = jTabbedPane1.getSelectedIndex();
        
        if (index2 != 0) {
            
            String nombre = jTabbedPane1.getTitleAt(index2);
            jTextArea1.setText("");
            String textoanalizar = Ventanas.get(nombre).getText();
            
            try {
                Compiladores.Lexer lexer = new Compiladores.Lexer(new BufferedReader(new StringReader(textoanalizar))); 
                Compiladores.Parser parser = new Compiladores.Parser(lexer);
                var resultado = parser.parse();
                var ast = new Arbol((LinkedList<Instruccion>) resultado.value);
                var tabla = new TablaSimbolos();
                tabla.setNombre("GLOBAL");
                ast.setConsola("");
                lista.clear();
                lista.addAll(lexer.listaErrores);
                lista.addAll(parser.listaErrores);
                
                for(var a : ast.getInstrucciones()){
                    if(a == null){
                        continue;
                    }
                    
                    var res = a.interpretar(ast, tabla);
                    if(res instanceof Errores){
                        lista.add((Errores) res);
                    }
                }
                
                String consola = ast.getConsola();

                for (var i : lista){
                    consola += i + "\n";
                }
                
                jTextArea1.setText(consola);
                
            } catch (Exception e) {
                System.out.println("Error fatal en compilación de entrada.");
                System.out.println(e);
            } 
            
        } else{
            
            JOptionPane.showMessageDialog(null, "No se puede Ejecutar esa Pestaña");
            
        }
   
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
        JFileChooser fc = new JFileChooser("D:\\USAC\\VACACIONES JUNIO 2024\\LAB COMPI 1 VACIONES 2024\\OLC1_VJ24_202200061");
        
        int selec = fc.showSaveDialog(this);
        
        if (selec == JFileChooser.APPROVE_OPTION) {
            
            File GArchivo = fc.getSelectedFile();
            
            try(FileWriter fw = new FileWriter(GArchivo)){
                
                int posicion = jTabbedPane1.getSelectedIndex();
                
                String nombre = jTabbedPane1.getTitleAt(posicion);
                fw.write(Ventanas.get(nombre).getText());
                
                
            } catch(IOException e){

                e.printStackTrace();
                
            }  
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    
    // REPORTE DE ERRORES
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Funciones.Errores.contadora = 1;
        
        StringBuilder htmlTable = new StringBuilder();
        htmlTable.append("<html>");
        htmlTable.append("<head>");
        htmlTable.append("<title>Tabla de Errores</title>");
        htmlTable.append("<style>");
        htmlTable.append("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 20px; }");
        htmlTable.append("h1 { color: #333; }");
        htmlTable.append("table { width: 100%; border-collapse: collapse; margin: 20px 0; }");
        htmlTable.append("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
        htmlTable.append("th { background-color: #f2f2f2; color: #333; }");
        htmlTable.append("tr:nth-child(even) { background-color: #f9f9f9; }");
        htmlTable.append("tr:hover { background-color: #e0e0e0; }");
        htmlTable.append("</style>");
        htmlTable.append("</head>");
        htmlTable.append("<body>");
        htmlTable.append("<h1>Tabla de Errores</h1>");
        htmlTable.append("<table border=\"1\">");
        htmlTable.append("<tr>");
        htmlTable.append("<th>Número</th>");
        htmlTable.append("<th>Tipo</th>");
        htmlTable.append("<th>Descripcion</th>");
        htmlTable.append("<th>Línea</th>");
        htmlTable.append("<th>Columna</th>");
        htmlTable.append("</tr>");
        
        for(var i: lista){
            htmlTable.append("<tr>");
            htmlTable.append("<td>").append(i.getNumero()).append("</td>");
            htmlTable.append("<td>").append(i.getTipo()).append("</td>");
            htmlTable.append("<td>").append(i.getDescripcion()).append("</td>");
            htmlTable.append("<td>").append(i.getLinea()).append("</td>");
            htmlTable.append("<td>").append(i.getColumna()).append("</td>");
            htmlTable.append("</tr>");
        } 
        
        htmlTable.append("</table>");
        htmlTable.append("</body>");
        htmlTable.append("</html>");
        
        String ruta = "src/ReporteErrores.html";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))){
            writer.write(htmlTable.toString());
            System.out.println("Tabla de Errores generada y guardada en: " + ruta);
        } catch (IOException e){
            System.out.println("Error al guardar la tabla de Errores en el archivo: " + e.getMessage());
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(() -> {
            new VistaPrincipal().setVisible(true);
        });
    }
   
    // FUNCION PARA AGREGAR UNA VENTANA NUEVA
    public static void AgregarVentana(){
                           
        JLabel TituloTab = new JLabel("Archivo " + indice);
        JTextArea textArea = new JTextArea();
                
        jTabbedPane1.addTab(TituloTab.getText(), textArea);
        jTabbedPane1.setTabComponentAt(jTabbedPane1.getTabCount()- 1 ,TituloTab);
        Ventanas.put(TituloTab.getText(), textArea);
        
        indice++;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    public static javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
