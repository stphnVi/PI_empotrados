#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <gpio_lib.h>

#define GPIO_BASE_PATH "/sys/class/gpio"
#define MAX_BUF 64

//Establece el modo I/O de un pin específico
int pinMode(int pin, int mode) {
    char path[MAX_BUF];
    int fd;
    
    //Exportar el pin
    fd = open(GPIO_BASE_PATH "/export", O_WRONLY);
    if (fd < 0) {
        perror("Error exportando el GPIO");
        return -1;
    }
    dprintf(fd, "%d", pin);
    close(fd);
    
    //Configurar I/O 
    snprintf(path, sizeof(path), GPIO_BASE_PATH "/gpio%d/direction", pin);
    fd = open(path, O_WRONLY);
    if (fd < 0) {
        perror("Error configurando dirección GPIO");
        return -1;
    }
    
    if (mode == INPUT) {
        write(fd, "in", 3);
    } else {
        write(fd, "out", 4);
    }
    
    close(fd);
    return 0;
}

//Escribe un valor en el pin de salida
int digitalWrite(int pin, int value) {
    char path[MAX_BUF];
    int fd;
    
    snprintf(path, sizeof(path), GPIO_BASE_PATH "/gpio%d/value", pin);
    fd = open(path, O_WRONLY);
    if (fd < 0) {
        perror("Error escribiendo en GPIO");
        return -1;
    }
    
    dprintf(fd, "%d", value ? 1 : 0);
    close(fd);
    return 0;
}
//Lee un valor del pin de entrada
int digitalRead(int pin) {
    char path[MAX_BUF];
    char value_str[3];
    int fd;
    
    snprintf(path, sizeof(path), GPIO_BASE_PATH "/gpio%d/value", pin);
    fd = open(path, O_RDONLY);
    if (fd < 0) {
        perror("Error leyendo GPIO");
        return -1;
    }
    
    read(fd, value_str, 3);
    close(fd);
    
    return atoi(value_str);
}
//Genera un parpadeo en un pin, a una freq y durante una duración
int blink(int pin, float freq, int duration) {
    int iterations = duration * freq;
    float delay = (1.0f / freq) / 2.0f;
    
    for (int i = 0; i < iterations; i++) {
        digitalWrite(pin, HIGH);
        usleep(delay * 1000000);
        digitalWrite(pin, LOW);
        usleep(delay * 1000000);
    }
    
    return 0;
}
