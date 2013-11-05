/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trinary.sps.net;

/**
 *
 * @author mmain
 */
public enum SPSOperation {
    DISCOVERY       ('1'),
    POWER_ON        ('2'),
    POWER_OFF       ('3'),
    SET_ALARM       ('4'),
    RESET_ALARM     ('5'),
    GET_STATUS      ('6'),
    GET_ALARM       ('7'),
    GET_MAC         ('8'),
    SET_MAC         ('9'),
    GET_STRIP_NAME  ('A'),
    SET_STRIP_NAME  ('B'),
    SET_SOCKET_NAME ('C'),
    GET_SOCKET_NAME ('D');
    
    protected byte identifier;
    
    public byte getIdentifier() {
        return identifier;
    }
    
    SPSOperation(char identifier) {
        this.identifier = (byte)identifier;
    }
}
