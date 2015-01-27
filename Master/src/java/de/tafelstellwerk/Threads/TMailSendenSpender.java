/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tafelstellwerk.Threads;

import de.tafelstellwerk.Model.CEMail;
import de.tafelstellwerk.Repository.CAllgemeinesDAO;

/**
 *
 * @author gpaschke
 */
public class TMailSendenSpender implements Runnable{
    private String EMail = null;
    private int ID = 0;
    
    public TMailSendenSpender(String eMail, int id){
        this.EMail = eMail;
        this.ID = id;
    }
    
    @Override
    public void run() {
        if(EMail!= null && ID != 0){
            
            CEMail eMailVerteiler = CEMail.createInstance();
            eMailVerteiler.setSender("info@tafel-stellwerk-nshb.de");
            eMailVerteiler.setEmpfaenger(EMail);
            eMailVerteiler.sendeMailausImport(ID); // Spendenbestätigungsmail
   
        }  
    }
    
    
    
}
