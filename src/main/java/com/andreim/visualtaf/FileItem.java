/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.andreim.visualtaf;

/**
 *
 * @author Andrei
 */
class FileItem implements java.io.Serializable {
    private String name;
    String path;

    public FileItem(String name, String path) {
        this.name = name;
        this.path = path;
    }
    
    public String toString() {
        return this.name;
    }
    
}
