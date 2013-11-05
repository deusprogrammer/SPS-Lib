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
        eeprom[address] = b;
    }

    static byte read(int address) {
        return eeprom[address];
    }

    static void loadFromFile(const char* filename) {
        FILE* f = fopen(filename, "r");

        fread(eeprom, 1, 64, f);
    }

    static void storeToFile(const char* filename) {
        FILE* f = fopen(filename, "w");

        fwrite(eeprom, 1, 64, f);
    }
};

byte EEPROM::eeprom[64];

struct EEPROM_Config {
    byte flags[4];
    byte address[4];
    byte macAddress[6];
    byte name[10];
    byte sname[40];
};

class EEPROM_Holder {
protected:
    EEPROM_Config eeprom;
public:
    void setFlags(const char* flags) {
        memcpy(eeprom.flags, flags, 4);
    }

    void setAddress(const char* address) {
        char* token;
        char* next;
        char copy[32];

        strcpy(copy, address);

        int i = 0;
        token = strtok_r(copy, ".", &next);
        do {
            printf("octet[%d]: %s\n", i, token);
            eeprom.address[i++] = (byte)atoi(token);
        } while ((token = strtok_r(NULL, ".", &next)) != NULL);
    }

    void setMacAddress(const char* macAddress) {
        char* token;
        char* next;
        char copy[32];

        strcpy(copy, macAddress);

        int i = 0;
        token = strtok_r(copy, "-:", &next);
        do {
            printf("octet[%d]: %s\n", i, token);
            eeprom.macAddress[i++] = (byte)atoh(token);
        } while ((token = strtok_r(NULL, "-:", &next)) != NULL);
    }

    void setName(const char* name) {
        strcpy((char*)eeprom.name, name);
    }

    void setSocketName(int index, const char* name) {
        strcpy((char*)(eeprom.sname + (index * 10)), name);
    }

    void writeToEEPROM() {
        byte* b = (byte*)&eeprom;

        for (int i = 0; i < 64; i++) {
            printf("WRITING[%2d] %02x (%c)(%03d)\n", i, *(b + i), *(b + i) == '\0' ? '0' : *(b + i), *(b + i));
            EEPROM::write(*(b + i), i);
        }
    }

    void readFromEEPROM() {
        byte* b = (byte*)&eeprom;

        for (int i = 0; i < 64; i++) {
            printf("READING[%2d] %02x (%c)(%03d)\n", i, EEPROM::read(i), EEPROM::read(i), EEPROM::read(i) == '\0' ? '0' : EEPROM::read(i), EEPROM::read(i));
            *(b + i) = EEPROM::read(i);
        }
    }
};

int main() {
    // EEPROM::init();
    // EEPROM::storeToFile("eeprom.dump");

    EEPROM::loadFromFile("eeprom.dump");

    EEPROM_Holder eeprom;
    eeprom.readFromEEPROM();
    eeprom.setAddress("192.168.1.2");
    eeprom.setMacAddress("de-ad-be-ef-fe-ed");
    eeprom.setName("Retro1");
    eeprom.setSocketName(0, "NES");
    eeprom.setSocketName(1, "SNES");
    eeprom.setSocketName(2, "Genesis");
    eeprom.setSocketName(3, "Dreamcast");
    eeprom.writeToEEPROM();

    EEPROM::storeToFile("eeprom.dump");

    return 0;
}
