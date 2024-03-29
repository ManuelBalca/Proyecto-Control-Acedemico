/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.in5bm.manuelbalcarcel.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Spinner;

import javafx.scene.control.SpinnerValueFactory;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.in5bm.manuelbalcarcel.system.Principal;

import org.in5bm.manuelbalcarcel.models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import org.in5bm.manuelbalcarcel.bean.db.Conection;

public class CursosController implements Initializable {

    @FXML
    private Button btnNuevo;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private ImageView imgModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnReporte;

    @FXML
    private TableView<Cursos> tblCursos;
    @FXML
    private TableColumn<Cursos, Integer> colId;
    @FXML
    private TableColumn<Cursos, String> colNombreCurso;
    @FXML
    private TableColumn<Cursos, Integer> colCiclo;
    @FXML
    private TableColumn<Cursos, Integer> colCupoMaximo;
    @FXML
    private TableColumn<Cursos, Integer> colCupoMinimo;

    @FXML
    private TableColumn<Cursos, Integer> colInstructorId;
    @FXML
    private TableColumn<Cursos, String> colSalonId;
    @FXML
    private TableColumn<Cursos, String> colCodigoTecnico;
    @FXML
    private TableColumn<Cursos, Integer> colHorarioId;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombreCurso;

    @FXML
    private Spinner<Integer> spnCiclo;

    private SpinnerValueFactory<Integer> valueFactoryCiclo;

    @FXML
    private Spinner<Integer> spnCupoMaximo;

    private SpinnerValueFactory<Integer> valueFactoryCupoMaximo;

    @FXML
    private Spinner<Integer> spnCupoMinimo;

    private SpinnerValueFactory<Integer> valueFactoryCupoMinimo;

    @FXML
    private ComboBox<CarrerasTecnicas> cmbCarreraTecnica;
    @FXML
    private ComboBox<Horarios> cmbHorario;
    @FXML
    private ComboBox<Instructores> cmbInstructor;
    @FXML
    private ComboBox<Salones> cmbSalon;

    @FXML
    private TextField txtid;

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    private final String TIPO_ALERT_WARNING = "warning";
    private final String TIPO_ALERT_INFORMATION = "information";
    private final String TITULO_ALERT = "Control Académico Kinal";

    private Operacion operacion = Operacion.NINGUNO;

    private ObservableList<Cursos> listaObservableCursos;
    private ObservableList<Instructores> listaObservableInstructores;
    private ObservableList<Salones> listaObservableSalones;
    private ObservableList<CarrerasTecnicas> listaObservableCarrerasTecnicas;
    private ObservableList<Horarios> listaObservableHorarios;

    private Principal escenarioPrincipal;

    private final String PAQUETE_IMAGES = "org/in5bm/manuelbalcarcel/resources/image/";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        valueFactoryCiclo = new SpinnerValueFactory.IntegerSpinnerValueFactory(2020, 2050, 2022);
        spnCiclo.setValueFactory(valueFactoryCiclo);

