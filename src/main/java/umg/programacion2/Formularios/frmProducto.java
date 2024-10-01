package umg.programacion2.Formularios;

import umg.programacion2.DataBase.Model.ProductoModel;
import umg.programacion2.DataBase.Service.ProductoService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private ProductoService productoService;

    public frmProducto() {
        // Instanciar el servicio de Producto
        productoService = new ProductoService();

        // Configuración del JFrame
        setContentPane(panel1);
        setTitle("Gestión de Productos");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Agregar opciones al comboBox (Origen)
        comboBoxOrigen.addItem("Estados Unidos");
        comboBoxOrigen.addItem("Corea del Sur");
        comboBoxOrigen.addItem("Vietnam");
        comboBoxOrigen.addItem("Colombia");
        comboBoxOrigen.addItem("Italia");
        comboBoxOrigen.addItem("Japón");
        comboBoxOrigen.addItem("Suiza");
        comboBoxOrigen.addItem("México");
        comboBoxOrigen.addItem("España");
        comboBoxOrigen.addItem("Argentina");
        comboBoxOrigen.addItem("Noruega");
        comboBoxOrigen.addItem("Suecia");
        comboBoxOrigen.addItem("Francia");
        comboBoxOrigen.addItem("Irlanda");
        comboBoxOrigen.addItem("China");
        comboBoxOrigen.addItem("Alemania");
        comboBoxOrigen.addItem("Escocia");

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
                    int confirm = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        productoService.eliminarProducto(idProducto);
                        JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente");
                        limpiarCampos();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Código inválido");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + ex.getMessage());
                }
            }
        });
    }

    // Método para limpiar los campos del formulario
    private void limpiarCampos() {
        textFieldCodigo.setText("");
        textFieldNombre.setText("");
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
