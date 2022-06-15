/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.in5bm.manuelbalcarcel.bean.db;

/**
 *
 * @author Juan José Pineda Soto
 * @date3/05/2022
 * @time11:09:02
 *
 * Código técnico IN5BM Carné 2021044 Grupo Lunes(2)
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class Conection {

    private final String URL;
    private final String IP_SERVER = "127.0.0.1";
    private final String PORT = "3306";
    private final String DB = "control_academico_grupo6_in5bm";
    private final String USER = "kinal";
    private final String PASS = "admin";
    private Connection conection;
    private static Conection instancia;

    public static Conection getInstance() {

        if (instancia == null) {
            instancia = new Conection();
        }
        return instancia;
    }

    public Connection getConection(){
    return conection;
    } 
    public Conection() {

        URL = "jdbc:mysql://" + IP_SERVER + ":" + PORT + "/" + DB;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexion exitosa");

            DatabaseMetaData dma = conection.getMetaData();
            System.out.println("\nConected to " + dma.getURL());
            System.out.println("driver" + dma.getDriverName());
            System.out.println("version" + dma.getDriverVersion() + "\n");
        } catch (ClassNotFoundException e) {
            System.err.println("no se encuentra ninguna definicion para la clase");
            e.printStackTrace();

            /*
            }catch (InstantiationException e){ 
             
        }catch(IllegalAccessException e){
             */
        } catch (Exception e) {
            System.out.println("Se produjo un error");
            e.printStackTrace();
        }
    }
}
