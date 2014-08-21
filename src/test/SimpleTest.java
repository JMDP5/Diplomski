/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author aleksandar
 */
public class SimpleTest {

    public static void main(String[] args) {
        String text = "This is a tweet text.";
        String[] tokens = text.split("[,]");
        for (String string : tokens) {
            System.out.println(string);
        }
    }

}
