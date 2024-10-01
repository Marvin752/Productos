package umg.programacion2.DataBase.Dao;

import umg.programacion2.DataBase.DbConnection.DatabaseConnection;
import umg.programacion2.DataBase.Model.ProductoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductoDao {

    public void agregarProducto(String descripcion, String origen) throws Exception {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO tb_producto (descripcion, origen, precio, existencia) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, descripcion);
        ps.setString(2, origen);

        ps.executeUpdate();
        connection.close();
    }

    public ProductoModel obtenerProductoPorId(int idProducto) throws Exception {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM tb_producto WHERE id_producto = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idProducto);

        ResultSet rs = ps.executeQuery();
        ProductoModel producto = null;
        if (rs.next()) {
            producto = new ProductoModel();
            producto.setIdProducto(rs.getInt("id_producto"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setOrigen(rs.getString("origen"));
            producto.setPrecio(rs.getInt("precio"));
            producto.setExistencia(rs.getInt("existencia"));
        }
        connection.close();
        return producto;
    }



    public void actualizarProducto(int idProducto, String descripcion, String origen) throws Exception {
        Connection connection = DatabaseConnection.getConnection();
        String query = "UPDATE tb_producto SET descripcion = ?, origen = ? WHERE id_producto = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, descripcion);
        ps.setString(2, origen);
        ps.setInt(3, idProducto);

        ps.executeUpdate();
        connection.close();
    }

    public void eliminarProducto(int idProducto) throws Exception {
        Connection connection = DatabaseConnection.getConnection();
        String query = "DELETE FROM tb_producto WHERE id_producto = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, idProducto);

        ps.executeUpdate();
        connection.close();
    }

    public List<ProductoModel> obtenerTodosLosProductos() throws Exception {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM tb_producto order by origen";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<ProductoModel> productos = new ArrayList<>();

        while (rs.next()) {
            ProductoModel producto = new ProductoModel();
            producto.setIdProducto(rs.getInt("id_producto"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setOrigen(rs.getString("origen"));
            producto.setPrecio(rs.getInt("precio"));
            producto.setExistencia(rs.getInt("existencia"));
            productos.add(producto);
        }

        connection.close();
        return productos;
    }

    public List<ProductoModel> obtenerGenericos(String condicion) throws Exception {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM tb_producto where " + condicion;

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<ProductoModel> productos = new ArrayList<>();

        while (rs.next()) {
            ProductoModel producto = new ProductoModel();
            producto.setIdProducto(rs.getInt("id_producto"));
            producto.setDescripcion(rs.getString("descripcion"));
            producto.setOrigen(rs.getString("origen"));
            producto.setPrecio(rs.getInt("precio"));
            producto.setExistencia(rs.getInt("existencia"));
            productos.add(producto);
        }

        connection.close();
        return productos;
    }
}
