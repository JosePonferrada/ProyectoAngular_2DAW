package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.models.Usuario;
import com.example.demo.repositories.UsuarioRepositorio;
import com.example.demo.jwt.AutenticadorJWT;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioRepositorio usuRep;
    
    // Codificador de contraseñas
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Obtiene todos los usuarios
     * @return Lista de DTOs con datos de usuarios
     */
    @GetMapping("/obtenerTodos")
    public List<DTO> getUsuarios() {
        List<DTO> listaUsuarios = new ArrayList<DTO>();
        List<Usuario> usuarios = usuRep.findAll();

        for (Usuario usuario : usuarios) {
            DTO dto = new DTO();
            dto.put("id", usuario.getId());
            dto.put("username", usuario.getUsername());
            dto.put("email", usuario.getEmail());
            dto.put("role", usuario.getRole());
            
            listaUsuarios.add(dto);
        }
        return listaUsuarios;
    }

    /**
     * Obtiene un usuario por su ID
     * @param soloId DTO con el ID del usuario
     * @param request Objeto HttpServletRequest
     * @return DTO con datos del usuario o mensaje de error
     */
    @PostMapping(path = "/obtenerPorId", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getUsuarioPorId(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoUsuario = new DTO();
        Usuario usuario = usuRep.findById(Integer.parseInt(soloId.get("id").toString()));

        if (usuario != null) {
            dtoUsuario.put("id", usuario.getId());
            dtoUsuario.put("username", usuario.getUsername());
            dtoUsuario.put("email", usuario.getEmail());
            dtoUsuario.put("role", usuario.getRole());
        } else {
            dtoUsuario.put("error", "No se ha encontrado el usuario");
        }
        return dtoUsuario;
    }

    /**
     * Obtiene un usuario por su email
     * @param soloEmail DTO con el email del usuario
     * @param request Objeto HttpServletRequest
     * @return DTO con datos del usuario o mensaje de error
     */
    @PostMapping(path = "/obtenerPorEmail", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getUsuarioPorEmail(@RequestBody DTO soloEmail, HttpServletRequest request) {
        DTO dtoUsuario = new DTO();
        Usuario usuario = usuRep.findByEmail(soloEmail.get("email").toString());

        if (usuario != null) {
            dtoUsuario.put("id", usuario.getId());
            dtoUsuario.put("username", usuario.getUsername());
            dtoUsuario.put("email", usuario.getEmail());
            dtoUsuario.put("role", usuario.getRole());
        } else {
            dtoUsuario.put("error", "No se ha encontrado el usuario");
        }
        return dtoUsuario;
    }
    
    /**
     * Obtiene un usuario por su nombre de usuario
     * @param soloUsername DTO con el nombre de usuario
     * @param request Objeto HttpServletRequest
     * @return DTO con datos del usuario o mensaje de error
     */
    @PostMapping(path = "/obtenerPorUsername", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getUsuarioPorUsername(@RequestBody DTO soloUsername, HttpServletRequest request) {
        DTO dtoUsuario = new DTO();
        Usuario usuario = usuRep.findByUsername(soloUsername.get("username").toString());

        if (usuario != null) {
            dtoUsuario.put("id", usuario.getId());
            dtoUsuario.put("username", usuario.getUsername());
            dtoUsuario.put("email", usuario.getEmail());
            dtoUsuario.put("role", usuario.getRole());
        } else {
            dtoUsuario.put("error", "No se ha encontrado el usuario");
        }
        return dtoUsuario;
    }

    /**
     * Elimina un usuario por su ID
     * @param soloId DTO con el ID del usuario a eliminar
     * @param request Objeto HttpServletRequest
     * @return DTO con resultado de la operación
     */
    @DeleteMapping(path = "/eliminar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO eliminarUsuarioPorId(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoUsuario = new DTO();
        Usuario usuario = usuRep.findById(Integer.parseInt(soloId.get("id").toString()));

        if (usuario != null) {
            usuRep.delete(usuario);
            dtoUsuario.put("result", "ok");
            dtoUsuario.put("mensaje", "Usuario eliminado correctamente");
        } else {
            dtoUsuario.put("result", "fail");
            dtoUsuario.put("error", "No se ha encontrado el usuario");
        }
        return dtoUsuario;
    }

    /**
     * Crea un nuevo usuario
     * @param datos Datos del nuevo usuario
     * @param request Objeto HttpServletRequest
     * @return DTO con resultado de la operación
     */
    @PostMapping("/anadirNuevo")
    public DTO anadirUsuario(@RequestBody DatosAltaUsuario datos, HttpServletRequest request) {
        DTO resultado = new DTO();
        
        // Verificar si ya existe un usuario con el mismo username o email
        Usuario existeUsername = usuRep.findByUsername(datos.username);
        Usuario existeEmail = usuRep.findByEmail(datos.email);
        
        if (existeUsername != null || existeEmail != null) {
            resultado.put("result", "fail");
            resultado.put("mensaje", "El nombre de usuario o email ya existe");
            return resultado;
        }
        
        // Encriptar la contraseña
        String passwordEncriptada = passwordEncoder.encode(datos.password);
        
        // Crear y guardar el nuevo usuario
        Usuario nuevoUsuario = new Usuario(
            datos.id, 
            datos.username, 
            passwordEncriptada, 
            datos.email, 
            datos.role
        );
        
        usuRep.save(nuevoUsuario);
        resultado.put("result", "ok");
        resultado.put("mensaje", "Usuario creado correctamente");
        return resultado;
    }
    
    /**
     * Clase interna para datos de alta de usuario
     */
    static class DatosAltaUsuario {
        public int id;
        public String username;
        public String password;
        public String email;
        public String role;

        public DatosAltaUsuario(int id, String username, String password, String email, String role) {
            super();
            this.id = id;
            this.username = username;
            this.password = password;
            this.email = email;
            this.role = role;
        }
    }

    /**
     * Edita los datos de un usuario existente
     * @param datos Datos actualizados del usuario
     * @param request Objeto HttpServletRequest
     * @return DTO con resultado de la operación
     */
    @PutMapping("/editar")
    public DTO editarUsuario(@RequestBody DatosEdicionUsuario datos, HttpServletRequest request) {
        DTO dtoUsuario = new DTO();
        Usuario usuario = usuRep.findById(datos.id);

        if (usuario != null) {
            usuario.setUsername(datos.username);
            usuario.setEmail(datos.email);
            usuario.setRole(datos.role);
            
            // Solo actualizar la contraseña si se proporciona una nueva
            if (datos.password != null && !datos.password.isEmpty()) {
                String passwordEncriptada = passwordEncoder.encode(datos.password);
                usuario.setPassword(passwordEncriptada);
            }
            
            usuRep.save(usuario);
            dtoUsuario.put("result", "ok");
            dtoUsuario.put("mensaje", "Usuario editado correctamente");
        } else {
            dtoUsuario.put("result", "fail");
            dtoUsuario.put("error", "No se ha encontrado el usuario");
        }
        return dtoUsuario;
    }

    /**
     * Clase interna para datos de edición de usuario
     */
    static class DatosEdicionUsuario {
        public int id;
        public String username;
        public String password;
        public String email;
        public String role;

        public DatosEdicionUsuario(int id, String username, String password, String email, String role) {
            super();
            this.id = id;
            this.username = username;
            this.password = password;
            this.email = email;
            this.role = role;
        }
    }

    @PostMapping(path = "/registro", consumes = MediaType.APPLICATION_JSON_VALUE)
public DTO registrarUsuario(@RequestBody DatosRegistroUsuario datos) {
    // Para registro público, siempre asignar rol USER
    DatosAltaUsuario datosAlta = new DatosAltaUsuario(
        0, // ID automático
        datos.username,
        datos.password,
        datos.email,
        "USER" // Rol fijo para nuevos registros
    );
    
    return anadirUsuario(datosAlta, null);
}

static class DatosRegistroUsuario {
    public String username;
    public String password;
    public String email;
    
    public DatosRegistroUsuario() {}
    
    public DatosRegistroUsuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}

    /**
     * Autentica un usuario y genera un token JWT
     * @param datos Datos de login (username y password)
     * @param request Objeto HttpServletRequest
     * @param response Objeto HttpServletResponse para añadir cookies
     * @return DTO con resultado de la autenticación y token JWT
     */
    @PostMapping(path = "/iniciarSesion", consumes = MediaType.APPLICATION_JSON_VALUE)
public DTO iniciarSesion(@RequestBody DatosLoginUsuario datos, HttpServletRequest request, HttpServletResponse response) {
    DTO dtoUsuario = new DTO();
    
    try {
        System.out.println("Intentando login para usuario: " + datos.username);
        Usuario usuario = usuRep.findByUsername(datos.username);

        if (usuario != null && passwordEncoder.matches(datos.password, usuario.getPassword())) {
            // Generar token JWT
            String token = AutenticadorJWT.codificaJWT(usuario);
            
            // Guardar token en cookie
            Cookie cookie = new Cookie("jwt", token);
            cookie.setMaxAge(-1); // La cookie expira al cerrar el navegador
            cookie.setPath("/");  // Importante: define el path
            response.addCookie(cookie);
            
            dtoUsuario.put("id", usuario.getId());
            dtoUsuario.put("username", usuario.getUsername());
            dtoUsuario.put("email", usuario.getEmail());
            dtoUsuario.put("role", usuario.getRole());
            dtoUsuario.put("token", token);
            dtoUsuario.put("autenticado", true);
        } else {
            dtoUsuario.put("error", "Credenciales incorrectas");
            dtoUsuario.put("autenticado", false);
        }
        return dtoUsuario;
    } catch (Exception e) {
        // Registrar el error detallado en los logs
        System.err.println("Error en iniciarSesion: " + e.getMessage());
        e.printStackTrace();
        
        // Devolver una respuesta adecuada
        dtoUsuario.put("autenticado", false);
        dtoUsuario.put("error", "Error en el servidor: " + e.getMessage());
        return dtoUsuario;
    }
}
    /**
     * Clase interna para datos de login de usuario
     */
    static class DatosLoginUsuario {
        public String username;
        public String password;

        public DatosLoginUsuario(String username, String password) {
            super();
            this.username = username;
            this.password = password;
        }
    }
    
    /**
     * Obtiene los datos del usuario autenticado a partir del token JWT
     * @param request Objeto HttpServletRequest con la cabecera de autorización
     * @return DTO con los datos del usuario autenticado
     */
    @GetMapping("/usuarioActual")
    public DTO getUsuarioActual(HttpServletRequest request) {
        DTO resultado = new DTO();
        
        // Obtener token de la cabecera Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            resultado.put("result", "fail");
            resultado.put("error", "No se proporcionó un token válido");
            return resultado;
        }
        
        String token = authHeader.substring(7); // Eliminar "Bearer "
        Integer usuarioId = AutenticadorJWT.getIdUsuarioDesdeJWT(token);
        
        if (usuarioId == -1) {
            resultado.put("result", "fail");
            resultado.put("error", "Token inválido o expirado");
            return resultado;
        }
        
        Usuario usuario = usuRep.findById(usuarioId).orElse(null);
        if (usuario != null) {
            resultado.put("id", usuario.getId());
            resultado.put("username", usuario.getUsername());
            resultado.put("email", usuario.getEmail());
            resultado.put("role", usuario.getRole());
            resultado.put("result", "ok");
        } else {
            resultado.put("result", "fail");
            resultado.put("error", "Usuario no encontrado");
        }
        
        return resultado;
    }
    
    /**
     * Cierra la sesión del usuario eliminando la cookie JWT
     * @param response Objeto HttpServletResponse para eliminar cookies
     * @return DTO con resultado de la operación
     */
    @PostMapping("/cerrarSesion")
    public DTO cerrarSesion(HttpServletResponse response) {
        DTO resultado = new DTO();
        
        // Eliminar la cookie JWT
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        
        resultado.put("result", "ok");
        resultado.put("mensaje", "Sesión cerrada correctamente");
        return resultado;
    }
}