package com.example.demo.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Carrera;
import com.example.demo.model.Circuito;
import com.example.demo.model.Escuderia;
import com.example.demo.model.Piloto;
import com.example.demo.repository.CarreraRepositorio;
import com.example.demo.repository.CircuitoRepositorio;
import com.example.demo.repository.EscuderiaRepositorio;
import com.example.demo.repository.PilotoRepositorio;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/carrera")
public class CarreraController {

    @Autowired
    private CarreraRepositorio carreraRepositorio;

    @Autowired
    private CircuitoRepositorio circuitoRepositorio;

    @Autowired
    private EscuderiaRepositorio escuderiaRepositorio;

    @Autowired
    private PilotoRepositorio pilotoRepositorio;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @GetMapping("/obtener")
    public List<DTO> getCarreras() {
        List<DTO> listaCarreras = new ArrayList<DTO>();
        List<Carrera> carreras = carreraRepositorio.findAll();

        for (Carrera c : carreras) {
            DTO dtoCarrera = new DTO();
            dtoCarrera.put("id", c.getId());
            dtoCarrera.put("fecha", dateFormat.format(c.getFecha()));
            dtoCarrera.put("circuito", c.getCircuito().getNombre());
            dtoCarrera.put("escuderia", c.getEscuderia().getNombre());
            dtoCarrera.put("piloto", c.getPiloto().getNombre());
            listaCarreras.add(dtoCarrera);
        }

        return listaCarreras;
    }

    @PostMapping(path = "/obtener1", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO getCarrera(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoCarrera = new DTO();
        Optional<Carrera> c = Optional.of(carreraRepositorio.findById(Integer.parseInt(soloId.get("id").toString())));
        if (c.isPresent()) {
            dtoCarrera.put("id", c.get().getId());
            dtoCarrera.put("fecha", dateFormat.format(c.get().getFecha()));
            dtoCarrera.put("circuito", c.get().getCircuito().getNombre());
            dtoCarrera.put("escuderia", c.get().getEscuderia().getNombre());
            dtoCarrera.put("piloto", c.get().getPiloto().getNombre());
        } else {
            dtoCarrera.put("result", "fail");
        }
        return dtoCarrera;
    }

    @DeleteMapping(path = "/borrar1", consumes = MediaType.APPLICATION_JSON_VALUE)
    public DTO deleteCarrera(@RequestBody DTO soloId, HttpServletRequest request) {
        DTO dtoCarrera = new DTO();
        Optional<Carrera> c = Optional.of(carreraRepositorio.findById(Integer.parseInt(soloId.get("id").toString())));
        if (c.isPresent()) {
            carreraRepositorio.delete(c.get());
            dtoCarrera.put("borrado", "realizado");
        } else {
            dtoCarrera.put("borrado", "fail");
        }
        return dtoCarrera;
    }

    @PostMapping("/addnew")
    public void addNewCarrera(@RequestBody DatosAltaCarrera datos, HttpServletRequest request) {
        Optional<Circuito> circuito = Optional.of(circuitoRepositorio.findById(datos.circuitoId));
        Optional<Escuderia> escuderia = Optional.of(escuderiaRepositorio.findById(datos.escuderiaId));
        Optional<Piloto> piloto = Optional.of(pilotoRepositorio.findById(datos.pilotoId));

        if (circuito.isPresent() && escuderia.isPresent() && piloto.isPresent()) {
            Carrera nuevaCarrera = new Carrera();
            nuevaCarrera.setFecha(datos.fecha);
            nuevaCarrera.setCircuito(circuito.get());
            nuevaCarrera.setEscuderia(escuderia.get());
            nuevaCarrera.setPiloto(piloto.get());
            carreraRepositorio.save(nuevaCarrera);
        }
    }

    static class DatosAltaCarrera {
        int id;
        Date fecha;
        int circuitoId;
        int escuderiaId;
        int pilotoId;

        public DatosAltaCarrera(int id, Date fecha, int circuitoId, int escuderiaId, int pilotoId) {
            this.id = id;
            this.fecha = fecha;
            this.circuitoId = circuitoId;
            this.escuderiaId = escuderiaId;
            this.pilotoId = pilotoId;
        }
    }
}