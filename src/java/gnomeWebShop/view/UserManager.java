/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gnomeWebShop.view;

import gnomeWebShop.controller.GWSFacade;
import gnomeWebShop.model.Gnome;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author albert
 */
@Named("userManager")
@SessionScoped
public class UserManager implements Serializable{

    private String username;
    private String password;
   
    private ArrayList<Gnome> gnomesList;

    @EJB
    private GWSFacade gwsFacade;

    public String login() {

        if(! gwsFacade.login(username, password,false)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unknown login, try again"));
            return (username = password = null);
        } else {
            return "shop.xhtml?faces-redirect=true";
        }
    }
    
    public String loginAdmin() {

        if(! gwsFacade.login(username, password,true)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unknown login, try again"));
            return (username = password = null);
        } else {
            return "adminpanel.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true";
    }

    public ArrayList<Gnome> getInventory(){
        gnomesList = gwsFacade.getInventory();
        return gnomesList;
    }
    
    public boolean isLoggedIn() {
        if(username == null) return false;
        return  true;
    }

    /**
     * Register a new user.
     */
    public void register() {
        gwsFacade.register(username, password, false);
    }
    
     /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

     /**
     * @return the username
     */
    public String getPassword() {
        return password;
    }

     /**
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
     /**
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
