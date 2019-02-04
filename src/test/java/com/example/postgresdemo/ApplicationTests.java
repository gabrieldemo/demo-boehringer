package com.example.postgresdemo;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.postgresdemo.model.Cliente;
import com.example.postgresdemo.model.Mascota;
import com.example.postgresdemo.model.Veterinaria;
import com.example.postgresdemo.repository.MascotaRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationTests {
 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private MascotaRepository mascotaRepository;
 
    // write test cases here

    @Test
    public void whenFindByName_thenReturnEmployee() {
    	Veterinaria veterinaria = new Veterinaria();
    	veterinaria.setNombre("Vet test");
    	veterinaria.setDireccion("direccion test");
    	veterinaria.setTelefono("2432-535-353");
    	entityManager.persist(veterinaria);
    	
    	Cliente cliente = new Cliente();
    	cliente.setNombre("test");
    	cliente.setTelefono("23232-232-32");
    	cliente.setVeterinaria(veterinaria);
        entityManager.persist(cliente);
        
        // given
    	Mascota mascota = new Mascota("Tomy", "Gato");
    	mascota.setCliente(cliente);
        entityManager.persist(mascota);
        entityManager.flush();
     
        // when
        Mascota found = mascotaRepository.findByNombre(mascota.getNombre());
     
        // then
        assertThat(found.getNombre())
          .isEqualTo(mascota.getNombre());
    }    

}
