package umg.programacion2.Formularios;

import umg.programacion2.DataBase.Model.ProductoModel;
import umg.programacion2.DataBase.Service.ProductoService;
import umg.programacion2.Reportes.PdfReport;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class frmProducto extends JFrame {
    private JPanel panel1;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblOrigen;
    private JTextField textFieldCodigo;
    private JTextField textFieldNombre;
    private JComboBox<String> comboBoxOrigen;
    private JButton buttonBuscar;
    private JButton buttonGuardar;
    private JButton buttonActualizar;
    private JButton buttonBorrar;
    private JButton buttonPDF;
    private JLabel lblPrecio;
    private JLabel lblExistencia;
    private JTextField textFieldExistencia;
    private JTextField textFieldPrecio;
    private JLabel lblReportes;
    private JComboBox comboBoxReportes;

    private ProductoService productoService;

    public frmProducto() {
        // Instanciar el servicio de Producto
        productoService = new ProductoService();

        // Configuración del JFrame
        setContentPane(panel1);
        setTitle("Gestión de Productos");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        //Definir las opciones en un array

        String[] opcionesOrigen = {
                "Estados Unidos", "Corea del Sur", "Vietnam", "Colombia", "Italia",
                "Japón", "Suiza", "México", "España", "Argentina",
                "Noruega", "Suecia", "Francia", "Irlanda", "China",
                "Alemania", "Escocia"
        };

        // Agregar opciones al comboBox (Origen)
        for (String opcion : opcionesOrigen) {
            comboBoxOrigen.addItem(opcion);
        }

        // Definir las opciones en un array
        String[] opcionesReportes = {
                "Reporte General",
                "Precio menores a 100",
                "Existencia menor a 30 unidades",
                "Precio entre 200 y 400",
                "Ordenar de precio mayor a menor",
                "Ordenar de  precio menor a mayor"
        };

        // Agregar las opciones al comboBox en un bucle
        for (String opcion : opcionesReportes) {
            comboBoxReportes.addItem(opcion);
        }

        // Acción del botón Guardar
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Guardar producto
                String descripcion = textFieldNombre.getText();
                String origen = comboBoxOrigen.getSelectedItem().toString();

                ProductoModel producto = new ProductoModel();
                producto.setDescripcion(descripcion);
                producto.setOrigen(origen);

                try {
                    productoService.agregarProducto(producto.getDescripcion(), producto.getOrigen());
                    JOptionPane.showMessageDialog(null, "Producto guardado exitosamente");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar el producto: " + ex.getMessage());
                }
            }
        });

        // Acción del botón Buscar
        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Buscar producto por ID
                try {
                    int idProducto = Integer.parseInt(textFieldCodigo.getText());
                    ProductoModel producto = productoService.obtenerProductoPorId(idProducto);

                    if (producto != null) {
                        textFieldNombre.setText(producto.getDescripcion());
                        comboBoxOrigen.setSelectedItem(producto.getOrigen());
                        textFieldExistencia.setText(String.valueOf(producto.getExistencia()));
                        textFieldPrecio.setText(String.valueOf(producto.getPrecio()));

                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Código inválido");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar el producto: " + ex.getMessage());
                }
            }
        });

        // Acción del botón Actualizar
        buttonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Actualizar producto
                try {
                    int idProducto = Integer.parseInt(textFieldCodigo.getText());
                    String descripcion = textFieldNombre.getText();
                    String origen = comboBoxOrigen.getSelectedItem().toString();

                    ProductoModel producto = new ProductoModel();
                    producto.setIdProducto(idProducto);
                    producto.setDescripcion(descripcion);
                    producto.setOrigen(origen);

                    productoService.actualizarProducto(producto.getIdProducto(), producto.getDescripcion(), producto.getOrigen());
                    JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Código inválido");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el producto: " + ex.getMessage());
                }
            }
        });

        // Acción del botón Borrar
        buttonBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Borrar producto
                try {
                    int idProducto = Integer.parseInt(textFieldCodigo.getText());
                    ProductoModel producto = productoService.obtenerProductoPorId(idProducto);
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        if(producto.getPrecio() == 0) {
                            productoService.eliminarProducto(idProducto);
                            JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente");
                            limpiarCampos();
                        }else
                        {
                            JOptionPane.showMessageDialog(null, "Error no se puede eliminar un prodcuto si su precio no es de Q0.00");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Código inválido");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + ex.getMessage());
                }
            }
        });
        buttonPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    int ReporteSelccionado = comboBoxReportes.getSelectedIndex();
                    switch (ReporteSelccionado) {
                        case 0:
                            // Acción para "Reporte General"
                            List<ProductoModel> prod = new ProductoService().obtenerTodosLosProductos();
                            new PdfReport().generateProductReport(prod,"C:\\PdfProgra\\reporte.pdf");
                            break;
                        case 1:
                            // Acción para "Precio menores a 100"
                            List<ProductoModel> prod2 = new ProductoService().obtenerGenericos("precio < 100");
                            new PdfReport().generateProductReport(prod2,"C:\\PdfProgra\\reporte.pdf");
                            break;
                        case 2:
                            // Acción para "Existencia menor a 30 unidades"
                            List<ProductoModel> prod3 = new ProductoService().obtenerGenericos("existencia < 30");
                            new PdfReport().generateProductReport(prod3,"C:\\PdfProgra\\reporte.pdf");
                            break;
                        case 3:
                            // Acción para "Precio entre 200 y 400"
                            List<ProductoModel> prod4 = new ProductoService().obtenerGenericos("precio BETWEEN 200 AND 400");
                            new PdfReport().generateProductReport(prod4,"C:\\PdfProgra\\reporte.pdf");
                            break;
                        case 4:
                            // Acción para "Ordenar de mayor a menor"
                            List<ProductoModel> prod5 = new ProductoService().obtenerGenericos("1=1 ORDER BY precio DESC");
                            new PdfReport().generateProductReport(prod5,"C:\\PdfProgra\\reporte.pdf");
                            break;
                        case 5:
                            // Acción para "Ordenar de menor a mayor"
                            List<ProductoModel> prod6 = new ProductoService().obtenerGenericos("1=1 ORDER BY precio ASC");
                            new PdfReport().generateProductReport(prod6,"C:\\PdfProgra\\reporte.pdf");
                            break;
                    }

                    JOptionPane.showMessageDialog(null, "Reporte generado en C:\\PdfProgra");
                }catch (Exception exception)
                {
                    System.out.println("Error: " + exception.getMessage());
                }
            }
        });
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        textFieldCodigo.setText("");
        textFieldNombre.setText("");
        textFieldExistencia.setText("");
        textFieldPrecio.setText("");
        comboBoxOrigen.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frmProducto frame = new frmProducto();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });
    }
}
