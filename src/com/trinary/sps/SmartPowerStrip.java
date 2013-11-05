/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinary.sps;

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
    
    public void powerOnSocket(int socketNumber) {
        sockets[socketNumber].powerOn();
    }
    
    public void powerOnSocket(String socketName) {
        for (SmartPowerSocket sps : sockets) {
            if (sps.getSocketName().equals(socketName)) {
                sps.powerOn();
            }
        }
    }
    
    public void powerOffSocket(int socketNumber) {
        sockets[socketNumber].powerOff();
    }
    
    public void powerOffSocket(String socketName) {
        for (SmartPowerSocket sps : sockets) {
            if (sps.getSocketName().equals(socketName)) {
                sps.powerOff();
            }
        }
    }
    
    public void alarmOn(int socketNumber) {
        sockets[socketNumber].alarmOn();
    }
    
    public void alarmOn(String socketName) {
        for (SmartPowerSocket sps : sockets) {
            if (sps.getSocketName().equals(socketName)) {
                sps.alarmOn();
            }
        }
    }
    
    public void alarmOff(int socketNumber) {
        sockets[socketNumber].alarmOff();
    }
    
    public void alarmOff(String socketName) {
        for (SmartPowerSocket sps : sockets) {
            if (sps.getSocketName().equals(socketName)) {
                sps.alarmOff();
            }
        }
    }
    
    public void setMac(String macAddress) {
        this.macAddress = macAddress;
        // Create and send SPSRequestPacket
    }
    
    public void setName(String name) {
        this.name = name;
        // Create and send SPSRequestPacket
    }
}