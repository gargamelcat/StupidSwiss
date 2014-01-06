/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates and open the template
 * in the editor.
 */
package gnomeWebShop.view;

import gnomeWebShop.controller.GWSFacade;
import gnomeWebShop.model.Client;
import gnomeWebShop.model.Gnome;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import org.omg.CORBA.DomainManager;

/**
 *
 * @author albert
 */
@Named("userManager")
@SessionScoped
public class UserManager implements Serializable {

    private String username;
    private String password;

    private String gnometypeAdmin;
    private Integer amountAdmin;

    private String gnometypeAdminNew;
    private Integer amountAdminNew;

    private String banUsername;

    private ArrayList<Gnome> gnomesList;
    private ArrayList<Client> clientList;

    private String gnometype = null;

    private Integer amount = 0;

    private ArrayList<Gnome> shoppingCart;

    @EJB
    private GWSFacade gwsFacade;

    public String login() {

        if (!gwsFacade.login(username, password, false)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unknown login, try again"));
            username = null;
            password = null;            
            return (username = password = null);
        } else {
            username = null;
            password = null;            
            return "shop.xhtml?faces-redirect=true";
        }
    }

    public String loginAdmin() {

        if (!gwsFacade.login(username, password, true)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unknown login or lack of permissions, try again"));
            username = null;
            password = null;
            return (username = password = null);
        } else {
            username = null;
            password = null;
            return "adminpanel.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        gwsFacade.logout();
        return "login.xhtml?faces-redirect=true";
    }

    public ArrayList<Gnome> getInventory() {
        gnomesList = gwsFacade.getInventory();
        return gnomesList;
    }

    public boolean isLoggedIn() {
        return gwsFacade.isLoggedIn();
    }

    public boolean isAdminLoggedIn() {
            return gwsFacade.isAdminLoggedIn();
    }

    public ArrayList<Client> getClients() {
        clientList = gwsFacade.getUnbannedClients();
        return clientList;
    }

    /**
     * Register a new user.
     */
    public void register() {
        if (gwsFacade.register(username, password, false) == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User already exists. Try a different name."));
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You are now registered to the best online gnome shop!"));
        username = null;
        password = null;
        shoppingCart = null;
    }

    public void banUsername() {
        if (gwsFacade.bannUser(banUsername) != true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User you tried to bann does not exist. No one was banned."));
        };
        banUsername = null;
    }

    public void addCart() {
        if (shoppingCart == null) {
            shoppingCart = new ArrayList<Gnome>();
        }
        if(gnometype.equals("")){
            Gnome gnometemp;
            gnometemp = new Gnome(gnometype, amount);
            boolean trobat = false;
            for (int i = 0; i < shoppingCart.size(); i++) {
                if (shoppingCart.get(i).getName().equals(gnometype)) {
                    shoppingCart.get(i).setAmount(shoppingCart.get(i).getAmount() + amount);
                    trobat = true;
                }
            }
            if (!trobat) {
                shoppingCart.add(gnometemp);
            }
            gnometype = null;
            amount = 0;
        }
    }

    public void buy() {
        for (int i = 0; i < shoppingCart.size(); i++) {
            if (gwsFacade.buyGnome(shoppingCart.get(i).getName(), shoppingCart.get(i).getAmount())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You successfully bought the gnomes!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Some error occurred during the buying process"));
            }
        }
        shoppingCart = new ArrayList<Gnome>();
    }

    public void addNewGnome() {
        if (gwsFacade.addNewGnomeToInventory(gnometypeAdminNew, amountAdminNew) == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Gnome you want to add already exists."));
        }
        gnometypeAdminNew = null;
        amountAdminNew = 0;
    }

    public void deleteGnome() {
        if (gwsFacade.removeGnomeFromInventory(gnometypeAdmin) == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Gnome you want to delete does not exist."));
        };
        gnometypeAdmin = null;
    }

    public void addGnomeAmount() {
        if (gwsFacade.addGnomeToInventory(gnometypeAdmin, amountAdmin) == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Gnome you want to add amount does not exist."));
        };
        gnometypeAdmin = null;
        amountAdmin = null;
    }

    //GETTERS AND SETTERS
    public void setShoppingCart(ArrayList<Gnome> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ArrayList<Gnome> getShoppingCart() {
        return shoppingCart;
    }

    public ArrayList<Gnome> getGnomesList() {
        return gnomesList;
    }

    public void setGnomesList(ArrayList<Gnome> gnomesList) {
        this.gnomesList = gnomesList;
    }

    public void setBanUsername(String banUsername) {
        this.banUsername = banUsername;
    }

    public String getBanUsername() {
        return banUsername;
    }

    public String getGnometypeAdminNew() {
        return gnometypeAdminNew;
    }

    public Integer getAmountAdminNew() {
        return amountAdminNew;
    }

    public void setGnometype(String gnometype) {
        this.gnometype = gnometype;
    }

    public String getGnometype() {
        return gnometype;
    }

    public void setGnometypeAdminNew(String gnometypeAdminNew) {
        this.gnometypeAdminNew = gnometypeAdminNew;
    }

    public void setAmountAdminNew(Integer amountAdminNew) {
        this.amountAdminNew = amountAdminNew;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    public void setGnometypeAdmin(String gnometypeAdmin) {
        this.gnometypeAdmin = gnometypeAdmin;
    }

    public void setAmountAdmin(Integer amountAdmin) {
        this.amountAdmin = amountAdmin;
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

    public String getGnometypeAdmin() {
        return gnometypeAdmin;
    }

    public Integer getAmountAdmin() {
        return amountAdmin;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }
}
