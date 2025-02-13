package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Circuito;
import com.example.demo.repository.CircuitoRepositorio;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/circuito")
public class CircuitoController {

    @Autowired
    CircuitoRepositorio circuitoRepositorio;

    @GetMapping("/obtener")
    public List<DTO> getCircuitos() {
        
        List<DTO> listaCircuitos = new ArrayList<DTO>();
        List<Circuito> circuitos = circuitoRepositorio.findAll();

        for (Circuito c : circuitos) {
            DTO dtoCircuito = new DTO();
            dtoCircuito.put("id", c.getId());
            dtoCircuito.put("nombre", c.getNombre());
            dtoCircuito.put("pais", c.getPais());
            dtoCircuito.put("longitud", c.getLongitud());
            dtoCircuito.put("curvas", c.getCurvas());
            dtoCircuito.put("rectaMaxima", c.getRectaMaxima());
            dtoCircuito.put("record", c.getRecord());
            listaCircuitos.add(dtoCircuito);
        }

        return listaCircuitos;

    }
    
    @GetMapping("/obtener1")
    public List<Circuito> getCircuitos() {

        DTO dtoCircuito = new DTO();
        Circuito circuito = circuitoRepositorio.findById(1);

        if (circuito != null) {
            dtoCircuito.put("id", circuito.getId());
            dtoCircuito.put("nombre", circuito.getNombre());
            dtoCircuito.put("pais", circuito.getPais());
            dtoCircuito.put("longitud", circuito.getLongitud());
            dtoCircuito.put("vueltas", circuito.getVueltas());
            if (circuito.getFoto() != null) {
                dtoCircuito.put("foto", circuito.getFoto());
            } else {
                dtoCircuito.put("foto", "No disponible");
            }

            return dtoCircuito;

        }

    }

}
