import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroUsuario extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JPasswordField txtPasswordConfirm;
    private JLabel lblTitulo;

    CrudBase crud;
    public RegistroUsuario() {

        crud = CrudBase.createCrudFor("usuarios");

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    int currentUerId = -1;

    public void setEditing( int userId, String userName, String nombre, String apellido, String telefono, String email, String password )
    {
        currentUerId = userId;
        lblTitulo.setText( "Modificando Usuario" );
        //
        txtUsuario.setText(userName);
        txtNombre.setText(nombre);
        txtApellido.setText(apellido);
        txtTelefono.setText(telefono);
        txtEmail.setText(email);
        txtPassword.setText(password);
        txtPasswordConfirm.setText(password);
    }

    private ArrayList<Object> getRecordData()
    {
        var passWord = new String(txtPassword.getPassword());
        var result = new ArrayList<Object>();
        result.add(txtUsuario.getText());
        result.add(txtNombre.getText());
        result.add(txtApellido.getText());
        result.add(txtTelefono.getText());
        result.add(txtEmail.getText());
        result.add(txtUsuario.getText());
        result.add(passWord);
        return result;
    }

    public boolean performUpdate()
    {
        var recordData = getRecordData();
        return crud.updateRecord(recordData, currentUerId);
    }

    public boolean performInsert()
    {
        var recordData = getRecordData();
        return crud.insertRecord(recordData);
    }

    private void onOK() {
        // add your code here

        var pass1 = new String(txtPassword.getPassword());
        var pass2 = new String(txtPasswordConfirm.getPassword());

        if(pass1.compareTo(pass2) != 0)
        {
            JOptionPane.showMessageDialog(null,"Las contraseñas no son iguales, favor revisar.");
            return;
        }
        if(currentUerId < 0)
        {
            if(performInsert())
            {
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(null,"Ha ocurrido un error creando el usuario, favor intentar nuevamente.");
            }
        } else {
            if(performUpdate())
            {
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(null,"Ha ocurrido un error modificando el usuario, favor intentar nuevamente.");
            }
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        RegistroUsuario dialog = new RegistroUsuario();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
