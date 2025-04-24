typedef enum {
    ROOM1 = 1,
    ROOM2,
    KITCHEN,
    DINNING,
    LIVING,
    ALL
} Lights;

typedef enum{
    ROOM1_D = 1,
    ROOM2_D,
    FRONT_D,
    BACK_D
} Doors;

#define GPIO_OFFSET 512

#define ROOM1_LIGHT_PIN (18 + GPIO_OFFSET)
#define ROOM2_LIGHT_PIN (23 + GPIO_OFFSET)
#define KITCHEN_LIGHT_PIN (24 + GPIO_OFFSET)
#define DINNING_LIGHT_PIN (25 + GPIO_OFFSET)
#define LIVING_LIGHT_PIN (16 + GPIO_OFFSET)

#define ROOM1_DOOR_PIN (17 + GPIO_OFFSET)
#define ROOM2_DOOR_PIN (27 + GPIO_OFFSET)
#define FRONT_DOOR_PIN (22 + GPIO_OFFSET)
#define BACK_DOOR_PIN (5 + GPIO_OFFSET)

int set_light_state(Lights light, int value);
int get_door_state(int door);
int pin_setup(void);
int take_photo(void);