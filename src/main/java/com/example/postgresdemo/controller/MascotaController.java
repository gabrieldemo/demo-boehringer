package com.example.postgresdemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Mascota;
import com.example.postgresdemo.repository.ClienteRepository;
import com.example.postgresdemo.repository.MascotaRepository;

@RestController
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes/{clienteId}/mascotas")
    public List<Mascota> getMascotasByClienteId(@PathVariable Long clienteId) {
        return mascotaRepository.findByClienteId(clienteId);
    }

    @PostMapping("/clientes/{clienteId}/mascotas")
    public Mascota addMascota(@PathVariable Long clienteId,
                            @Valid @RequestBody Mascota mascota) {
        return clienteRepository.findById(clienteId)
                .map(cliente -> {
                    mascota.setCliente(cliente);
                    return mascotaRepository.save(mascota);
                }).orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id " + clienteId));
    }

    @PutMapping("/clientes/{clienteId}/mascotas/{mascotaId}")
    public Mascota updateMascota(@PathVariable Long clienteId,
                               @PathVariable Long mascotaId,
                               @Valid @RequestBody Mascota mascotaRequest) {
        if(!clienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException("Cliente not found with id " + clienteId);
        }

        return mascotaRepository.findById(mascotaId)
                .map(mascota -> {
                	mascota.setNombre(mascotaRequest.getNombre());
                	mascota.setTipo(mascotaRequest.getTipo());
                    return mascotaRepository.save(mascota);
                }).orElseThrow(() -> new ResourceNotFoundException("Mascota not found with id " + mascotaId));
    }

    @DeleteMapping("/clientes/{clienteId}/mascotas/{mascotaId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long clienteId,
                                          @PathVariable Long mascotaId) {
        if(!clienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException("Cliente not found with id " + clienteId);
        }

        return mascotaRepository.findById(mascotaId)
                .map(mascota -> {
                	mascotaRepository.delete(mascota);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Mascota not found with id " + mascotaId));

    }
}
