package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import com.example.demo.repositories.CircuitoRepositorio;
import com.example.demo.repositories.EscuderiaRepositorio;
import com.example.demo.repositories.PilotoRepositorio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@CrossOrigin
@RestController
@RequestMapping("/pilotos")
public class PilotoController {

    @Autowired
    PilotoRepositorio pilRep;

    @Autowired
    CircuitoRepositorio cirRep;

    @Autowired
    EscuderiaRepositorio escRep;

    @Transactional
    @GetMapping("/obtenerTodos")
    public List<DTO> getPilotos() {
        List<DTO> listaPilotos = new ArrayList<>();
        List<Piloto> pilotos = pilRep.findAll();

        for (Piloto piloto : pilotos) {
            DTO dto = new DTO();
            dto.put("id", piloto.getId());
            dto.put("nombre", piloto.getNombre());
            dto.put("dorsal", piloto.getDorsal());
            dto.put("escuderia_id", piloto.getEscuderia().getId());

            /* // Obtener los circuitos en los que participa el piloto
            List<String> nombresCircuitos = piloto.getCircuitos()
                .stream()
                .map(Circuito::getNombre)
                .collect(Collectors.toList());

            dto.put("circuitos", nombresCircuitos); */

            listaPilotos.add(dto);
        }
        return listaPilotos;
    }

