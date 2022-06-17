

/*
Integrantes:
Juan José Pineda Soto: 2021044
Manuel Barcarcel: 2021016

Código técnico: IN5BM 
Fecha de creación: 26/04/2022
Fecha de modificación: 

Grupo: Lunes(2)
*/
DROP DATABASE if exists control_academico_grupo6_in5bm;
CREATE DATABASE control_academico_grupo6_in5bm;
USE control_academico_grupo6_in5bm;
-- 

DROP TABLE IF EXISTS alumnos;

CREATE TABLE IF NOT EXISTS alumnos(
	carne 		VARCHAR(7) 	NOT NULL,
    nombre1 	VARCHAR(15)	NOT NULL,
    nombre2 	VARCHAR(15) NULL, 
    nombre3 	VARCHAR(15) NULL,
    apellido1 	VARCHAR(15) NOT NULL,
    apellido2 	VARCHAR(15) NULL,
    CONSTRAINT pk_alumnos PRIMARY KEY(carne)
);

DROP TABLE IF EXISTS instructores;

CREATE TABLE IF NOT EXISTS instructores(
	id 			INT AUTO_INCREMENT 	NOT NULL,
    nombre1 	VARCHAR(15)			NOT NULL,
    nombre2 	VARCHAR(15) 		NULL, 
    nombre3 	VARCHAR(15) 		NULL,
    apellido1 	VARCHAR(15) 		NOT NULL,
    apellido2 	VARCHAR(15) 		NULL,
    direccion 	VARCHAR(45)			NULL,
    email		VARCHAR(45)			NOT NULL,
    telefono	VARCHAR(45)			NOT NULL,
    fecha_nacimiento DATE			NULL,
    CONSTRAINT pk_instructores PRIMARY KEY(id)
);

DROP TABLE IF EXISTS salones;

CREATE TABLE IF NOT EXISTS salones(
	codigo_salon 	 VARCHAR(5)  NOT NULL,
    descripcion	 	 VARCHAR(45) NULL,
    capacidad_maxima INT	 	 NOT NULL,
    edificio 		 VARCHAR(15) NULL,
    nivel 			 INT 		 NULL,
    CONSTRAINT pk_salones PRIMARY KEY(codigo_salon)
);

DROP TABLE IF EXISTS carreras_tecnicas;

CREATE TABLE IF NOT EXISTS carreras_tecnicas(
	codigo_tecnico	VARCHAR(10) NOT NULL,
    carrera 	   	VARCHAR(45) NOT NULL, 
    grado 	       	VARCHAR(10) NOT NULL,
    seccion 		CHAR(1) 	NOT NULL,
    jornada 		VARCHAR(10) NOT NULL,
    CONSTRAINT pk_carreras_tecnicas PRIMARY KEY(codigo_tecnico)
);

DROP TABLE IF EXISTS horarios;

CREATE TABLE IF NOT EXISTS horarios(
	id 				INT AUTO_INCREMENT 	NOT NULL,
    horario_inicio 	TIME 				NOT NULL,
    horario_final 	TIME 				NOT NULL,
    lunes 			TINYINT(1) 			NULL,
    martes 			TINYINT(1) 			NULL, 
    miercoles 		TINYINT(1) 			NULL,
    jueves 			TINYINT(1) 			NULL,
    viernes 		TINYINT(1) 			NULL,
    CONSTRAINT pk_horarios PRIMARY KEY(id)
);


DROP TABLE IF EXISTS cursos;

