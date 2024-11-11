package Sesion09;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class tests {
    // Clase Principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PaisModel model = new PaisModel();
            PaisView view = new PaisView(model.obtenerPaises());
            PaisController controller = new PaisController(model, view);
        });
    }

    // MODELO
    static class PaisModel {
        private Map<String, PaisInfo> paisesDatos;

        public PaisModel() {
            paisesDatos = new HashMap<>();
            inicializarDatosPaises();
        }

        private void inicializarDatosPaises() {
            agregarPais("Argentina", "Buenos Aires", 45_000_000, 
                "Español", "Peso Argentino", 
                "País sudamericano conocido por el tango, el fútbol y la carne.");

            agregarPais("Brasil", "Brasilia", 212_000_000, 
                "Portugués", "Real Brasileño", 
                "El país más grande de Sudamérica, famoso por el Amazonas y el Carnaval.");

            agregarPais("Chile", "Santiago", 19_000_000, 
                "Español", "Peso Chileno", 
                "País largo y estrecho en la costa del Pacífico, conocido por su diversidad geográfica.");
        }

        // Método público para agregar país
        public boolean agregarPais(String nombre, String capital, long poblacion, 
                                   String idioma, String moneda, String descripcion) {
            // Validar que el país no exista
            if (paisesDatos.containsKey(nombre)) {
                return false;
            }

            // Validar campos obligatorios
            if (nombre == null || nombre.trim().isEmpty()) {
                return false;
            }

            PaisInfo nuevoPais = new PaisInfo(
                nombre.trim(), 
                capital != null ? capital.trim() : "N/A", 
                Math.max(poblacion, 0), 
                idioma != null ? idioma.trim() : "N/A", 
                moneda != null ? moneda.trim() : "N/A", 
                descripcion != null ? descripcion.trim() : "Sin descripción"
            );

            paisesDatos.put(nombre, nuevoPais);
            return true;
        }

        // Método para eliminar país
        public boolean eliminarPais(String nombre) {
            if (paisesDatos.containsKey(nombre)) {
                paisesDatos.remove(nombre);
                return true;
            }
            return false;
        }

        // Método para actualizar información de país
        public boolean actualizarPais(String nombre, String capital, Long poblacion, 
                                      String idioma, String moneda, String descripcion) {
            if (!paisesDatos.containsKey(nombre)) {
                return false;
            }

            PaisInfo paisExistente = paisesDatos.get(nombre);
            
            // Actualizar campos, manteniendo el valor original si el nuevo es null
            PaisInfo paisActualizado = new PaisInfo(
                nombre,
                capital != null ? capital.trim() : paisExistente.capital,
                poblacion != null ? poblacion : paisExistente.poblacion,
                idioma != null ? idioma.trim() : paisExistente.idioma,
                moneda != null ? moneda.trim() : paisExistente.moneda,
                descripcion != null ? descripcion.trim() : paisExistente.descripcion
            );

            paisesDatos.put(nombre, paisActualizado);
            return true;
        }

        public List<String> obtenerPaises() {
            return new ArrayList<>(paisesDatos.keySet());
        }

        public PaisInfo obtenerInfoPais(String pais) {
            return paisesDatos.get(pais);
        }

        // Clase interna para información de país
        static class PaisInfo {
            private String nombre;
            private String capital;
            private long poblacion;
            private String idioma;
            private String moneda;
            private String descripcion;

            public PaisInfo(String nombre, String capital, long poblacion, 
                            String idioma, String moneda, String descripcion) {
                this.nombre = nombre;
                this.capital = capital;
                this.poblacion = poblacion;
                this.idioma = idioma;
                this.moneda = moneda;
                this.descripcion = descripcion;
            }

            @Override
            public String toString() {
                return String.format(
                    "País: %s\n" +
                    "Capital: %s\n" +
                    "Población: %,d\n" +
                    "Idioma: %s\n" +
                    "Moneda: %s\n\n" +
                    "Descripción:\n%s", 
                    nombre, capital, poblacion, idioma, moneda, descripcion
                );
            }

            // Getters para acceder a la información
            public String getNombre() { return nombre; }
            public String getCapital() { return capital; }
            public long getPoblacion() { return poblacion; }
            public String getIdioma() { return idioma; }
            public String getMoneda() { return moneda; }
            public String getDescripcion() { return descripcion; }
        }
    }

    // VISTA
    static class PaisView extends JFrame {
        private JComboBox<String> comboPaises;
        private JLabel etiquetaPais;
        private JTextArea areaInfoPais;
        private JButton btnAgregar;
        private JButton btnEliminar;

        public PaisView(List<String> paises) {
            // Configuración básica del JFrame
            setTitle("Información de Países");
            setSize(700, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10, 10));

            // Etiqueta de título
            etiquetaPais = new JLabel("Selecciona un País", SwingConstants.CENTER);
            etiquetaPais.setFont(new Font("Arial", Font.BOLD, 24));
            add(etiquetaPais, BorderLayout.NORTH);

            // Área de información
            areaInfoPais = new JTextArea();
            areaInfoPais.setEditable(false);
            areaInfoPais.setLineWrap(true);
            areaInfoPais.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(areaInfoPais);
            add(scrollPane, BorderLayout.CENTER);

            // Combo de países
            comboPaises = new JComboBox<>(paises.toArray(new String[0]));
            
            // Panel de control
            JPanel panelControl = new JPanel(new FlowLayout());
            panelControl.add(new JLabel("Selecciona un País: "));
            panelControl.add(comboPaises);
            
            // Botones de agregar y eliminar
            btnAgregar = new JButton("Agregar País");
            btnEliminar = new JButton("Eliminar País");
            panelControl.add(btnAgregar);
            panelControl.add(btnEliminar);
            
            add(panelControl, BorderLayout.SOUTH);
        }

        // Métodos para interactuar con la vista
        public JComboBox<String> getComboPaises() {
            return comboPaises;
        }

        public JButton getBtnAgregar() {
            return btnAgregar;
        }

        public JButton getBtnEliminar() {
            return btnEliminar;
        }

        public void actualizarEtiqueta(String pais) {
            etiquetaPais.setText("País seleccionado: " + pais);
        }

        public void mostrarInfoPais(String info) {
            areaInfoPais.setText(info);
            //? No se que es
            areaInfoPais.setCaretPosition(0);
        }

        // Método para mostrar diálogo de agregar país
        public PaisModel.PaisInfo mostrarDialogoAgregarPais() {
            JPanel panel = new JPanel(new GridLayout(0, 2));
            JTextField nombreField = new JTextField();
            JTextField capitalField = new JTextField();
            JTextField poblacionField = new JTextField();
            JTextField idiomaField = new JTextField();
            JTextField monedaField = new JTextField();
            JTextArea descripcionArea = new JTextArea(4, 20);

            panel.add(new JLabel("Nombre:"));
            panel.add(nombreField);
            panel.add(new JLabel("Capital:"));
            panel.add(capitalField);
            panel.add(new JLabel("Población:"));
            panel.add(poblacionField);
            panel.add(new JLabel("Idioma:"));
            panel.add(idiomaField);
            panel.add(new JLabel("Moneda:"));
            panel.add(monedaField);
            panel.add(new JLabel("Descripción:"));
            panel.add(new JScrollPane(descripcionArea));

            int resultado = JOptionPane.showConfirmDialog(
                this, 
                panel, 
                "Agregar Nuevo País", 
                JOptionPane.OK_CANCEL_OPTION
            );

            if (resultado == JOptionPane.OK_OPTION) {
                try {
                    return new PaisModel.PaisInfo(
                        nombreField.getText(),
                        capitalField.getText(),
                        Long.parseLong(poblacionField.getText()),
                        idiomaField.getText(),
                        monedaField.getText(),
                        descripcionArea.getText()
                    );
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Población inválida");
                }
            }
            return null;
        }

        // Método para actualizar lista de países
        public void actualizarListaPaises(List<String> paises) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(paises.toArray(new String[0]));
            comboPaises.setModel(model);
        }
    }

    // CONTROLADOR
    static class PaisController {
        private PaisModel modelo;
        private PaisView vista;

        public PaisController(PaisModel modelo, PaisView vista) {
            this.modelo = modelo;
            this.vista = vista;

            // Configurar listeners
            configurarListeners();

            // Mostrar la vista
            vista.setVisible(true);
        }

        private void configurarListeners() {
            // Listener de selección de país
            vista.getComboPaises().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String paisSeleccionado = (String) vista.getComboPaises().getSelectedItem();
                    
                    // Actualizar etiqueta
                    vista.actualizarEtiqueta(paisSeleccionado);
                    
                    // Obtener y mostrar información del país
                    PaisModel.PaisInfo infoPais = modelo.obtenerInfoPais(paisSeleccionado);
                    if (infoPais != null) {
                        vista.mostrarInfoPais(infoPais.toString());
                    }
                }
            });

            // Listener de agregar país
            vista.getBtnAgregar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PaisModel.PaisInfo nuevoPais = vista.mostrarDialogoAgregarPais();
                    if (nuevoPais != null) {
                        if (modelo.agregarPais(
                            nuevoPais.getNombre(), 
                            nuevoPais.getCapital(), 
                            nuevoPais.getPoblacion(), 
                            nuevoPais.getIdioma(), 
                            nuevoPais.getMoneda(), 
                            nuevoPais.getDescripcion()
                        )) {
                            // Actualizar lista de países
                            vista.actualizarListaPaises(modelo.obtenerPaises());
                            JOptionPane.showMessageDialog(vista, "País agregado exitosamente");
                        } else {
                            JOptionPane.showMessageDialog(vista, "Error al agregar el país");
                        }
                    }
                }
            });

            // Listener de eliminar país
            vista.getBtnEliminar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String paisSeleccionado = (String) vista.getComboPaises().getSelectedItem();
                    if (paisSeleccionado != null) {
                        int confirmacion = JOptionPane.showConfirmDialog(
                            vista, 
                            "¿Está seguro de eliminar " + paisSeleccionado + "?", 
                            "Confirmar Eliminación", 
                            JOptionPane.YES_NO_OPTION
                        );

                        if (confirmacion == JOptionPane.YES_OPTION) {
                            if (modelo.eliminarPais(paisSeleccionado)) {
                                // Actualizar lista de países
                                vista.actualizarListaPaises(modelo.obtenerPaises());
                                vista.mostrarInfoPais("");
                                vista.actualizarEtiqueta("Selecciona un País");
                                JOptionPane.showMessageDialog(vista, "País eliminado exitosamente");
                            } else {
                                JOptionPane.showMessageDialog(vista, "Error al eliminar el país");
                            }
                        }
                    }
                }
            });
        }
    }
}
//https://www.perplexity.ai/search/dame-una-clase-en-java-que-man-S3_z.OUxTs.GJXQqIfuu6A