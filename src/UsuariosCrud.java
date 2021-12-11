import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//
// Clase CRUD para usuarios heredando de la clase base (Herencia)
//
public class UsuariosCrud extends CrudBase {


    public UsuariosCrud(String allRecordsQuery) {
        super(allRecordsQuery);
    }

    @Override
    protected void prepareInsertStatement(ArrayList<Object> data) throws SQLException {
        insertStatement = con.prepareStatement("INSERT into usuarios (UserName, Nombre, Apellido, Telefono, Email, Password) values (?,?,?,?,?,?) ");
        insertStatement.setString(1, data.get(0).toString());
        insertStatement.setString(2, data.get(1).toString());
        insertStatement.setString(3, data.get(2).toString());
        insertStatement.setString(4, data.get(3).toString());
        insertStatement.setString(5, data.get(4).toString());
        insertStatement.setString(6, data.get(5).toString());
    }

    @Override
    protected void prepareDeleteStatement(int id) throws SQLException {
        deleteStatement = con.prepareStatement("DELETE FROM usuarios WHERE idUser = ? ");
        deleteStatement.setInt(1, id);
    }

    @Override
    protected void prepareUpdateStatement(ArrayList<Object> data, int id) throws SQLException {
        updateStatement = con.prepareStatement("UPDATE usuarios set UserName = ?, Nombre = ?, Apellido = ?, Telefono = ?, Email = ?, Password=? WHERE idUser = ? ");
        updateStatement.setString(1, data.get(0).toString());
        updateStatement.setString(2, data.get(1).toString());
        updateStatement.setString(3, data.get(2).toString());
        updateStatement.setString(4, data.get(3).toString());
        updateStatement.setString(5, data.get(4).toString());
        updateStatement.setString(6, data.get(5).toString());
        updateStatement.setInt(7, id);
    }

}
