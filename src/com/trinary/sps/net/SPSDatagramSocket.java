/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinary.sps.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author mmain
 */
public class SPSDatagramSocket extends DatagramSocket {
    public SPSDatagramSocket() throws SocketException {
        super();
    }
    
    public void send(SPSRequestPacket packet) throws IOException {
        send(packet.toDatagramPacket());
    }
    
    public void receive(SPSResponsePacket packet) throws IOException { 
        DatagramPacket datagram = new DatagramPacket(new byte[64], 64);
        receive(datagram);
        packet.decode(datagram.getData());
    }
}