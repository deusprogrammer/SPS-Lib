/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinary.sps;

import com.trinary.sps.net.response.SPSDataPacket;
import com.trinary.sps.net.response.SPSDiscoveryPacket;
import com.trinary.sps.net.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author mmain
 */
public class SmartPowerSocket {
    protected String socketName = "Socket";
    protected SmartPowerStrip strip;
    
    public SmartPowerSocket(SmartPowerStrip strip) {
        this.strip = strip;
    }
    
    public SmartPowerSocket(SmartPowerStrip strip, String socketName) {
        this(strip);
        this.socketName = socketName;
    }
    
    public String getSocketName() {
        return socketName;
    }
    
    public void powerOn() throws UnknownHostException, SocketException, IOException {
        SPSDatagramSocket socket = new SPSDatagramSocket();
        
        System.out.println("Sending discovery packet.");
        SPSRequestPacket packet = new SPSRequestPacket(SPSOperation.POWER_ON);
        packet.setAddress(strip.ipAddress);
        packet.setPort(SmartPowerSystem.getPort());
        
        socket.send(packet);
    }
    
    public void powerOff() throws UnknownHostException, SocketException, IOException {
        SPSDatagramSocket socket = new SPSDatagramSocket();
        
        System.out.println("Sending discovery packet.");
        SPSRequestPacket packet = new SPSRequestPacket(SPSOperation.POWER_OFF);
        packet.setAddress(strip.ipAddress);
        packet.setPort(SmartPowerSystem.getPort());
        
        socket.send(packet);
    }
    
    public void alarmOn() throws UnknownHostException, SocketException, IOException {
        SPSDatagramSocket socket = new SPSDatagramSocket();
        
        System.out.println("Sending discovery packet.");
        SPSRequestPacket packet = new SPSRequestPacket(SPSOperation.SET_ALARM);
        packet.setAddress(strip.ipAddress);
        packet.setPort(SmartPowerSystem.getPort());
        
        socket.send(packet);
    }
    
    public void alarmOff() throws UnknownHostException, SocketException, IOException {
        SPSDatagramSocket socket = new SPSDatagramSocket();
        
        System.out.println("Sending discovery packet.");
        SPSRequestPacket packet = new SPSRequestPacket(SPSOperation.RESET_ALARM);
        packet.setAddress(strip.ipAddress);
        packet.setPort(SmartPowerSystem.getPort());
        
        socket.send(packet);
    }
    
    public boolean powerState() throws UnknownHostException, SocketException, IOException {
        SPSDatagramSocket socket = new SPSDatagramSocket();
        SPSDataPacket response = new SPSDataPacket();
        
        System.out.println("Sending discovery packet.");
        SPSRequestPacket packet = new SPSRequestPacket(SPSOperation.GET_STATUS);
        packet.setAddress(strip.ipAddress);
        packet.setPort(SmartPowerSystem.getPort());
        
        socket.send(packet);
        socket.receive(response);
        
        return response.getStatus();
    }
    
    public boolean alarmState() throws UnknownHostException, SocketException, IOException {
        SPSDatagramSocket socket = new SPSDatagramSocket();
        SPSDataPacket response = new SPSDataPacket();
        
        System.out.println("Sending discovery packet.");
        SPSRequestPacket packet = new SPSRequestPacket(SPSOperation.GET_ALARM);
        packet.setAddress(strip.ipAddress);
        packet.setPort(SmartPowerSystem.getPort());
        
        socket.send(packet);
        socket.receive(response);
        
        return response.getStatus();
    }
    
    public void setName(String socketName) throws UnknownHostException, SocketException, IOException {
        // Truncate to 10 characters
        this.socketName = socketName;
        
        SPSDatagramSocket socket = new SPSDatagramSocket();
        
        System.out.println("Sending discovery packet.");
        SPSRequestPacket packet = new SPSRequestPacket(SPSOperation.SET_SOCKET_NAME);
        packet.setAddress(strip.ipAddress);
        packet.setPort(SmartPowerSystem.getPort());
        
        socket.send(packet);
    }
}