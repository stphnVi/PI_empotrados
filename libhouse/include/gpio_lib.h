#ifndef GPIO_LIB_H
#define GPIO_LIB_H

#define HIGH 1
#define LOW 0
#define INPUT 0
#define OUTPUT 1

int pinMode(int pin, int mode);
int digitalWrite(int pin, int value);
int digitalRead(int pin);
int blink(int pin, float freq, int duration);

#endif
