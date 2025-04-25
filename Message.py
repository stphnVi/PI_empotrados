def receive_info(message):
    test = ["0","1","0","1"]
    
    message = message.strip()  # elimina espacios, saltos de línea, etc.
    print(f"Mensaje recibido limpio: '{message}'")
    
    if message == 'CheckPuerta':
        return test
    return "1"