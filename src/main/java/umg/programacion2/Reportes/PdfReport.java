package umg.programacion2.Reportes;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import umg.programacion2.DataBase.Model.ProductoModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class PdfReport {
    private static final Font TITLE_FONT = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

    public void generateProductReport(List<ProductoModel> productos, String outputPath) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        addTitle(document);
        addProductTable(document, productos);

        document.close();
    }

    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Reporte de Aguacates", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);
    }

    private void addProductTable(Document document, List<ProductoModel> productos) throws DocumentException {
        PdfPTable table = new PdfPTable(6); // 4 columnas para id, descripciÃ³n, origen y precio
        table.setWidthPercentage(100);
        addTableHeader(table);
        addRows(table, productos);
        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
        // Este cÃ³digo utiliza la clase Stream de Java para crear un flujo de datos con los tÃ­tulos de las columnas de una tabla PDF. Luego,
        // para cada tÃ­tulo de columna, se crea una celda de encabezado en la tabla con ciertas propiedades (color de fondo, ancho del borde, y texto).



        Stream.of("ID", "Descripción", "Origen","Precio","Existencia","Precio Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.CYAN);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle, HEADER_FONT));
                    table.addCell(header);
                });

        //Stream.of("ID", "DescripciÃ³n", "Origen", "Precio"):
        //Crea un flujo (Stream) de datos con los elementos "ID", "DescripciÃ³n", "Origen", y "Precio".


//        Este cÃ³digo utiliza la clase Stream de Java para crear un flujo de datos con los tÃ­tulos de las columnas de una tabla PDF. Luego, para cada tÃ­tulo de columna, se crea una celda de encabezado en la tabla con ciertas propiedades (color de fondo, ancho del borde, y texto).
//                ExplicaciÃ³n del CÃ³digo
//        Stream.of("ID", "DescripciÃ³n", "Origen", "Precio"):
//        Crea un flujo (Stream) de datos con los elementos "ID", "DescripciÃ³n", "Origen", y "Precio".
//.forEach(columnTitle -> { ... }):
//        Para cada elemento en el flujo (en este caso, cada tÃ­tulo de columna), ejecuta el bloque de cÃ³digo dentro de las llaves { ... }.
//        Dentro del bloque forEach:
//        PdfPCell header = new PdfPCell();: Crea una nueva celda para la tabla PDF.
//        header.setBackgroundColor(BaseColor.LIGHT_GRAY);: Establece el color de fondo de la celda a gris claro.
//                header.setBorderWidth(2);: Establece el ancho del borde de la celda a 2 puntos.
//                header.setPhrase(new Phrase(columnTitle, HEADER_FONT));: Establece el texto de la celda con el tÃ­tulo de la columna y la fuente de encabezado.
//                table.addCell(header);: AÃ±ade la celda a la tabla.
//Â¿QuÃ© es Stream.of?
//                Stream.of es un mÃ©todo estÃ¡tico de la clase Stream en Java que se utiliza para crear un flujo (Stream) a partir de una secuencia de elementos. En este caso, se estÃ¡ utilizando para crear un flujo de cadenas de texto ("ID", "DescripciÃ³n", "Origen", "Precio").
//        CÃ³digo

    } //Fin de addTableHeader

    private void addRows(PdfPTable table, List<ProductoModel> productos) {
        for (ProductoModel producto : productos) {
            table.addCell(new Phrase(String.valueOf(producto.getIdProducto()), NORMAL_FONT));
            table.addCell(new Phrase(producto.getDescripcion(), NORMAL_FONT));
            table.addCell(new Phrase(producto.getOrigen(), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getPrecio()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getExistencia()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf((producto.getExistencia() * producto.getPrecio())), NORMAL_FONT));
            //table.addCell(new Phrase(String.format("Q%.2f", producto.getPrecio()), NORMAL_FONT));
        }
    }
}