package TP6;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.TreeSet;

public class SupermercadoApp extends JFrame {
    private TreeSet<Producto> productos = new TreeSet<>();
    private JTextField codigoField, descripcionField, precioField, stockField;
    private JComboBox<String> rubroComboBox;
    private JButton agregarButton, eliminarButton, modificarButton;
    private JTable productosTable;
    private DefaultTableModel tableModel;

    public SupermercadoApp() {
        setTitle("Supermercado DeTodo S.A.");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Gestión de Productos", crearPanelGestionProductos());
        tabbedPane.addTab("Consultas", crearPanelConsultas());

        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // Método para crear el panel de gestión de productos
    private JPanel crearPanelGestionProductos() {
        JPanel panelGestion = new JPanel(new BorderLayout());
        panelGestion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel para los campos de entrada (GridLayout)
        JPanel camposPanel = new JPanel(new GridLayout(6, 2, 10, 10));

        codigoField = new JTextField();
        descripcionField = new JTextField();
        precioField = new JTextField();
        stockField = new JTextField();
        rubroComboBox = new JComboBox<>(new String[]{"Comestible", "Limpieza", "Perfumería"});

        camposPanel.add(new JLabel("Código:"));
        camposPanel.add(codigoField);
        camposPanel.add(new JLabel("Descripción:"));
        camposPanel.add(descripcionField);
        camposPanel.add(new JLabel("Precio:"));
        camposPanel.add(precioField);
        camposPanel.add(new JLabel("Stock:"));
        camposPanel.add(stockField);
        camposPanel.add(new JLabel("Rubro:"));
        camposPanel.add(rubroComboBox);

        // Panel para los botones (FlowLayout para alinearlos)
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        agregarButton = new JButton("Agregar");
        modificarButton = new JButton("Modificar");
        eliminarButton = new JButton("Eliminar");

        // Agregar iconos a los botones (opcional)
        agregarButton.setIcon(new ImageIcon("icons/add.png")); // Debes tener los íconos en tu proyecto
        modificarButton.setIcon(new ImageIcon("icons/edit.png"));
        eliminarButton.setIcon(new ImageIcon("icons/delete.png"));

        botonesPanel.add(agregarButton);
        botonesPanel.add(modificarButton);
        botonesPanel.add(eliminarButton);

        // Panel para la tabla de productos
        String[] columnNames = {"Código", "Descripción", "Precio", "Stock", "Rubro"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productosTable = new JTable(tableModel);
        productosTable.setEnabled(false); // Evitar que las celdas sean editables

        JScrollPane scrollPane = new JScrollPane(productosTable);

        // Añadir componentes al panel de gestión
        panelGestion.add(camposPanel, BorderLayout.NORTH);
        panelGestion.add(scrollPane, BorderLayout.CENTER);
        panelGestion.add(botonesPanel, BorderLayout.SOUTH);

        // Añadir funcionalidad a los botones
        agregarButton.addActionListener(e -> agregarProducto());

        return panelGestion;
    }

    // Método para crear el panel de consultas
    private JPanel crearPanelConsultas() {
        JPanel panelConsultas = new JPanel();
        panelConsultas.setLayout(new BoxLayout(panelConsultas, BoxLayout.Y_AXIS));
        panelConsultas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelConsulta = new JLabel("Realizar consultas de productos:");
        labelConsulta.setFont(new Font("Arial", Font.BOLD, 16));

        JButton consultaNombreButton = new JButton("Consultar por Nombre");
        JButton consultaPrecioButton = new JButton("Consultar por Precio");
        JButton consultaRubroButton = new JButton("Consultar por Rubro");

        // Agregar iconos opcionales a los botones de consulta
        consultaNombreButton.setIcon(new ImageIcon("icons/search.png"));
        consultaPrecioButton.setIcon(new ImageIcon("icons/price.png"));
        consultaRubroButton.setIcon(new ImageIcon("icons/category.png"));

        // Añadir botones al panel
        panelConsultas.add(labelConsulta);
        panelConsultas.add(Box.createVerticalStrut(20)); // Espacio entre el título y los botones
        panelConsultas.add(consultaNombreButton);
        panelConsultas.add(Box.createVerticalStrut(10));
        panelConsultas.add(consultaPrecioButton);
        panelConsultas.add(Box.createVerticalStrut(10));
        panelConsultas.add(consultaRubroButton);

        return panelConsultas;
    }

    // Método para agregar un producto
    private void agregarProducto() {
        try {
            int codigo = Integer.parseInt(codigoField.getText());
            String descripcion = descripcionField.getText();
            double precio = Double.parseDouble(precioField.getText());
            int stock = Integer.parseInt(stockField.getText());
            String rubro = (String) rubroComboBox.getSelectedItem();

            Producto producto = new Producto(codigo, descripcion, precio, stock, rubro);

            // Verificar si el producto ya existe
            if (productos.contains(producto)) {
                JOptionPane.showMessageDialog(this, "El código de producto ya existe.");
            } else {
                productos.add(producto);
                tableModel.addRow(new Object[]{codigo, descripcion, precio, stock, rubro});
                limpiarCampos();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Formato de número inválido.");
        }
    }

    private void limpiarCampos() {
        codigoField.setText("");
        descripcionField.setText("");
        precioField.setText("");
        stockField.setText("");
        rubroComboBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        // Ejecutar la aplicación
        SwingUtilities.invokeLater(SupermercadoApp::new);
    }
}
