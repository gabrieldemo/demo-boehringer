package com.example.postgresdemo.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "mascotas")
public class Mascota extends AuditModel {
	
	public Mascota(String nombre, String tipo){
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
    @Id
    @GeneratedValue(generator = "mascota_generator")
    @SequenceGenerator(
            name = "mascota_generator",
            sequenceName = "mascota_generator",
            initialValue = 400
    )
    private Long id;

    @Column(columnDefinition = "text")
    private String nombre;

    @Column(columnDefinition = "text")
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Cliente cliente;    

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	   
}

