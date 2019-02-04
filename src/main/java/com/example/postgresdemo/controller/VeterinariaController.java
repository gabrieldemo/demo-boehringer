package com.example.postgresdemo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Veterinaria;
import com.example.postgresdemo.repository.VeterinariaRepository;

@RestController
public class VeterinariaController {

    @Autowired
    private VeterinariaRepository veterinariaRepository;

    @GetMapping("/veterinarias")
    public Page<Veterinaria> getVeterinarias(Pageable pageable) {
        return veterinariaRepository.findAll(pageable);
    }


    @PostMapping("/veterinarias")
    public Veterinaria createVeterinaria(@Valid @RequestBody Veterinaria veterinaria) {
        return veterinariaRepository.save(veterinaria);
    }

    @PutMapping("/veterinarias/{veterinariaId}")
    public Veterinaria updateVeterinaria(@PathVariable Long veterinariaId,
                                   @Valid @RequestBody Veterinaria veterinariaRequest) {
        return veterinariaRepository.findById(veterinariaId)
                .map(veterinaria -> {
                	veterinaria.setNombre(veterinariaRequest.getNombre());
                	veterinaria.setDireccion(veterinariaRequest.getDireccion());
                	veterinaria.setTelefono(veterinariaRequest.getTelefono());
                    return veterinariaRepository.save(veterinaria);
                }).orElseThrow(() -> new ResourceNotFoundException("Veterinaria not found with id " + veterinariaId));
    }


    @DeleteMapping("/veterinarias/{veterinariaId}")
    public ResponseEntity<?> deleteVeterinaria(@PathVariable Long veterinariaId) {
        return veterinariaRepository.findById(veterinariaId)
                .map(veterinaria -> {
                	veterinariaRepository.delete(veterinaria);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Veterinaria not found with id " + veterinariaId));
    }
}
