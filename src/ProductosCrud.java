import java.sql.SQLException;
import java.util.ArrayList;

public class ProductosCrud extends CrudBase{
    public ProductosCrud(String allRecordsQuery) {
        super(allRecordsQuery);
    }

    @Override
    protected void prepareInsertStatement(ArrayList<Object> data) throws SQLException {
        insertStatement = con.prepareStatement("INSERT into productos (NombreProducto, MarcaProducto, CategoriaProducto, PrecioProducto, StockProducto) values (?,?,?,?,?) ");
        insertStatement.setString(1, data.get(0).toString());
        insertStatement.setString(2, data.get(1).toString());
        insertStatement.setString(3, data.get(2).toString());
        insertStatement.setInt(4, (int)data.get(3));
        insertStatement.setInt(5, (int)data.get(4));
    }

    @Override
    protected void prepareDeleteStatement(int id) throws SQLException {
        deleteStatement = con.prepareStatement("DELETE FROM productos WHERE idProducto = ? ");
        deleteStatement.setInt(1, id);
    }

    @Override
    protected void prepareUpdateStatement(ArrayList<Object> data, int id) throws SQLException {
        updateStatement = con.prepareStatement("UPDATE productos set NombreProducto = ?, MarcaProducto = ?, CategoriaProducto = ?, PrecioProducto = ?, StockProducto = ? WHERE idProducto = ? ");
        updateStatement.setString(1, data.get(0).toString());
        updateStatement.setString(2, data.get(1).toString());
        updateStatement.setString(3, data.get(2).toString());
        updateStatement.setInt(4, (int)data.get(3));
        updateStatement.setInt(5, (int)data.get(4));
        updateStatement.setInt(6, id);
    }
}
