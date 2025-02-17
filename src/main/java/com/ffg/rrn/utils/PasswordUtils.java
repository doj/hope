/**
 * 
 */
package com.ffg.rrn.utils;

import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author mycomputer
 *
 */
public class PasswordUtils {
	 
    // Encrypt Password with BCryptPasswordEncoder
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
 
    public static void main(String[] args) {
        String pw = "123";
        String encrytedPassword = encryptPassword(pw);
        System.out.println("Password: " + pw);
        System.out.println("Encryted Password: " + encrytedPassword);
    }
    
    //I do not this this actually works...
    //If you want to get this random password from front end, you need
    // to implement a REST API
    //and I didn't see any benefit of providing a random password, who can memorize it?
    //at least we should allow user to change this random password
    public static String createRandomPassword() {
    	int len = 10;
    	String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_=+-/.?<>():,;";
    	Random rndm_method = new Random(); 
    	  
        char[] password = new char[len];
        for (int i = 0; i < len; i++) {
        	password[i] = values.charAt(rndm_method.nextInt(values.length())); 
        }
    	return password.toString();
    }
 
}