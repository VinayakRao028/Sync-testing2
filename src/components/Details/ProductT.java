package melreposteria.org.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="productos")

public class Producto {
	
	@Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name="id", unique=true, nullable=false)
    private Long id;
    @Column (nullable=false)
    private String nombre;
    private String descripcion;
    private String imagen;
    @Column (nullable=false)
    private Double precio;
    
    
    @OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "productosId", referencedColumnName = "id")
	List<Pedido> pedidos = new ArrayList<Pedido>();
        

    public Producto(String nombre, String descripcion, String imagen, Double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.precio = precio;
        
        
    }//Constructor

    public Producto() {

    }//ConstructorVacio

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }	

	

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", imagen=" + imagen
				+ ", precio=" + precio + "]";
	}    
    

    

	
	


}
