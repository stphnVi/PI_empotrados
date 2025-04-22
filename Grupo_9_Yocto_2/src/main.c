#include <stdio.h>
#include <unistd.h>
#include <gpio_lib.h>

#define GPIO_OFFSET 512

#define OUTPUT_PIN1 (17 + GPIO_OFFSET) 

#define OUTPUT_PIN2 (18 + GPIO_OFFSET) 

#define INPUT_PIN (23 + GPIO_OFFSET)
int main() {
    // Configurar pines
    pinMode(OUTPUT_PIN1, OUTPUT);
    pinMode(OUTPUT_PIN2, OUTPUT);
    pinMode(INPUT_PIN, INPUT);
    
    // Escribir valores alternos en OUTPUT_PIN1
    for (int i = 0; i < 10; i++) {
        digitalWrite(OUTPUT_PIN1, i % 2);
        sleep(1);
    }
    
    // Generar blink en OUTPUT_PIN2 (1Hz por 5s)
    blink(OUTPUT_PIN2, 1.0, 5);
    
    // Leer valor del pin de entrada
    int value = digitalRead(INPUT_PIN);
    printf("Valor leído del pin %d: %d\n", INPUT_PIN, value);
    
    return 0;
}
