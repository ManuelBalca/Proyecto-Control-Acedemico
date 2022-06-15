package org.in5bm.manuelbalcarcel.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.manuelbalcarcel.bean.db.Conection;
import org.in5bm.manuelbalcarcel.models.Salones;
import org.in5bm.manuelbalcarcel.system.Principal;

/**
 *
 * @author DELL
 */
public class SalonesController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private final String PAQUETE_IMAGE = "org/in5bm/manuelbalcarcel/resources/image/";
    private SalonesController.Operacion operacion = SalonesController.Operacion.NINGUNO;
    private Principal escenarioPrincipal;
    private final String TIPO_ALERT_WARNING = "warning";
    private final String TITULO_ALERT = "Control Académico Kinal";

    @FXML
    private TableView tblSalones;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgModificar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnReporte;
    @FXML
    private TextField txtSalon;
    @FXML
    private TextField txtCapacidadMaxima;
    @FXML
    private TextField txtEdificio;
    @FXML
    private TextField txtNivel;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TableColumn colSalon;
    @FXML
    private TableColumn colCapacidad;
    @FXML
    private TableColumn colEdificio;
    @FXML
    private TableColumn colNivel;
    @FXML
    private TableColumn colDescripcion;

    private ObservableList<Salones> listaSalones;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public boolean existeElementoSeleccionado() {
        return (tblSalones.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtSalon.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());

            txtCapacidadMaxima.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima()));

            txtEdificio.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getEdificio());

            txtNivel.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel()));

            txtDescripcion.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());

        }
    }

    private void habilitarCampo() {
        txtSalon.setEditable(false);
        txtCapacidadMaxima.setEditable(true);
        txtEdificio.setEditable(true);
        txtNivel.setEditable(true);
        txtDescripcion.setEditable(true);

        txtSalon.setDisable(true);
        txtCapacidadMaxima.setDisable(false);
        txtEdificio.setDisable(false);
        txtNivel.setDisable(false);
        txtDescripcion.setDisable(false);
    }

    private void desHabilitarCampos() {
        txtSalon.setEditable(true);
        txtDescripcion.setEditable(false);
        txtCapacidadMaxima.setEditable(false);
        txtEdificio.setEditable(false);
        txtNivel.setEditable(false);

        txtSalon.setEditable(true);
        txtDescripcion.setEditable(true);
        txtCapacidadMaxima.setEditable(true);
        txtEdificio.setEditable(true);
        txtNivel.setEditable(true);
    }

    public void cargarDatos() {
        tblSalones.setItems(getSalones());
        colSalon.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("codigoSalon"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("nivel"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
    }

    public ObservableList getSalones() {

        ArrayList<Salones> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conection.getInstance().getConection().prepareCall("Call sp_salones_read()");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Salones salones = new Salones();
                salones.setCodigoSalon(rs.getString(1));
                salones.setDescripcion(rs.getString(2));
                salones.setCapacidadMaxima(rs.getInt(3));
                salones.setEdificio(rs.getString(4));
                salones.setNivel(rs.getInt(5));

                lista.add(salones);

                System.out.println(salones.toString());
            }
            listaSalones = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de salones");
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
        return listaSalones;
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
        txtSalon.setText("");
        txtSalon.setText("");
        txtCapacidadMaxima.setText("");
        txtEdificio.setText("");
        txtNivel.setText("");
        txtDescripcion.setText("");
    }

    @FXML
    private void nuevoIngreso() {
        switch (operacion) {
            case NINGUNO:
                habilitarCampo();
                tblSalones.setDisable(true);
        
                txtSalon.setEditable(true);
                txtSalon.setDisable(false);
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
                    if (agregarSalones()) {
                        cargarDatos();
                        limpiarTxtField();
                        desHabilitarCampos();
                    }
                    tblSalones.setDisable(false);
        
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

    private boolean agregarSalones() {
        Salones salones = new Salones();

        salones.setCodigoSalon(txtSalon.getText());
        salones.setDescripcion(txtDescripcion.getText());
        salones.setCapacidadMaxima(Integer.parseInt(txtCapacidadMaxima.getText()));
        salones.setEdificio(txtEdificio.getText());
        salones.setNivel(Integer.parseInt(txtNivel.getText()));
        
        PreparedStatement pstmt = null;

        try {
            pstmt = Conection.getInstance().getConection()
              .prepareCall("CALL sp_salones_create(?, ?, ?, ?, ?)");
            pstmt.setString(1, salones.getCodigoSalon());
            pstmt.setString(2, salones.getDescripcion());
            pstmt.setInt(3, salones.getCapacidadMaxima());
            pstmt.setString(5, salones.getEdificio());
            pstmt.setInt(5, salones.getNivel());

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaSalones.add(salones);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar el regisntro de: " + salones.toString());
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
        boolean salon = false;
        boolean capacidadMaxima = false;
        boolean edificio = false;
        boolean nivel = false;
        boolean descripcion = false;

        if (txtSalon.getText().isEmpty()
                && txtSalon.getText().isEmpty()
                && txtCapacidadMaxima.getText().isEmpty()
                && txtNivel.getText().isEmpty()
                && txtDescripcion.getText().isEmpty()) {
            System.out.println("vacio");
            mostrarAlert(TIPO_ALERT_WARNING, "Los campos se encuentran vacíos");
        } else {

            if (txtSalon.getText().isEmpty()) {

                mostrarAlert(TIPO_ALERT_WARNING, "El campo codigo salon esta vacio");
            } else {
                if (txtSalon.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "el campo codigo salon tiene espacios al inicio.");
                } else {
                    codigo = true;
                }
            }

            if (txtSalon.getText().isEmpty()) {
                mostrarAlert(TIPO_ALERT_WARNING, "El campo salon esta vacio");
            } else {
                if (txtSalon.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "el campo salon tiene espacios al inicio.");
                } else {
                    salon = true;
                }
            }

            if (txtCapacidadMaxima.getText().isEmpty()) {
                capacidadMaxima = true;
            } else {
                if (txtCapacidadMaxima.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "el campo capacidad tiene espacios al inicio.");
                } else {
                    capacidadMaxima = true;
                }
            }

            if (txtEdificio.getText().isEmpty()) {
                edificio = true;
            } else {
                if (txtEdificio.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "el campo edificio tiene espacios al inicio.");
                } else {
                    edificio = true;
                }
            }

            if (txtNivel.getText().isEmpty()) {
                mostrarAlert(TIPO_ALERT_WARNING, "Vel campo nivel esta vacio.");
            } else {
                if (txtNivel.getText().charAt(0) == ' ') {
                    mostrarAlert(TIPO_ALERT_WARNING, "el campo nivel tiene espacios al inicio.");
                } else {
                    nivel = true;
                }
            }
            if (codigo && salon && capacidadMaxima && edificio && nivel && descripcion) {
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
                if (actualizarSalones()) {
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

    public boolean actualizarSalones() {
        Salones salones = new Salones();
        salones.setCodigoSalon(txtSalon.getText());
        salones.setCapacidadMaxima(Integer.parseInt(txtCapacidadMaxima.getText()));
        salones.setEdificio(txtEdificio.getText());
        salones.setNivel(Integer.parseInt(txtNivel.getText()));
        salones.setDescripcion(txtDescripcion.getText());
        PreparedStatement pstmt = null;
        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("CALL sp_salones_update(?, ?, ?, ?, ?)");
            pstmt.setString(1, salones.getCodigoSalon());
            pstmt.setInt(2, salones.getCapacidadMaxima());
            pstmt.setString(3, salones.getEdificio());
            pstmt.setInt(4, salones.getNivel());
            pstmt.setString(5, salones.getDescripcion());

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
                        if (eliminarSalon());
                        {
                            listaSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
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

    public boolean eliminarSalon() {
        if (existeElementoSeleccionado()) {
            Salones salones = (Salones) tblSalones.getSelectionModel().getSelectedItem();
            System.out.println(salones.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conection.getInstance().getConection().prepareCall("CALL sp_salones_delete(?)");
                pstmt.setString(1, salones.getCodigoSalon());
                System.out.println(pstmt);
                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.err.println("\n Se produjo un error al intentar eliminar el siguiente registro: " + salones.toString());
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
