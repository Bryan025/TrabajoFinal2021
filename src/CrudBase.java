import java.io.NotActiveException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//
// Clase base para operaciones CRUD
//
public class CrudBase {
    PreparedStatement queryStatement;
    PreparedStatement insertStatement;
    PreparedStatement updateStatement;
    PreparedStatement deleteStatement;
    //
    // Connexion a la base de datos privada NO VISIBLE a los consumidores (Encapsulamiento)
    //
    Connection con;
    public CrudBase(String allRecordsQuery) {
        con = DbTools.getConnection();
        try {
            queryStatement = con.prepareStatement(allRecordsQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //
    // Metodo para crear los diferentes CRUDs usados (Patron de diseño FACTORY)
    //
    public static CrudBase createCrudFor(String tableName)
    {
        if(tableName == "usuarios")
        {
            return new UsuariosCrud("Select * from usuarios");
        }
        if(tableName == "productos")
        {
            return new ProductosCrud("Select * from productos");
        }
        return null;
    }

    protected void prepareInsertStatement(ArrayList<Object> data) throws SQLException {
        // Override in subClass
    }
    protected void prepareUpdateStatement(ArrayList<Object> data, int id) throws SQLException {
        // Override in subClass
    }
    protected void prepareDeleteStatement(int id) throws SQLException {
        // Override in subClass
    }

    //
    // Metodos genericos invocando a metodos abstractos definidos en subclases (polimorfismo)
    //
    public boolean insertRecord(ArrayList<Object> data)
    {
        boolean result = false;
        try {
            prepareInsertStatement(data);
            insertStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateRecord(ArrayList<Object> data, int id)
    {
        boolean result = false;
        try {
            prepareUpdateStatement(data,id);
            updateStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteRecord(int id)
    {
        boolean result = false;
        try {
            prepareDeleteStatement(id);
            deleteStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean exists(PreparedStatement statement)
    {
        boolean result = false;
        ResultSet rs = null;
        try {
            rs = statement.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ResultSet getAll()
    {
        ResultSet result = null;
        try {
            result = queryStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
