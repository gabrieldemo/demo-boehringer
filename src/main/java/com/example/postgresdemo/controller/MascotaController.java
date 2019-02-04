package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Answer;
import com.example.postgresdemo.model.Mascota;
import com.example.postgresdemo.repository.AnswerRepository;
import com.example.postgresdemo.repository.ClienteRepository;
import com.example.postgresdemo.repository.MascotaRepository;
import com.example.postgresdemo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

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
