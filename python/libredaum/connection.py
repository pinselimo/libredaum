import socket
from threading import Thread

from .protocol.packet import Packet
from .protocol.responses import Response

class ConnectionProvider:
    PORT = 51955

    self._last_packet = None

    def __init__(self, ip, receiver):
        self._socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self._socket.connect(ip, PORT)

        # Spawn thread receiving responses
        responseThread = Thread(target=handle_responses, args=(self._socket, self, receiver))

    def sendPacket(self, packet):
        self._last_packet = packet
        self._socket.send(packet)

    def resendPacket(self):
        self.sendPacket(self._last_packet)

class Receiver:
    def newResponse(self, response):
        pass

def handle_responses(socket, sender, receiver):
    pos = 0
    while 1:
        response = socket.recv(1024)
        while pos < len(response):
            if response[pos] == Packet.NAK:
                sender.resendPacket()
                pos += 1
            elif response[pos] == Packet.ACK:
                pos += 1
            else:
                end = packet_length(pos, response)
                receiver.newResponse(Response.read(response[pos:end+1]
                pos = end+2
        pos = 0

def packet_length(pos, response):
    end = pos
    while end < len(response) && response[end] != Packet.ETB:
        end += 1
    return end

