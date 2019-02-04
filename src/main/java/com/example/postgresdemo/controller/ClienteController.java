package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Cliente;
import com.example.postgresdemo.model.Question;
import com.example.postgresdemo.repository.ClienteRepository;
import com.example.postgresdemo.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public Page<Cliente> getClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }


    @PostMapping("/clientes")
    public Cliente createCliente(@Valid @RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/clientes/{clienteId}")
    public Cliente updateCliente(@PathVariable Long clienteId,
                                   @Valid @RequestBody Cliente clienteRequest) {
        return clienteRepository.findById(clienteId)
                .map(cliente -> {
                	cliente.setNombre(clienteRequest.getNombre());
                	cliente.setTelefono(clienteRequest.getTelefono());
                    return clienteRepository.save(cliente);
                }).orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id " + clienteId));
    }


    @DeleteMapping("/clientes/{clienteId}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long clienteId) {
        return clienteRepository.findById(clienteId)
                .map(cliente -> {
                	clienteRepository.delete(cliente);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Cliente not found with id " + clienteId));
    }
}
