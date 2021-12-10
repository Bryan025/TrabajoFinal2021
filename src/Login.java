import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login {
    public JPanel Main;
    private JButton btnLogin;
    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JButton registrarButton;

    static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("ITLA Stock");
        frame.setContentPane(new Login().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public boolean isValidUser()
    {
        var passWord = new String(txtPassword.getPassword());
        try {
            var exitsStatement = DbTools.getConnection().prepareStatement("SELECT * from usuarios where UserName= ? and Password = ?");
            exitsStatement.setString(1, txtUserName.getText());
            exitsStatement.setString(2, passWord);
            return crud.exists(exitsStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    CrudBase crud;

    public Login() {

        crud = CrudBase.createCrudFor("usuarios");

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isValidUser())
                {
                    frame.dispose();
                    //
                    JFrame frame = new JFrame("MenuPrincipal");
                    MenuPrincipal.menuFrame = frame;
                    frame.setContentPane(new MenuPrincipal().panel1);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(Main,"Usuario / clave erronea!.");
                }
            }
        });
        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistroUsuario dialog = new RegistroUsuario();
                dialog.pack();
                dialog.setVisible(true);
            }
        });
    }
}
