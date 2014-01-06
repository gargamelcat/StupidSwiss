package gnomeWebShop.controller;

import gnomeWebShop.model.Client;
import gnomeWebShop.model.Gnome;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * A controller. All calls to the model that are executed because of an action
 * taken by the ConvManager pass through here.
 */
//git test 
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class GWSFacade {

    @PersistenceContext(unitName = "ConvPU")
    private EntityManager em;
    private Client loggedInClient = null;

    public boolean login(String name, String password, boolean isAdmin) {

        boolean loginSuccessful = false;

        Client client = em.find(Client.class, name);

        if (client != null) {
            if (this.isBanned(client.getName()) == false) {
                if (isAdmin) {
                    if (client.getAdmin() == 1 && client.getPassword().equals(password)) {
                        loginSuccessful = true;
                        loggedInClient = client;
                    }
                } else {
                    if (client.getAdmin() == 0 && client.getPassword().equals(password)) {
                        loginSuccessful = true;
                        loggedInClient = client;
                    }
                }
            }

        }

        return loginSuccessful;
    }

    public void logout() {
        loggedInClient = null;
    }

    public boolean register(String name, String password, boolean isAdmin) {

        boolean result = false;
        if (em.find(Client.class, name) == null) {
            try {
                Client client = new Client(name, password, 0, 0);
                em.persist(client);
                result = true;
            } catch (Exception e) {
                System.out.println("Error when registering a user.");
            }
        }
        return result;
    }

    public boolean addNewGnomeToInventory(String name, int amount) {
        boolean returnValue = false;
        if (em.find(Gnome.class, name) == null) {
            Gnome gnome = new Gnome(name, amount);
            try {
                em.persist(gnome);
            } catch (Exception e) {
                System.out.println("Error when adding a new gnome.");
            }
            returnValue = true;
        }
        return returnValue;
    }

    public boolean removeGnomeFromInventory(String name) {
        boolean returnValue = false;
        try {
            Gnome gnome = em.find(Gnome.class, name);
            if(gnome != null){
                em.remove(gnome);
                returnValue = true;
            }
        } catch (Exception e) {
            System.out.println("Error when deleting a new gnome.");
        }
        return returnValue;
    }

    public ArrayList<Gnome> getInventory() {
        ArrayList<Gnome> resultList = new ArrayList<>();
        Query query = em.createQuery("SELECT g FROM Gnome g", Gnome.class);
        List<Gnome> tempResultList = query.getResultList();

        for (int i = 0; i < tempResultList.size(); i++) {
            resultList.add(tempResultList.get(i));
            System.out.println(tempResultList.get(i).getName());
        }
        return resultList;
    }

    public ArrayList<Client> getUnbannedClients() {
        ArrayList<Client> resultList = new ArrayList<>();
        Query query = em.createQuery("SELECT c FROM Client c WHERE c.administrator = 0 AND c.banned = 0 ", Client.class);
        List<Client> tempResultList = query.getResultList();

        for (int i = 0; i < tempResultList.size(); i++) {
            resultList.add(tempResultList.get(i));
        }
        return resultList;
    }
    
        public ArrayList<Client> getBannedClients() {
        ArrayList<Client> resultList = new ArrayList<>();
        Query query = em.createQuery("SELECT c FROM Client c WHERE c.administrator = 0 AND c.banned = 1 ", Client.class);
        List<Client> tempResultList = query.getResultList();

        for (int i = 0; i < tempResultList.size(); i++) {
            resultList.add(tempResultList.get(i));
        }
        return resultList;
    }

    public boolean buyGnome(String gnomeName, int amount) {
        boolean buyingSuccessful = false;
        try {
            Gnome gnome = em.find(Gnome.class, gnomeName);
            if (gnome != null) {
                if (gnome.getAmount() >= amount) {
                    gnome.setAmount(gnome.getAmount() - amount);
                    buyingSuccessful = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error when trying to buy a gnome");
        }
        return buyingSuccessful;
    }

    public boolean addGnomeToInventory(String gnomeName, int amount) {
        boolean addingSuccessful = false;
        Gnome gnome = em.find(Gnome.class, gnomeName);

        if (gnome != null) {
            gnome.setAmount(gnome.getAmount() + amount);
        }
        return addingSuccessful;
    }

    public boolean bannUser(String userName) {
        boolean banningSuccessful = false;
        Client client = em.find(Client.class, userName);

        if (client != null) {
            client.setBanned(1);
        }
        return banningSuccessful;
    }

    public boolean unbannUser(String userName) {
        boolean unbanningSuccessful = false;
        Client client = em.find(Client.class, userName);

        if (client != null) {
            client.setBanned(0);
        }
        return unbanningSuccessful;
    }

    public boolean isLoggedIn() {
        boolean returnValue = false;
        if (loggedInClient != null) {
            returnValue = true;
        }
        return returnValue;
    }

    public boolean isAdmin(String userName) {
        boolean returnValue = false;
        Client client = em.find(Client.class, userName);
        if (client != null) {
            if (client.getAdmin() == 1) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    public boolean isBanned(String userName) {
        boolean returnValue = false;
        Client client = em.find(Client.class, userName);
        if (client != null) {
            if (client.getBanned() == 1) {
                returnValue = true;
            }
        }
        return returnValue;
    }

    public boolean isAdminLoggedIn() {
        boolean returnValue = false;
        if (loggedInClient != null && loggedInClient.getAdmin() == 1) {
            returnValue = true;
        }
        return returnValue;
    }
    
    public boolean doesGnomeExist(String name){
        boolean returnValue = false;
        Gnome gnome = em.find(Gnome.class, name);
        if(gnome != null){
            returnValue = true;
        }
        return returnValue;
    }
}
