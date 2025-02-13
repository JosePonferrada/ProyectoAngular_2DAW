package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;


/**
 * The persistent class for the escuderia database table.
 * 
 */
@Entity
@NamedQuery(name="Escuderia.findAll", query="SELECT e FROM Escuderia e")
public class Escuderia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String foto;

	private String jefeEquipo;

	private String nacionalidad;

	private String nombre;

	private int puntos;

	//bi-directional many-to-one association to Carrera
	@OneToMany(mappedBy="escuderia")
	private List<Carrera> carreras;

	//bi-directional many-to-one association to Piloto
	@OneToMany(mappedBy="escuderia")
	private List<Piloto> pilotos;

	//bi-directional many-to-one association to Resultado
	@OneToMany(mappedBy="escuderia")
	private List<Resultado> resultados;

	public Escuderia() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getJefeEquipo() {
		return this.jefeEquipo;
	}

	public void setJefeEquipo(String jefeEquipo) {
		this.jefeEquipo = jefeEquipo;
	}

	public String getNacionalidad() {
		return this.nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntos() {
		return this.puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public List<Carrera> getCarreras() {
		return this.carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

	public Carrera addCarrera(Carrera carrera) {
		getCarreras().add(carrera);
		carrera.setEscuderia(this);

		return carrera;
	}

	public Carrera removeCarrera(Carrera carrera) {
		getCarreras().remove(carrera);
		carrera.setEscuderia(null);

		return carrera;
	}

	public List<Piloto> getPilotos() {
		return this.pilotos;
	}

	public void setPilotos(List<Piloto> pilotos) {
		this.pilotos = pilotos;
	}

	public Piloto addPiloto(Piloto piloto) {
		getPilotos().add(piloto);
		piloto.setEscuderia(this);

		return piloto;
	}

	public Piloto removePiloto(Piloto piloto) {
		getPilotos().remove(piloto);
		piloto.setEscuderia(null);

		return piloto;
	}

	public List<Resultado> getResultados() {
		return this.resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

	public Resultado addResultado(Resultado resultado) {
		getResultados().add(resultado);
		resultado.setEscuderia(this);

		return resultado;
	}

	public Resultado removeResultado(Resultado resultado) {
		getResultados().remove(resultado);
		resultado.setEscuderia(null);

		return resultado;
	}

}