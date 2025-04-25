#include <stdio.h>
#include <unistd.h>
#include <gpio_lib.h>
#include <house_lib.h>



int pin_setup(void)
{
    // Configurar pines
    pinMode(ROOM1_LIGHT_PIN, OUTPUT);
    pinMode(ROOM2_LIGHT_PIN, OUTPUT);
    pinMode(KITCHEN_LIGHT_PIN, OUTPUT);
    pinMode(DINNING_LIGHT_PIN, OUTPUT);
    pinMode(LIVING_LIGHT_PIN, OUTPUT);

    pinMode(ROOM1_DOOR_PIN, INPUT);
    pinMode(ROOM2_DOOR_PIN, INPUT);
    pinMode(FRONT_DOOR_PIN, INPUT);
    pinMode(BACK_DOOR_PIN, INPUT);

    return 0;
}


int get_door_state(int door)
{
    switch (door)
    {
    case ROOM1_D:
        return digitalRead(ROOM1_DOOR_PIN);
    case ROOM2_D:
        return digitalRead(ROOM2_DOOR_PIN);
    case FRONT_D:
        return digitalRead(FRONT_DOOR_PIN);
    case BACK_D:
        return digitalRead(BACK_DOOR_PIN);
    default:
        return -2;
    }
}

int set_light_state(int light, int value)
{
    switch (light)
    {
    case ROOM1:
        return digitalWrite(ROOM1_LIGHT_PIN, value);
    case ROOM2:
        return digitalWrite(ROOM2_LIGHT_PIN, value);
    case KITCHEN:
        return digitalWrite(KITCHEN_LIGHT_PIN, value);
    case DINNING:
        return digitalWrite(DINNING_LIGHT_PIN, value);
    case LIVING:
        return digitalWrite(LIVING_LIGHT_PIN, value);
    case ALL:
        digitalWrite(ROOM1_LIGHT_PIN, value);
        digitalWrite(ROOM2_LIGHT_PIN, value);
        digitalWrite(KITCHEN_LIGHT_PIN, value);
        digitalWrite(DINNING_LIGHT_PIN, value);
        digitalWrite(LIVING_LIGHT_PIN, value);
        return 0;
    default:
        return -2;
    }
}


int take_photo(void){
    return 0;
}
