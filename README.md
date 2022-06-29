# AI Trainer

Es una aplicación que propone implementar algoritmos de IA para entrenar neuronas artificiales a partir de conjuntos de datos en formato CSV.
La aplicación es capaz de leer estos conjuntos de datos y realizar los ajustes necesarios en las neuronas de regresión lineal para generar un entrenamiento óptimo.

<p align="center">
  <img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/AITrainer/app/src/main/res/mipmap-xxxhdpi/ic_launcher_foreground.png" width="350" title="Icon Aplication">
</p>

### Pre-requisitos 📋

Antes de iniciar a probar la aplicacion, debe instalar las herramientas necesarias para poder utiñizar el proyecto de Android en nuestro dispositivo de computo.
#### Paso 1.
Debemos instalar nuestra herramienta de desarrollo movil y en este casi vamos a usar Android Studio la cual es el IDE oficial para el desarrollo movil para la plataforma de Android.
Dicha herramienta la vamos a poder encontrar dentro de la pagina oficial de <a href="https://developer.android.com/studio?hl=es-419&gclsrc=ds&gclsrc=ds">Android Studio</a>.
#### Paso 2.
Una vez instalada nuestra herramienta Android Studio debemos crear un emulador para poder previsualizar nuestra aplicacion en accion. Igual podemos utilizar un dispositivo fisico.

### Instalación 🔧

Una vez descargado nuestra aplicacion del repositorio nos arrojara un archivo *.zip* dicho archivo podemos descomprimirlo con la herramienta <a href="https://www.winrar.es/descargas">WinRAR</a>.

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h02_18.png" width = "250" title = "Downloaded File">
</p>

Una vez descomprimido el archivo podemos abrirlo con nuestro IDE *Android Studio*.
Una vez abierto nuestro IDE seleccionaremos el boton de *Open* para poder navegar en nuestros documentos y así encontrar el archivo.

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h06_07.png" width = "450" title = "Main screen of Android Studio IDE">
</p>

Una vez encontrado el archivo en nuestra computadora daremos clic en *Ok* para así de esta manera abra el IDE con nuestro proyecto.

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h08_29.png" width = "450" title = "Proyect Location">
</p>

Una vez sincronizado nuestro proyecto debemos obtener esta pantalla:

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h11_07.png" width = "450" title = "Final Proyect Load">
</p>


## Ejecutando las pruebas ⚙️

Para generar las pruebas de nuestra aplicación es necesario instalar unos dataset dentro de nuestro emulador los cuales se encuentran dentro de la misma aplicacion dentro de la carpeta <a href="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/tree/main/AITrainer/app/src/main/res/raw">Raw</a>
Lo que haremos es pasar esos archivos a nuestro escritorio de la computadora y arrastrarlos el emulador.

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h22_57.png" width = "250" title = "Passing dataset to emulator">
</p>

Para poder correr la aplicación presionaremos la combinación de teclas *Shift + F10* para compilar la aplicacion y instalarla ya sea en nuestro emulador o dispositivo fisico.
Una vez acabada la compilacion nos mostrara una pantalla la siguiente pantalla:

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h27_53.png" width = "250" title = "Main screen to AI Trainer">
</p>

Daremos clic en la esquina superor izquierda para activar el menu desplegable y así poder previsualizar nuestras opciones que tenemos:

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h30_17.png" width = "250" title = "AI Trainer Dropdown Menu">
</p>

Seleccionaremos la segunda opcion de *Automatic Regression* y nos mostrara la siguiente pantalla:

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h33_07.png" width = "250" title = "AI Trainer Automatic Regression Pages">
</p>

### Analice las pruebas end-to-end 🔩

Aquí generaremos nuestra primer prueba de entrenamiento de neurona con un dataset.
#### Paso 1
Cargaremos nuestro dataset dando clic en el boton de *Load Data* y procederemos a ubicarlo en nuestro dispositivo.
Usaremos el dataset de *Datos Sinteticos*

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h36_59.png" width = "250" title = "Load dataset Datos Sinteticos">
</p>

Una vez cargado el dataset previsualizaremos los datos de esta manera:

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h37_10.png" width = "250" title = "Data Previsualization">
</p>

Ahora pasaremos a la pantalla de *Operations* en dicha pantalla añadiremos un *Umbral* con un valor de *0.001* el cual nos ayudara a darle mas precision al entrenamiento de nuestra neurona:

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h39_38.png" width = "250" title = "Umbral Value">
</p>

Una vez añadido el Umbral daremos clic en *Calculate* y esperaremos a que arroje los resultados como estos:

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h41_03.png" width = "250" title = "Umbral Value">
</p>

Una vez obtenidos los resultados podemos observar ciertos apartados que son los siguientes:

```
Value W0: Este valor corresponde al valor en el que se ajusto la primer W para el entrenamiento de la neurona con este dataset.

Value W1: Este valor corresponde al valor en el que se ajusto la segunda W para el entrenamiento de la neurona con este dataset.

Value J: El valor de J corresponde al costo que vamos a ir obteniendo, mientras mas cerca a 0 mas precisa sera nuestra neurona.

Value R: El valor de R corresponde a la forma en que vamos a medir la exactitus de nuestra neurona artificial. Mientras mas cerca del 0 quiere decir que nuestra neurona no sera tan precisa pero mientras mas cerca de 1 sera mejor.

Total Epoch: Este valor corresponde a las Epocas que le tomo a nuestra neurona entrenare para poder lograr los resultados.
```
Una vez entendido ello pasaremos a la pantalla de *Chart* en donde se nos va a generar nuestras graficas para ver como fue el entrenamiento de nuestra neurona:

<p align="center">
<img src="[https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h45_40.png" width = "250" title = "Chart generated with training](https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h51_20.png)">
</p>

Por ultimo se nos reprenta la pantalla de *Print* en donde seremos capaces de imprimir nuestros resultados ya sea en *CSV*, *JSON*, *TXT*.

<p align="center">
<img src="https://github.com/Johnmorales26/johnmorales26.aitrainer.github.com/blob/main/src/2022-06-29_16h51_32.png" width = "250" title = "Chart generated with training">
</p>

## Construido con 🛠️

Las herramientas que se usaron para crear dicho proyecto fue:

* [Android Studio](https://developer.android.com/studio?hl=es-419&gclsrc=ds&gclsrc=ds) - El IDE utilizado

## Autores ✒️

* **Iván Rivalcoba** - *Investigador y Educador profesional en TI* - [Jorge Ivan Rivalcoba Rivas](https://www.linkedin.com/in/jorge-ivan-rivalcoba-rivas-ab392532/)
* **Jonatan Morales** - *Estudiante de Ingenieria En Tecnologias De Informacion Y Comunicaciones* - [Jonatan Arturo Morales Tavera](https://www.linkedin.com/in/jonatan-arturo-morales-tavera-3b825b240/)

## Licencia 📄

Este proyecto está bajo la Licencia (Tu Licencia) - mira el archivo [LICENSE.md](LICENSE.md) para detalles

## Expresiones de Gratitud 🎁

* Comenta a otros sobre este proyecto 📢

