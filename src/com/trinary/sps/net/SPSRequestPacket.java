/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinary.sps.net;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author mmain
 */
public class SPSRequestPacket {
    protected SPSOperation operation;
    protected byte flags = 0;
    protected byte[] data = new byte[14];
    
    protected InetAddress address;
    protected int port;
    
    public SPSRequestPacket(SPSOperation operation) {
        this.operation = operation;
        for (byte b : data) {
            b = 0;
        }
    }
    
    public SPSRequestPacket(SPSOperation operation, SPSFlags flags) {
        this(operation);
        this.flags = flags.getByte();
    }
    
    public SPSRequestPacket(SPSOperation operation, SPSFlags flags, byte[] data) throws Exception {
        this(operation, flags);
        
        if (data.length > 14) {
            throw new Exception("Data field invalid!");
        }
        
        for (int i = 0; i < 12; i++) {
            this.data[i] = data[i];
        }
    }
    
    public SPSRequestPacket(SPSOperation operation, SPSFlags flags, String hexString) throws Exception {
        this(operation, flags, DatatypeConverter.parseHexBinary(hexString.replace(":", "").replace("-", "")));
    }
    
    public void setAddress(String address) throws UnknownHostException {
        this.address = InetAddress.getByName(address);
    }
    
    public void setAddress(InetAddress address) {
        this.address = address;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public InetAddress getAddress() {
        return address;
    }
    
    public int getPort() {
        return port;
    }
    
    public DatagramPacket toDatagramPacket() {
        return new DatagramPacket(getBytes(), 16, address, port);
    }
    
    public byte[] getBytes() {
        byte[] bytes = new byte[16];
        bytes[0] = operation.getIdentifier();
        bytes[1] = flags;
        for (int i = 2; i < 16; i++) {
            bytes[i] = data[i - 2];
        }
        return bytes;
    }
}
