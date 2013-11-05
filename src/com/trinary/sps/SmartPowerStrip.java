/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinary.sps;

import com.trinary.sps.net.response.SPSDiscoveryPacket;
import com.trinary.sps.net.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author mmain
 */
public class SmartPowerStrip {
    protected String name;
    protected String ipAddress;
    protected String macAddress;
    protected SmartPowerSocket[] sockets = new SmartPowerSocket[4];

    public SmartPowerStrip() {}
    
    public SmartPowerStrip(String name, String ipAddress, String macAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
    }
    
    public SmartPowerSocket[] getSockets() {
        return sockets;
    }
    
    public void powerOnSocket(int socketNumber) throws UnknownHostException, SocketException, IOException, Exception {
        sockets[socketNumber].powerOn();
    }
    
    public void powerOnSocket(String socketName) throws UnknownHostException, SocketException, IOException, Exception {
        for (SmartPowerSocket sps : sockets) {
            if (sps.getSocketName().equals(socketName)) {
                sps.powerOn();
            }
        }
    }
    
    public void powerOffSocket(int socketNumber) throws UnknownHostException, SocketException, IOException, Exception {
        sockets[socketNumber].powerOff();
    }
    
    public void powerOffSocket(String socketName) throws UnknownHostException, SocketException, IOException, Exception {
        for (SmartPowerSocket sps : sockets) {
            if (sps.getSocketName().equals(socketName)) {
                sps.powerOff();
            }
        }
    }
    
    public void alarmOn(int socketNumber) throws UnknownHostException, SocketException, IOException, Exception {
        sockets[socketNumber].alarmOn();
    }
    
    public void alarmOn(String socketName) throws UnknownHostException, SocketException, IOException, Exception {
        for (SmartPowerSocket sps : sockets) {
            if (sps.getSocketName().equals(socketName)) {
                sps.alarmOn();
            }
        }
    }
    
    public void alarmOff(int socketNumber) throws UnknownHostException, SocketException, IOException, Exception {
        sockets[socketNumber].alarmOff();
    }
    
    public void alarmOff(String socketName) throws UnknownHostException, SocketException, IOException, Exception {
        for (SmartPowerSocket sps : sockets) {
            if (sps.getSocketName().equals(socketName)) {
                sps.alarmOff();
            }
        }
    }
    
    public void setMac(String macAddress) throws UnknownHostException, SocketException, IOException {
        this.macAddress = macAddress;
        SPSDatagramSocket socket = new SPSDatagramSocket();
        
        System.out.println("Sending discovery packet.");
        SPSRequestPacket packet = new SPSRequestPacket(SPSOperation.SET_MAC);
        packet.setAddress(ipAddress);
        packet.setPort(SmartPowerSystem.getPort());
        
        socket.send(packet);
    }
    
    public void setName(String name) throws UnknownHostException, SocketException, IOException {
        this.name = name;
        SPSDatagramSocket socket = new SPSDatagramSocket();
        
        System.out.println("Sending discovery packet.");
        SPSRequestPacket packet = new SPSRequestPacket(SPSOperation.SET_STRIP_NAME);
        packet.setAddress(ipAddress);
        packet.setPort(SmartPowerSystem.getPort());
        
        socket.send(packet);
    }
}