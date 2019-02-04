package com.example.postgresdemo.repository;

import com.example.postgresdemo.model.Veterinaria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinariaRepository extends JpaRepository<Veterinaria, Long> {
}
