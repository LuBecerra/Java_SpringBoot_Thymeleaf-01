package edu.egg.libreria.controladores;
import edu.egg.libreria.entidades.Usuario;
import edu.egg.libreria.entidades.Zona;
import edu.egg.libreria.errores.ErrorServicio;
import edu.egg.libreria.repositorios.UsuarioRepositorio;
import edu.egg.libreria.servicios.UsuarioServicio;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
@Controller
@RequestMapping("/")
public class PortalControlador {
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
//    Registro de cliente
    @Autowired
    UsuarioServicio us;
    
    @PostMapping("/crear")
    public String crear(@RequestParam(value="imagen")MultipartFile archivo, @RequestParam(value="nombre") String nombre, @RequestParam(value="apellido") String apellido, 
            @RequestParam(value="mail") String mail,@RequestParam(value="zona") Zona zona, @RequestParam(value="contrasena") String contrasena)throws ErrorServicio, IOException{
        try {
            us.registrar(archivo, nombre, apellido, mail, zona, contrasena);
        }catch (ErrorServicio e){
            e.printStackTrace();
            System.out.println("Faltan datos");
        }
        return "index.html";
    }
//   Ingreso de clientes
    @Autowired
    UsuarioServicio usIngreso;
    
    @PostMapping("/ingresar")
    public String ingresar (@RequestParam(value = "mail")String mail, @RequestParam(value ="contrasena") String clave) throws ErrorServicio{
       
        usIngreso.loadUserByUsername(mail);
        return null;
    }
}



//
//    @GetMapping("/modificar")
//    public String modificar(@RequestParam(value = "documento") String documento, @RequestParam(value = "apellido") String apellido, @RequestParam(value = "domicilio") String domicilio, @RequestParam(value = "nombre") String nombre, @RequestParam(value = "telefono") String telefono) throws ErrorServicio {
//        
//            try {
//                cs.ModificarCliente(documento, apellido, domicilio, nombre, telefono);
//            } catch (ErrorServicio ex) {
//                Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return "redirect:/home";
//
//        }
//
//        @GetMapping("/eliminar")
//         public String eliminar(@RequestParam(value = "documento") String documento)throws ErrorServicio {
//         
//        
//         cs.EliminarCliente(documento);
//         
//         return "redirect:/home";
//
//         }
//    


//