import socket
import threading
from Message import *

# Servidor HTTP para imágenes
def start_image_server():
    PORT = 8080
    DIRECTORY = "images"

    import http.server
    import socketserver
    import os

    os.chdir(DIRECTORY)

    class CustomHandler(http.server.SimpleHTTPRequestHandler):
        def do_GET(self):
            print(f"Solicitud para imágenes recibida: {self.path}")
            return super().do_GET()

    handler = CustomHandler

    with socketserver.TCPServer(("", PORT), handler) as httpd:
        print(f"Servidor de imágenes activo en el puerto {PORT}")
        httpd.serve_forever()

# Iniciar servidor HTTP en segundo plano
threading.Thread(target=start_image_server, daemon=True).start()


class ChatServer:
    def __init__(self, host='0.0.0.0', port=1717):
        self.server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.server_socket.bind((host, port))
        self.server_socket.listen(5)
        self.clients = []

        # Hilo para manejar conexiones entrantes
        self.thread = threading.Thread(target=self.accept_connections)
        self.thread.start()
        
        # Hilo extra para leer información sin intervención de la app
        self.raspi_thread = threading.Thread(target=self.read_rasp)
        self.raspi_thread.start()

        

    def accept_connections(self):
        while True:
            client_socket, addr = self.server_socket.accept()
            self.clients.append(client_socket)
            print(f"Conexión de {addr}")
            threading.Thread(target=self.handle_client, args=(client_socket,)).start()

    def handle_client(self, client_socket):
        while True:
            try:
                message = client_socket.recv(1024).decode('utf-8')
                if message:
                    self.broadcast(message, client_socket)
                    resultado = receive_info(message) + "\n"
                    self.send_direct_message(client_socket, resultado)
                else:
                    break
            except:
                break
        client_socket.close()
        self.clients.remove(client_socket)

    def broadcast(self, message, sender_socket):
        print(f"Cliente: {message}")
        for client in self.clients:
            if client != sender_socket:
                try:
                    client.send(message.encode('utf-8'))
                except:
                    client.close()
                    self.clients.remove(client)

    def send_direct_message(self, client_socket, message):
        try:
            client_socket.send(message.encode('utf-8'))
            print(f"Mensaje enviado a cliente: {message}")
        except:
            print("No se pudo enviar el mensaje a este cliente")
    

    def close_server(self):
        for client in self.clients:
            client.close()
        self.server_socket.close()
        
    def read_rasp(self):
        while True:
           
            message = input()
            if message.lower() == "/exit":
                self.close_server()
                break
            message = message + "\n"
                
            self.broadcast(f"Prueba_envio_rasp_app: {message}", None)

if __name__ == "__main__":
    ChatServer()
