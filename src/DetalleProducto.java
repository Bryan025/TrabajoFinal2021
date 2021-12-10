import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DetalleProducto extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner spinPrecio;
    private JSpinner spinStock;
    private JTextField txtCategoria;
    private JTextField txtMarca;
    private JTextField txtNombre;
    private JLabel lblTitulo;
    private JButton borrarButton;

    CrudBase crud;
    int currentRecordId = -1;

    public DetalleProducto() {
        borrarButton.setVisible(false);
        setContentPane(contentPane);
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setAlwaysOnTop(true);
        toFront();
        requestFocus();
        repaint();
        crud = CrudBase.createCrudFor("productos");

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
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = 0;
                setAlwaysOnTop(false);
                returnValue = JOptionPane.showConfirmDialog(null,"Seguro que deseaa eliminar este producto?","Confirme",JOptionPane.YES_NO_OPTION);
                setAlwaysOnTop(true);
                if(returnValue == JOptionPane.YES_OPTION)
                {
                    performDelete();
                    dispose();
                }
            }
        });
    }

    public void setEditing(String nombreProducto, String marcaProucto, String categoriaProducto, int precioProducto, int stockProducto, int recordId)
    {
        currentRecordId = recordId;
        txtNombre.setText(nombreProducto);
        txtMarca.setText(marcaProucto);
        txtCategoria.setText(categoriaProducto);
        spinPrecio.setValue(precioProducto);
        spinStock.setValue(stockProducto);
        //
        lblTitulo.setText("Modificando Producto");
        borrarButton.setVisible(true);
    }

    private ArrayList<Object> getRecordData()
    {
        var result = new ArrayList<Object>();
        result.add(txtNombre.getText());
        result.add(txtMarca.getText());
        result.add(txtCategoria.getText());
        result.add(spinPrecio.getValue());
        result.add(spinStock.getValue());
        return result;
    }

    public boolean performUpdate()
    {
        var recordData = getRecordData();
        return crud.updateRecord(recordData, currentRecordId);
    }

    public boolean performInsert()
    {
        var recordData = getRecordData();
        return crud.insertRecord(recordData);
    }


    public boolean performDelete()
    {
        return crud.deleteRecord(currentRecordId);
    }

    private void onOK() {
        // add your code here
        if(currentRecordId < 0)
        {
            if(performInsert())
            {
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(null,"Ha ocurrido un error creando el producto, favor intentar nuevamente.");
            }
        } else {
            if(performUpdate())
            {
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(null,"Ha ocurrido un error modificando el producto, favor intentar nuevamente.");
            }
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();

    }

    public static void main(String[] args) {
        DetalleProducto dialog = new DetalleProducto();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
