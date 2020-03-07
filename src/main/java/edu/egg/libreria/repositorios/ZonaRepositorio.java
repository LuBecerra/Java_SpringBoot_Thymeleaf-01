
package edu.egg.libreria.repositorios;

import edu.egg.libreria.entidades.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaRepositorio extends JpaRepository<Zona,String>{
    
}
