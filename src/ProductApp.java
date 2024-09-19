package TP6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ProductApp extends JFrame {
    private JComboBox<String> categoryComboBox;
    private JTextField productNameField, productPriceField;
    private JButton addButton;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private ArrayList<Product> productList = new ArrayList<>();

    public ProductApp() {
        setTitle("Gestión de Productos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel para los campos de entrada
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        // ComboBox para categorías
        categoryComboBox = new JComboBox<>(new String[]{"Electrónica", "Ropa", "Alimentos"});
        inputPanel.add(new JLabel("Categoría:"));
        inputPanel.add(categoryComboBox);

        // Campos de texto para nombre y precio
        productNameField = new JTextField(10);
        productPriceField = new JTextField(10);
        inputPanel.add(new JLabel("Nombre del producto:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Precio del producto:"));
        inputPanel.add(productPriceField);

        // Botón para agregar producto
        addButton = new JButton("Agregar producto");
        addButton.setEnabled(false); // Inhabilitar botón al inicio
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Tabla para mostrar productos
        String[] columnNames = {"Nombre", "Categoría", "Precio"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        productTable.setEnabled(false); // Evitar que se editen las celdas directamente
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Listeners para validar los campos de texto
        productNameField.getDocument().addDocumentListener(new InputValidation());
        productPriceField.getDocument().addDocumentListener(new InputValidation());

        // Acción del botón agregar
        addButton.addActionListener(e -> agregarProducto());

        setVisible(true);
    }

    private void agregarProducto() {
        String nombre = productNameField.getText();
        String categoria = (String) categoryComboBox.getSelectedItem();
        String precioStr = productPriceField.getText();

        // Validación
        if (nombre.isEmpty() || precioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return;
        }

        try {
            double precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                JOptionPane.showMessageDialog(this, "El precio no puede ser negativo.");
                return;
            }

            // Crear producto y agregarlo a la lista
            Product producto = new Product(nombre, categoria, precio);
            productList.add(producto);

            // Actualizar la tabla
            tableModel.addRow(new Object[]{nombre, categoria, precio});

            // Limpiar campos de texto
            productNameField.setText("");
            productPriceField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número válido.");
        }
    }

    // Listener para habilitar/deshabilitar el botón
    private class InputValidation implements javax.swing.event.DocumentListener {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            validateInputs();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            validateInputs();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            validateInputs();
        }

        private void validateInputs() {
            boolean enableButton = !productNameField.getText().trim().isEmpty()
                    && !productPriceField.getText().trim().isEmpty();
            addButton.setEnabled(enableButton);
        }
    }

    public static void main(String[] args) {
        new ProductApp();
    }
}
