package edu.egg.libreria.servicios;

import edu.egg.libreria.entidades.Mascota;
import edu.egg.libreria.entidades.Voto;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.MascotaRepositorio;
import edu.egg.libreria.repositorios.VotoRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class VotoServicio {
    @Autowired
    private VotoRepositorio votoRepositorio;
    @Autowired
    private MascotaRepositorio mascotaRepositorio;
    @Autowired
    private ModificacionServicio modificacionServicio;
    
    public void votar(String idUsuario, String idMascota1,
            String idMascota2) throws ErrorServicio{
        Voto voto = new Voto();
        voto.setFecha(new Date());
        
        if(idMascota1.equals(idMascota2)){
            throw new ErrorServicio("No puede votarse a si mismo");
        }
        
        Optional<Mascota> respuesta = mascotaRepositorio.findById(idMascota1);
        if (respuesta.isPresent()){
            Mascota mascota1 = respuesta.get();
            if (mascota1.getUsuario().getId().equals(idUsuario)){
              voto.setMascota1(mascota1);  
            }else{
                throw new ErrorServicio("No tiene permisos para votar esta mascota");
            }
        }else {
            throw new ErrorServicio("No existe una mascota con ese id");
        }
        
        Optional<Mascota> respuesta2 = mascotaRepositorio.findById(idMascota2);
        if(respuesta2.isPresent()){
            Mascota mascota2 = respuesta2.get();
            voto.setMascota1(mascota2);
            
            
            modificacionServicio.enviar("Tu mascota ha sido votada", "Tinder de mascotas", mascota2.getUsuario().getMaiil());
            if(!mascota2.getUsuario().getId().equals(idUsuario)){                
            } else{
                throw new ErrorServicio("No hay una mascota vinculada a ese identificador");
            }          
        }
        
        votoRepositorio.save(voto);
    }
    
    public void responder (String idUsuario,String idVoto) throws ErrorServicio{
        Optional<Voto> respuesta = votoRepositorio.findById(idVoto);
        if(respuesta.isPresent()){
            Voto voto = respuesta.get();
            voto.setRespuesta(new Date()); 
            if(voto.getMascota2().getUsuario().getId().equals(idUsuario)){
                votoRepositorio.save(voto);
                modificacionServicio.enviar("Tu voto fue correspondido", "Tinder de mascotas", voto.getMascota1().getUsuario().getMaiil());
            }else{
                throw new ErrorServicio("No existe un usuario con ese identificador para votar");
            }
        }else {
            throw new ErrorServicio("No existe identificador para ese voto");
        }
    }
}
