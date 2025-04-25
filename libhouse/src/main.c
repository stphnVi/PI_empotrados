#include <stdio.h>
#include <unistd.h>
#include <house_lib.h>


int main() {

    if(pin_setup() == 0){
        set_light_state(ROOM1, 1);
        set_light_state(ROOM2, 1);
        set_light_state(KITCHEN, 1);
        set_light_state(DINNING, 1);
        set_light_state(LIVING, 1);
        printf("Door 1: %d", get_door_state(ROOM1_D));
        printf("Door 2: %d",get_door_state(ROOM2_D));
    }else{
        printf("Error iniciando pines");
        return -1;
    }
    return 0;
}
