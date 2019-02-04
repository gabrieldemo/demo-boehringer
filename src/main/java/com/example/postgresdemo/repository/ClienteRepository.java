package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Cliente;
import com.example.postgresdemo.model.Mascota;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByVeterinariaId(Long veterinariaId);
    List<Cliente> findByNombre(String nombre);    
}
