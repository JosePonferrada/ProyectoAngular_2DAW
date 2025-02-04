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
import com.example.demo.repositories.EscuderiaRepositorio;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("/escuderias")
public class EscuderiaController {

    @Autowired
    EscuderiaRepositorio escRepo;

    @GetMapping("/obtener")
    public List<DTO> obtenerEscuderias() {
        List<DTO> listaEscuderias = new ArrayList<DTO>();
        List<Escuderia> escuderias = escRepo.findAll();
        for (Escuderia escuderia : escuderias) {
            DTO dtoEsc = new DTO();
            dtoEsc.put("id", escuderia.getId());
            dtoEsc.put("nombre", escuderia.getNombre());
            dtoEsc.put("puntos", escuderia.getPuntos());
            listaEscuderias.add(dtoEsc);
        }
        return listaEscuderias;
    }
    
    @GetMapping(path = "/obtener1", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO obtenerEscuderia(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoEscuderia = new DTO();
        Escuderia escuderia = escRepo.findById(Integer.parseInt(soloId.get("id").toString()));
        if (escuderia != null) {
            dtoEscuderia.put("id", escuderia.getId());
            dtoEscuderia.put("nombre", escuderia.getNombre());
            dtoEscuderia.put("puntos", escuderia.getPuntos());
        }
        return dtoEscuderia;
    }

    @DeleteMapping("/borrar")
    public DTO borrarEscuderia(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoEscuderia = new DTO();
        Escuderia escuderia = escRepo.findById(Integer.parseInt(soloId.get("id").toString()));
        if (escuderia != null) {
            escRepo.delete(escuderia);
            dtoEscuderia.put("mensaje", "Escudería eliminada");
        } else {
            dtoEscuderia.put("error", "Escudería no encontrada");
        }
        return dtoEscuderia;
    }

    @PutMapping("/actualizar")
    public DTO actualizarEscuderia(@RequestBody DTO datos, HttpServletRequest request) {
        DTO dtoEscuderia = new DTO();
        Escuderia escuderia = escRepo.findById(Integer.parseInt(datos.get("id").toString()));
        if (escuderia != null) {
            escuderia.setNombre(datos.get("nombre").toString());
            escuderia.setPuntos(Integer.parseInt(datos.get("puntos").toString()));
            escRepo.save(escuderia);
            dtoEscuderia.put("mensaje", "Escudería actualizada");
        } else {
            dtoEscuderia.put("error", "Escudería no encontrada");
        }
        return dtoEscuderia;
    }

    @PostMapping(path = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void anadirEscuderia(@RequestBody DatosAltaEscuderia datos, HttpServletRequest request) {
        escRepo.save(new Escuderia(datos.id, datos.nombre, datos.puntos));
    }

    static class DatosAltaEscuderia {
        public int id;
        public String nombre;
        public int puntos;

        public DatosAltaEscuderia(int id, String nombre, int puntos) {
            super();
            this.id = id;
            this.nombre = nombre;
            this.puntos = puntos;
        }
    }

}
