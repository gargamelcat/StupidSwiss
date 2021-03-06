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
public class GWSManager implements Serializable {

    private String username;
    private String password;
    private String gnometypeAdmin;
    private Integer amountAdmin;
    private String gnometypeAdminNew;
    private Integer amountAdminNew;
    private String banUsername;
    private String unbanUsername;
    private ArrayList<Gnome> gnomesList;
    private ArrayList<Client> clientList;
    private ArrayList<Client> bannedClients;
    private String gnometype = null;
    private String gnometypeToRemove = null;
    private Integer amount = 0;
    private ArrayList<Gnome> shoppingCart;
    private Exception transactionFailure;

    @EJB
    private GWSFacade gwsFacade;

    public String login() {
        if(gwsFacade.isBanned(username)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You have been banned by an admin!"));
            return null;
        }
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

    public void unbanUsername() {
        if (gwsFacade.unbannUser(unbanUsername) != true) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User you tried to bann does not exist. No one was banned."));
        };
        unbanUsername = null;
    }

    public void addCart() {
        if (shoppingCart == null) {
            shoppingCart = new ArrayList<Gnome>();
        }
        if (gwsFacade.doesGnomeExist(gnometype)) {
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

    public void removeFromCard() {
        if (shoppingCart != null) {
            if (gwsFacade.doesGnomeExist(gnometypeToRemove)) {
                for (int i = 0; i < shoppingCart.size(); i++) {
                    if (shoppingCart.get(i).getName().equals(gnometypeToRemove)) {
                        shoppingCart.remove(i);
                    }
                }
            }
            gnometypeToRemove = null;

        }

    }

    public void buy() {
        boolean boughtSuccessful = true;
        for (int i = 0; i < shoppingCart.size(); i++) {
            if (gwsFacade.buyGnome(shoppingCart.get(i).getName(), shoppingCart.get(i).getAmount()) == false) {
                boughtSuccessful = false;
            }
        }

        if (boughtSuccessful) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You successfully bought the gnomes!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Some error occurred during the buying process"));
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
    public ArrayList<Client> getClients() {
        clientList = gwsFacade.getUnbannedClients();
        return clientList;
    }

    public ArrayList<Client> getBannedClients() {
        bannedClients = gwsFacade.getBannedClients();
        return bannedClients;
    }

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

    public String getUnbanUsername() {
        return unbanUsername;
    }

    public void setUnbanUsername(String unbanUsername) {
        this.unbanUsername = unbanUsername;
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

    public String getGnometypeToRemove() {
        return gnometypeToRemove;
    }

    public void setGnometypeToRemove(String gnometypeToRemove) {
        this.gnometypeToRemove = gnometypeToRemove;
    }

    /**
     * @return <code>true</code> if the latest transaction succeeded, otherwise
     * <code>false</code>.
     */
    public boolean getSuccess() {
        return transactionFailure == null;
    }

    /**
     * Returns the latest thrown exception.
     */
    public Exception getException() {
        return transactionFailure;
    }

    /**
     * This return value is needed because of a JSF 2.2 bug. Note 3 on page 7-10
     * of the JSF 2.2 specification states that action handling methods may be
     * void. In JSF 2.2, however, a void action handling method plus an
     * if-element that evaluates to true in the faces-config navigation case
     * causes an exception.
     *
     * @return an empty string.
     */
    private String jsf22Bugfix() {
        return "";
    }
}
