package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Piloto;
import com.example.demo.repositories.EscuderiaRepositorio;
import com.example.demo.repositories.PilotoRepositorio;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin
@RestController
@RequestMapping("/pilotos")
public class PilotoController {
    
    @Autowired
    EscuderiaRepositorio escRepo;

    @Autowired
    PilotoRepositorio pilRepo;

    @GetMapping("/obtener")
    public List<DTO> obtenerPilotos() {
        List<DTO> listaPilotos = new ArrayList<>();
        List<Piloto> pilotos = pilRepo.findAll();
        for (Piloto piloto : pilotos) {
            DTO dto = new DTO();
            dto.put("id", piloto.getId());
            dto.put("dorsal", piloto.getDorsal());
            dto.put("nombre", piloto.getNombre());
            dto.put("equipo", piloto.getEquipo().getNombre());
            dto.put("puntos", piloto.getPuntos());
            listaPilotos.add(dto);
        }
        return listaPilotos;
    }

    @PostMapping(path = "/obtener1", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO obtenerPiloto(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoPiloto = new DTO();
        Piloto piloto = pilRepo.findById(Integer.parseInt(soloId.get("id").toString()));
        if (piloto != null) {
            dtoPiloto.put("id", piloto.getId());
            dtoPiloto.put("dorsal", piloto.getDorsal());
            dtoPiloto.put("nombre", piloto.getNombre());
            dtoPiloto.put("equipo", piloto.getEquipo().getNombre());
            dtoPiloto.put("puntos", piloto.getPuntos());
        } else {
            dtoPiloto.put("error", "No se ha encontrado el piloto");
        }
        return dtoPiloto;
    }

    @DeleteMapping("/borrar")
    public DTO borrarPiloto(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoPiloto = new DTO();
        Piloto piloto = pilRepo.findById(Integer.parseInt(soloId.get("id").toString()));
        if (piloto != null) {
            pilRepo.delete(piloto);
            dtoPiloto.put("mensaje", "Piloto borrado correctamente");
        } else {
            dtoPiloto.put("error", "No se ha encontrado el piloto");
        }
        return dtoPiloto;
    }

    @PutMapping(path = "/modificar")
    public void modificarPiloto(@RequestBody DatosAltaPiloto datosAltaPiloto, HttpServletRequest request) {
        Piloto piloto = pilRepo.findById(datosAltaPiloto.id);
        piloto.setDorsal(datosAltaPiloto.dorsal);
        piloto.setNombre(datosAltaPiloto.nombre);
        piloto.setEquipo(escRepo.findByNombre(datosAltaPiloto.equipo));
        piloto.setPuntos(datosAltaPiloto.puntos);
        pilRepo.save(piloto);
    }

    @PostMapping(path = "/anadir")
    public void anadirPiloto(@RequestBody DatosAltaPiloto datosAltaPiloto, HttpServletRequest request) {
        pilRepo.save(new Piloto(datosAltaPiloto.id, datosAltaPiloto.dorsal, datosAltaPiloto.nombre,
                escRepo.findByNombre(datosAltaPiloto.equipo), datosAltaPiloto.puntos));
    }

    static class DatosAltaPiloto {
        int id;
        int dorsal;
        String nombre;
        String equipo;
        int puntos;
        
        public DatosAltaPiloto(int id, int dorsal, String nombre, String equipo, int puntos) {
            super();
            this.id = id;
            this.dorsal = dorsal;
            this.nombre = nombre;
            this.equipo = equipo;
            this.puntos = puntos;
        }
        
    }

}
