package org.in5bm.manuelbalcarcel.models;

/**
 *
 * @author Juan José Pineda Soto
 * @date28/04/2022
 * @time03:13:03
 *
 * Código técnico IN5BM
 * Carné 2021044
 * Grupo Lunes(2)
 */
public class Salones {
    private String codigoSalon;
    private String descripcion;
    private int capacidadMaxima;
    private String edificio;
    private int nivel;
    
    public  Salones(){

    }

    public Salones(String codigoSalon, int capacidadMaxima){
        this.codigoSalon = codigoSalon;
        this.capacidadMaxima = capacidadMaxima;
    }

    public Salones(String codigoSalon, String descripcion, int capacidadMaxima, String edificio, int nivel){
        this.codigoSalon = codigoSalon;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.edificio = edificio;
        this.nivel = nivel;
    }

    public String getCodigoSalon() {
        return codigoSalon;
    }

    public void setCodigoSalon(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

//    @Override
//    public String toString() {
//        return "Salones{" + "codigoSalon=" + codigoSalon + ", descripcion=" + descripcion + ", capacidadMaxima=" + capacidadMaxima + ", edificio=" + edificio + ", nivel=" + nivel + '}';
//    } 
//  

    @Override
    public String toString() {
        return codigoSalon + "|" + descripcion ;
    }
    
}






