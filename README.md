# 🌟 MetaConti – DAM – Pablo Rey Ramos

## Índice
1. [Introducción](#introducción)
2. [Funcionalidades del Proyecto y Tecnologías Utilizadas](#funcionalidades-del-proyecto-y-tecnologías-utilizadas)
3. [Guía de Instalación](#guía-de-instalación)
4. [Guía de Uso](#guía-de-uso)
5. [Enlace a la Documentación](#enlace-a-la-documentación)
6. [Enlace a Figma de la Interfaz](#enlace-a-figma-de-la-interfaz)
7. [Conclusión](#conclusión)
8. [Contribuciones, Agradecimientos y Referencias](#contribuciones-agradecimientos-y-referencias)
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

## Funcionalidades del Proyecto y Tecnologías Utilizadas

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
[Jakarta EE](https://jakarta.ee), [OpenTelemetry](https://opentelemetry.io), etc).

[React](https://react.dev) es una librería para JavaScript que permite al desarrollador construir la interfaz deseada a partir de
piezas individuales reutilizables llamadas "componentes", construidas a partir de funciones básicas integradas en sus archivos que juntan
marcado con código, llamados JSX. Para añadir funcionalidades extra propias de una aplicación reactiva de _frontend_, React se vale de
los llamados "_hooks_" para establecer variables renderizables, navegación, ejecuciones en bucle, referencias a elementos renderizados, etc.

Para crear este proyecto de React se ha utilizado [Vite](https://vite.dev), una herramienta de compilación para _frontend_ con una capacidad de
servicio increíblemente veloz y HMR (capacidad de reflejar los cambios en la interfaz) muy rápida. Ya le da soporte a algunos de los
_frameworks_ más usados para el desarrollo web, como son [Angular](https://angular.dev/), [VueJS](https://vuejs.org/), [Preact](https://preactjs.com/),
[Astro](https://astro.build/), [Laravel](https://laravel.com/), etc. Al tener compatibilidad con [TypeScript](https://www.typescriptlang.org),
se ha optado por elegir este lenguaje para la aplicación web, dada la robustez que aporta su tipado.

En el _frontend_ también se ha empleado [TailwindCSS](https://tailwindcss.com) para agilizar el estilizado de los componentes, manteniendo un diseño moderno y
minimalista.

<!--  
Tecnologías, frameworks y herramientas empleadas  
-->

---

## Guía de Instalación

<!--  
Pasos para clonar el repositorio  
Dependencias y requisitos previos  
Instrucciones para ejecutar la aplicación localmente  
-->

---

## Guía de Uso

<!--  
Capturas de pantalla o ejemplos de uso  
Explicación de flujos principales (login, navegación, funcionalidades clave)  
-->

---

## Enlace a la Documentación

[Documentación Completa](#)
<!-- Reemplazar “#” con el enlace real a la documentación -->

---

## Enlace a Figma de la Interfaz

[Ver Interfaz en Figma](#)
<!-- Reemplazar “#” con el enlace de Figma correspondiente -->

---

## Conclusión

<!--  
Resumen de logros  
Posibles mejoras futuras  
Reflexión personal sobre el proyecto  
-->

---

## Contribuciones, Agradecimientos y Referencias

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
