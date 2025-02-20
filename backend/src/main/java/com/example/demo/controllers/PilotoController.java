package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping(path = "/obtenerPorId", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getPilotoPorId(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoPiloto = new DTO();
        Piloto piloto = pilRep.findById(Integer.parseInt(soloId.get("id").toString()));

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

    @PostMapping("/anadirNuevo")
    public void anadirPiloto(@RequestBody DatosAltaPiloto datos, HttpServletRequest request) {
        Escuderia escuderia = escRep.findById(datos.escuderiaId);
        
        pilRep.save(new Piloto(datos.id, datos.dorsal, datos.nombre, escuderia));

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

}
