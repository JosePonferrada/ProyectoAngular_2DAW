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

import com.example.demo.models.Circuito;
import com.example.demo.models.Piloto;
import com.example.demo.repositories.CircuitoRepositorio;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@CrossOrigin
@RestController
@RequestMapping("/circuitos")
public class CircuitoController {

    @Autowired
    CircuitoRepositorio cirRep;

    @GetMapping("/obtenerTodos")
    public List<DTO> getCircuitos() {
        List<DTO> listaCircuitos = new ArrayList<DTO>();
        List<Circuito> circuitos = cirRep.findAll();

        for (Circuito circuito : circuitos) {
            DTO dto = new DTO();
            dto.put("id", circuito.getId());
            dto.put("nombre", circuito.getNombre());
            dto.put("pais", circuito.getPais());
            
            listaCircuitos.add(dto);
        }
        return listaCircuitos;
    }

    @PostMapping(path = "/obtenerPorId", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getCircuitoPorId(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoCircuito = new DTO();
        Circuito circuito = cirRep.findById(Integer.parseInt(soloId.get("id").toString()));

        if (circuito != null) {
            dtoCircuito.put("id", circuito.getId());
            dtoCircuito.put("nombre", circuito.getNombre());
            dtoCircuito.put("pais", circuito.getPais());
        } else {
            dtoCircuito.put("error", "No se ha encontrado el circuito");
        }
        return dtoCircuito;
    }

    @PostMapping(path = "/obtenerPorNombre", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getCircuitoPorNombre(@RequestBody DTO soloNombre, HttpServletRequest request) {
        DTO dtoCircuito = new DTO();
        Circuito circuito = cirRep.findByNombre(soloNombre.get("nombre").toString());

        if (circuito != null) {
            dtoCircuito.put("id", circuito.getId());
            dtoCircuito.put("nombre", circuito.getNombre());
            dtoCircuito.put("pais", circuito.getPais());
        } else {
            dtoCircuito.put("error", "No se ha encontrado el circuito");
        }
        return dtoCircuito;
    }

    @PostMapping(path = "/obtenerPorPais", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getCircuitoPorPais(@RequestBody DTO soloPais, HttpServletRequest request) {
        DTO dtoCircuito = new DTO();
        Circuito circuito = cirRep.findByPais(soloPais.get("pais").toString());

        if (circuito != null) {
            dtoCircuito.put("id", circuito.getId());
            dtoCircuito.put("nombre", circuito.getNombre());
            dtoCircuito.put("pais", circuito.getPais());
        } else {
            dtoCircuito.put("error", "No se ha encontrado el circuito");
        }
        return dtoCircuito;
    }

    @DeleteMapping(path = "/eliminar1", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO eliminarCircuitoPorId(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoCircuito = new DTO();
        Circuito circuito = cirRep.findById(Integer.parseInt(soloId.get("id").toString()));

        if (circuito != null) {
            cirRep.delete(circuito);
            dtoCircuito.put("mensaje", "Circuito eliminado correctamente");
        } else {
            dtoCircuito.put("error", "No se ha encontrado el circuito");
        }
        return dtoCircuito;
    }

    @PostMapping("/anadirNuevo")
    public void anadirCircuito(@RequestBody DatosAltaCircuito datos, HttpServletRequest request) {
        
        cirRep.save(new Circuito(datos.id, datos.nombre, datos.pais));

    }
    
    static class DatosAltaCircuito {
        public int id;
        public String nombre;
        public String pais;

        public DatosAltaCircuito(int id, String nombre, String pais) {
            super();
            this.id = id;
            this.nombre = nombre;
            this.pais = pais;
        }
    }

    @Transactional
    @GetMapping("/obtenerParticipantes")
    public List<DTO> getParticipantes() {
        List<DTO> listaParticipantes = new ArrayList<DTO>();
        List<Circuito> circuitos = cirRep.findAll();

        for (Circuito circuito : circuitos) {
            DTO dto = new DTO();
            dto.put("id", circuito.getId());
            dto.put("nombre", circuito.getNombre());
            dto.put("pais", circuito.getPais());
            
            List<String> nombresPilotos = circuito.getPilotos()
                .stream()
                .map(Piloto::getNombre)
                .collect(Collectors.toList());

            dto.put("pilotos", nombresPilotos);

            listaParticipantes.add(dto);
        }
        return listaParticipantes;
    }

    @PutMapping("/editar")
    public DTO editarCircuito(@RequestBody DatosEdicionCircuito datos, HttpServletRequest request) {
        DTO dtoCircuito = new DTO();
        Circuito circuito = cirRep.findById(datos.id);

        if (circuito != null) {
            circuito.setNombre(datos.nombre);
            circuito.setPais(datos.pais);
            cirRep.save(circuito);
            dtoCircuito.put("mensaje", "Circuito editado correctamente");
        } else {
            dtoCircuito.put("error", "No se ha encontrado el circuito");
        }
        return dtoCircuito;
    }

    static class DatosEdicionCircuito {
        public int id;
        public String nombre;
        public String pais;

        public DatosEdicionCircuito(int id, String nombre, String pais) {
            super();
            this.id = id;
            this.nombre = nombre;
            this.pais = pais;
        }
    }

}
