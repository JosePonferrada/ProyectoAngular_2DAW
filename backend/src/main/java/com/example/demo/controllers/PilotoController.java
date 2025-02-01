package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Piloto;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
@RequestMapping("/pilotos")
public class PilotoController {
    
    @Autowired
    EscuderiaRepository escRepo;

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

}
