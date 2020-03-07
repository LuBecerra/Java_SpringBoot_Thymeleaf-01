package edu.egg.libreria.servicios;
import edu.egg.libreria.entidades.Foto;
import edu.egg.libreria.entidades.Usuario;
import edu.egg.libreria.entidades.Zona;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.FotoRepositorio;
import edu.egg.libreria.servicios.FotoServicio;
import edu.egg.libreria.repositorios.UsuarioRepositorio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import static jdk.nashorn.internal.runtime.Debug.id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class UsuarioServicio implements UserDetailsService{
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private FotoServicio fotoServicio;
    @Autowired
    private ModificacionServicio modificacionServicio;
    
    @Transactional
    public void registrar (MultipartFile archivo,String nombre, String apellido,
            String mail,Zona zona, String clave) throws ErrorServicio, IOException{
        validar(nombre,apellido, mail, clave);
          
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMaiil(mail);
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptada);
        usuario.setAlta(new Date()); 
        usuario.setZona(zona);
        
        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);
        usuarioRepositorio.save(usuario);
        System.out.println(nombre+ apellido+ mail+clave);
    }   
    @Transactional
    public void modificar (MultipartFile archivo,String id,String nombre, String apellido,
            String mail, String clave) throws ErrorServicio{
        validar(nombre,apellido, mail, clave);
        
        Optional <Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()){
            Usuario usuario = usuarioRepositorio.findById(id).get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setMaiil(mail);
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            usuario.setClave(encriptada);

            String idFoto = null;
            if (usuario.getFoto() != null){
                idFoto = usuario.getFoto().getId();
            }
            
            Foto foto = fotoServicio.actualizar(idFoto, archivo);
            usuario.setFoto(foto);
            
            usuarioRepositorio.save(usuario);
            
            modificacionServicio.enviar("Bienvenidos al tinder de mascotas", "Tinder de mascotas", usuario.getMaiil());
        }else{
            throw new ErrorServicio("No se encontró el usuario solicitado");
        }    
    }
    @Transactional
    public void deshabilitar(String id) throws ErrorServicio{
        Optional <Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setBaja(new Date());

            usuarioRepositorio.save(usuario);
        }else{
            throw new ErrorServicio("No se encontró el usuario solicitado");
        } 
    }
    @Transactional
    public void habilitar(String id) throws ErrorServicio{
        Optional <Usuario> respuesta = usuarioRepositorio.findById(id);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            usuario.setBaja(null);

            usuarioRepositorio.save(usuario);
        }else{
            throw new ErrorServicio("No se encontró el usuario solicitado");
        } 
    }
    
    public void validar(String nombre, String apellido,
            String mail, String clave) throws ErrorServicio{
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("El nombre no puede estar vacío");
        }
        if(apellido == null || apellido.isEmpty()){
            throw new ErrorServicio("El apellido no puede estar vacío");
        }
        if(mail == null || mail.isEmpty()){
            throw new ErrorServicio("El mail no puede estar vacío");
        }
        if(clave == null || clave.isEmpty() || clave.length() <= 6){
            throw new ErrorServicio("La clave no puede estar vacía o contener menos de 6 dígitos");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);
        if (usuario != null){
            List<GrantedAuthority> permisos = new ArrayList<>();
            
            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTOS");
            permisos.add(p1);
            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTAS");
            permisos.add(p2);
            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTOS");
            permisos.add(p3);
            
            
            User user = new User(usuario.getMaiil(), usuario.getClave(), permisos);
            return user;
            
         }else {
            return null;
        }
        
    }
    
}
