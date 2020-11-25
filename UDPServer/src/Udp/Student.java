/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Udp;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class Student implements Serializable{
    private String name;
    private float gpa;
    private String phanloai;
    private static final long serialVersionUID = 2499339822666080654L;
     public Student(String name, float gpa, String phanloai) {
        this.name = name;
        this.gpa = gpa;
        this.phanloai = phanloai;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public String getPhanloai() {
        return phanloai;
    }

    public void setPhanloai(String phanloai) {
        this.phanloai = phanloai;
    }
}
