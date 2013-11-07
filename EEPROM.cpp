#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define byte unsigned char

byte atoh(char s[2]) {
    int hex[2];

    for (int i = 0; i < 2; i++) {
        switch(s[i]) {
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            hex[i] = s[i] - '0';
            break;
        case 'a':
        case 'b':
        case 'c':
        case 'd':
        case 'e':
        case 'f':
            hex[i] = 10 + (s[i] - 'a');
            break;
        };
    }

    return (hex[0] * 16) + hex[1];
}

class EEPROM {
protected:
    static byte eeprom[64];
public:
    static void init() {
        for (int i = 0; i < 64; i++) {
            eeprom[i] = 255;
        }
    }

    static void write(byte b, int address) {
        printf("WRITING[%d]: %c (%d)\n", address, b, b);
        eeprom[address] = b;
    }

    static byte read(int address) {
        printf("READING[%d]: %c (%d)\n", address, eeprom[address], eeprom[address]);
        return eeprom[address];
    }

    static void loadFromFile(const char* filename) {
        FILE* f = fopen(filename, "r");

        printf("\n\n");
        for (int i = 0; i < 64; i++) {
            printf("%02x", eeprom[i]);
        }
        printf("\n\n");

        fread(eeprom, 1, 64, f);
    }

    static void storeToFile(const char* filename) {
        FILE* f = fopen(filename, "w");

        printf("\n\n");
        for (int i = 0; i < 64; i++) {
            printf("%02x", eeprom[i]);
        }
        printf("\n\n");

        fwrite(eeprom, 1, 64, f);
    }
};

byte EEPROM::eeprom[64];

struct EEPROM_Element {
    int offset;
    int size;
    int elements;
};

class EEPROM_Holder {
protected:
    EEPROM_Element addressElement;
    EEPROM_Element flagsElement;
    EEPROM_Element macAddressElement;
    EEPROM_Element nameElement;
    EEPROM_Element snameElement;
public:
    EEPROM_Holder() {
        flagsElement.offset = 0;
        flagsElement.size = 4;
        flagsElement.elements = 1;

        addressElement.offset = 3;
        addressElement.size = 4;
        addressElement.elements = 1;

        macAddressElement.offset = 8;
        macAddressElement.size = 6;
        macAddressElement.elements = 1;

        nameElement.offset = 14;
        nameElement.size = 10;
        nameElement.elements = 1;

        snameElement.offset = 24;
        snameElement.size = 40;
        snameElement.elements = 4;
    }

    void setFlags(const char* flags) {
        int i = flagsElement.offset;
        int j = 0;
        while (j < flagsElement.size / flagsElement.elements) {
            EEPROM::write((byte)flags[j++], i++);
        }
    }

    void setAddress(const char* address) {
        char* token;
        char* next;
        char copy[32];

        strcpy(copy, address);

        int i = addressElement.offset;
        int j = 0;
        token = strtok_r(copy, ".", &next);
        do {
            printf("octet[%d]: %s\n", j, token);
            EEPROM::write((byte)atoi(token), i++);
        } while ((token = strtok_r(NULL, ".", &next)) != NULL && j < addressElement.size / addressElement.elements);
    }

    void setMacAddress(const char* macAddress) {
        char* token;
        char* next;
        char copy[32];

        strcpy(copy, macAddress);

        int i = macAddressElement.offset;
        int j = 0;
        token = strtok_r(copy, "-:", &next);
        do {
            printf("octet[%d]: %s\n", j, token);
            EEPROM::write((byte)atoh(token), i++);
        } while ((token = strtok_r(NULL, "-:", &next)) != NULL && j < macAddressElement.size / addressElement.elements);
    }

    void setName(const char* name) {
        int i = nameElement.offset;
        int j = 0;
        while (j < nameElement.size / nameElement.elements && name[j] != '\0') {
            EEPROM::write((byte)name[j++], i++);
        }
    }

    void setSocketName(int index, const char* name) {
        int i = snameElement.offset + (index * 10);
        int j = 0;
        while (j < snameElement.size / snameElement.elements && name[j] != '\0') {
            EEPROM::write((byte)name[j++], i++);
        }
    }
};

int main() {
    //EEPROM::init();
    //EEPROM::storeToFile("eeprom.dump");

    EEPROM::loadFromFile("eeprom.dump");

    EEPROM_Holder eeprom;
    eeprom.setAddress("192.168.1.2");
    eeprom.setMacAddress("de-ad-be-ef-fe-ed");
    eeprom.setName("Retro1");
    eeprom.setSocketName(0, "NES");
    eeprom.setSocketName(1, "SNES");
    eeprom.setSocketName(2, "Genesis");
    eeprom.setSocketName(3, "Dreamcast");

    EEPROM::storeToFile("eeprom.dump");

    return 0;
}
