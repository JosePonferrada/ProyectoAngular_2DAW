package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the piloto database table.
 * 
 */
@Entity
@NamedQuery(name="Piloto.findAll", query="SELECT p FROM Piloto p")
public class Piloto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int dorsal;

	@Temporal(TemporalType.DATE)
	private Date fechaNacimiento;

	private String foto;

	private String nacionalidad;

	private String nombre;

	private int puntos;

	//bi-directional many-to-one association to Carrera
	@OneToMany(mappedBy="piloto")
	private List<Carrera> carreras;

	//bi-directional many-to-one association to Escuderia
	@ManyToOne
	@JoinColumn(name="equipoId")
	private Escuderia escuderia;

	//bi-directional many-to-one association to Resultado
	@OneToMany(mappedBy="piloto")
	private List<Resultado> resultados;

	public Piloto() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDorsal() {
		return this.dorsal;
	}

	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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
		carrera.setPiloto(this);

		return carrera;
	}

	public Carrera removeCarrera(Carrera carrera) {
		getCarreras().remove(carrera);
		carrera.setPiloto(null);

		return carrera;
	}

	public Escuderia getEscuderia() {
		return this.escuderia;
	}

	public void setEscuderia(Escuderia escuderia) {
		this.escuderia = escuderia;
	}

	public List<Resultado> getResultados() {
		return this.resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

	public Resultado addResultado(Resultado resultado) {
		getResultados().add(resultado);
		resultado.setPiloto(this);

		return resultado;
	}

	public Resultado removeResultado(Resultado resultado) {
		getResultados().remove(resultado);
		resultado.setPiloto(null);

		return resultado;
	}

}