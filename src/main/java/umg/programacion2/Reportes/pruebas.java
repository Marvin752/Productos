package umg.programacion2.Reportes;

import umg.programacion2.DataBase.Model.ProductoModel;
import umg.programacion2.DataBase.Service.ProductoService;

import javax.swing.*;
import java.util.List;

public class pruebas {
    public static void main(String[] args) {
        try{
            //List<ProductoModel> prod = new ProductoService().obtenerTodosLosProductos();
            List<ProductoModel> prod = new ProductoService().obtenerGenericos("precio > 30");
            //new PdfReport().generateProductReport(prod,"C:\\PdfProgra\\reporte.pdf");

            //List<ProductoModel> prodigio = new ProductoService().obtenerGenericos("precio > 30");
            new PdfReport().generateProductReport(prod,"C:\\PdfProgra\\reporte.pdf");
            
            //Mostrar un mensaje de que se genero el reporte con el Jpanel
            JOptionPane.showMessageDialog(null, "Reporte generado en C:\\PdfProgra");
        }catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
    }
}
