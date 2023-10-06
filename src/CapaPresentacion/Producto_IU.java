/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package CapaPresentacion;

import CapaDatos.Categoria;
import CapaDatos.Marca;
import CapaDatos.Medida;
import CapaDatos.Producto;
import CapaNegocio.CategoriaBD;
import CapaNegocio.MarcaBD;
import CapaNegocio.MedidaBD;
import CapaNegocio.ProductoBD;
import static CapaPresentacion.Menu_IU.escritorio;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author labor
 */
public class Producto_IU extends javax.swing.JInternalFrame {

    List<Categoria> lista_categoria;
    List<Marca> lista_marca;
    List<Medida> lista_medida;

    /**
     * Creates new form Producto_IU
     */
    public Producto_IU() {
        initComponents();
        cargarCategoria();
        cargarMarca();
        cargarMedida();
        reportar();
        deshabilitar();
    }

    private void exito(String mensaje) {

        JOptionPane.showConfirmDialog(this, mensaje, "MENSAJE", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    private void error(String mensaje) {
        JOptionPane.showConfirmDialog(this, mensaje, "ERROR", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    private void advertencia(String mensaje) {
        JOptionPane.showConfirmDialog(this, mensaje, "ADVERTENCIA", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    private void reportar() {

        try {
            setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

            limpiar_tabla_formulario();
            DefaultTableModel tabla_temporal;
            ProductoBD oProductoBD = new ProductoBD();

            tabla_temporal = oProductoBD.reportarProducto();

            int cantidad_productos_encontrados = tabla_temporal.getRowCount();
            txtCantidad.setText("" + cantidad_productos_encontrados);

            DefaultTableModel tabla_temporal_producto = (DefaultTableModel) this.tabla_reporte_producto.getModel();

            for (int i = 0; i < cantidad_productos_encontrados; i++) {

                String serie = tabla_temporal.getValueAt(i, 0).toString();
                String descripcion = tabla_temporal.getValueAt(i, 1).toString();
                String observacion = tabla_temporal.getValueAt(i, 2).toString();
                String digemi = tabla_temporal.getValueAt(i, 3).toString();
                String condicion = tabla_temporal.getValueAt(i, 4).toString();
                String categoria = tabla_temporal.getValueAt(i, 5).toString();
                String marca = tabla_temporal.getValueAt(i, 6).toString();
                String medida = tabla_temporal.getValueAt(i, 7).toString();

                Object[] data = {serie, descripcion, observacion, digemi, condicion, categoria, marca, medida};
                tabla_temporal_producto.addRow(data);

            }
            tabla_reporte_producto.setModel(tabla_temporal_producto);

            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        } catch (Exception ex) {
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            ex.printStackTrace();

        }

    }

    private void cargarMedida() {

        try {

            cmbMedida.removeAllItems();
            MedidaBD oMedidaBD = new MedidaBD();
            DefaultTableModel tabla_temporal;
            tabla_temporal = oMedidaBD.reportarMedida();

            lista_medida = new ArrayList<>();

            cmbMedida.addItem("Seleccionar");
            for (int i = 0; i < tabla_temporal.getRowCount(); i++) {

                int codigo = Integer.valueOf(tabla_temporal.getValueAt(i, 0).toString());
                String presentacion = String.valueOf(tabla_temporal.getValueAt(i, 1));
                String equivalencia = String.valueOf(tabla_temporal.getValueAt(i, 2));

                lista_medida.add(new Medida(codigo, presentacion, equivalencia));
                cmbMedida.addItem(presentacion);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error al cargar medida", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cargarMarca() {

        try {

            cmbMarca.removeAllItems();
            MarcaBD oMarcaBD = new MarcaBD();
            DefaultTableModel tabla_temporal;
            tabla_temporal = oMarcaBD.reportarMarca();

            lista_marca = new ArrayList<>();

            cmbMarca.addItem("Seleccionar");
            for (int i = 0; i < tabla_temporal.getRowCount(); i++) {

                int codigo = Integer.valueOf(tabla_temporal.getValueAt(i, 0).toString());
                String nombre = String.valueOf(tabla_temporal.getValueAt(i, 1));

                lista_marca.add(new Marca(codigo, nombre));
                cmbMarca.addItem(nombre);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error al cargar marca", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cargarCategoria() {

        try {

            cmbCategoria.removeAllItems();
            CategoriaBD oCategoriaBD = new CategoriaBD();

            lista_categoria = oCategoriaBD.reportarCategoria();

            cmbCategoria.addItem("Seleccionar");
            for (int i = 0; i < lista_categoria.size(); i++) {

                int idcategoria = lista_categoria.get(i).getIdcategoria();
                String nombre = lista_categoria.get(i).getCaNombre();

                cmbCategoria.addItem(nombre);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Error al cargar categoria", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void habilitar() {
        btnBuscar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnModificar.setEnabled(true);
        btnRegistrarrkk.setEnabled(true);

    }

    private void deshabilitar() {
        btnBuscar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnRegistrarrkk.setEnabled(false);

    }

    private void limpiar() {

        cmbCategoria.setSelectedIndex(0);
        cmbMarca.setSelectedIndex(0);
        cmbMedida.setSelectedIndex(0);

        txtSerie.setText("");
        txtDescripcion.setText("");
        txtObservacion.setText("");
        txtDigemi.setText("");
        txtIdCategoria.setText("");
        txtIdMarca.setText("");
        txtIdMedida.setText("");

    }

    private void limpiar_tabla_formulario() {

        DefaultTableModel tabla_temporal_productos = (DefaultTableModel) tabla_reporte_producto.getModel();
        tabla_temporal_productos.setRowCount(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField8 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIdMedida = new javax.swing.JTextField();
        txtIdMarca = new javax.swing.JTextField();
        txtIdCategoria = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSerie = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtObservacion = new javax.swing.JTextField();
        txtDigemi = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cmbCondicion = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cmbCategoria = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cmbMarca = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cmbMedida = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtProducto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_reporte_producto = new javax.swing.JTable();
        btnComposicion = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnRegistrarrkk = new javax.swing.JButton();

        jTextField8.setText("jTextField8");

        setClosable(true);
        setTitle("MANTENIMIENTO DE PRODUCTOS");

        jLabel1.setText("SERIE");

        jLabel2.setText("DESCRIPCION");

        jLabel3.setText("OBSERVACION");

        txtIdMedida.setEnabled(false);

        txtIdMarca.setEnabled(false);

        txtIdCategoria.setEnabled(false);

        jLabel4.setText("DIGEMI");

        txtSerie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSerieKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSerieKeyTyped(evt);
            }
        });

        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyPressed(evt);
            }
        });

        txtObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtObservacionKeyPressed(evt);
            }
        });

        txtDigemi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDigemiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDigemiKeyTyped(evt);
            }
        });

        jLabel5.setText("CONDICION");

        cmbCondicion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NORMAL", "POR RECETA MEDICA" }));
        cmbCondicion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbCondicionKeyPressed(evt);
            }
        });

        jLabel6.setText("CATEGORIA");

        cmbCategoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCategoriaItemStateChanged(evt);
            }
        });
        cmbCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbCategoriaKeyPressed(evt);
            }
        });

        jLabel7.setText("LABORATORIO");

        cmbMarca.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMarcaItemStateChanged(evt);
            }
        });
        cmbMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMarcaKeyPressed(evt);
            }
        });

        jLabel8.setText("MEDIDA");

        cmbMedida.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMedidaItemStateChanged(evt);
            }
        });
        cmbMedida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMedidaKeyPressed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Buscar Productos"));

        jLabel9.setText("PRODUCTO");

        txtProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProductoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtProducto)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        tabla_reporte_producto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "SERIE", "DESCRIPCION", "OBSERVACION", "DIGEMI", "CONDICION", "CATEGORIA", "MARCA", "MEDIDA"
            }
        ));
        tabla_reporte_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabla_reporte_productoMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_reporte_producto);

        btnComposicion.setText("COMPOSICION");
        btnComposicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComposicionActionPerformed(evt);
            }
        });

        jLabel10.setText("CANTIDAD");

        txtCantidad.setEnabled(false);

        btnRegistrar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/application_1.png"))); // NOI18N
        btnNuevo.setText("NUEVO");
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/report_magnify.png"))); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.setEnabled(false);
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/report_edit.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.setEnabled(false);
        btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/bin_empty.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setEnabled(false);
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cross.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCerrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        btnRegistrarrkk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/disk.png"))); // NOI18N
        btnRegistrarrkk.setText("REGISTRAR");
        btnRegistrarrkk.setEnabled(false);
        btnRegistrarrkk.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegistrarrkk.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRegistrarrkk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarrkkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnRegistrarLayout = new javax.swing.GroupLayout(btnRegistrar);
        btnRegistrar.setLayout(btnRegistrarLayout);
        btnRegistrarLayout.setHorizontalGroup(
            btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnRegistrarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnRegistrarLayout.createSequentialGroup()
                        .addComponent(btnModificar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnRegistrarLayout.createSequentialGroup()
                        .addGroup(btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, btnRegistrarLayout.createSequentialGroup()
                                .addGroup(btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnRegistrarrkk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        btnRegistrarLayout.setVerticalGroup(
            btnRegistrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnRegistrarLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnNuevo)
                .addGap(49, 49, 49)
                .addComponent(btnRegistrarrkk)
                .addGap(55, 55, 55)
                .addComponent(btnBuscar)
                .addGap(44, 44, 44)
                .addComponent(btnModificar)
                .addGap(38, 38, 38)
                .addComponent(btnEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(btnCerrar)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(157, 1003, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtIdMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(txtIdMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(txtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtDescripcion))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnComposicion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addGap(40, 40, 40)
                                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtObservacion)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtDigemi, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbMarca, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cmbMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(18, 18, 18)))
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDigemi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cmbCondicion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(cmbMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(cmbMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnComposicion)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCategoriaItemStateChanged
        // TODO add your handling code here:

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String textoSeleccionado = (String) cmbCategoria.getSelectedItem();
            if (textoSeleccionado.equals("Seleccionar")) {
                txtIdCategoria.setText("");

            } else {
                int i = cmbCategoria.getSelectedIndex() - 1;
                txtIdCategoria.setText("" + lista_categoria.get(i).getIdcategoria());
            }

        }

    }//GEN-LAST:event_cmbCategoriaItemStateChanged

    private void cmbMarcaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMarcaItemStateChanged
        // TODO add your handling code here:

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String textoSeleccionado = (String) cmbMarca.getSelectedItem();
            if (textoSeleccionado.equals("Seleccionar")) {
                txtIdMarca.setText("");

            } else {
                int i = cmbMarca.getSelectedIndex() - 1;
                txtIdMarca.setText("" + lista_marca.get(i).getIdmarca());
            }

        }
    }//GEN-LAST:event_cmbMarcaItemStateChanged

    private void cmbMedidaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMedidaItemStateChanged
        // TODO add your handling code here:

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String textoSeleccionado = (String) cmbMedida.getSelectedItem();
            if (textoSeleccionado.equals("Seleccionar")) {
                txtIdMedida.setText("");

            } else {
                int i = cmbMedida.getSelectedIndex() - 1;
                txtIdMedida.setText("" + lista_medida.get(i).getIdmedida());
            }

        }
    }//GEN-LAST:event_cmbMedidaItemStateChanged

    private void btnRegistrarrkkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarrkkActionPerformed
        // TODO add your handling code here:
        if (txtSerie.getText().length() > 0) {
            if (txtDescripcion.getText().length() > 0) {
                if (txtIdCategoria.getText().length() > 0) {
                    if (txtIdMarca.getText().length() > 0) {
                        if (txtIdMedida.getText().length() > 0) {

                            Producto oProducto = new Producto();
                            ProductoBD oProductoBD = new ProductoBD();

                            oProducto.setpSerie(txtSerie.getText().trim());
                            oProducto.setpDescripcion(txtDescripcion.getText().toUpperCase().trim());
                            oProducto.setpObservacion(txtObservacion.getText().toUpperCase());
                            oProducto.setDigemi(txtDigemi.getText().toUpperCase());
                            oProducto.setpCondicion(cmbCondicion.getSelectedItem().toString());
                            oProducto.setIdcategoria(Integer.parseInt(txtIdCategoria.getText()));
                            oProducto.setIdmarca(Integer.parseInt(txtIdMarca.getText()));
                            oProducto.setIdmedida(Integer.parseInt(txtIdMedida.getText()));

                            boolean rpta = oProductoBD.registrarProducto(oProducto);
                            if (rpta) {
                                exito("Se registro con exito");
                                reportar();
                                limpiar();
                                deshabilitar();

                            } else {
                                error("Tienes problemas al registrar");
                            }

                        } else {
                            error("Seleccione una medida");
                        }

                    } else {
                        error("Seleccione un laboratorio");
                    }

                } else {
                    error("Seleccione una categoria");

                }

            } else {
                error("Ingrese la descripcion del producto");
                txtDescripcion.requestFocus();
            }

        } else {
            error("Ingrese el codigo del producto");
            txtSerie.requestFocus();

        }


    }//GEN-LAST:event_btnRegistrarrkkActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        if (txtSerie.getText().length() > 0) {

            String serie = txtSerie.getText();

            DefaultTableModel tabla_temporal;
            ProductoBD oProductoBD = new ProductoBD();

            tabla_temporal = oProductoBD.buscarProducto(serie);

            int cantidad_productos_encontrados = tabla_temporal.getRowCount();
            txtCantidad.setText("" + cantidad_productos_encontrados);

            limpiar_tabla_formulario();

            if (cantidad_productos_encontrados > 0) {

                txtDescripcion.setText(tabla_temporal.getValueAt(0, 1).toString());
                txtObservacion.setText(tabla_temporal.getValueAt(0, 2).toString());
                txtDigemi.setText(tabla_temporal.getValueAt(0, 3).toString());
                cmbCondicion.setSelectedItem(tabla_temporal.getValueAt(0, 4).toString());
                cmbMedida.setSelectedItem(tabla_temporal.getValueAt(0, 7).toString());
                cmbMarca.setSelectedItem(tabla_temporal.getValueAt(0, 6));
                cmbCategoria.setSelectedItem(tabla_temporal.getValueAt(0, 5));
                txtIdMedida.setText(tabla_temporal.getValueAt(0, 10).toString());
                txtIdCategoria.setText(tabla_temporal.getValueAt(0, 8).toString());
                txtIdMarca.setText(tabla_temporal.getValueAt(0, 9).toString());

                DefaultTableModel tabla_temporal_producto = (DefaultTableModel) this.tabla_reporte_producto.getModel();
                for (int i = 0; i < cantidad_productos_encontrados; i++) {

                    serie = tabla_temporal.getValueAt(i, 0).toString();
                    String descripcion = tabla_temporal.getValueAt(i, 1).toString();
                    String observacion = tabla_temporal.getValueAt(i, 2).toString();
                    String digemi = tabla_temporal.getValueAt(i, 3).toString();
                    String condicion = tabla_temporal.getValueAt(i, 4).toString();
                    String categoria = tabla_temporal.getValueAt(i, 5).toString();
                    String marca = tabla_temporal.getValueAt(i, 6).toString();
                    String medida = tabla_temporal.getValueAt(i, 7).toString();

                    Object[] data = {serie, descripcion, observacion, digemi, condicion, categoria, marca, medida};
                    tabla_temporal_producto.addRow(data);

                }
                tabla_reporte_producto.setModel(tabla_temporal_producto);

            } else {

                JOptionPane.showMessageDialog(this, "No se encontro el peoducto buscado");
                txtSerie.requestFocus();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Ingrese serie..");
            txtSerie.requestFocus();
        }


    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:

        if (txtSerie.getText().length() > 0) {
            if (txtDescripcion.getText().length() > 0) {
                if (txtIdCategoria.getText().length() > 0) {
                    if (txtIdMarca.getText().length() > 0) {
                        if (txtIdMedida.getText().length() > 0) {

                            Producto oProducto = new Producto();
                            ProductoBD oProductoBD = new ProductoBD();

                            oProducto.setpSerie(txtSerie.getText().trim());
                            oProducto.setpDescripcion(txtDescripcion.getText().toUpperCase().trim());
                            oProducto.setpObservacion(txtObservacion.getText().toUpperCase());
                            oProducto.setDigemi(txtDigemi.getText().toUpperCase());
                            oProducto.setpCondicion(cmbCondicion.getSelectedItem().toString());
                            oProducto.setIdcategoria(Integer.parseInt(txtIdCategoria.getText()));
                            oProducto.setIdmarca(Integer.parseInt(txtIdMarca.getText()));
                            oProducto.setIdmedida(Integer.parseInt(txtIdMedida.getText()));

                            boolean rpta = oProductoBD.modificarProducto(oProducto);
                            if (rpta) {
                                exito("Se modifico con exito");
                                reportar();
                                limpiar();
                                deshabilitar();

                            } else {
                                error("Tienes problemas al modificar");
                            }

                        } else {
                            error("Seleccione una medida");
                        }

                    } else {
                        error("Seleccione un laboratorio");
                    }

                } else {
                    error("Seleccione una categoria");

                }

            } else {
                error("Ingrese la descripcion del producto");
                txtDescripcion.requestFocus();
            }

        } else {
            error("Ingrese el codigo del producto");
            txtSerie.requestFocus();

        }


    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:

        try {
            if (txtSerie.getText().length() > 0) {

                String serie = txtSerie.getText();

                int aviso = JOptionPane.showConfirmDialog(rootPane, "Estas seguro de eliminar");
                if (aviso == 0) {

                    ProductoBD oProductoBD = new ProductoBD();

                    boolean rpta = oProductoBD.eliminarProducto(serie);
                    if (rpta) {

                        exito("Se elimino el producto");
                        reportar();
                        limpiar();
                        deshabilitar();
                        txtSerie.requestFocus();

                    } else {
                        error("Tienes problemas al eliminar  el producto");
                    }

                }

            } else {
                error("Falta codigo del producto a eliminar");
                txtSerie.requestFocus();

            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
        }


    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductoKeyPressed
        // TODO add your handling code here:

        limpiar_tabla_formulario();

        String descripcion = txtProducto.getText();
        DefaultTableModel tabla_temporal;
        DefaultTableModel tabla_temporal_producto = (DefaultTableModel) tabla_reporte_producto.getModel();
        ProductoBD oProductoBD = new ProductoBD();
        tabla_temporal = oProductoBD.buscarProductoDescripcion(descripcion);
        int cant = tabla_temporal.getRowCount();

        for (int i = 0; i < cant; i++) {
            String serie = tabla_temporal.getValueAt(i, 0).toString();
            descripcion = tabla_temporal.getValueAt(i, 1).toString();
            String observacion = tabla_temporal.getValueAt(i, 2).toString();
            String digemi = tabla_temporal.getValueAt(i, 3).toString();
            String condicion = tabla_temporal.getValueAt(i, 4).toString();
            String categoria = tabla_temporal.getValueAt(i, 5).toString();
            String marca = tabla_temporal.getValueAt(i, 6).toString();
            String medida = tabla_temporal.getValueAt(i, 7).toString();

            Object[] data = {serie, descripcion, observacion, digemi, condicion, categoria, marca, medida};
            tabla_temporal_producto.addRow(data);

        }
        tabla_reporte_producto.setModel(tabla_temporal_producto);
        txtCantidad.setText("" + cant);


    }//GEN-LAST:event_txtProductoKeyPressed

    private void tabla_reporte_productoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_reporte_productoMousePressed
        // TODO add your handling code here:


    }//GEN-LAST:event_tabla_reporte_productoMousePressed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        habilitar();
        limpiar();
        txtSerie.requestFocus();
        reportar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnComposicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComposicionActionPerformed
        // TODO add your handling code here:
        Composicion_IU frame = new Composicion_IU();
        escritorio.add(frame);
        Dimension deskopSize = escritorio.getSize();
        Dimension FrameSize = frame.getSize();
        frame.setLocation((deskopSize.width - FrameSize.width) / 2, (deskopSize.height - FrameSize.height) / 2);

        frame.show();
    }//GEN-LAST:event_btnComposicionActionPerformed

    private void txtSerieKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSerieKeyPressed
        // TODO add your handling code here:
        if (evt.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            txtDescripcion.requestFocus();
        }
    }//GEN-LAST:event_txtSerieKeyPressed

    private void txtDescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyPressed
        // TODO add your handling code here:
        if (evt.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            txtObservacion.requestFocus();
        }
    }//GEN-LAST:event_txtDescripcionKeyPressed

    private void txtObservacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionKeyPressed
        // TODO add your handling code here:

        if (evt.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            txtDigemi.requestFocus();
        }
    }//GEN-LAST:event_txtObservacionKeyPressed

    private void txtDigemiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDigemiKeyPressed
        // TODO add your handling code here:
        if (evt.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            cmbCondicion.requestFocus();
        }
    }//GEN-LAST:event_txtDigemiKeyPressed

    private void cmbCondicionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbCondicionKeyPressed
        // TODO add your handling code here:

        if (evt.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            cmbCategoria.requestFocus();
        }
    }//GEN-LAST:event_cmbCondicionKeyPressed

    private void cmbCategoriaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbCategoriaKeyPressed
        // TODO add your handling code here:

        if (evt.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            cmbMarca.requestFocus();
        }
    }//GEN-LAST:event_cmbCategoriaKeyPressed

    private void cmbMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMarcaKeyPressed
        // TODO add your handling code here:

        if (evt.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            cmbMedida.requestFocus();
        }
    }//GEN-LAST:event_cmbMarcaKeyPressed

    private void cmbMedidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMedidaKeyPressed
        // TODO add your handling code here:

        if (evt.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
            btnRegistrarrkk.doClick();
        }
    }//GEN-LAST:event_cmbMedidaKeyPressed

    private void txtSerieKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSerieKeyTyped
        // TODO add your handling code here:

        char c = evt.getKeyChar();
        if (!Character.isDigit(c) || txtSerie.getText().length() >= 15) {

            evt.consume();

        }
    }//GEN-LAST:event_txtSerieKeyTyped

    private void txtDigemiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDigemiKeyTyped
        // TODO add your handling code here:

        char type = evt.getKeyChar();
        if (!Character.isLetter(type) && type != KeyEvent.VK_SPACE) {
            getToolkit().beep();
            evt.consume();

        }
    }//GEN-LAST:event_txtDigemiKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnComposicion;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JPanel btnRegistrar;
    private javax.swing.JButton btnRegistrarrkk;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbCondicion;
    private javax.swing.JComboBox<String> cmbMarca;
    private javax.swing.JComboBox<String> cmbMedida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTable tabla_reporte_producto;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDigemi;
    private javax.swing.JTextField txtIdCategoria;
    private javax.swing.JTextField txtIdMarca;
    private javax.swing.JTextField txtIdMedida;
    private javax.swing.JTextField txtObservacion;
    private javax.swing.JTextField txtProducto;
    private javax.swing.JTextField txtSerie;
    // End of variables declaration//GEN-END:variables
}
