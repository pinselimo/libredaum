class Packet:
    ENCODING = 'ascii'

    SOH = b'\x01'
    ETB = b'\x17'
    ACK = b'\x06'
    NAK = b'\x15'

    GS = b'\x1D'

    def __init__(self, header, data):
        self.__bytes = SOH + header + data + checksum(header,data) + ETB

    @staticmethod
    def checksum(header, data):
        cs = sum(header + data) % 100
        return bytes(str(cs), Packet.ENCODING)

    @property
    def bytes(self):
        return self.__bytes

