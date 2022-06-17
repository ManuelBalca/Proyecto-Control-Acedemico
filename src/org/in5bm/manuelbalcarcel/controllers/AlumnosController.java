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
import org.in5bm.manuelbalcarcel.models.Alumnos;
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
public class AlumnosController implements Initializable {

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
    private TextField txtCarne;
    @FXML
    private TextField txtNombre1;
    @FXML
    private TextField txtNombre2;
    @FXML
    private TextField txtNombre3;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtApellido2;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnReporte;
    @FXML
    private TableView tblAlumnos;
    @FXML
    private TableColumn colCarne;
    @FXML
    private TableColumn colNombre1;
    @FXML
    private TableColumn colNombre2;
    @FXML
    private TableColumn colNombre3;
    @FXML
    private TableColumn colApellido1;
    @FXML
    private TableColumn colApellido2;

    private ObservableList<Alumnos> listaAlumnos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public boolean existeElementoSeleccionado() {
        return (tblAlumnos.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {

            txtCarne.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()) .getCarne() );
                  
            txtNombre1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre1());

            txtNombre2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre2());

            txtNombre3.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre3());

            txtApellido1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido1());

            txtApellido2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido2());
        }
    }

    private void desHabilitarCampos() {
        txtCarne.setEditable(true);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
    }

    public void cargarDatos() {
        tblAlumnos.setItems(getAlumnos());
        colCarne.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido2"));

    }

    private void habilitarCampo() {
        txtCarne.setEditable(false);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
    }

    public ObservableList getAlumnos() {

        ArrayList<Alumnos> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conection.getInstance().getConection().prepareCall("Call sp_alumnos_read()");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));

                lista.add(alumno);

                System.out.println(alumno.toString());
            }
            listaAlumnos = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de alumnos");
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
        return listaAlumnos;
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
        txtCarne.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
    }

    @FXML
    private void nuevoIngreso() {
        switch (operacion) {
            case NINGUNO:
                habilitarCampo();
                txtCarne.setEditable(true);
                txtCarne.setDisable(false);
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
                if (comprobacionCampostxt()) {
                    if (agregarAlumno()) {
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

    private boolean agregarAlumno() {
        Alumnos alumno = new Alumnos();

        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("CALL sp_alumnos_create(?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, alumno.getCarne());
            pstmt.setString(2, alumno.getNombre1());
            pstmt.setString(3, alumno.getNombre2());
            pstmt.setString(4, alumno.getNombre3());
            pstmt.setString(5, alumno.getApellido1());
            pstmt.setString(6, alumno.getApellido2());

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaAlumnos.add(alumno);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar el regisntro de: " + alumno.toString());
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
        boolean carne = false;
        boolean nombre1 = false;
        boolean apellido1 = false;
        boolean nombre2 = false;
        boolean nombre3 = false;
        boolean apellido2 = false;

        if (txtCarne.getText().isEmpty()
                && txtNombre1.getText().isEmpty()
                && txtApellido1.getText().isEmpty()
                && txtNombre2.getText().isEmpty()
                && txtNombre3.getText().isEmpty()
                && txtApellido2.getText().isEmpty()) {
            System.out.println("vacio");
            mostrarAlert(TIPO_ALERT_WARNING, "Los campos se encuentran vacíos");
        } else {

            if (txtCarne.getText().isEmpty()) {

                mostrarAlert(TIPO_ALERT_WARNING, "Verifique que el campo carné contenga datos.");
            } else {
                if (txtCarne.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "Verifique que el campo carné no contenga espacios al inicio.");
                } else {
                    carne = true;
                }
            }

            if (txtNombre1.getText().isEmpty()) {
                mostrarAlert(TIPO_ALERT_WARNING, "Verifique que el campo Primer Nombre contenga datos.");
            } else {
                if (txtNombre1.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "Verifique que el campo Primer Nombre no contenga espacios al inicio.");
                } else {
                    nombre1 = true;
                }
            }

            if (txtNombre2.getText().isEmpty()) {
                nombre2 = true;
            } else {
                if (txtNombre2.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "Verifique que el campo Segundo Nombre no contenga espacios al inicio.");
                } else {
                    nombre2 = true;
                }
            }

            if (txtNombre3.getText().isEmpty()) {
                nombre3 = true;
            } else {
                if (txtNombre3.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "Verifique que el campo Tercer Nombre no contenga espacios al inicio.");
                } else {
                    nombre3 = true;
                }
            }

            if (txtApellido1.getText().isEmpty()) {
                mostrarAlert(TIPO_ALERT_WARNING, "Verifique que el campo Primer Apellido contenga datos.");
            } else {
                if (txtApellido1.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "Verifique que el campo Primer Apellido no contenga espacios al inicio.");
                } else {
                    apellido1 = true;
                }
            }

            if (txtApellido2.getText().isEmpty()) {
                apellido2 = true;
            } else {
                if (txtApellido2.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "Verifique que el campo Segundo Apellido no contenga espacios al inicio.");
                } else {
                    apellido2 = true;
                }
            }

            if (carne && nombre1 && apellido1 && nombre2 && nombre3 && apellido2) {
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
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "cancel"));

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
                if (actualizarAlumno()) {
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

    public boolean actualizarAlumno() {
        Alumnos alumno = new Alumnos();
        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());

        PreparedStatement pstmt = null;
        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("CALL sp_alumnos_update(?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, alumno.getCarne());
            pstmt.setString(2, alumno.getNombre1());
            pstmt.setString(3, alumno.getNombre2());
            pstmt.setString(4, alumno.getNombre3());
            pstmt.setString(5, alumno.getApellido1());
            pstmt.setString(6, alumno.getApellido2());

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
                            listaAlumnos.remove(tblAlumnos.getSelectionModel().getFocusedIndex());
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
            Alumnos alumno = (Alumnos) tblAlumnos.getSelectionModel().getSelectedItem();
            System.out.println(alumno.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conection.getInstance().getConection().prepareCall("CALL sp_alumnos_delete(?)");
                pstmt.setString(1, alumno.getCarne());
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
