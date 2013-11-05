/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinary.sps;

/**
 *
 * @author mmain
 */
public class SmartPowerSocket {
    protected String socketName = "Socket";
    
    public SmartPowerSocket() {}
    
    public SmartPowerSocket(String socketName) {
        this.socketName = socketName;
    }
    
    public String getSocketName() {
        return socketName;
    }
    
    public void powerOn() {
        // Create and send SPSRequestPacket
    }
    
    public void powerOff() {
        // Create and send SPSRequestPacket
    }
    
    public void alarmOn() {
        // Create and send SPSRequestPacket
    }
    
    public void alarmOff() {
        // Create and send SPSRequestPacket
    }
    
    public boolean powerState() {
        // Create and send SPSRequestPacket
        return false;
    }
    
    public boolean alarmState() {
        // Create and send SPSRequestPacket
        return false;
    }
    
    public void setName(String socketName) {
        // Truncate to 10 characters
        this.socketName = socketName;
        
        // Create and send SPSRequestPacket
    }
}
