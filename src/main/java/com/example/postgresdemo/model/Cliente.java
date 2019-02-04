package com.example.postgresdemo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "clientes")
public class Cliente extends AuditModel {
    @Id
    @GeneratedValue(generator = "cliente_generator")
    @SequenceGenerator(
            name = "cliente_generator",
            sequenceName = "cliente_generator",
            initialValue = 1000
    )
    private Long id;

    @Column(columnDefinition = "text")
    private String nombre;

    @Column(columnDefinition = "text")
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "veterinaria_id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonIgnore
    private Veterinaria veterinaria; 

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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Veterinaria getVeterinaria() {
		return veterinaria;
	}

	public void setVeterinaria(Veterinaria veterinaria) {
		this.veterinaria = veterinaria;
	}
  
}
