package org.in5bm.manuelbalcarcel.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.manuelbalcarcel.bean.db.Conection;
import org.in5bm.manuelbalcarcel.models.CarrerasTecnicas;
import org.in5bm.manuelbalcarcel.system.Principal;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

/**
 *
 * @author DELL
 */
public class CarrerasTecnicasController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
   
    private final String PAQUETE_IMAGE = "org/in5bm/manuelbalcarcel/resources/image/";
    private Operacion operacion = Operacion.NINGUNO;
    private Principal escenarioPrincipal;
    private final String TIPO_ALERT_WARNING = "warning";
    private final String TITULO_ALERT = "Control Académico Kinal";
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgModificar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField txtCodigoTecnico;
    @FXML
    private TextField txtCarrera;
    @FXML
    private TextField txtGrado;
    @FXML
    private TextField txtSeccion;
    @FXML
    private TextField txtJornada;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnReporte;
    @FXML
    private TableView tblCarreras;
    @FXML
    private TableColumn colCodigoTec;
    @FXML
    private TableColumn colCarrera;
    @FXML
    private TableColumn colGrado;
    @FXML
    private TableColumn colSeccion;
    @FXML
    private TableColumn colJornada;

    private ObservableList<CarrerasTecnicas> listaCarreras;
   

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public boolean existeElementoSeleccionado() {
        return (tblCarreras.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtCodigoTecnico.setText(((CarrerasTecnicas) tblCarreras.getSelectionModel().getSelectedItem()).getCodigo_tecnico());

            txtCarrera.setText(((CarrerasTecnicas) tblCarreras.getSelectionModel().getSelectedItem()).getCarrera());

            txtGrado.setText(((CarrerasTecnicas) tblCarreras.getSelectionModel().getSelectedItem()).getGrado());

            txtSeccion.setText(((CarrerasTecnicas) tblCarreras.getSelectionModel().getSelectedItem()).getSeccion());

            txtJornada.setText(((CarrerasTecnicas) tblCarreras.getSelectionModel().getSelectedItem()).getJornada());

        }
    }

    private void desHabilitarCampos() {
        txtCodigoTecnico.setEditable(true);
        txtCarrera.setEditable(false);
        txtGrado.setEditable(false);
        txtSeccion.setEditable(false);
        txtJornada.setEditable(false);

        txtCodigoTecnico.setDisable(true);
        txtCarrera.setDisable(true);
        txtGrado.setDisable(true);
        txtSeccion.setDisable(true);
        txtJornada.setDisable(true);
    }

    public void cargarDatos() {
        tblCarreras.setItems(getCarreras());
        colCodigoTec.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigo_tecnico"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("jornada"));

    }

    private void habilitarCampo() {
        txtCodigoTecnico.setEditable(false);
        txtCarrera.setEditable(true);
        txtGrado.setEditable(true);
        txtSeccion.setEditable(true);
        txtJornada.setEditable(true);

        txtCodigoTecnico.setDisable(true);
        txtCarrera.setDisable(false);
        txtGrado.setDisable(false);
        txtSeccion.setDisable(false);
        txtJornada.setDisable(false);
    }
   
    public ObservableList getCarreras() {

        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conection.getInstance().getConection().prepareCall("Call sp_carreras_tecnicas_read()");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CarrerasTecnicas carreras = new CarrerasTecnicas();
                carreras.setCodigo_tecnico(rs.getString(1));
                carreras.setCarrera(rs.getString(2));
                carreras.setGrado(rs.getString(3));
                carreras.setSeccion(rs.getString(4));
                carreras.setJornada(rs.getString(5));
;

                lista.add(carreras);

                System.out.println(carreras.toString());
            }
            listaCarreras = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de carreras");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //return FXCollections.observableArrayList(lista);
        return listaCarreras;
    }

    private void info() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); //Instancia la clase Alert y se indica el tipo de alerta
        //alert.setHeaderText("Informe"); //Nombre del encabezado
        alert.setHeaderText(null); //Si no se desea encabezado se coloca un null
        alert.setTitle("Informe"); //Titulo del cuadro 
        alert.setContentText("Opcion exclusiva de la version pro"); //Mensaje que se quiere mostrar

        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
        Image icon = new Image(getClass().getResourceAsStream("../resources/image/IconoAlert.png"));
        stageAlert.getIcons().add(icon);

        alert.showAndWait();
    }

    public void limpiarTxtField() {
        txtCodigoTecnico.setText("");
        txtCarrera.setText("");
        txtGrado.setText("");
        txtSeccion.setText("");
        txtJornada.setText("");
    }

    @FXML
    private void nuevoIngreso() {
        switch (operacion) {
            case NINGUNO:
                habilitarCampo();
                txtCodigoTecnico.setEditable(true);
                txtCodigoTecnico.setDisable(false);
                limpiarTxtField();
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "image.png"));

                btnModificar.setText("cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "cancel.png"));

                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);

                btnReporte.setDisable(true);
                btnReporte.setVisible(false);

                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
              if(comprobacionCampostxt()){  
                if (agregarCarreras()) {
                    cargarDatos();
                    limpiarTxtField();
                    desHabilitarCampos();
                }
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "image (2).png"));

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "image (3).png"));

                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                operacion = Operacion.NINGUNO;
              }
                break;
        }
    }

    private boolean agregarCarreras() {
        CarrerasTecnicas carreras = new CarrerasTecnicas();

        carreras.setCodigo_tecnico(txtCodigoTecnico.getText());
        carreras.setCarrera(txtCarrera.getText());
        carreras.setGrado(txtGrado.getText());
        carreras.setSeccion(txtSeccion.getText());
        carreras.setJornada(txtJornada.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("CALL sp_carreras_tecnicas_create(?, ?, ?, ?, ?)");
            pstmt.setString(1, carreras.getCodigo_tecnico());
            pstmt.setString(2, carreras.getCarrera());
            pstmt.setString(3, carreras.getGrado());
            pstmt.setString(4, carreras.getSeccion());
            pstmt.setString(5, carreras.getJornada());
           

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaCarreras.add(carreras);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar el regisntro de: " + carreras.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private boolean comprobacionCampostxt() {
        boolean codigo = false;
        boolean carrera = false ;
        boolean jornada = false;
        boolean grado = false ;
        boolean seccion = false;

        if (txtCodigoTecnico.getText().isEmpty()
                && txtCarrera.getText().isEmpty()
                && txtJornada.getText().isEmpty()
                && txtGrado.getText().isEmpty()
                && txtSeccion.getText().isEmpty()
                ) {
            System.out.println("vacio");
            mostrarAlert(TIPO_ALERT_WARNING, "Los campos se encuentran vacíos");
        } else {

            if (txtCodigoTecnico.getText().isEmpty()) {

                mostrarAlert(TIPO_ALERT_WARNING, "El campo codigo tecnico esta vacio");
            } else {
                if (txtCodigoTecnico.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "el campo codigo tecnico tiene espacios al inicio.");
                } else {
                    codigo = true;
                }
            }

            if (txtCarrera.getText().isEmpty()) {
                mostrarAlert(TIPO_ALERT_WARNING, "El campo carrera esta vacio");
            } else {
                if (txtCarrera.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "el campo carrera tiene espacios al inicio.");
                } else {
                    carrera = true;
                }
            }

            if (txtGrado.getText().isEmpty()) {
                grado = true;
            } else {
                if (txtGrado.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "el campo grado tiene espacios al inicio.");
                } else {
                    grado = true;
                }
            }

            if (txtSeccion.getText().isEmpty()) {
                seccion = true;
            } else {
                if (txtSeccion.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "el campo seccion tiene espacios al inicio.");
                } else {
                    seccion = true;
                }
            }

            if (txtJornada.getText().isEmpty()) {
                mostrarAlert(TIPO_ALERT_WARNING, "Vel campo jornada esta vacio.");
            } else {
                if (txtJornada.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "el campo jornada tiene espacios al inicio.");
                } else {
                    jornada = true;
                }
            }
            if (codigo && carrera && jornada && grado && seccion) {
                return true;
            }
        }

        return false;
    }

    private void mostrarAlert(String alertType, String contentText) {
        alertType = alertType.toLowerCase();
        switch (alertType) {
            case "warning":
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(TITULO_ALERT);
                alert.setHeaderText(null);
                alert.setContentText(contentText);
                alert.show();
                break;
        }
    }

    @FXML
    private void clicModificar() {

        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampo();

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "image.png"));

                    btnEliminar.setText("cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "cancel.png"));

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("CONTROL ACADEMICO KINAL");
                    alert.setHeaderText(null);
                    alert.setContentText("Seleccione un registro para continuar");
                    alert.show();
                }
                break;
            case GUARDAR:
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "image (2).png"));

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "image (3).png"));

                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                operacion = Operacion.NINGUNO;
                desHabilitarCampos();
                limpiarTxtField();

                break;

            case ACTUALIZAR:
                if (actualizarCarrera()) {
                    btnNuevo.setDisable(false);
                    btnNuevo.setVisible(true);

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "image (3).png"));

                    btnEliminar.setText("Eliminar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "image (4).png"));

                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);

                    operacion = Operacion.NINGUNO;
                    desHabilitarCampos();
                    limpiarTxtField();
                    cargarDatos();

                }
                break;
        }
    }

    public boolean actualizarCarrera() {
        CarrerasTecnicas carreras = new CarrerasTecnicas();
        carreras.setCodigo_tecnico(txtCodigoTecnico.getText());
        carreras.setCarrera(txtCarrera.getText());
        carreras.setGrado(txtGrado.getText());
        carreras.setSeccion(txtSeccion.getText());
        carreras.setJornada(txtJornada.getText());

        PreparedStatement pstmt = null;
        try {
            pstmt = Conection.getInstance().getConection()
                .prepareCall("CALL sp_carreras_update(?, ?, ?, ?, ?)");
            pstmt.setString(1, carreras.getCodigo_tecnico());
            pstmt.setString(2, carreras.getCarrera());
            pstmt.setString(3, carreras.getGrado());
            pstmt.setString(4, carreras.getSeccion());
            pstmt.setString(5, carreras.getJornada());


            System.out.println(pstmt.toString());
            pstmt.execute();

            return true;

        } catch (SQLException e) {
            System.err.println("No se logor actualizar la tabla: ");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case ACTUALIZAR:
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "image (3).png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGE + "image (4).png"));

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                operacion = Operacion.NINGUNO;
                desHabilitarCampos();
                limpiarTxtField();

                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
                    aler.setTitle("CONTROL ACADEMICO KINAL");
                    aler.setHeaderText(null);
                    aler.setContentText("Desea eliminar lo seleccionado?");
                    Optional<ButtonType> result = aler.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        if (eliminarAlumno());
                        {
                            listaCarreras.remove(tblCarreras.getSelectionModel().getFocusedIndex());
                            limpiarTxtField();
                            cargarDatos();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("CONTROL ACADEMICO KINAL");
                            alert.setHeaderText(null);
                            alert.setContentText("Registro Eliminado Exitosamente");
                            alert.show();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("CONTROL ACADEMICO KINAL");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro.");

                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGE + "IconoAlert.png"));

                    alert.show();

                }
                break;

        }
    }

    public boolean eliminarAlumno() {
        if (existeElementoSeleccionado()) {
            CarrerasTecnicas alumno = (CarrerasTecnicas) tblCarreras.getSelectionModel().getSelectedItem();
            System.out.println(alumno.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conection.getInstance().getConection().prepareCall("CALL sp_carreras_tecnicas_delete(?)");
                pstmt.setString(1, alumno.getCodigo_tecnico());
                System.out.println(pstmt);
                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.err.println("\n Se produjo un error al intentar eliminar el siguiente registro: " + alumno.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @FXML
    public void mostrarAlertInfo() {
        info();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    private void clicregresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenearioPrincipal();
    }
}






