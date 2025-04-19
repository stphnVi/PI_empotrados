# Biblioteca GPIO para Raspberry Pi 4

## Integrantes
- Sebastián Hidalgo Vargas
- Karina Martínez Guerrero 
- Estefanny Villalta Segura

## Prueba de toolchain

6) ¿Por qué no debería verse "Hello World!"? ¿Qué quiere decir la salida de la aplicación?
    bash: ./sayhello: no se puede ejecutar fichero binario: Formato de ejecutable incorrecto
    
El archivo no debería poder ejecutarse porque fue compilado para la arquitectura de la Raspberry Pi 4, no para la máquina host                                                             

9) Ejecute finalmente la aplicación sayhello dentro de la Raspberry pi: ./sayhello
¿Cuál es la salida en este caso? ¿Qué quiere decir?

El archivo ahora sí muestra una salida porque fue compilado para la arquitectura en la que se está ejecutando

## Investigación

1)¿Qué pasos debe seguir antes de escribir o leer de un puerto de entrada/salida general (GPIO)?

R/Antes de escribir o leer un puerto GPIO en Linux, se debe utilizar el comando "export" para que el pin sea accesible desde el userspace. Asimismo se debe definir si será de entrada o de salida de datos, y si es necesario las resistencias de pull-up/pull-down. Para leer se puede utilizar el comando "cat" y para escribir el comando "echo" en el pin gpio. Finalmente, se debe liberar el GPIO "unexport" cuando ya no se necesite.

2. ¿Qué comando podría utilizar, bajo Linux, para escribir a un GPIO específico?

R/
```
echo 17 > /sys/class/gpio/export # Export GPIO17
echo out > /sys/class/gpio/gpio17/direction # Output
echo 1 > /sys/class/gpio/gpio17/value # 1 (HIGH)
```


## Instrucciones de Compilación

