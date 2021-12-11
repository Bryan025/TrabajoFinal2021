import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal {
    public JPanel panel1;
    private JButton btnUsuarios;
    private JButton btnLogout;
    private JButton btnProducts;

    public MenuPrincipal() {
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                //
                JFrame  frame = new JFrame("ITLA Stock");
                Login.frame = frame;
                frame.setContentPane(new Login().Main);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        btnUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaDeUsuarios dialog = new ListaDeUsuarios();
                dialog.pack();
                dialog.setModal(true);
//                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
            }
        });
        btnProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaDeProductos dialog = new ListaDeProductos();
                dialog.pack();
                dialog.setModal(true);
//                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
            }
        });
    }
    static JFrame menuFrame;
    public static void main(String[] args) {
        menuFrame = new JFrame("MenuPrincipal");
        menuFrame.setContentPane(new MenuPrincipal().panel1);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.pack();
        menuFrame.setVisible(true);
    }
}
