/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.in5bm.manuelbalcarcel.models;

/**
 *
 * @author informatica
 */
import java.sql.Time;

public class Horarios {

    private int id;
    private Time horarioInicio;
    private Time horarioFinal;
    private boolean lunes;
    private boolean martes;
    private boolean miercoles;
    private boolean jueves;
    private boolean viernes;

    public Horarios() {

    }

    public Horarios(int id, Time horarioInicio, Time horarioFinal, boolean lunes, boolean martes, boolean miercoles, boolean jueves, boolean viernes) {
        this.id = id;
        this.horarioInicio = horarioInicio;
        this.horarioFinal = horarioFinal;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Time horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public Time getHorarioFinal() {
        return horarioFinal;
    }

    public void setHorarioFinal(Time horarioFinal) {
        this.horarioFinal = horarioFinal;
    }

    public boolean isLunes() {
        return lunes;
    }

    public void setLunes(boolean lunes) {
        this.lunes = lunes;
    }

    public boolean isMartes() {
        return martes;
    }

    public void setMartes(boolean martes) {
        this.martes = martes;
    }

    public boolean isMiercoles() {
        return miercoles;
    }

    public void setMiercoles(boolean miercoles) {
        this.miercoles = miercoles;
    }

    public boolean isJueves() {
        return jueves;
    }

    public void setJueves(boolean jueves) {
        this.jueves = jueves;
    }

    public boolean isViernes() {
        return viernes;
    }

    public void setViernes(boolean viernes) {
        this.viernes = viernes;
    }

    @Override
    public String toString() {
        return id + " | " + horarioInicio + " - " + horarioFinal;
    }
    
    
}