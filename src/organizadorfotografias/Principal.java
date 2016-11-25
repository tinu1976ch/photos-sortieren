
package organizadorfotografias;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    private File carpetaOrigen;
    private File carpetaDestino;
    private StringBuilder registro;
    private ArrayList<File> ficheros;
    private ArrayList<Fotografia> fotografias;
    private ButtonGroup opcion1 = new ButtonGroup();
    public Principal() {
        initComponents();
    }
   
    private File elegirFichero(){
        JFileChooser elegirCarpeta = new JFileChooser(System.getProperty("user.home")+"/Desktop");
        elegirCarpeta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (elegirCarpeta.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            if (elegirCarpeta.getSelectedFile().isDirectory()){
               return elegirCarpeta.getSelectedFile();
            }else{
               JOptionPane.showMessageDialog(null, "Debe seleccionar un directorio", "Fichero incorrecto", JOptionPane.WARNING_MESSAGE);
            }
        }
        return null;
    }
    private void inicar(){
        registro = new StringBuilder();
        dlg_log.setLocationRelativeTo(this);
        dlg_log.setVisible(true);
        txa_registro.setText("");
        
        
        anadirRegistro("Buscando archivos ...");
        ficheros = new ArrayList<>();
        if (rb_opcion1_si.isSelected()){
            obtenerFicheros(carpetaOrigen.getAbsolutePath(), ficheros, true);
        }else{
            System.out.println("no");
            obtenerFicheros(carpetaOrigen.getAbsolutePath(), ficheros, false);
        }
        mostrarFicheros();
        
        
        anadirRegistro("\nBuscando fotografias compatibles ...");
        fotografias = obtenerFotografiasCompatibles();
        mostrarFotografiasCompatibles();
        
        anadirRegistro("\nUbicando archivos ...");
        crearCarpetasYmover();
        anadirRegistro("\nCompletado");
    }
    private void anadirRegistro(String linea){
        registro.append(linea);
        txa_registro.setText(txa_registro.getText()+"\n"+linea);
    }
    private void obtenerFicheros(String ruta, ArrayList<File> ficheros, boolean recursivo){
        if (recursivo){
            File directorio = new File(ruta);
            
            File[] archivos = directorio.listFiles();
            for (File archivo : archivos) {
                if (archivo.isFile()){
                    ficheros.add(archivo);
                }else if(archivo.isDirectory()){
                    obtenerFicheros(archivo.getAbsolutePath(), ficheros, recursivo);
                }
            }
        }
        else{
            for (File archivo: carpetaOrigen.listFiles()){
                if (archivo.isFile()){
                    ficheros.add(archivo);
                }
            }
        }
    }
    private void mostrarFicheros(){
        for (File fichero : ficheros) {
            anadirRegistro(fichero.getAbsolutePath());
        }
        anadirRegistro("\nTotal de ficheros:" + ficheros.size());
    }
    
    private ArrayList<Fotografia> obtenerFotografiasCompatibles(){
        fotografias = new ArrayList<>();
        
        for (File fichero: ficheros){
            try {
                Metadata metadata = ImageMetadataReader.readMetadata(fichero);
                Directory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                if( directory != null ){
                    for (Tag tag : directory.getTags()) {
                        String descripcion = tag.toString();
                        if (descripcion.startsWith("[Exif SubIFD] Date/Time Original")){
                            try{
                                Fotografia nuevaFotografia = new Fotografia(fichero, Fotografia.obtenerCalendario(descripcion.substring(35, descripcion.length())));
                                fotografias.add(nuevaFotografia);
                            } catch (ParseException ex) {
                                anadirRegistro("Error al interpretar la fecha:\n"+ex.getMessage());
                            }
                        }
                    }
                }
            } catch (ImageProcessingException|IOException ex) {
                    System.out.println(ex.getMessage());
            }
        }
        
        return fotografias;
    }
    private void mostrarFotografiasCompatibles(){
        for (Fotografia fotografia : fotografias) {
            anadirRegistro(fotografia.getArchivo().getAbsolutePath());
        }
         anadirRegistro("\nTotal de fotografias compatibles:" + fotografias.size());
    }
    private void crearCarpetasYmover(){
        for (Fotografia fotografia : fotografias) {
            File archivo = fotografia.getArchivo();
            GregorianCalendar fecha = fotografia.getFecha();

            int anyo = fecha.get(Calendar.YEAR);
            String mes = Fotografia.obtenerMes(fecha.get(Calendar.MONTH));
            File nuevaRutaFotografia = new File(carpetaDestino.getAbsolutePath()+"/"+anyo+"/"+mes);
            nuevaRutaFotografia.mkdirs();
            
            
            if (archivo.renameTo(new File(nuevaRutaFotografia.getAbsolutePath()+"/"+archivo.getName()))){
                anadirRegistro("Archivo " + archivo.getName()+ " movido a " + nuevaRutaFotografia.getAbsolutePath());
            }else{
                anadirRegistro("ERROR al mover el archivo " + archivo.getName()+ " a " + nuevaRutaFotografia.getAbsolutePath());
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlg_log = new javax.swing.JDialog();
        scroll_registro = new javax.swing.JScrollPane();
        txa_registro = new javax.swing.JTextArea();
        txf_carpetaOrigen = new javax.swing.JTextField();
        bt_elegirCarpetaOrigen = new javax.swing.JButton();
        bt_elegirCarpetaDestino = new javax.swing.JButton();
        txf_carpetaDestino = new javax.swing.JTextField();
        lb_opciones = new javax.swing.JLabel();
        lb_opcion1 = new javax.swing.JLabel();
        rb_opcion1_no = new javax.swing.JRadioButton();
        rb_opcion1_si = new javax.swing.JRadioButton();
        bt_aceptar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        dlg_log.setTitle("Registro");
        dlg_log.setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        dlg_log.setSize(new java.awt.Dimension(550, 330));

        txa_registro.setEditable(false);
        txa_registro.setColumns(20);
        txa_registro.setRows(5);
        scroll_registro.setViewportView(txa_registro);

        javax.swing.GroupLayout dlg_logLayout = new javax.swing.GroupLayout(dlg_log.getContentPane());
        dlg_log.getContentPane().setLayout(dlg_logLayout);
        dlg_logLayout.setHorizontalGroup(
            dlg_logLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_logLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll_registro, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );
        dlg_logLayout.setVerticalGroup(
            dlg_logLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlg_logLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll_registro, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Organizador de fotografias");

        txf_carpetaOrigen.setFont(txf_carpetaOrigen.getFont());

        bt_elegirCarpetaOrigen.setFont(bt_elegirCarpetaOrigen.getFont());
        bt_elegirCarpetaOrigen.setText("Seleccionar carpeta origen");
        bt_elegirCarpetaOrigen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_elegirCarpetaOrigenActionPerformed(evt);
            }
        });

        bt_elegirCarpetaDestino.setFont(bt_elegirCarpetaDestino.getFont());
        bt_elegirCarpetaDestino.setText("Seleccionar carpeta destino");
        bt_elegirCarpetaDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_elegirCarpetaDestinoActionPerformed(evt);
            }
        });

        txf_carpetaDestino.setFont(txf_carpetaDestino.getFont());

        lb_opciones.setFont(lb_opciones.getFont().deriveFont(lb_opciones.getFont().getStyle() | java.awt.Font.BOLD, lb_opciones.getFont().getSize()+4));
        lb_opciones.setText("Opciones");

        lb_opcion1.setFont(lb_opcion1.getFont());
        lb_opcion1.setText("Buscar archivos dentro de carpetas");

        opcion1.add(rb_opcion1_no);
        rb_opcion1_no.setFont(rb_opcion1_no.getFont());
        rb_opcion1_no.setText("NO");

        opcion1.add(rb_opcion1_si);
        rb_opcion1_si.setFont(rb_opcion1_si.getFont());
        rb_opcion1_si.setSelected(true);
        rb_opcion1_si.setText("SI");

        bt_aceptar.setFont(bt_aceptar.getFont());
        bt_aceptar.setText("Aceptar");
        bt_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_aceptarActionPerformed(evt);
            }
        });

        jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()+10));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Organizador de fotografias por Metadatos");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lb_opcion1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_opciones, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(92, 92, 92)
                        .addComponent(rb_opcion1_si)
                        .addGap(15, 15, 15)
                        .addComponent(rb_opcion1_no)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bt_aceptar)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(bt_elegirCarpetaOrigen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bt_elegirCarpetaDestino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txf_carpetaOrigen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txf_carpetaDestino, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_elegirCarpetaOrigen)
                    .addComponent(txf_carpetaOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_elegirCarpetaDestino)
                    .addComponent(txf_carpetaDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_opciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_opcion1)
                    .addComponent(rb_opcion1_no)
                    .addComponent(rb_opcion1_si))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bt_aceptar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bt_elegirCarpetaOrigenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_elegirCarpetaOrigenActionPerformed
        File fichero = elegirFichero();
        if (fichero!=null){
            carpetaOrigen = fichero;
            txf_carpetaOrigen.setText(fichero.getAbsolutePath());
        }
    }//GEN-LAST:event_bt_elegirCarpetaOrigenActionPerformed

    private void bt_elegirCarpetaDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_elegirCarpetaDestinoActionPerformed
        File fichero = elegirFichero();
        if (fichero!=null){
            carpetaDestino = fichero;
            txf_carpetaDestino.setText(fichero.getAbsolutePath());
        }
    }//GEN-LAST:event_bt_elegirCarpetaDestinoActionPerformed

    private void bt_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_aceptarActionPerformed

        if (!txf_carpetaOrigen.getText().equals(carpetaOrigen.getAbsoluteFile())){
            File nuevaCarpetaOrigen = new File(txf_carpetaOrigen.getText());
            if (nuevaCarpetaOrigen.exists()){
                carpetaOrigen = nuevaCarpetaOrigen;
                if (!txf_carpetaDestino.getText().equals(carpetaDestino.getAbsoluteFile())){
                     File nuevaCarpetaDestino = new File(txf_carpetaDestino.getText());
                      if (nuevaCarpetaDestino.exists()){
                          carpetaDestino = nuevaCarpetaDestino;
                          inicar();
                      }else{
                          JOptionPane.showMessageDialog(null, "La nueva ruta de la carpeta destino no es correcta", "Ruta incorrecta", JOptionPane.WARNING_MESSAGE);
                      }
                }else{
                    inicar();
                }
            }else{
               JOptionPane.showMessageDialog(null, "La nueva ruta de la carpeta origen no es correcta", "Ruta incorrecta", JOptionPane.WARNING_MESSAGE); 
            }
        }
    }//GEN-LAST:event_bt_aceptarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_aceptar;
    private javax.swing.JButton bt_elegirCarpetaDestino;
    private javax.swing.JButton bt_elegirCarpetaOrigen;
    private javax.swing.JDialog dlg_log;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lb_opcion1;
    private javax.swing.JLabel lb_opciones;
    private javax.swing.JRadioButton rb_opcion1_no;
    private javax.swing.JRadioButton rb_opcion1_si;
    private javax.swing.JScrollPane scroll_registro;
    private javax.swing.JTextArea txa_registro;
    private javax.swing.JTextField txf_carpetaDestino;
    private javax.swing.JTextField txf_carpetaOrigen;
    // End of variables declaration//GEN-END:variables
}
