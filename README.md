jboss-javaee-jpa-empleados
==========================

Un ejemplo simple que corre JPA con Hibernate con las versiones de la JBoss EAP 6.2 con Unit Test con Arquillian.

Ejecutar los Test:
==================

Para ejecutar los tests es necesario tener configurada la variable de entorno $JBOSS_HOME que apunte al home del jboss.

Luego, para correr los tests con el JBoss embebido, se debe ejecutar el comando:

> mvn test -Parq-jbossas-managed

Asegurarse de no tener ningún JBoss levantado porque Arquillian se encarga de levantar y bajar el server.

Paquetes y Clases de Interés:
=============================

El ejemplo muestra un mapeo más o menos completo hecho con JPA 2.0. En el paquete model se pueden encontrar las entidades.

En el paquete service se puede encontrar un EJB Stateless que provee servicios CRUD para la entidad Empleados.

La Parte más Interesante está en los Tests:
===========================================

<b>1) ProyectoTest:</b> inyecta el EntityManager y el UserTransaction de JBoss para probar la carga de proyectos, teniendo que manejar las transacciones de negocio a mano, ya que corre fuera de un EJB.

<b>2) EmpleadoServiceTest:</b> prueba los servicios del EJB EmpleadoService. No necesita manejar Tx.

<b>3) QueriesTest:</b> prueba 4 distintos tipos de Queries: dynamicQuery, namedQuery, nativeQuery y criteriaQuery.

