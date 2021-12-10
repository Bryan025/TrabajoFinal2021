import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListaDeProductos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table1;

    CrudBase crud;

    public ListaDeProductos() {
        setContentPane(contentPane);
        setModal(true);
        crud = CrudBase.createCrudFor("productos");
        table_load();

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

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table1.rowAtPoint(e.getPoint());
                int productoId = (int)table1.getModel().getValueAt(row,0);
                String nombreProducto = table1.getModel().getValueAt(row,1).toString();
                String marcaProducto = table1.getModel().getValueAt(row,2).toString();
                String categoriaProducto = table1.getModel().getValueAt(row,3).toString();
                int precioProducto = (int)table1.getModel().getValueAt(row,4);
                int stockProducto = (int)table1.getModel().getValueAt(row,5);
                //
                DetalleProducto dialog = new DetalleProducto();
                dialog.pack();
                dialog.setEditing(nombreProducto,marcaProducto,categoriaProducto,precioProducto,stockProducto,productoId);
                dialog.setModal(true);
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                table_load();

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

    private void table_load() {
        ResultSet rs = crud.getAll();
        try {
            table1.setModel( DbTools.buildTableModel(rs) );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DefaultTableCellRenderer renderer= new DefaultTableCellRenderer();
        table1.getTableHeader().setDefaultRenderer(renderer);
    }

    private void onOK() {
        // add your code here
        DetalleProducto dialog = new DetalleProducto();
        dialog.pack();
        dialog.setModal(true);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
        table_load();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ListaDeProductos dialog = new ListaDeProductos();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
