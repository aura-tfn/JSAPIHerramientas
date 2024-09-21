# JSAPIHerramientas

## Introducción


Aquí tienes el texto corregido ortográfica y sintácticamente:

Este repositorio contiene herramientas para la automatización de tareas utilizando JSAPI en Java. Está basado en las librerías escritas por una empresa llamada CloseGarden (o eso creo), que debió desaparecer, ya que su web está en venta.
Estas librerías implementan la interfaz definida por Oracle, conocida como Java Speech (https://en.wikipedia.org/wiki/Java_Speech_API), para conectarse con la librería nativa de Windows, **Microsoft Speech API (SAPI)** (https://es.wikipedia.org/wiki/Microsoft_Speech_API).

Mi contribución consiste en la implementación de un conjunto de herramientas que permiten controlar el ratón y el teclado con la voz, para enviar teclas y clics de ratón, fundamentalmente con el objetivo de ser utilizadas en juegos por personas con discapacidad que no puedan usar el teclado o el ratón con la suficiente rapidez.


Con esta herramienta se puede crear una gramática que responda a los comandos de voz específicos de ese juego, y que permita enviar pulsaciones de tecla o clics de ratón en segundo plano. La ventaja de esta herramienta es que siempre intentará asociar lo que escucha con uno de los comandos definidos en la gramática. Además, lo hace de manera prácticamente instantánea, a diferencia de los modelos de lenguaje como ChatGPT u otros, donde la llamada a la nube y la ejecución mediante un agente harían el proceso mucho más lento.
La herramienta también permite cargar y descargar gramáticas en función del contexto de la situación del juego, así como ejecutar código, guardar estados y decir frases que nos permitan saber en qué situación o contexto se encuentra este asistente.



## Contenido

- `jsapiw/`: La librería que he creado y qué hay que utilizar como base Para crear asistentes de juegos.
- `NetbeansTyper/`: Asistente para programar con la ID Apache netbeans. no funciona bien.
- `emergency/`: Asistente para jugar a emergency (https://store.steampowered.com/app/850170/EMERGENCY/). Este asistente es bastante simple y sirve como ejemplo para un asistente que no guarda contextos o estados y simplemente lanza pulsaciones de teclado o commander de ratón según la frase que ha entendido.
- `OFICIAL/`: Asistente para jugar a World of Warships (https://promo.worldofwarships.com/) Es muy probable que esté obsoleto pero sirve como ejemplo para ver cómo crear un asistente complejo que puede cambiar de contexto a lo largo del juego
- `RatonChino`: Asistente para realizar tareas del ratón complejas para una persona con discapacidad.

## Instalación
Antes de iniciar un nuevo proyecto o de intentar modificar los existentes, es necesario instalar Las librerías de CloseGarden en el sistema. Ejecuta el script para que copie las librerías en el repositorio. A partir de ahí las puedes añadir a tu proyecto en el fichero pom.
Es evidente que esta herramienta sólo funciona en Windows. Está probada en las versiones 10 y 11

El código no está comentado pero lo comentaré en sucesivas actualizaciones.

Espero que os sea de utilidad.
