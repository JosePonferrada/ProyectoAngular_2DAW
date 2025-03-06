package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Escuderia;
import com.example.demo.models.Piloto;
import com.example.demo.repositories.EscuderiaRepositorio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@CrossOrigin
@RestController
@RequestMapping("/escuderias")
public class EscuderiaController {

    @Autowired
    EscuderiaRepositorio escuderiaRep;

    /**
     * Obtiene todas las escuderías
     * 
     * @return Lista de DTOs con datos de escuderías
     */
    @GetMapping("/obtenerTodas")
    public List<DTO> getEscuderias() {
        List<DTO> listaEscuderias = new ArrayList<DTO>();
        List<Escuderia> escuderias = escuderiaRep.findAll();

        for (Escuderia escuderia : escuderias) {
            DTO dto = new DTO();
            dto.put("id", escuderia.getId());
            dto.put("nombre", escuderia.getNombre());
            dto.put("pais", escuderia.getPais());

            listaEscuderias.add(dto);
        }
        return listaEscuderias;
    }

    /**
     * Obtiene una escudería por su ID
     * 
     * @param soloId  DTO con el ID de la escudería
     * @param request Objeto HttpServletRequest
     * @return DTO con datos de la escudería o mensaje de error
     */
    @PostMapping(path = "/obtenerPorId", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getEscuderiaPorId(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoEscuderia = new DTO();
        Escuderia escuderia = escuderiaRep.findById(Integer.parseInt(soloId.get("id").toString()));

        if (escuderia != null) {
            dtoEscuderia.put("id", escuderia.getId());
            dtoEscuderia.put("nombre", escuderia.getNombre());
            dtoEscuderia.put("pais", escuderia.getPais());
        } else {
            dtoEscuderia.put("result", "fail");
            dtoEscuderia.put("error", "No se ha encontrado la escudería");
        }
        return dtoEscuderia;
    }

    /**
     * Obtiene una escudería por su nombre
     * 
     * @param soloNombre DTO con el nombre de la escudería
     * @param request    Objeto HttpServletRequest
     * @return DTO con datos de la escudería o mensaje de error
     */
    @PostMapping(path = "/obtenerPorNombre", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getEscuderiaPorNombre(@RequestBody DTO soloNombre, HttpServletRequest request) {
        DTO dtoEscuderia = new DTO();
        Escuderia escuderia = escuderiaRep.findByNombre(soloNombre.get("nombre").toString());

        if (escuderia != null) {
            dtoEscuderia.put("id", escuderia.getId());
            dtoEscuderia.put("nombre", escuderia.getNombre());
            dtoEscuderia.put("pais", escuderia.getPais());
        } else {
            dtoEscuderia.put("result", "fail");
            dtoEscuderia.put("error", "No se ha encontrado la escudería");
        }
        return dtoEscuderia;
    }

    /**
     * Obtiene escuderías por país
     * 
     * @param soloPais DTO con el país de la escudería
     * @param request  Objeto HttpServletRequest
     * @return DTO con datos de las escuderías o mensaje de error
     */
    @PostMapping(path = "/obtenerPorPais", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<DTO> getEscuderiasPorPais(@RequestBody DTO soloPais, HttpServletRequest request) {
        List<DTO> listaEscuderias = new ArrayList<DTO>();
        List<Escuderia> escuderias = escuderiaRep.findAllByPais(soloPais.get("pais").toString());

        if (escuderias != null && !escuderias.isEmpty()) {
            for (Escuderia escuderia : escuderias) {
                DTO dto = new DTO();
                dto.put("id", escuderia.getId());
                dto.put("nombre", escuderia.getNombre());
                dto.put("pais", escuderia.getPais());
                listaEscuderias.add(dto);
            }
        }
        return listaEscuderias;
    }

    /**
     * Elimina una escudería por su ID
     * 
     * @param soloId  DTO con el ID de la escudería a eliminar
     * @param request Objeto HttpServletRequest
     * @return DTO con resultado de la operación
     */
    @DeleteMapping(path = "/eliminar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO eliminarEscuderiaPorId(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoEscuderia = new DTO();
        Escuderia escuderia = escuderiaRep.findById(Integer.parseInt(soloId.get("id").toString()));

        if (escuderia != null) {
            escuderiaRep.delete(escuderia);
            dtoEscuderia.put("result", "ok");
            dtoEscuderia.put("mensaje", "Escudería eliminada correctamente");
        } else {
            dtoEscuderia.put("result", "fail");
            dtoEscuderia.put("error", "No se ha encontrado la escudería");
        }
        return dtoEscuderia;
    }

    /**
     * Crea una nueva escudería
     * 
     * @param datos   Datos de la nueva escudería
     * @param request Objeto HttpServletRequest
     * @return DTO con resultado de la operación
     */
    @PostMapping("/anadirNueva")
    public DTO anadirEscuderia(@RequestBody DatosAltaEscuderia datos, HttpServletRequest request) {
        DTO resultado = new DTO();

        // Verificar si ya existe una escudería con el mismo nombre
        Escuderia existeNombre = escuderiaRep.findByNombre(datos.nombre);

        if (existeNombre != null) {
            resultado.put("result", "fail");
            resultado.put("mensaje", "Ya existe una escudería con ese nombre");
            return resultado;
        }

        // Crear y guardar la nueva escudería
        Escuderia nuevaEscuderia = new Escuderia(
                datos.id,
                datos.nombre,
                datos.pais);

        escuderiaRep.save(nuevaEscuderia);
        resultado.put("result", "ok");
        resultado.put("mensaje", "Escudería creada correctamente");
        return resultado;
    }

    /**
     * Clase interna para datos de alta de escudería
     */
    static class DatosAltaEscuderia {
        public int id;
        public String nombre;
        public String pais;

        public DatosAltaEscuderia(int id, String nombre, String pais) {
            super();
            this.id = id;
            this.nombre = nombre;
            this.pais = pais;
        }
    }

    /**
     * Edita los datos de una escudería existente
     * 
     * @param datos   Datos actualizados de la escudería
     * @param request Objeto HttpServletRequest
     * @return DTO con resultado de la operación
     */
    @PutMapping("/editar")
    public DTO editarEscuderia(@RequestBody DatosEdicionEscuderia datos, HttpServletRequest request) {
        DTO dtoEscuderia = new DTO();
        Escuderia escuderia = escuderiaRep.findById(datos.id);

        if (escuderia != null) {
            escuderia.setNombre(datos.nombre);
            escuderia.setPais(datos.pais);

            escuderiaRep.save(escuderia);
            dtoEscuderia.put("result", "ok");
            dtoEscuderia.put("mensaje", "Escudería editada correctamente");
        } else {
            dtoEscuderia.put("result", "fail");
            dtoEscuderia.put("error", "No se ha encontrado la escudería");
        }
        return dtoEscuderia;
    }

    /**
     * Clase interna para datos de edición de escudería
     */
    static class DatosEdicionEscuderia {
        public int id;
        public String nombre;
        public String pais;

        public DatosEdicionEscuderia(int id, String nombre, String pais) {
            super();
            this.id = id;
            this.nombre = nombre;
            this.pais = pais;
        }
    }

    /**
     * Obtiene los pilotos de una escudería
     * 
     * @param soloId  DTO con el ID de la escudería
     * @param request Objeto HttpServletRequest
     * @return Lista de DTOs con datos de los pilotos
     */
    @PostMapping(path = "/obtenerPilotos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<DTO> getPilotosEscuderia(@RequestBody DTO soloId, HttpServletRequest request) {
        List<DTO> listaPilotos = new ArrayList<DTO>();
        Escuderia escuderia = escuderiaRep.findById(Integer.parseInt(soloId.get("id").toString()));

        if (escuderia != null) {
            List<Piloto> pilotos = escuderia.getPilotos();

            for (Piloto piloto : pilotos) {
                DTO dto = new DTO();
                dto.put("id", piloto.getId());
                dto.put("dorsal", piloto.getDorsal());
                dto.put("nombre", piloto.getNombre());
                // No need to include escuderia_id since we're already in the context of that
                // escuderia
                listaPilotos.add(dto);
            }
        }

        return listaPilotos;
    }
}