    @PostMapping(path = "/obtenerPorId", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getPilotoPorId(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoPiloto = new DTO();
        Piloto piloto = pilRep.findById(Integer.parseInt(soloId.get("id").toString()));

        if (piloto != null) {
            dtoPiloto.put("id", piloto.getId());
            dtoPiloto.put("nombre", piloto.getNombre());
            dtoPiloto.put("dorsal", piloto.getDorsal());
            dtoPiloto.put("escuderia_id", piloto.getEscuderia().getId());

            /* // Obtener los circuitos en los que participa el piloto
            List<String> nombresCircuitos = piloto.getCircuitos()
                .stream()
                .map(Circuito::getNombre)
                .collect(Collectors.toList());

            dtoPiloto.put("circuitos", nombresCircuitos); */
        } else {
            dtoPiloto.put("error", "No se ha encontrado el piloto");
        }
        return dtoPiloto;
    }

    @PostMapping(path = "/obtenerPorNombre", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getPilotoPorNombre(@RequestBody DTO soloNombre, HttpServletRequest request) {
        DTO dtoPiloto = new DTO();
        Piloto piloto = pilRep.findByNombre(soloNombre.get("nombre").toString());

        if (piloto != null) {
            dtoPiloto.put("id", piloto.getId());
            dtoPiloto.put("nombre", piloto.getNombre());
            dtoPiloto.put("dorsal", piloto.getDorsal());
            dtoPiloto.put("escuderia", piloto.getEscuderia().getNombre());

            /* // Obtener los circuitos en los que participa el piloto
            List<String> nombresCircuitos = piloto.getCircuitos()
                .stream()
                .map(Circuito::getNombre)
                .collect(Collectors.toList());

            dtoPiloto.put("circuitos", nombresCircuitos); */
        } else {
            dtoPiloto.put("error", "No se ha encontrado el piloto");
        }
        return dtoPiloto;
    }

    @PostMapping(path = "/obtenerPorDorsal", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getPilotoPorDorsal(@RequestBody DTO soloDorsal, HttpServletRequest request) {
        DTO dtoPiloto = new DTO();
        Piloto piloto = pilRep.findByDorsal(Integer.parseInt(soloDorsal.get("dorsal").toString()));

        if (piloto != null) {
            dtoPiloto.put("id", piloto.getId());
            dtoPiloto.put("nombre", piloto.getNombre());
            dtoPiloto.put("dorsal", piloto.getDorsal());
            dtoPiloto.put("escuderia", piloto.getEscuderia().getNombre());

            /* // Obtener los circuitos en los que participa el piloto
            List<String> nombresCircuitos = piloto.getCircuitos()
                .stream()
                .map(Circuito::getNombre)
                .collect(Collectors.toList());

            dtoPiloto.put("circuitos", nombresCircuitos); */
        } else {
            dtoPiloto.put("error", "No se ha encontrado el piloto");
        }
        return dtoPiloto;
    }

    @PostMapping(path = "/obtenerPorEscuderia", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<DTO> getPilotoPorEscuderia(@RequestBody DTO soloEscuderia, HttpServletRequest request) {
        List<DTO> listaPilotos = new ArrayList<>();
        List<Piloto> pilotos = pilRep.findByEscuderia_Nombre(soloEscuderia.get("escuderia").toString());

        for (Piloto piloto : pilotos) {
            DTO dto = new DTO();
            dto.put("id", piloto.getId());
            dto.put("nombre", piloto.getNombre());
            dto.put("dorsal", piloto.getDorsal());
            dto.put("escuderia", piloto.getEscuderia().getNombre());

            /* // Obtener los circuitos en los que participa el piloto
            List<String> nombresCircuitos = piloto.getCircuitos()
                .stream()
                .map(Circuito::getNombre)
                .collect(Collectors.toList());

            dto.put("circuitos", nombresCircuitos); */

            listaPilotos.add(dto);
        }
        return listaPilotos;
    }

    @Transactional
    @PostMapping(path = "/obtenerParticipaciones", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<DTO> getParticipaciones(@RequestBody DTO soloId, HttpServletRequest request) {
        List<DTO> listaParticipaciones = new ArrayList<>();
        Piloto piloto = pilRep.findById(Integer.parseInt(soloId.get("id").toString()));

        if (piloto != null) {

            DTO dtoPiloto = new DTO();
            dtoPiloto.put("id", piloto.getId());
            dtoPiloto.put("nombre", piloto.getNombre());
            dtoPiloto.put("dorsal", piloto.getDorsal());
            dtoPiloto.put("escuderia", piloto.getEscuderia().getNombre());
            listaParticipaciones.add(dtoPiloto);

            // Obtener los circuitos en los que participa el piloto
            List<DTO> listaCircuitos = piloto.getCircuitos()
            .stream()
            .map(circuito -> {
                DTO dtoCircuito = new DTO();
                dtoCircuito.put("nombre", circuito.getNombre());
                return dtoCircuito;
            })
            .collect(Collectors.toList());

            dtoPiloto.put("circuitos", listaCircuitos);
        } else {
            DTO dto = new DTO();
            dto.put("error", "No se ha encontrado el piloto");
            listaParticipaciones.add(dto);
        }
        return listaParticipaciones;
    }

    @DeleteMapping(path = "/eliminar1", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO eliminarPilotoPorId(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoPiloto = new DTO();
        Piloto piloto = pilRep.findById(Integer.parseInt(soloId.get("id").toString()));

        if (piloto != null) {
            pilRep.delete(piloto);
            dtoPiloto.put("mensaje", "Piloto eliminado correctamente");
        } else {
            dtoPiloto.put("error", "No se ha encontrado el piloto");
        }
        return dtoPiloto;
    }

    @PostMapping(path = "/anadirNuevo", consumes = MediaType.APPLICATION_JSON_VALUE)
public DTO anadirPiloto(@RequestBody DTO datos, HttpServletRequest request) {
    DTO respuesta = new DTO();
    
    try {
        System.out.println("Datos recibidos para nuevo piloto: " + datos.toString());
        
        // Extraer los datos con manejo de errores
        String nombre = datos.get("nombre").toString();
        int dorsal = Integer.parseInt(datos.get("dorsal").toString());
        
        // Manejar escuderia_id que puede venir como String
        int escuderiaId;
        Object escuderiaObj = datos.get("escuderia_id");
        if (escuderiaObj instanceof String) {
            escuderiaId = Integer.parseInt((String)escuderiaObj);
        } else if (escuderiaObj instanceof Number) {
            escuderiaId = ((Number)escuderiaObj).intValue();
        } else {
            throw new IllegalArgumentException("Formato de escuderia_id inválido");
        }
        
        System.out.println("Buscando escudería con ID: " + escuderiaId);
        Escuderia escuderia = escRep.findById(escuderiaId);
        
        if (escuderia == null) {
            System.err.println("No se encontró escudería con ID: " + escuderiaId);
            respuesta.put("resultado", "error");
            respuesta.put("mensaje", "Escudería no encontrada");
            return respuesta;
        }
        
        // Crear y guardar el piloto
        Piloto nuevoPiloto = new Piloto();
        nuevoPiloto.setNombre(nombre);
        nuevoPiloto.setDorsal(dorsal);
        nuevoPiloto.setEscuderia(escuderia);
        
        System.out.println("Guardando piloto: " + nuevoPiloto.getNombre() + 
                           " con escudería: " + escuderia.getNombre());
        
        pilRep.save(nuevoPiloto);
        
        System.out.println("Piloto guardado con ID: " + nuevoPiloto.getId());
        respuesta.put("resultado", "ok");
        respuesta.put("id", nuevoPiloto.getId());
        respuesta.put("mensaje", "Piloto añadido correctamente");
        
    } catch (Exception e) {
        System.err.println("Error al añadir piloto: " + e.getMessage());
        e.printStackTrace();
        respuesta.put("resultado", "error");
        respuesta.put("mensaje", "Error: " + e.getMessage());
    }
    
    return respuesta;
}

    static class DatosAltaPiloto {
        public int id;
        public int dorsal;
        public String nombre;
        public int escuderiaId; 
        

        public DatosAltaPiloto(int id, int dorsal, String nombre, int escuderiaId) {
            super();
            this.id = id;
            this.dorsal = dorsal;
            this.nombre = nombre;
            this.escuderiaId = escuderiaId;
        }
    }

    @PutMapping(path = "/editar", consumes = MediaType.APPLICATION_JSON_VALUE)
public DTO editarPiloto(@RequestBody DTO datos, HttpServletRequest request) {
    DTO respuesta = new DTO();
    
    try {
        System.out.println("Datos recibidos para edición: " + datos.toString());
        
        // Extraer los datos con manejo de errores
        int id = Integer.parseInt(datos.get("id").toString());
        String nombre = datos.get("nombre").toString();
        int dorsal = Integer.parseInt(datos.get("dorsal").toString());
        
        // Manejar escuderia_id que puede venir como String
        int escuderiaId;
        Object escuderiaObj = datos.get("escuderia_id");
        if (escuderiaObj instanceof String) {
            escuderiaId = Integer.parseInt((String)escuderiaObj);
        } else if (escuderiaObj instanceof Number) {
            escuderiaId = ((Number)escuderiaObj).intValue();
        } else {
            throw new IllegalArgumentException("Formato de escuderia_id inválido");
        }
        
        // Buscar el piloto a editar
        Piloto piloto = pilRep.findById(id);
        if (piloto == null) {
            respuesta.put("resultado", "error");
            respuesta.put("mensaje", "Piloto no encontrado");
            return respuesta;
        }
        
        // Buscar la escudería
        Escuderia escuderia = escRep.findById(escuderiaId);
        if (escuderia == null) {
            respuesta.put("resultado", "error");
            respuesta.put("mensaje", "Escudería no encontrada");
            return respuesta;
        }
        
        // Actualizar el piloto
        piloto.setNombre(nombre);
        piloto.setDorsal(dorsal);
        piloto.setEscuderia(escuderia);
        
        pilRep.save(piloto);
        
        respuesta.put("resultado", "ok");
        respuesta.put("mensaje", "Piloto actualizado correctamente");
        
    } catch (Exception e) {
        System.err.println("Error al editar piloto: " + e.getMessage());
        e.printStackTrace();
        respuesta.put("resultado", "error");
        respuesta.put("mensaje", "Error: " + e.getMessage());
    }
    
    return respuesta;
}

    static class DatosEdicionPiloto {
        public int id;
        public int dorsal;
        public String nombre;
        public String escuderia;

        public DatosEdicionPiloto(int id, int dorsal, String nombre, String escuderia) {
            super();
            this.id = id;
            this.dorsal = dorsal;
            this.nombre = nombre;
            this.escuderia = escuderia;
        }
    }

}
