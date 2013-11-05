/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinary.sps.net;

/**
 *  Note: The actual response is always 64 bytes.
 * 
 * @author mmain
 */
public class SPSResponsePacket {
    public SPSResponsePacket() {}
    
    public SPSResponsePacket(byte[] bytes) {
        decode(bytes);
    }
    
    public void decode(byte[] bytes) {}
}
