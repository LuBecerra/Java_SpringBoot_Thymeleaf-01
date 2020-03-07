
package edu.egg.libreria.repositorios;
import edu.egg.libreria.entidades.Mascota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface MascotaRepositorio extends JpaRepository<Mascota, String>{
    
    @Query ("SELECT c FROM Mascota c WHERE c.usuario.id = :id")
    public List<Mascota> buscarMascotasPorUsuario (@Param("id") String id);
    
//    @Query ("SELECT c FROM Guia c WHERE c.usuario.email = :email")
//    public Guia buscarGuiaPorEmail (@Param("email") String email);
}
