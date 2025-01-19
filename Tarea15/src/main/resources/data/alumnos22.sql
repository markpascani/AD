DROP DATABASE Alumnos22;
CREATE DATABASE Alumnos22;

use Alumnos22;

CREATE TABLE Grupo(
	Id INT NOT NULL,
	Nombre VARCHAR(255) NOT NULL,
	PRIMARY KEY (Id)
);

CREATE TABLE Alumno(
	NIA INT NOT NULL,
	Nombre VARCHAR(255) NOT NULL,
	Apellidos VARCHAR(255) NOT NULL,
	Genero CHAR(1) NOT NULL,
	FechaDeNacimiento DATE NOT NULL,
	Ciclo VARCHAR(255) NOT NULL,
	Curso VARCHAR(255) NOT NULL,
	Grupo INT  NOT NULL,
	PRIMARY KEY (NIA),
	FOREIGN KEY (Grupo) REFERENCES Grupo (id) ON DELETE NO ACTION ON UPDATE CASCADE
);

INSERT INTO Grupo(Id, Nombre) values (1, "DAM");
INSERT INTO Grupo(Id, Nombre) values (2, "ASIR");

