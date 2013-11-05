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
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mmain
 */
public class SmartPowerSystem {
    protected static ArrayList<SmartPowerStrip> powerStrips = new ArrayList<SmartPowerStrip>();
    protected static Integer port = 8888;
    
    public static Integer getPort() {
        return port;
    }
    
    protected static void addPowerStrip(String name, String ipAddress, String macAddress) {
        if (!powerStripAlreadyDiscovered(macAddress)) {
            SmartPowerStrip strip = new SmartPowerStrip(
                    name, 
                    ipAddress, 
                    macAddress);
            strip.getSockets()[0] = new SmartPowerSocket(strip, "SOCKET 1", 1);
            strip.getSockets()[1] = new SmartPowerSocket(strip, "SOCKET 2", 2);
            strip.getSockets()[2] = new SmartPowerSocket(strip, "SOCKET 3", 3);
            strip.getSockets()[3] = new SmartPowerSocket(strip, "SOCKET 4", 4);
            powerStrips.add(strip);
        }
    }
    
    protected static void addPowerStrip(SPSDiscoveryPacket packet) {
        if (!powerStripAlreadyDiscovered(packet.getMacAddress())) {
            SmartPowerStrip strip = new SmartPowerStrip(
                        packet.getName(), 
                        packet.getAddress(), 
                        packet.getMacAddress());
            for (int i = 0; i < 4; i++) {
                strip.getSockets()[i] = 
                        new SmartPowerSocket(strip, packet.getSocketNames()[i], i);
            }
            powerStrips.add(strip);
        }
    }
    
    public static SmartPowerStrip getPowerStrip(String macAddress) {
        for (SmartPowerStrip powerStrip : powerStrips) {
            if (powerStrip.macAddress.equals(macAddress)) {
                return powerStrip;
            }
        }
        
        return null;
    }
    
    public static ArrayList<SmartPowerStrip> getPowerStrips() {
        return powerStrips;
    }
    
    protected static Boolean powerStripAlreadyDiscovered(String macAddress) {
        return getPowerStrip(macAddress) != null;
    }
    
    public static void discover(String broadcastIp, Integer port, long timeout) throws UnknownHostException, SocketException, IOException {
        SmartPowerSystem.port = port;
        
        SPSDatagramSocket socket = new SPSDatagramSocket();
        SPSDiscoveryPacket response = new SPSDiscoveryPacket();
        
        System.out.println("Sending discovery packet.");
        SPSRequestPacket packet = new SPSRequestPacket(SPSOperation.DISCOVERY);
        packet.setAddress(broadcastIp);
        packet.setPort(port);
        
        socket.send(packet);
        
        long start = new Date().getTime();
        
        System.out.println("Waiting for ip returns.");
        while (new Date().getTime() - start > timeout) {
            socket.receive(response);
            
            System.out.println("ADDRESS: " + response.getAddress());
            System.out.println("MAC:     " + response.getMacAddress());
            System.out.println("NAME:    " + response.getName());
            System.out.println("SOCKETS: ");
            for (String socketName : response.getSocketNames()) {
                System.out.println("\t" + socketName);
            }
            
            addPowerStrip(response);
        }
    }
}
