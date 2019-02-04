package com.example.postgresdemo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "veterinarias")
public class Veterinaria extends AuditModel {
    @Id
    @GeneratedValue(generator = "veterinaria_generator")
    @SequenceGenerator(
            name = "veterinaria_generator",
            sequenceName = "veterinaria_sequence",
            initialValue = 4000
    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String nombre;

    @Column(columnDefinition = "text")
    private String direccion;
    
    @Column(columnDefinition = "text")
    private String telefono;    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
