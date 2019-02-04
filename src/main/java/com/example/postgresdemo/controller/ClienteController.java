package com.example.postgresdemo.controller;

import java.util.List;

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
import com.example.postgresdemo.model.Cliente;
import com.example.postgresdemo.repository.ClienteRepository;
import com.example.postgresdemo.repository.VeterinariaRepository;

@RestController
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private VeterinariaRepository veterinariaRepository;    

    @GetMapping("/clientes")
    public Page<Cliente> getClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }
    
    @GetMapping("/clientes/{veterinariaId}")
    public List<Cliente> getClientesByVeterinariId(@PathVariable Long veterinariaId) {
        return clienteRepository.findByVeterinariaId(veterinariaId);
    }  
    
    @GetMapping("/clientes/{nombre}")
    public List<Cliente> getClientesByNombre(@PathVariable String nombre) {
        return clienteRepository.findByNombre(nombre);
    }      

    @PostMapping("/clientes/{veterinariaId}")
    public Cliente createCliente(@PathVariable Long veterinariaId,
                            @Valid @RequestBody Cliente cliente) {
        return veterinariaRepository.findById(veterinariaId)
                .map(veterinaria -> {
                	cliente.setVeterinaria(veterinaria);
                    return clienteRepository.save(cliente);
                }).orElseThrow(() -> new ResourceNotFoundException("Veterinaria not found with id " + veterinariaId));
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
