package org.in5bm.manuelbalcarcel.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.in5bm.manuelbalcarcel.controllers.*;

/**
 *
 * @author DELL
 */
public class Principal extends Application {

    private Stage escenarioPrincipal;
    private final String PAQUETE_IMAGE = "org/in5bm/manuelbalcarcel/resources/image/";
    private final String PAQUETE_VIEW = "../views/";

    public static void main(String[] args) {
        launch();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.setTitle("Control Academico Kinal");
        this.escenarioPrincipal.getIcons().add(new Image(PAQUETE_IMAGE + "imagenapp.png"));
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();
        mostrarEscenearioPrincipal();
    }

    public void mostrarEscenaAlumnos() {
        try {
            AlumnosController alumnosController = (AlumnosController) cambiarEscena("AlumnosView.fxml", 1121, 610);
            alumnosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\n Se produjo un error al intentar mostrar la vista de Alumnos\"");
            ex.printStackTrace();
        }
    }

    public void mostrarEscenaSalones() {
        try {
            SalonesController salonesController = (SalonesController) cambiarEscena("SalonesView.fxml", 1121, 610);
            salonesController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\n Se produjo un error al intentar mostrar la vista de salones\"");
            ex.printStackTrace();
        }
    }

    public void mostrarEscenaCarreras() {
        try {
            CarrerasTecnicasController CarrerasTecnicasController = (CarrerasTecnicasController) cambiarEscena("CarrerasTecnicasView.fxml", 1121, 610);
            CarrerasTecnicasController.setEscenarioPrincipal(this);
        } catch (IOException ex) {
            System.err.println("\n Se produjo un error al intentar mostrar la vista de Carreras\"");
            ex.printStackTrace();
        }
    }

    public void mostrarEscenearioPrincipal() {
        try {
            MenuPrincipalController menuController = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 1068, 622);
            menuController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Se produjo un error al mostrar la vista del menu principal ");
            ex.printStackTrace();
        }
    }

    public void mostrarCursos() {
        try {
            CursosController cursosController = (CursosController) cambiarEscena("CursosView.fxml", 1151, 610);
            cursosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Se produjo un error al mostrar la vista de cursos ");
            ex.printStackTrace();
        }
    }

    public void mostrarHorarios() {
        try {
            HorariosController horarioController = (HorariosController) cambiarEscena("HorariosView.fxml", 1151, 610);
            horarioController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("Se produjo un error al mostrar la vista de horario");
            ex.printStackTrace();
        }
    }

    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto) throws IOException {
        System.out.println(PAQUETE_VIEW + vistaFxml);
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFxml));
        Scene scene = new Scene((AnchorPane) cargadorFXML.load(), ancho, alto);
        this.escenarioPrincipal.setScene(scene);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        return (Initializable) cargadorFXML.getController();
    }

}
