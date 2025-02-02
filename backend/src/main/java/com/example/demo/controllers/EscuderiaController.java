package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Escuderia;
import com.example.demo.repositories.EscuderiaRepositorio;

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
    
}
