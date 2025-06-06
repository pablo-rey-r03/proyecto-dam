# 🌟 MetaConti – DAM – Pablo Rey Ramos

## Índice
1. [Introducción](#introducción)
2. [Funcionalidades del proyecto y tecnologías utilizadas](#funcionalidades-del-proyecto-y-tecnologías-utilizadas)
3. [Guía de instalación](#guía-de-instalación)
4. [Guía de uso](#guía-de-uso)
5. [Documentación](#documentación)
6. [Diseño de la interfaz en Figma](#diseño-de-la-interfaz-en-figma)
7. [Conclusión](#conclusión)
8. [Contribuciones, agradecimientos y referencias](#contribuciones-agradecimientos-y-referencias)
9. [Licencias](#licencias)
10. [Contacto](#contacto)

---

## Introducción

_MetaConti_ es una aplicación web destinada a empresas contratistas y a sus subcontratas correspondientes. Ofrece un portal
de gestión empresarial y documental, para iniciar contratos, validar documentos, modificar información, etc.

Hoy día, la subcontratación es un fenómeno cada vez más común, especialmente en entornos de grandes empresas donde se mueven
grandes cantidades de dinero. Asociada a ello, existe una gran maraña burocrática y numerosas complejidades legales, laborales,
judiciales y financieras. Por ello, las empresas contratistas requieren de un _software_ que les permita agilizar el trabajo
y la gestión relacionada con sus subcontratas.

En esta aplicación, se busca aportar una solución para el problema de estas empresas, de forma que puedan subir documentos
de forma segura y se pueda obtener y modificar la información de las subcontratas, todo desde una plataforma centralizada.

---

## Funcionalidades del proyecto y tecnologías utilizadas

La aplicación _MetaConti_ permitirá:
- El registro y autenticación de usuarios.
- La modificación de la información pública tanto de nuestra empresa como de nuestro perfil.
- La subida, modificación y borrado de documentos para contratistas de nuestra empresa (tanto propios como de la empresa a la que 
pertenecemos).
- El inicio, la modificación y el cese de relaciones contractuales con empresas subcontratadas.
- La validación de documentos subidos por subcontratas.

Para este proyecto, se hará uso de una base de datos [MySQL](https://www.mysql.com). Los datos serán consultados y modificados
por un _backend_ Java, que implementará el _framework_ Quarkus. Esta API será atacada por nuestra aplicación web, que usa React
con Vite.

[Quarkus](https://quarkus.io) es un _framework_ para [Java](https://www.java.com/es/) orientado al desarrollo en la nube lanzado en
2019, diseñado para integrarse a la perfección en entornos de contenedores. Hace años, las aplicaciones monolíticas se alojaban
en un servidor físico con tiempos de arranque tediosos y alto coste de memoria. Con la llegada de los contenedores, [Docker](https://www.docker.com),
[Kubernetes](https://kubernetes.io) y la nube, Quarkus se postuló como una fuerte alternativa a [Spring](https://spring.io) gracias
a su rápido despliegue en contenedores, su compilación a nativo con [GraalVM](https://www.graalvm.org) y su integración de la programación
reactiva. Ofrece una gran suite de librerías, inyectables mediante "extensiones", y sigue muchas de las especificaciones y estándares
más usados (CDI, JAX-RS, JPA, JTA, [MicroProfile](https://microprofile.io), [Vert.x](https://vertx.io), [Camel](https://camel.apache.org),
[Jakarta EE](https://jakarta.ee), [OpenTelemetry](https://opentelemetry.io), etc.)

[React](https://react.dev) es una librería para JavaScript que permite al desarrollador construir la interfaz deseada a partir de
piezas individuales reutilizables llamadas "componentes", construidas a partir de funciones básicas integradas en sus archivos
particulares, llamados JSX, que juntan marcado con código. Para añadir funcionalidades extra propias de una aplicación reactiva
de _frontend_, React se vale de los llamados "_hooks_" para establecer variables renderizables, navegación, ejecuciones en bucle,
referencias a elementos renderizados, etc.

Para crear este proyecto de React se ha utilizado [Vite](https://vite.dev), una herramienta de compilación para _frontend_
con una capacidad de servicio increíblemente veloz y HMR (capacidad de reflejar los cambios en la interfaz) muy rápida. Ya
le da soporte a algunos de los _frameworks_ más usados para el desarrollo web, como son [Angular](https://angular.dev/),
[VueJS](https://vuejs.org/), [Preact](https://preactjs.com/), [Astro](https://astro.build/), [Laravel](https://laravel.com/), etc.
Al tener compatibilidad con [TypeScript](https://www.typescriptlang.org), se ha optado por elegir este lenguaje para la aplicación
web, dada la robustez que aporta su tipado.

En el _frontend_ también se ha empleado [TailwindCSS](https://tailwindcss.com) para agilizar el estilizado de los componentes,
manteniendo un diseño moderno y minimalista.

La base de datos de producción se ha alojado en [FreeSQLDatabase](https://www.freesqldatabase.com). La API se ejecuta en una
instancia de un contenedor de [Render](https://render.com) que es consumida por la aplicación web, desplegada en [Vercel](https://vercel.com).

---

## Guía de instalación

<!--  
Pasos para clonar el repositorio  
Dependencias y requisitos previos  
Instrucciones para ejecutar la aplicación localmente  
-->
Antes de clonar y ejecutar los proyectos localmente, debe asegurarse de que tiene instalado en su sistema:
- [Git](https://git-scm.com)
  - Necesario para el clonado de los repositorios.
  - Puede comprobar su versión de Git instalada con:
  ```bash
  git -v
  ```
- [Java 17](https://jdk.java.net/archive/)
    - La versión de Java más compatible con Quarkus 3.x, y la más usada actualmente.
    - Puede comprobar su versión de Java instalada con:
    ```bash
    java -version
    ```
    Si no tiene Java 17, puede descargarlo buscándolo en la web del enlace, o instalarlo mediante su gestor de paquetes.


- [Maven](https://maven.apache.org)
    - Herramienta de construcción utilizada por el _backend_ de Quarkus.
    - Puede comprobar su versión de Maven instalada con:
    ```bash
    mvn -v
    ```
    Si no tiene Maven, puede descargarlo en la web del enlace, o instalarlo mediante su gestor de paquetes.


- [Node.js](https://nodejs.org/es) y [NPM](https://www.npmjs.com)
    - Vite y React utilizan Node.js para ejecutarse. NPM es el gestor de paquetes de Node.js que te permitirá instalar las dependencias.
    - Puede comprobar sus versiones de Node.js y NPM instaladas con:
    ```bash
    node -v
    npm -v
    ```
    Si no tiene Node.js, puede descargarlo en la web del enlace.


- [MySQL Server](https://www.mysql.com)
    - Versión 5.7 o superior.
    - Asegúrate de poder acceder a MySQL localmente en el puerto 3306.
    - Puede comprobar su versión de MySQL instalada con:
    ```bash
    mysql --version
    ```
    Si no tiene MySQL, puede descargarlo en la web del enlace.

Para clonar los repositorios sólo necesita ejecutar los siguientes comandos:
- API de Quarkus
```bash
# Sitúate en la carpeta donde quieras clonar el backend
cd ~/Proyectos
git clone https://github.com/pablo-rey-r03/proyecto-dam.git
cd proyecto-dam
```
- _Frontend_ de React
```bash
# Sitúate en la carpeta donde quieras clonar el frontend
cd ~/Proyectos
git clone https://github.com/pablo-rey-r03/react-dam.git
cd react-dam
```
Configura MySQL para que se conecte correctamente con la API de Quarkus. Por defecto, la API de Quarkus se conectará a una
base de datos MySQL `sql7782531` con usuario `root` y contraseña `root`.

En la carpeta raíz del _backend_, ejecuta:
```bash
  mvn clean install
```
Tras instalar las dependencias y comprobar que se ha compilado correctamente, puedes levantar la API en modo desarrollo con:
```bash
  mvn quarkus:dev
```
La API estará disponible en ``http://localhost:8080/metaconti/api/v1`` (o el _endpoint_ raíz configurado). Las CORS están
habilitadas en el archivo de configuración ``application.properties``, así que tu _frontend_ podrá consumirla sin problema.

---

## Guía de uso

<!--  
Capturas de pantalla o ejemplos de uso  
Explicación de flujos principales (login, navegación, funcionalidades clave)  
-->

---

## Documentación

En [este enlace](https://github.com/pablo-rey-r03/proyecto-dam/blob/main/MetaConti.pdf) puede consultar la documentación
avanzada del proyecto, especialmente los diagramas y modelos UML de la aplicación.

---

## Diseño de la interfaz en Figma

En [este enlace](https://www.figma.com/proto/JGjq1dDmNDUdeyxRVTKuOp/MetaConti?node-id=0-1&t=omiFQAORmfdanFGR-1) podrá
ver el diseño aproximado de la interfaz web en [Figma](https://www.figma.com/es-es/) a través de una simulación interactiva.

Si, por el contrario, desea acceder al proyecto de Figma al completo con todos los componentes, haga clic en
[este enlace](https://www.figma.com/design/JGjq1dDmNDUdeyxRVTKuOp/MetaConti?node-id=0-1&t=omiFQAORmfdanFGR-1).

---

## Conclusión

<!--  
Resumen de logros  
Posibles mejoras futuras  
Reflexión personal sobre el proyecto  
-->

---

## Contribuciones, agradecimientos y referencias

<!--  
Quiénes han colaborado o aportado ideas  
Agradecimientos a mentores, compañeros, recursos externos  
Referencias (artículos, libros, repositorios)  
-->

---

## Licencias

<!--  
Licencia(s) de código abierto utilizadas  
Términos de redistribución y uso  
-->

---

## Contacto

<!--  
Correo electrónico  
LinkedIn, GitHub u otras redes profesionales  
-->
