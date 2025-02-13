package com.example.demo.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the carrera database table.
 * 
 */
@Entity
@NamedQuery(name="Carrera.findAll", query="SELECT c FROM Carrera c")
public class Carrera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	//bi-directional many-to-one association to Circuito
	@ManyToOne
	@JoinColumn(name="circuitoId")
	private Circuito circuito;

	//bi-directional many-to-one association to Escuderia
	@ManyToOne
	@JoinColumn(name="ganadorEquipoId")
	private Escuderia escuderia;

	//bi-directional many-to-one association to Piloto
	@ManyToOne
	@JoinColumn(name="ganadorPilotoId")
	private Piloto piloto;

	//bi-directional many-to-one association to Resultado
	@OneToMany(mappedBy="carrera")
	private List<Resultado> resultados;

	public Carrera() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Circuito getCircuito() {
		return this.circuito;
	}

	public void setCircuito(Circuito circuito) {
		this.circuito = circuito;
	}

	public Escuderia getEscuderia() {
		return this.escuderia;
	}

	public void setEscuderia(Escuderia escuderia) {
		this.escuderia = escuderia;
	}

	public Piloto getPiloto() {
		return this.piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public List<Resultado> getResultados() {
		return this.resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

	public Resultado addResultado(Resultado resultado) {
		getResultados().add(resultado);
		resultado.setCarrera(this);

		return resultado;
	}

	public Resultado removeResultado(Resultado resultado) {
		getResultados().remove(resultado);
		resultado.setCarrera(null);

		return resultado;
	}

}