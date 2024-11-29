// Archivo: SistemaGestionProductos.java
package Project;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.UUID;

// 1. ENUMS Y CLASES BASE
enum Categoria {
    ELECTRONICA, ROPA, ALIMENTOS, TECNOLOGIA
}

enum UnidadMedida {
    UNIDAD, KILOGRAMO, LITRO, METRO
}


// 2. EXCEPCIONES PERSONALIZADAS
class ProductoException extends Exception {
    public ProductoException(String mensaje) {
        super(mensaje);
    }
}

class StockInsuficienteException extends RuntimeException {
    public StockInsufienteException(String mensaje) {
        super(mensaje);
    }
}

// 3. MODELO DE DOMINIO
class Producto {
    private String id;
    private String codigo;
    private String nombre;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Categoria categoria;
    private Integer stock;
    private UnidadMedida unidadMedida;

    // Constructor
    public Producto() {
        this.id = UUID.randomUUID().toString();
    }

    // Métodos de Negocio
    public void reducirStock(int cantidad) throws StockInsuficienteException {
        if (cantidad > this.stock) {
            throw new StockInsuficienteException("Stock insuficiente");
        }
        this.stock -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        this.stock += cantidad;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public BigDecimal getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(BigDecimal precioCompra) { this.precioCompra = precioCompra; }
    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public UnidadMedida getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(UnidadMedida unidadMedida) { this.unidadMedida = unidadMedida; }
}

// 4. INTERFACES DE SERVICIO
interface IProductoRepository {
    void guardar(Producto producto);
    Optional<Producto> buscarPorCodigo(String codigo);
    List<Producto> listarTodos();
    void actualizar(Producto producto);
    void eliminar(String codigo);
}

interface IProductoService {
    void registrarProducto(Producto producto) throws ProductoException;
    Producto buscarProducto(String codigo) throws ProductoException;
    List<Producto> obtenerProductos();
    void actualizarStock(String codigo, int cantidad) throws ProductoException;
}

// 5. IMPLEMENTACIÓN DE REPOSITORIO
class ProductoRepositoryMemoria implements IProductoRepository {
    private List<Producto> productos = new ArrayList<>();

    @Override
    public void guardar(Producto producto) {
        productos.add(producto);
    }

    @Override
    public Optional<Producto> buscarPorCodigo(String codigo) {
        return productos.stream()
            .filter(p -> p.getCodigo().equals(codigo))
            .findFirst();
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }

    @Override
    public void actualizar(Producto producto) {
        productos.removeIf(p -> p.getCodigo().equals(producto.getCodigo()));
        productos.add(producto);
    }

    @Override
    public void eliminar(String codigo) {
        productos.removeIf(p -> p.getCodigo().equals(codigo));
    }
}

// 6. IMPLEMENTACIÓN DE SERVICIO
class ProductoServiceImpl implements IProductoService {
    private final IProductoRepository repository;

    public ProductoServiceImpl(IProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registrarProducto(Producto producto) throws ProductoException {
        validarProducto(producto);
        repository.guardar(producto);
    }

    @Override
    public Producto buscarProducto(String codigo) throws ProductoException {
        return repository.buscarPorCodigo(codigo)
            .orElseThrow(() -> new ProductoException("Producto no encontrado"));
    }

    @Override
    public List<Producto> obtenerProductos() {
        return repository.listarTodos();
    }

    @Override
    public void actualizarStock(String codigo, int cantidad) throws ProductoException {
        Producto producto = buscarProducto(codigo);
        producto.aumentarStock(cantidad);
        repository.actualizar(producto);
    }

    private void validarProducto(Producto producto) throws ProductoException {
        if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
            throw new ProductoException("Nombre de producto inválido");
        }
        if (producto.getPrecioVenta().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductoException("Precio de venta inválido");
        }
    }
}

// 7. VISTA SWING
class ProductoView extends JFrame {
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecioCompra;
    private JTextField txtPrecioVenta;
    private JTextField txtStock;
    private JComboBox<Categoria> cbCategoria;
    private JComboBox<UnidadMedida> cbUnidadMedida;
    private JButton btnGuardar;
    private JTextArea areaResultados;

    private final IProductoService productoService;

    public ProductoView(IProductoService productoService) {
        this.productoService = productoService;
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        txtCodigo = new JTextField(15);
        txtNombre = new JTextField(20);
        txtPrecioCompra = new JTextField(10);
        txtPrecioVenta = new JTextField(10);
        txtStock = new JTextField(5);
        
        cbCategoria = new JComboBox<>(Categoria.values());
        cbUnidadMedida = new JComboBox<>(UnidadMedida.values());
        
        btnGuardar = new JButton("Guardar Producto");
        areaResultados = new JTextArea(10, 40);
        areaResultados.setEditable(false);

        btnGuardar.addActionListener(e -> guardarProducto());
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión de Productos");
        setLayout(new GridLayout(8, 2));
        
        add(new JLabel("Código:"));
        add(txtCodigo);
        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Precio Compra:"));
        add(txtPrecioCompra);
        add(new JLabel("Precio Venta:"));
        add(txtPrecioVenta);
        add(new JLabel("Stock:"));
        add(txtStock);
        add(new JLabel("Categoría:"));
        add(cbCategoria);
        add(new JLabel("Unidad Medida:"));
        add(cbUnidadMedida);
        add(btnGuardar);
        add(new JScrollPane(areaResultados));

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void guardarProducto() {
        try {
            Producto producto = new Producto();
            producto.setCodigo(txtCodigo.getText());
            producto.setNombre(txtNombre.getText());
            producto.setPrecioCompra(new BigDecimal(txtPrecioCompra.getText()));
            producto.setPrecioVenta(new BigDecimal(txtPrecioVenta.getText()));
            producto.setStock(Integer.parseInt(txtStock.getText()));
            producto.setCategoria((Categoria) cbCategoria.getSelectedItem());
            producto.setUnidadMedida((UnidadMedida) cbUnidadMedida.getSelectedItem());

            productoService.registrarProducto(producto);
            
            mostrarMensaje("Producto guardado con éxito");
            listarProductos();
        } catch (Exception e) {
            mostrarError("Error: " + e.getMessage());
        }
    }

    private void listarProductos() {
        List<Producto> productos = productoService.obtenerProductos();
        StringBuilder sb = new StringBuilder();
        
        sb.append("LISTA DE PRODUCTOS:\n");
        productos.forEach(p -> {
            sb.append(String.format("Código: %s, Nombre: %s, Precio: $%.2f\n", 
                p.getCodigo(), p.getNombre(), p.getPrecioVenta()));
        });

        areaResultados.setText(sb.toString());
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

// 8. CLASE PRINCIPAL
public class SistemaGestionProductos {
    public static void main(String[] args) {
        // Configuración de dependencias
        IProductoRepository repository = new ProductoRepositoryMemoria();
        IProductoService service = new ProductoServiceImpl(repository);

        // Iniciar interfaz gráfica
        SwingUtilities.invokeLater(() -> {
            ProductoView vista = new ProductoView(service);
            vista.setVisible(true);
        });
    }
}
