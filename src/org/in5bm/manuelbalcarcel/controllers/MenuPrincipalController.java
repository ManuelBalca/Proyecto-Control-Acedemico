package org.in5bm.manuelbalcarcel.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.in5bm.manuelbalcarcel.system.Principal;
import javafx.event.ActionEvent;

/**
 *
 * @author informatica
 */
public class MenuPrincipalController implements Initializable {

    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void clicAlumno(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAlumnos();
    }

    @FXML
    public void clicSalones(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaSalones();
    }

    @FXML
    public void clicCarreras(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaCarreras();
    }

    @FXML
    public void clicCursos(ActionEvent event) {
        escenarioPrincipal.mostrarCursos();
    }
     @FXML
    public void clicHorarios(ActionEvent event) {
        escenarioPrincipal.mostrarHorarios();
    }
}