CREATE TABLE IF NOT EXISTS cursos(
	id 				INT AUTO_INCREMENT NOT NULL,
    nombre_curso 	VARCHAR(255) NOT NULL,
    ciclo 			year NULL,
    cupo_maximo 	INT NULL,
    cupo_minimo 	INT NULL,
    carrera_tecnica_id VARCHAR(255) NOT NULL,
    horario_id 		INT NOT NULL,
    instructor_id 	INT NOT NULL,
    salon_id 		VARCHAR(5) NOT NULL,
    CONSTRAINT pk_cursos PRIMARY KEY(id),
    CONSTRAINT fk_cursos_carrera_tecnica
		FOREIGN KEY(carrera_tecnica_id)
        REFERENCES carreras_tecnicas(codigo_tecnico)
	ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_cursos_horario
		FOREIGN KEY(horario_id)
        REFERENCES horarios(id)
	ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_cursos_instructor
		FOREIGN KEY(instructor_id)
        REFERENCES instructores(id)
	ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_cursos_salon
		FOREIGN KEY(salon_id)
        REFERENCES salones(codigo_salon)
        ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS asignaciones_alumnos;

CREATE TABLE IF NOT EXISTS asignaciones_alumnos(
	id 				int NOT NULL auto_increment,
    alumno_id 		VARCHAR(7) 	NOT NULL,
    curso_id 		INT 		NOT NULL,
    fecha_asignacion DATETIME 	NULL,
    CONSTRAINT pk_asignacioes_alumnos PRIMARY KEY(id),
    CONSTRAINT fk_asignaciones_alumnos_alumno
		FOREIGN KEY(alumno_id)
        REFERENCES alumnos(carne)
	ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_asignaciones_alumnos_curso
		FOREIGN KEY(curso_id)
        REFERENCES cursos(id)
	ON DELETE CASCADE ON UPDATE CASCADE
);

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_alumnos_create $$
	CREATE PROCEDURE sp_alumnos_create(
		IN _carne VARCHAR (7) ,
		IN _nombre1 VARCHAR (15),
		IN _nombre2 VARCHAR (15),
		IN _nombre3 VARCHAR (15),
		IN _apellido1 VARCHAR (15),
		IN _apellido2 VARCHAR (15)
	)
	BEGIN
	INSERT INTO alumnos (
		carne,
		nombre1,
		nombre2,
		nombre3,
		apellido1,
		apellido2
	)
	VALUES (
		_carne,
		_nombre1,
		_nombre2,
		_nombre3,
		_apellido1,
		_apellido2
	);
	END $$
	DELIMITER ;
    
    CALL sp_alumnos_create("2021044", "Juan", "Jose", NULL, "Pineda", "Soto");
    CALL sp_alumnos_create("2021000", "José", "Juan", "Maria", "Soto", NULL);
    CALL sp_alumnos_create("2021001", "Luis", "Miguel", NULL, "Canto", "Perez");
    CALL sp_alumnos_create ("2021022", "Cesar", "Alcar", NULL, "Monte", "Nuevo");
    CALL sp_alumnos_create ("2021012", "Pablo", "Miguel", NULL, "Alto", "Mendezes");
    CALL sp_alumnos_create("2021010", "Moises", NULL, NULL, "Monte", NULL);
    CALL sp_alumnos_create("2021100", "Alejandor", "Rodriguez", NULL, "Lucides", NULL);
    CALL sp_alumnos_create("2021011", "Tulio", NULL, NULL, "Jimenez", "Monte");
    CALL sp_alumnos_create("2021111", "José", "Pablo", NULL, "Pineda", "Potillo");
    CALL sp_alumnos_create("2021530", "Erick", NULL, NULL, "Rodriguezs", NULL);
    
	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_alumnos_read $$
	CREATE PROCEDURE sp_alumnos_read()
	BEGIN
		SELECT
			a.carne,
			a.nombre1,
			a.nombre2,
			a.nombre3,
			a.apellido1,
			a.apellido2
		FROM
			alumnos AS a;
	END $$
	DELIMITER ;
    
    DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_alumnos_read_by_id $$
	CREATE PROCEDURE sp_alumnos_read_by_id(
    in _carne varchar(7)
    )
	BEGIN
		SELECT
			a.carne,
			a.nombre1,
			a.nombre2,
			a.nombre3,
			a.apellido1,
			a.apellido2
		FROM
			alumnos AS a
            where
            carne = _carne;
	END $$
	DELIMITER ;
    
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_update $$
CREATE PROCEDURE sp_alumnos_update(
	IN _carne VARCHAR (7) ,
	IN _nombre1 VARCHAR (15),
	IN _nombre2 VARCHAR (15),
	IN _nombre3 VARCHAR (15),
	IN _apellido1 VARCHAR (15),
	IN _apellido2 VARCHAR (15)
)
	BEGIN
	UPDATE
		alumnos
		Set
		nombre1 = _nombre1,
		nombre2 = _nombre2,
		nombre3 = _nombre3,
		apellido1 = _apellido1,
		apellido2 = _apellido2
	WHERE
	carne = _carne;
	END $$
	DELIMITER ;
    
      DELIMITER $$
    DROP PROCEDURE IF EXISTS sp_alumnos_delete $$
    CREATE PROCEDURE sp_alumnos_delete(
		in _carne varchar(15)
    )
    BEGIN 
		delete from 
			alumnos
		where 
			carne = _carne;
    END $$
    DELIMITER ;
    
    DELIMITER $$
    DROP PROCEDURE IF EXISTS sp_instructores_create $$
    CREATE PROCEDURE sp_instructores_create(
		IN _nombre1 	VARCHAR(15),
        IN _nombre2 	VARCHAR(15),
        IN _nombre3 	VARCHAR(15),
        IN _apellido1 	VARCHAR(15),
        IN _apellido2 	VARCHAR(15),
        IN _direccion	VARCHAR(15),
        IN _email		VARCHAR(15),
        IN _telefono	VARCHAR(8),
        IN _fecha_nacimiento DATE 
    
    )
    BEGIN
		INSERT INTO instructores (
			nombre1,
			nombre2,
			nombre3,
			apellido1,
			apellido2,
			direccion,
			email,
			telefono,
			fecha_nacimiento 
        )
        VALUES (
			_nombre1,
			_nombre2,
			_nombre3,
			_apellido1,
			_apellido2,
			_direccion,
			_email,
			_telefono,
			_fecha_nacimiento
        );
	END $$
    DELIMITER ;
    
    CALL  sp_instructores_create("Juan", "José", NULL, "Pineda", NULL, "Zona 8", "soto@gmail.com", "12345678", "1980-04-01") ;
    CALL  sp_instructores_create("José", NULL, NULL, "Alvarado", NULL, "Zona 9", "jose@gmail.com", "43218765" , "1985-01-06");
    CALL  sp_instructores_create("Pablo", "Daniel", "Miguel", "Alvarado", "Rodriguez", "Zona 4", "pablo@gmail.com", "67547859", "1990-05-30");
    CALL  sp_instructores_create("Egdar", "Rolando", NULL, "Pineda", NULL, "zona 10", "edgar@gmail.com", "14537894", "1970-06-04");
    CALL  sp_instructores_create("Migule", NULL, NULL, "Astoria", NULL, "zona 6", "l@gmail.com", "14267358", "1986-07-31");
    CALL  sp_instructores_create("Pedro", NULL, NULL, "Avila", NULL, "Zona 10", "s@gmail.com", "14258936", "1979-09-08");
    CALL  sp_instructores_create("Josué", NULL, NULL, "Villeda", NULL, "Zona 11", "jouse@gmail.com", "35267838", "1990-03-06");
    CALL  sp_instructores_create("Duglas", NULL, NULL, "Mendez", "Altair", "Zona 4", "s@gmail.com", "14573689", "1995-09-01");
    CALL  sp_instructores_create("José", "Juan", NULL,  "Soto", "Pineda", "Zona 7", "juan@gmail.com", "12346780", "1999-01-01");
    CALL  sp_instructores_create("Juan", "Gabril", NULL , "Monte", "Bello", "Zona 14", "j@gmail.com", "14781356", "2000-06-09");
    
    DELIMITER $$
    DROP PROCEDURE IF EXISTS sp_instructores_read $$
    CREATE PROCEDURE sp_instructores_read()
    BEGIN
		SELECT
			i.id,
			i.nombre1,
			i.nombre2,
			i.nombre3,
			i.apellido1,
			i.apellido2,
			i.direccion,
			i.email,
			i.telefono,
			i.fecha_nacimiento
		FROM 
			instructores AS i;
    END $$
    DELIMITER ;
    call sp_instructores_read();
    DELIMITER $$
    DROP PROCEDURE IF EXISTS sp_instructores_read_by_id $$
    CREATE PROCEDURE sp_instructores_read_by_id(
    in _id int
    )
    BEGIN
		SELECT
			i.id,
			i.nombre1,
			i.nombre2,
			i.nombre3,
			i.apellido1,
			i.apellido2,
			i.direccion,
			i.email,
			i.telefono,
			i.fecha_nacimiento
		FROM 
			instructores AS i
            where 
            id= _id;
    END $$
    DELIMITER ;
    
    DELIMITER $$
    DROP PROCEDURE IF EXISTS sp_instructores_update $$
    CREATE PROCEDURE sp_instructores_update(
				in _id int ,
			in _nombre1 varchar(45),
			in _nombre2 varchar(45),
			in _nombre3 varchar(45),
			in _apellido1 varchar(45),
			in _apellido2 varchar(45),
			in _direccion varchar(45),
			in _email varchar(45),
			in _telefono varchar(45),
			in _fecha_nacimiento date
    )
    BEGIN
		UPDATE instructores SET 
        nombre1 = _nombre1,
        nombre2 = _nombre2,
		nombre3 = _nombre3,
		apellido1 = _apellido1,
		apellido2 = _apellido2,
		direccion = _direccion,
		email = _email,
		telefono = _telefono,
		fecha_nacimiento = fecha_nacimiento
        
        WHERE id = _id; 
    END $$
    DELIMITER ;
    
    DELIMITER $$
    DROP PROCEDURE IF EXISTS sp_instructores_delete $$
    CREATE PROCEDURE sp_instructores_delete(
    in _id int
    )
    BEGIN 
		DELETE FROM instructores WHERE id = _id; 
    END $$
    DELIMITER ;

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_salones_create $$
	CREATE PROCEDURE sp_salones_create(
		IN _codigo_salon 		VARCHAR(5),
		IN _descripcion  		VARCHAR(45),
		IN _capacidad_maxima 	INT,
		IN _edificio 			VARCHAR(15),
		IN _nivel				INT
	)
	BEGIN
		INSERT INTO salones (
			codigo_salon,
			descripcion,
			capacidad_maxima,
			edificio,
			nivel
			)
		VALUES (
			_codigo_salon,
			_descripcion,
			_capacidad_maxima,
			_edificio,
			_nivel
		);
	END $$
	DELIMITER ;
    
    CALL sp_salones_create("IN5BM", "Informatica", "20", "Torre C", "2");
    CALL sp_salones_create("DI5NB", "Dibujo", "20", "Torre C", "2");
    CALL sp_salones_create("MEINB", "Mecanica", "20", "Torre C", "2");
    CALL sp_salones_create("ELINB", "Electricidad", "20", "Torre C", "2");
    CALL sp_salones_create("COINB", "Contabilidad", "20", "Torre C", "2");
    CALL sp_salones_create("B1A10", "Basico 1", "20", "Torre A", "1");
    CALL sp_salones_create("B2A20", "Basico 2", "20", "Torre A", "1");
    CALL sp_salones_create("B3A30", "Basico 3", "20", "Torre A", "1");
    CALL sp_salones_create("PRI1B", "Primaria 1", "20", "Torre B", "3");
    CALL sp_salones_create("PRI2B", "Primaria 2", "20", "Torre B", "3");
    CALL sp_salones_create("ALGO", "Primaria 2", "20", "Torre B", "3");
select * from salones;
	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_salones_read $$
	CREATE PROCEDURE sp_salones_read()
	BEGIN 
		SELECT 
			s.codigo_salon,
			s.descripcion,
			s.capacidad_maxima,
			s.edificio,
			s.nivel
		FROM 
		salones AS s;
	END $$
	DELIMITER ;
    
    DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_salones_read_by_id $$
	CREATE PROCEDURE sp_salones_read_by_id(
	in _codigo_salon varchar(5)
    )
	BEGIN 
		SELECT 
			s.codigo_salon,
			s.descripcion,
			s.capacidad_maxima,
			s.edificio,
			s.nivel
		FROM 
		salones AS s
        where
        codigo_salon = _codigo_salon
        ;
	END $$
	DELIMITER ;

    
	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_salones_update $$
	CREATE PROCEDURE sp_salones_update(
    in _codigo_salon varchar(5),
    in _capacidad_maxima int,
    in _edificio varchar(15),
    in _nivel int ,
    in _descripcion varchar(25)
    )
	BEGIN 
		update 
        salones
        set
			codigo_salon = _codigo_salon,
            capacidad_maxima = _capacidad_maxima,
            edificio = _edificio,
            nivel = _nivel,
            descripcion = _descripcion
        where
        codigo_salon = _codigo_salon;
	END $$
	DELIMITER ; 
select * from salones;
	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_salones_delete $$
	CREATE PROCEDURE sp_salones_delete(
    in _codigo_salon varchar(5)
    )
	BEGIN 
	delete from 
			salones
		where 
			codigo_salon = _codigo_salon;
	END $$
	DELIMITER ;

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_create $$
	CREATE PROCEDURE sp_carreras_tecnicas_create(
		IN _codigo_tecnico VARCHAR(6),
		IN _carrera 		  VARCHAR(45),
		IN _grado		  VARCHAR(10),
		IN _seccion		  CHAR(1),
		IN _jornada		  VARCHAR(10)
	)
	BEGIN 
		INSERT INTO carreras_tecnicas(
			codigo_tecnico,
			carrera,
			grado, 
			seccion,
			jornada
		)
		VALUES (
			_codigo_tecnico,
			_carrera,
			_grado, 
			_seccion,
			_jornada
		);
	END $$
	DELIMITER ; 
    
    SELECT * FROM carreras_tecnicas;
    
    CALL sp_carreras_tecnicas_create("1", "Informatica", "5to", "A", "Matutina");
    CALL sp_carreras_tecnicas_create("2", "Dibujo", "5to", "B", "Matutina");
    CALL sp_carreras_tecnicas_create("3", "Mecanica", "5to", "C", "Matutina");
    CALL sp_carreras_tecnicas_create("4", "Electricidad", "5to", "D", "Matutina");
    CALL sp_carreras_tecnicas_create("5", "Contabilidad", "5to", "F", "Matutina");
    CALL sp_carreras_tecnicas_create("6", "Informatica", "4to", "G", "Vespertina");
    CALL sp_carreras_tecnicas_create("7", "Dibujo", "4to", "H", "Vespertina");
    CALL sp_carreras_tecnicas_create("8", "Mecanica", "4to", "I", "Vespertina");
    CALL sp_carreras_tecnicas_create("9", "Electricidad", "4to", "J", "Vespertina");
    CALL sp_carreras_tecnicas_create("10", "Contabilidad", "4to", "K", "Vespertina");

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read $$
	CREATE PROCEDURE sp_carreras_tecnicas_read()
	BEGIN 
		SELECT
			c.codigo_tecnico,
			c.carrera,
			c.grado,
			c.seccion,
			c.jornada
		FROM 
		carreras_tecnicas AS c;
	END $$
	DELIMITER ;
    
    DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read_by_id $$
	CREATE PROCEDURE sp_carreras_tecnicas_read_by_id(
    in _codigo_tecnico varchar(45) 
    )
	BEGIN 
		SELECT
			c.codigo_tecnico,
			c.carrera,
			c.grado,
			c.seccion,
			c.jornada
		FROM 
		carreras_tecnicas AS c
        where
        codigo_tecnico = _codigo_tecnico;
	END $$
	DELIMITER ;

    
	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_carreras_update $$
	CREATE PROCEDURE sp_carreras_update(
		IN _codigo_tecnico VARCHAR(6),
		IN _carrera 		  VARCHAR(45),
		IN _grado		  VARCHAR(10),
		IN _seccion		  CHAR(1),
		IN _jornada		  VARCHAR(10)
    )
	BEGIN
    update 
    carreras_tecnicas 
    set
    codigo_tecnico = _codigo_tecnico,
    carrera = _carrera,
    grado = _grado,
    seccion = _seccion,
    jornada = _jornada
    where
    codigo_tecnico = _codigo_tecnico;
	END $$
	DELIMITER ; 

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_delete $$
	CREATE PROCEDURE sp_carreras_tecnicas_delete(
    in _codigo_tecnico varchar(6) 
    )
	BEGIN 
		DELETE FROM carreras_tecnicas WHERE codigo_tecnico = _codigo_tecnico;
	END $$
	DELIMITER ;

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_horarios_create $$
	CREATE PROCEDURE sp_horarios_create(
		IN _horario_inicio TIME,
		IN _horario_final TIME,
		IN _lunes TINYINT,
		IN _martes TINYINT,
		IN _miercoles TINYINT,
		IN _jueves TINYINT,
		IN _viernes TINYINT
	)
	BEGIN 
		INSERT INTO horarios (
			horario_inicio,
			horario_final,
			lunes,
			martes,
			miercoles,
			jueves, 
			viernes
		)
		VALUES (
			_horario_inicio,
			_horario_final,
			_lunes,
			_martes,
			_miercoles,
			_jueves, 
			_viernes
		);
	END $$
	DELIMITER ; 
    
    CALL sp_horarios_create("07:05:00", "12:00:00", 1, 1, 1, 1, 1);
    CALL sp_horarios_create("07:05:00", "12:00:00", 1, 1, 1, 1, 1);
    CALL sp_horarios_create("07:05:00", "12:00:00", 1, 1, 1, 1, 1);
    CALL sp_horarios_create("01:05:00", "05:00:00", 2, 2, 2, 2, 2);
    CALL sp_horarios_create("07:05:00", "12:00:00", 2, 2, 2, 2, 2);
    CALL sp_horarios_create("01:05:00", "05:00:00", 2, 2, 2, 2, 2);
    CALL sp_horarios_create("01:05:00", "05:00:00", 2, 2, 2, 2, 2);
    CALL sp_horarios_create("01:05:00", "05:00:00", 2, 2, 2, 2, 2);
    CALL sp_horarios_create("01:05:00", "05:00:00", 2, 2, 2, 2, 2);
    CALL sp_horarios_create("01:05:00", "05:00:00", 2, 2, 2, 2, 2);

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_horarios_read $$
	CREATE PROCEDURE sp_horarios_read()
	BEGIN
		SELECT
			h.id,
			h.horario_inicio,
			h.horario_final,
			h.lunes,
			h.martes,
			h.miercoles,
			h.jueves, 
			h.viernes
		FROM 
		horarios AS h;
	END $$
	DELIMITER ;
    
    DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_horarios_read_by_id $$
	CREATE PROCEDURE sp_horarios_read_by_id(
    in _id int
    )
	BEGIN
		SELECT
			h.id,
			h.horario_inicio,
			h.horario_final,
			h.lunes,
			h.martes,
			h.miercoles,
			h.jueves, 
			h.viernes
		FROM 
		horarios AS h
        where 
         id = _id;
	END $$s
	DELIMITER ;

    
	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_horarios_update $$
	CREATE PROCEDURE sp_horarios_update(
		in _id int,
		IN _horario_inicio TIME,
		IN _horario_final TIME,
		IN _lunes TINYINT,
		IN _martes TINYINT,
		IN _miercoles TINYINT,
		IN _jueves TINYINT,
		IN _viernes TINYINT
    )
	BEGIN
	update 
    horarios
    set
			horario_inicio = _horario_inicio ,
			horario_final = _horario_final ,
			lunes = _lunes ,
			martes = _martes,
			miercoles = _miercoles,
			jueves = _jueves, 
			viernes = _viernes
	where
		id = _id ;
	END $$
	DELIMITER ;

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_horarios_delete $$
	CREATE PROCEDURE sp_horarios_delete(
    in _id int
    )
	BEGIN 
		DELETE FROM horarios WHERE id = _id;
	END $$
	DELIMITER ; 

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_cursos_create $$
	CREATE PROCEDURE sp_cursos_create(
		IN _nombre_curso 		VARCHAR(255),
		IN _ciclo 				YEAR,
		IN _cupo_maximo 		INT,
		IN _cupo_minimo 		INT,
		IN _carrera_tecnica_id  VARCHAR(128),
		IN _horario_id 			INT,
		IN _instructor_id 		INT, 
		IN _salon_id 			VARCHAR(5) 
	)
	BEGIN 
		INSERT INTO cursos (
			nombre_curso,
			ciclo,
			cupo_maximo,
			cupo_minimo,
			carrera_tecnica_id,
			horario_id,
			instructor_id,
			salon_id
		)
		VALUES (
			_nombre_curso,
			_ciclo,
			_cupo_maximo,
			_cupo_minimo,
			_carrera_tecnica_id,
			_horario_id,
			_instructor_id,
			_salon_id
		);
	END $$
	DELIMITER ; 
    
    CALL sp_cursos_create("Sociales", "2021", 20, 15, "1", 1, 1, "B1A10");
    CALL sp_cursos_create("Matematica", "2021", 20, 15, "2", 2, 2, "B1A10");
    CALL sp_cursos_create("Ingles", "2021", 20, 15, "3", 3, 3, "B3A30");
    CALL sp_cursos_create("Étcia", "2021", 20, 15, "1", 4, 3, "B1A10");
    CALL sp_cursos_create("Qumica", "2021", 20, 15, "6", 1, 3, "B2A20");
    CALL sp_cursos_create("Calculo", "2021", 20, 15, "1", 9, 8, "B2A20");
    CALL sp_cursos_create("Contabilidad", "2021", 20, 15, "1", 2, 3, "COINB");
    CALL sp_cursos_create("Taller", "2021", 20, 15, "9", 8, 5, "B1A10");
    CALL sp_cursos_create("Lengua", "2021", 20, 15, "2", 1, 4, "B1A10");
    CALL sp_cursos_create("Ciencias", "2021", 20, 15, "9", 1, 10, "DI5NB");
    
    select * from cursos;
	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_cursos_read $$
	CREATE PROCEDURE sp_cursos_read()
	BEGIN
		SELECT
			cs.id,
			cs.nombre_curso,
			cs.ciclo,
			cs.cupo_maximo,
			cs.cupo_minimo,
			cs.carrera_tecnica_id,
			cs.horario_id,
			cs.instructor_id,
			cs.salon_id
		FROM 
		cursos AS cs;
	END $$
	DELIMITER ; 
    call sp_cursos_read;
    DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_cursos_read_by_id $$
	CREATE PROCEDURE sp_cursos_read_by_id(
    in _id int
    )
	BEGIN
		SELECT
			cs.id,
			cs.nombre_curso,
			cs.ciclo,
			cs.cupo_maximo,
			cs.cupo_minimo,
			cs.carrera_tecnica_id,
			cs.horario_id,
			cs.instructor_id,
			cs.salon_id
		FROM 
		cursos AS cs
        where 
        id = _id;
	END $$
	DELIMITER ; 


	SELECT * FROM cursos;

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_cursos_update $$
	CREATE PROCEDURE sp_cursos_update(
   in  _id int ,
   in _nombre_curso varchar(255),
   in _ciclo year,
   in _cupo_maximo int,
   in _cupo_minimo int,
   in _carrera_tecnica_id varchar(255),
   in _horario_id int,
   in _instructor_id int,
   in _salon_id varchar(5)
   
    )
	BEGIN
		UPDATE cursos SET
         id = _id ,
   nombre_curso = _nombre_curso ,
    ciclo = _ciclo ,
    cupo_maximo = _cupo_maximo,
    cupo_minimo = _cupo_minimo ,
    carrera_tecnica_id = _carrera_tecnica_id,
    horario_id = _horario_id,
    instructor_id = _instructor_id ,
    salon_id = _salon_id
    where
    id = _id;
	END $$
	DELIMITER ; 

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_cursos_delete $$
	CREATE PROCEDURE sp_cursos_delete(
    in _id int
    )
	BEGIN 
		DELETE FROM cursos 
        WHERE id = _id;
	END $$
	DELIMITER ; 

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_create $$
	CREATE PROCEDURE sp_asignaciones_alumnos_create(
		IN _alumno_id VARCHAR(16),
		IN _curso_id INT,
		IN _fecha_asignacion DATETIME
	)
	BEGIN 
		INSERT INTO asignaciones_alumnos(
			alumno_id,
			curso_id,
			fecha_asignacion
		)
		VALUES (
			_alumno_id,
			_curso_id,
			_fecha_asignacion
		);
	END $$
	DELIMITER ;
    
    CALL sp_asignaciones_alumnos_create( "2021000", 1, "2021-04-01");
    CALL sp_asignaciones_alumnos_create( "2021001", 2, "2021-04-01");
    CALL sp_asignaciones_alumnos_create( "2021010", 3, "2021-04-01");
    CALL sp_asignaciones_alumnos_create( "2021011", 4, "2021-04-01");
    CALL sp_asignaciones_alumnos_create( "2021012", 2, "2021-04-01");
    CALL sp_asignaciones_alumnos_create( "2021022", 2, "2021-01-31");
    CALL sp_asignaciones_alumnos_create("2021044", 3, "2021-01-31");
    CALL sp_asignaciones_alumnos_create("2021100", 5, "2021-01-31");
    CALL sp_asignaciones_alumnos_create( "2021530", 2, "2021-01-31");
    CALL sp_asignaciones_alumnos_create( "2021111", 2, "2021-01-31");
    
	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read $$
	CREATE PROCEDURE sp_asignaciones_alumnos_read()
	BEGIN
		SELECT 
			al.id,
			al.alumno_id,
			al.curso_id,
			al.fecha_asignacion
		FROM 
		asignaciones_alumnos AS al;
	END $$
	DELIMITER ; 
    call sp_asignaciones_alumnos_read;
    DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_asiganciones_alumnos_read_by_id $$
	CREATE PROCEDURE sp_asignaciones_alumnos_read_by_id ()
	BEGIN
		SELECT 
			al.id,
			al.alumno_id,
			al.curso_id,
			al.fecha_asignacion
		FROM 
		asigaciones_alumnos AS al
        ORDER BY al.id;
	END $$
	DELIMITER ; 

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_update $$
	CREATE PROCEDURE sp_asignaciones_alumnos_update(
    		in _id int,
            IN _alumno_id VARCHAR(16),
		IN _curso_id INT,
		IN _fecha_asignacion DATETIME
        )
	BEGIN 
		UPDATE asignaciones_alumnos SET 
       id = _id,
       alumno_id = _alumno_id,
       curso_id = _curso_id,
       fecha_asignacion = _fecha_asignacion 
        WHERE id = _id;

	END $$
	DELIMITER ; 

	DELIMITER $$
	DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_delete $$
	CREATE PROCEDURE sp_asignaciones_alumnos_delete(
    in _id int 
    )
	BEGIN
		DELETE FROM asignaciones_alumnos WHERE id = _id;

	END $$
	DELIMITER ;