        valueFactoryCupoMaximo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 20);
        spnCupoMaximo.setValueFactory(valueFactoryCupoMaximo);

        valueFactoryCupoMinimo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 5);
        spnCupoMinimo.setValueFactory(valueFactoryCupoMinimo);

        cargarDatos();

    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNombreCurso.setEditable(false);
        spnCiclo.setEditable(false);
        spnCupoMaximo.setEditable(false);
        spnCupoMinimo.setEditable(false);
        cmbCarreraTecnica.setEditable(false);
        cmbHorario.setEditable(false);
        cmbInstructor.setEditable(false);
        cmbSalon.setEditable(false);

        txtId.setDisable(true);
        txtNombreCurso.setDisable(true);
        spnCiclo.setDisable(true);
        spnCupoMaximo.setDisable(true);
        spnCupoMinimo.setDisable(true);
        cmbCarreraTecnica.setDisable(true);
        cmbHorario.setDisable(true);
        cmbInstructor.setDisable(true);
        cmbSalon.setDisable(true);
    }

    private void habilitarCampos() {
        txtId.setEditable(false);
        txtNombreCurso.setEditable(true);
        spnCiclo.setEditable(true);
        spnCupoMaximo.setEditable(true);
        spnCupoMinimo.setEditable(true);

        //cmbCarreraTecnica.setEditable(true);
        //cmbHorario.setEditable(true);
        //cmbInstructor.setEditable(true);
        //cmbSalon.setEditable(true);
        txtId.setDisable(true);
        txtNombreCurso.setDisable(false);
        spnCiclo.setDisable(false);
        spnCupoMaximo.setDisable(false);
        spnCupoMinimo.setDisable(false);
        cmbCarreraTecnica.setDisable(false);
        cmbHorario.setDisable(false);
        cmbInstructor.setDisable(false);
        cmbSalon.setDisable(false);
    }

    private void limpiarCampos() {
        txtId.setText("0");
        txtNombreCurso.clear();
        spnCiclo.getValueFactory().setValue(2022);
        spnCupoMaximo.getValueFactory().setValue(0);
        spnCupoMinimo.getValueFactory().setValue(0);
        cmbCarreraTecnica.valueProperty().set(null);
        cmbHorario.valueProperty().set(null);
        cmbInstructor.valueProperty().set(null);
        cmbSalon.valueProperty().set(null);
    }

    // read -> Listar todos los registros
    private ObservableList getCursos() {
        ArrayList<Cursos> arrayListCursos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("{CALL sp_cursos_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setId(rs.getInt("id"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setCiclo(rs.getInt("ciclo"));
                curso.setCupoMaximo(rs.getInt("cupo_maximo"));
                curso.setCupoMinimo(rs.getInt("cupo_minimo"));
                curso.setCarreraTecnicaId(rs.getString("carrera_tecnica_id"));
                curso.setHorarioId(rs.getInt("horario_id"));
                curso.setIntructorId(rs.getInt("instructor_id"));
                curso.setSalonId(rs.getString("salon_id"));

                System.out.println(curso.toString());

                arrayListCursos.add(curso);
            }

            listaObservableCursos = FXCollections.observableArrayList(arrayListCursos);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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

        return listaObservableCursos;
    }

    private ObservableList getCarrerasTecnicas() {
        ArrayList<CarrerasTecnicas> arrayListCarreras = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("{CALL sp_carreras_tecnicas_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {

                CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();

                carreraTecnica.setCodigo_tecnico(rs.getString("codigo_tecnico"));
                carreraTecnica.setCarrera(rs.getString("carrera"));
                carreraTecnica.setGrado(rs.getString("grado"));
                carreraTecnica.setSeccion(rs.getString("seccion"));
                carreraTecnica.setJornada(rs.getString("jornada"));

                System.out.println(carreraTecnica);

                arrayListCarreras.add(carreraTecnica);
            }

            listaObservableCarrerasTecnicas = FXCollections.observableArrayList(arrayListCarreras);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Carreras Técnicas");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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

        return listaObservableCarrerasTecnicas;
    }

    private ObservableList getHorarios() {
        ArrayList<Horarios> arrayListHorarios = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conection.getInstance().getConection().prepareCall("{CALL sp_horarios_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Horarios horario = new Horarios();
                horario.setId(rs.getInt(1));
                horario.setHorarioInicio(rs.getTime(2));
                horario.setHorarioFinal(rs.getTime(3));
                horario.setLunes(rs.getBoolean(4));
                horario.setMartes(rs.getBoolean(5));
                horario.setMiercoles(rs.getBoolean(6));
                horario.setJueves(rs.getBoolean(7));
                horario.setViernes(rs.getBoolean(8));
                System.out.println(horario.toString());
                arrayListHorarios.add(horario);
            }
            listaObservableHorarios = FXCollections.observableArrayList(arrayListHorarios);
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al consultar la tabla de Horarios");
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
        return listaObservableHorarios;
    }

    private ObservableList getSalones() {
        ArrayList<Salones> arrayListSalones = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conection.getInstance().getConection().prepareCall("{CALL sp_salones_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));
                System.out.println(salon.toString());
                arrayListSalones.add(salon);
            }
            listaObservableSalones = FXCollections.observableArrayList(arrayListSalones);
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al consultar la tabla de Salones");
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
        return listaObservableSalones;
    }

    private ObservableList getInstructores() {
        ArrayList<Instructores> arrayListInstructores = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("{CALL sp_instructores_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {

                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt(1));
                instructor.setNombre1(rs.getString(2));
                instructor.setNombre2(rs.getString(3));
                instructor.setNombre3(rs.getString(4));
                instructor.setApellido1(rs.getString(5));
                instructor.setApellido2(rs.getString(6));
                instructor.setDireccion(rs.getString(7));
                instructor.setEmail(rs.getString(8));
                instructor.setTelefono(rs.getString(9));
                instructor.setFechaNacimiento(rs.getDate(10).toLocalDate());

                System.out.println(instructor);

                arrayListInstructores.add(instructor);
            }

            listaObservableInstructores = FXCollections.observableArrayList(arrayListInstructores);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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

        return listaObservableInstructores;
    }

    public void cargarDatos() {
        tblCursos.setItems(getCursos());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCurso.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colCiclo.setCellValueFactory(new PropertyValueFactory<>("ciclo"));
        colCupoMaximo.setCellValueFactory(new PropertyValueFactory<>("cupoMaximo"));
        colCupoMinimo.setCellValueFactory(new PropertyValueFactory<>("cupoMinimo"));
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<>("carreraTecnicaId"));
        colHorarioId.setCellValueFactory(new PropertyValueFactory<>("horarioId"));
        colInstructorId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colSalonId.setCellValueFactory(new PropertyValueFactory<>("salonId"));

        cmbCarreraTecnica.setItems(getCarrerasTecnicas());
        cmbHorario.setItems(getHorarios());
        cmbInstructor.setItems(getInstructores());
        cmbSalon.setItems(getSalones());
    }

    private boolean agregarCursos() {
        Cursos curso = new Cursos();

        curso.setId(Integer.parseInt(txtId.getText()));
        curso.setNombreCurso(txtNombreCurso.getText());
        curso.setCiclo(spnCiclo.getValue());
        curso.setCupoMaximo(spnCupoMaximo.getValue());
        curso.setCupoMinimo(spnCupoMinimo.getValue());

        curso.setCarreraTecnicaId(((CarrerasTecnicas) cmbCarreraTecnica
                .getSelectionModel().getSelectedItem()).getCodigo_tecnico());

        curso.setHorarioId(((Horarios) cmbHorario.getSelectionModel()
                .getSelectedItem()).getId());

        curso.setIntructorId(((Instructores) cmbInstructor.getSelectionModel()
                .getSelectedItem()).getId());

        curso.setSalonId(((Salones) cmbSalon.getSelectionModel()
                .getSelectedItem()).getCodigoSalon());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("{CALL sp_cursos_create(?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setString(1, curso.getNombreCurso());
            pstmt.setInt(2, curso.getCiclo());
            pstmt.setInt(3, curso.getCupoMaximo());
            pstmt.setInt(4, curso.getCupoMinimo());
            pstmt.setString(5, curso.getCarreraTecnicaId());
            pstmt.setInt(6, curso.getHorarioId());
            pstmt.setInt(7, curso.getInstructorId());
            pstmt.setString(8, curso.getSalonId());

            System.out.println(pstmt.toString());

            pstmt.execute();
            listaObservableCursos.add(curso);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar "
                    + "el siguiente registro: " + curso.toString());
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

    private boolean actualizarCursos() {

        Cursos curso = new Cursos();

        curso.setId(Integer.parseInt(txtId.getText()));

        curso.setNombreCurso(txtNombreCurso.getText());
        curso.setCiclo(spnCiclo.getValue());
        curso.setCupoMaximo(spnCupoMaximo.getValue());
        curso.setCupoMinimo(spnCupoMinimo.getValue());

        curso.setCarreraTecnicaId(((CarrerasTecnicas) cmbCarreraTecnica
                .getSelectionModel().getSelectedItem()).getCodigo_tecnico());

        curso.setHorarioId(((Horarios) cmbHorario.getSelectionModel()
                .getSelectedItem()).getId());

        curso.setIntructorId(((Instructores) cmbInstructor.getSelectionModel()
                .getSelectedItem()).getId());

        curso.setSalonId(((Salones) cmbSalon.getSelectionModel()
                .getSelectedItem()).getCodigoSalon());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("{CALL sp_cursos_update(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setInt(1, curso.getId());
            pstmt.setString(2, curso.getNombreCurso());
            pstmt.setInt(3, curso.getCiclo());
            pstmt.setInt(4, curso.getCupoMaximo());
            pstmt.setInt(5, curso.getCupoMinimo());
            pstmt.setString(6, curso.getCarreraTecnicaId());
            pstmt.setInt(7, curso.getHorarioId());
            pstmt.setInt(8, curso.getInstructorId());
            pstmt.setString(9, curso.getSalonId());

            System.out.println(pstmt.toString());

            pstmt.execute();
            listaObservableCursos.add(curso);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar actualizar "
                    + "el siguiente registro: " + curso.toString());
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

    private boolean eliminarCursos() {

        Cursos curso = (Cursos) tblCursos.getSelectionModel().getSelectedItem();

        System.out.println(curso.toString());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("{CALL sp_cursos_delete(?)}");

            pstmt.setInt(1, curso.getId());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaObservableCursos.remove(tblCursos.getSelectionModel().getFocusedIndex());

            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + curso.toString());
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

    private boolean existeElementoSeleccionado() {
        return (tblCursos.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    private void nuevoIngreso(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                limpiarCampos();
                habilitarCampos();
                tblCursos.setDisable(true);

                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "image.png"));

                btnModificar.setText("cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "cancel.png"));

                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);

                btnReporte.setDisable(true);
                btnReporte.setVisible(false);

                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                if (comprobacionCamposTxt()) {
                    if (agregarCursos()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();

                    tblCursos.setDisable(false);
                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGES + "image (2).png"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "image (3).png"));
                    btnEliminar.setDisable(false);
                    btnEliminar.setVisible(true);
                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);
                    operacion = Operacion.NINGUNO;
                }
                }
                break;
        }
    }

    @FXML
    private void clicModificar(ActionEvent event) {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();
                    txtId.setEditable(false);
                    txtId.setDisable(true);
                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "image.png"));

                    btnEliminar.setText("cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancel.png"));
                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro");
                    alert.show();
                }
                break;
            case GUARDAR: // Cancelar inserción
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "image (2).png"));
                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "image (3).png"));
                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);
                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();
                tblCursos.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (existeElementoSeleccionado()) {
                    if (actualizarCursos()) {
                        limpiarCampos();
                        deshabilitarCampos();
                        cargarDatos();
                        tblCursos.setDisable(false);
                        tblCursos.getSelectionModel().clearSelection();

                        btnNuevo.setText("Nuevo");
                        imgNuevo.setImage(new Image(PAQUETE_IMAGES + "image (2).png"));
                        btnNuevo.setDisable(false);
                        btnNuevo.setVisible(true);

                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "image (3).png"));

                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGES + "image (4).png"));
                        btnEliminar.setDisable(false);
                        btnEliminar.setVisible(true);

                        btnReporte.setDisable(false);
                        btnReporte.setVisible(true);
                        operacion = Operacion.NINGUNO;
                    }
                }
                break;
        }
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
        switch (operacion) {
            case ACTUALIZAR: // Cancelar una modificación
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblCursos.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO: // Eliminar un registro
                if (existeElementoSeleccionado()) {
                    Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                    alertConfirm.setTitle("Control Académico Kinal");
                    alertConfirm.setHeaderText(null);
                    alertConfirm.setContentText("¿Está seguro que desea eliminar el registro seleccionado?");

                    Stage stage = (Stage) alertConfirm.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "IconoAlert.png"));

                    Optional<ButtonType> result = alertConfirm.showAndWait();
                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarCursos()) {
                            listaObservableCursos.remove(tblCursos.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();

                            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                            alertInformation.setTitle("Control Académico Kinal");
                            alertInformation.setHeaderText(null);
                            alertInformation.setContentText("Registro eliminado exitosamente");
                            alertInformation.show();
                        }
                    } else if (result.get().equals(ButtonType.CANCEL)) {
                        alertConfirm.close();
                        tblCursos.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro");
                    alert.show();
                }
                break;
        }
    }

    @FXML
    private void mostrarAlertInfo(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("AVISO!!!");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta funcionalidad solo está disponible en la versión PRO");
        Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "IconoAlert.png"));
        alerta.show();
    }

    private Cursos buscarCurso(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cursos curso = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("{CALL sp_cursos_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                curso = new Cursos(
                        rs.getInt("id"),
                        rs.getString("nombre_curso"),
                        rs.getInt("ciclo"),
                        rs.getInt("cupo_maximo"),
                        rs.getInt("cupo_minimo"),
                        rs.getString("carrera_tecnica_id"),
                        rs.getInt("horario_id"),
                        rs.getInt("instructor_id"),
                        rs.getString("salon_id")
                );

                System.out.println(curso.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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

        return curso;
    }

    private CarrerasTecnicas buscarCarrerasTecnicas(String id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CarrerasTecnicas carrera = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("{CALL sp_carreras_tecnicas_read_by_id(?)}");

            pstmt.setString(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                carrera = new CarrerasTecnicas(
                        rs.getString("codigo_tecnico"),
                        rs.getString("carrera"),
                        rs.getString("grado"),
                        rs.getString("seccion"),
                        rs.getString("jornada")
                );

                System.out.println(carrera.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Carrera");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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

        return carrera;
    }

    private Horarios buscarHorarios(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Horarios horario = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("{CALL sp_horarios_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {

                horario = new Horarios(
                        rs.getInt("id"),
                        rs.getTime("horario_inicio"),
                        rs.getTime("horario_final"),
                        rs.getBoolean("lunes"),
                        rs.getBoolean("martes"),
                        rs.getBoolean("miercoles"),
                        rs.getBoolean("jueves"),
                        rs.getBoolean("viernes")
                );

                System.out.println(horario.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Horarios");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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

        return horario;
    }

    private Instructores buscarInstructor(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Instructores instructor = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("{CALL sp_instructores_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {

                instructor = new Instructores(
                        rs.getInt("id"),
                        rs.getString("nombre1"),
                        rs.getString("nombre2"),
                        rs.getString("nombre3"),
                        rs.getString("apellido1"),
                        rs.getString("apellido2"),
                        rs.getString("direccion"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fecha_nacimiento").toLocalDate()
                );

                System.out.println(instructor.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Instructores");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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

        return instructor;
    }

    private Salones buscarSalon(String id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Salones salon = null;

        try {
            pstmt = Conection.getInstance().getConection()
                    .prepareCall("{CALL sp_salones_read_by_id(?)}");

            pstmt.setString(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {

                salon = new Salones(
                        rs.getString("codigo_salon"),
                        rs.getString("descripcion"),
                        rs.getInt("capacidad_maxima"),
                        rs.getString("edificio"),
                        rs.getInt("nivel")
                );

                System.out.println(salon.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Salones");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
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

        return salon;
    }

    @FXML
    private void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            Cursos cs = new Cursos();
            cs = tblCursos.getSelectionModel().getSelectedItem();
            System.out.println(cs.toString());

            System.out.println(String.valueOf(((Cursos) tblCursos
                    .getSelectionModel().getSelectedItem()).getId()));

            txtId.setText(String.valueOf(((Cursos) tblCursos
                    .getSelectionModel().getSelectedItem()).getId()));
            txtNombreCurso.setText(((Cursos) tblCursos
                    .getSelectionModel().getSelectedItem()).getNombreCurso());

            cmbCarreraTecnica.getSelectionModel().select(
                    buscarCarrerasTecnicas(
                            ((Cursos) tblCursos.getSelectionModel().getSelectedItem())
                                    .getCarreraTecnicaId()
                    )
            );

            cmbHorario.getSelectionModel().select(
                    buscarHorarios(
                            ((Cursos) tblCursos.getSelectionModel().getSelectedItem())
                                    .getHorarioId()
                    )
            );

            cmbInstructor.getSelectionModel().select(
                    buscarInstructor(
                            ((Cursos) tblCursos.getSelectionModel().getSelectedItem())
                                    .getInstructorId()
                    )
            );

            cmbSalon.getSelectionModel().select(
                    buscarSalon(
                            ((Cursos) tblCursos.getSelectionModel().getSelectedItem())
                                    .getSalonId()
                    )
            );

        }

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

//    private void mostrarAlert(String alertType, String contentText) {
//        alertType = alertType.toLowerCase();
//        switch (alertType) {
//            case "warning":
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle(TITULO_ALERT);
//                alert.setHeaderText(null);
//                alert.setContentText(contentText);
//                alert.show();
//                break;
//        }
//    }
//    private boolean comprobacionCampostxt() {
//        boolean nombreCurso = false;
//        boolean carreraTecnicaId = false;
//        boolean horarioId = false;
//        boolean instructorId = false;
//        boolean salonId = false;
//
//        if ( txtNombreCurso.getText().isEmpty()
//                && cmbCarreraTecnica == null
//                && cmbHorario == null
//                && cmbInstructor == null
//                && cmbSalon == null) {
//            System.out.println("vacio");
//            mostrarAlert(TIPO_ALERT_WARNING, "Los campos se encuentran vacíos");
//        } else {
//            if (txtNombreCurso.getText().isEmpty()) {
//
//                mostrarAlert(TIPO_ALERT_WARNING, "Verifique que el campo Nombre curso contenga datos.");
//            } else {
//                if (txtNombreCurso.getText().charAt(0) == ' ') {
//                    mostrarAlert(TIPO_ALERT_WARNING, "el campo nombre curso tiene espacios al inicio.");
//                } else {
//                    nombreCurso = true;
//                }
//            }
//
//            if (cmbCarreraTecnica == null) {
//                mostrarAlert(TIPO_ALERT_WARNING, "el campo carreras tecnicas esta vacio.");
//            } else {
//                carreraTecnicaId = true;
//            }
//            if (cmbHorario == null) {
//                mostrarAlert(TIPO_ALERT_WARNING, "el campo Horarios tecnicas esta vacio.");
//            } else {
//                horarioId = true;
//            }
//            if (cmbInstructor == null) {
//                mostrarAlert(TIPO_ALERT_WARNING, "el campo instructor esta vacio.");
//            } else {
//                instructorId = true;
//            }
//            if (cmbSalon == null) {
//                mostrarAlert(TIPO_ALERT_WARNING, "el campo salon esta vacio.");
//            } else {
//                carreraTecnicaId = true;
//            }
//
//        }
//        return false;
//    }

private boolean comprobacionCamposTxt() {
        if (txtNombreCurso.getText().isEmpty()) {
            mostrarAlert(TIPO_ALERT_WARNING, "Verifique que los campos contengan datos.");
        } else {
            if (txtNombreCurso.getText().charAt(0) == ' ') 
            {
                mostrarAlert(TIPO_ALERT_WARNING, "Verifique que los campos no contenga un espacio al inicio.");
            } else {
                return true;
            }
        }
        return false;
    }

    private void mostrarAlert(String alertType, String alertContent) {
        switch (alertType) {
            case TIPO_ALERT_WARNING:
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle(TITULO_ALERT);
                alert.setContentText(alertContent);
                alert.show();
                break;
            case TIPO_ALERT_INFORMATION:
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText(null);
                alert2.setTitle(TITULO_ALERT);
                alert2.setContentText(alertContent);
                Stage stageAlert2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                stageAlert2.getIcons().add(new Image(PAQUETE_IMAGES + "informacion.png"));
                alert2.show();
                break;
        }
    }
    

}
