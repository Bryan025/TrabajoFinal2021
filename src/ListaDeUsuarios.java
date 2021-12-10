import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class ListaDeUsuarios extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table1;
    private JButton nuevoButton;
    private JButton borrarButton;

    CrudBase crud;

    public ListaDeUsuarios() {
        setContentPane(contentPane);
        setModal(true);
        crud = CrudBase.createCrudFor("usuarios");
        table_load();

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
        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistroUsuario dialog = new RegistroUsuario();
                dialog.pack();
                dialog.setModal(true);
                dialog.setVisible(true);
                dialog.setAlwaysOnTop(true);
                table_load();
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var rowSelected = table1.getSelectedRow();
                if(rowSelected < 0)
                {
                    setAlwaysOnTop(false);
                    JOptionPane.showMessageDialog(null,"No ha seleccionado ningun registro para editar!.");
                    setAlwaysOnTop(true);
                    return;
                }
                int userId = (int)table1.getModel().getValueAt(rowSelected,0);
                String userName = table1.getModel().getValueAt(rowSelected,1).toString();
                String nombre = table1.getModel().getValueAt(rowSelected,2).toString();
                String apellido = table1.getModel().getValueAt(rowSelected,3).toString();
                String telefono = table1.getModel().getValueAt(rowSelected,4).toString();
                String email = table1.getModel().getValueAt(rowSelected,5).toString();
                String password = table1.getModel().getValueAt(rowSelected,6).toString();
                RegistroUsuario dialog = new RegistroUsuario();
                dialog.setEditing(userId,userName,nombre,apellido,telefono,email,password);
                dialog.pack();
                dialog.setModal(true);
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                table_load();

            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var rowSelected = table1.getSelectedRow();
                if(rowSelected < 0)
                {
                    setAlwaysOnTop(false);
                    JOptionPane.showMessageDialog(null,"No ha seleccionado ningun registro para editar!.");
                    setAlwaysOnTop(true);
                    return;
                }
                int userId = (int)table1.getModel().getValueAt(rowSelected,0);
                //
                int returnValue = 0;
                setAlwaysOnTop(false);
                returnValue = JOptionPane.showConfirmDialog(null,"Seguro que deseaa eliminar este registro?","Confirme",JOptionPane.YES_NO_OPTION);
                setAlwaysOnTop(true);
                if(returnValue == JOptionPane.YES_OPTION)
                {
                    performDelete(userId);
                    table_load();
                }
            }
        });
    }

    void table_load()
    {
        ResultSet rs = crud.getAll();
        try {
            table1.setModel( DbTools.buildTableModel(rs) );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DefaultTableCellRenderer renderer= new DefaultTableCellRenderer();
        table1.getTableHeader().setDefaultRenderer(renderer);
    }

    public boolean performDelete(int userId)
    {
        return crud.deleteRecord(userId);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ListaDeUsuarios dialog = new ListaDeUsuarios();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }


}
