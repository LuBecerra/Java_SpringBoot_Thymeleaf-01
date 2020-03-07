
package edu.egg.libreria.repositorios;

import edu.egg.libreria.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepositorio extends JpaRepository<Foto,String> {
    
}
