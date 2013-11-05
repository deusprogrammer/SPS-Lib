/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinary.sps.net;

import java.util.Arrays;

/**
 *
 * @author mmain
 */
public class SPSDiscoveryPacket extends SPSResponsePacket {
    protected String address;                       //  4 bytes
    protected String macAddress;                    //  6 bytes
    protected String name;                          // 10 bytes
    protected String[] socketNames = new String[4]; // 10 bytes each
    
    public SPSDiscoveryPacket() {
        super();
    }
    
    public SPSDiscoveryPacket(byte[] bytes) {
        decode(bytes);
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getMacAddress() {
        return macAddress;
    }
    
    public String getName() {
        return name;
    }
    
    public String[] getSocketNames() {
        return socketNames;
    }
    
    public void decode(byte[] bytes) {
        byte[] addressBytes = Arrays.copyOfRange(bytes, 0,  3);  //  4 bytes
        byte[] macBytes     = Arrays.copyOfRange(bytes, 4,  9);  //  6 bytes
        byte[] nameBytes    = Arrays.copyOfRange(bytes, 10, 19); // 10 bytes
        byte[] snameBytes   = Arrays.copyOfRange(bytes, 20, 60); // 40 bytes
        
        address = String.format(
                "%d.%d.%d.%d", 
                addressBytes[0], 
                addressBytes[1], 
                addressBytes[2], 
                addressBytes[3]);
        
        macAddress = String.format(
                "%02x-%02x-%02x-%02x-%02x-%02x",
                macBytes[0],
                macBytes[1],
                macBytes[2],
                macBytes[3],
                macBytes[4],
                macBytes[5]);
        
        name = new String(nameBytes);
        
        for (int i = 0; i < 4; i++) {
            socketNames[i] = new String(Arrays.copyOfRange(snameBytes, i * 10, i * 10 + 10));
        }        
    }
}