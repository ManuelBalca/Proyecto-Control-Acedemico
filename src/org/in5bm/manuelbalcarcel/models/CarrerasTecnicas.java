package org.in5bm.manuelbalcarcel.models;

/**
 *
 * @author Juan José Pineda Soto
 * @date28/04/2022
 * @time03:24:47
 *
 * Código técnico IN5BM
 * Carné 2021044
 * Grupo Lunes(2)
 */
public class CarrerasTecnicas {
    private String codigo_tecnico;
    private String carrera;
    private String grado;
    private String Seccion;
    private String jornada;
    
    
    public CarrerasTecnicas(){
        
    }

    public CarrerasTecnicas(String codigo_tecnico, String carrera, String grado, String String, String jornada) {
        this.codigo_tecnico = codigo_tecnico;
        this.carrera = carrera;
        this.grado = grado;
        this.Seccion = String;
        this.jornada = jornada;
    }
    public CarrerasTecnicas(String codigo_tecnico){
    this.codigo_tecnico = codigo_tecnico;
    }

    public String getCodigo_tecnico() {
        return codigo_tecnico;
    }

    public void setCodigo_tecnico(String codigoCarrera) {
        this.codigo_tecnico = codigoCarrera;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return Seccion;
    }

    public void setSeccion(String String) {
        this.Seccion = String;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

//    @Override
//    public String toString() {
//        return "CarrerasTecnicas{" + "codigo_tecnico=" + codigo_tecnico + ", carrera=" + carrera + ", grado=" + grado + ", String=" + Seccion + ", jornada=" + jornada + '}';
//    }
//    

    @Override
    public String toString() {
        return codigo_tecnico + "|" + carrera + "|" + grado;
    }
    
}