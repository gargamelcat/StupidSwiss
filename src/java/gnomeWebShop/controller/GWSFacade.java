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

    public boolean login(String name, String password, boolean isAdmin) {

        boolean loginSuccessful = false;

        Client client = em.find(Client.class, name);

        if (client != null) {
            if (isAdmin) {
                if (client.getAdmin() == 1 && client.getPassword().equals(password)) {
                    loginSuccessful = true;
                }
            } else {
                if (client.getAdmin() == 0 && client.getPassword().equals(password)) {
                    loginSuccessful = true;
                }
            }
        }

        return loginSuccessful;
    }

    public boolean register(String name, String password, boolean isAdmin) {

        boolean result;
        try {
            Client client = new Client(name, password, 0, 0);
            em.persist(client);
            result = true;
        } catch (Exception e) {
            result = false;
            System.out.println("Error when registering a user.");
        }
        return result;
    }

    public void addNewGnomeToInventory(String name, int amount) {
        try {
            Gnome gnome = new Gnome(name, amount);
            em.persist(gnome);
        } catch (Exception e) {
            System.out.println("Error when adding a new gnome.");
        }
    }

    public void removeGnomeFromInventory(String name, int amount) {
        try {
            Gnome gnome = em.find(Gnome.class, name);

            em.getTransaction().begin();
            em.remove(gnome);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error when deleting a new gnome.");
        }
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

    public boolean buyGnome(String gnomeName, int amount) {
        boolean buyingSuccessful = false;
        try {
            Gnome gnome = em.find(Gnome.class, gnomeName);
            if (gnome != null) {
                if (gnome.getAmount() >= amount) {
                    em.getTransaction().begin();
                    gnome.setAmount(gnome.getAmount() - amount);
                    em.getTransaction().commit();
                    buyingSuccessful = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error when trying to buy a gnome");
        }
        return buyingSuccessful;
    }

    public boolean addGnomeToInventory(String gnomeName, int amount) {
        boolean addingSuccessful = false;
        Gnome gnome = em.find(Gnome.class, gnomeName);

        if (gnome != null) {
            em.getTransaction().begin();
            gnome.setAmount(gnome.getAmount() + amount);
            em.getTransaction().commit();
        }
        return addingSuccessful;
    }
}
