/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.trinary.sps.net.response;

import java.util.Arrays;

/**
 *
 * @author deusp_000
 */
public class SPSDataPacket extends SPSResponsePacket {
    protected String status;
    
    public Boolean getStatus() {
        return status.equals("true");
    }
    
    public SPSDataPacket(){}
    
    public SPSDataPacket(byte[] bytes) {
        decode(bytes);
    }
    
    public void decode(byte[] bytes) {
        byte[] statusBytes = Arrays.copyOfRange(bytes, 0, 6);
        
        status = new String(statusBytes);
    }
}