package gnomeWebShop.controller;

import gnomeWebShop.model.Client;
import gnomeWebShop.model.Gnome;
import gnomeWebShop.model.ConvRatePK;
import gnomeWebShop.model.ConversionRate;
import gnomeWebShop.model.ConversionRateDTO;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

/**
 * A controller. All calls to the model that are executed because of an action taken by
 * the ConvManager pass through here.
 */
//git test 
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class GWSFacade {
    @PersistenceContext(unitName = "ConvPU")
    private EntityManager em;
    
     /**
     * Convert a currency to another one.
     *
     * @param amount        The amount to convert.
     * @param originCurrency This is the origin currency that will be converted.
     * @param resultCurrency The amount is converted in this currency.
     * @throws OverdraftException If withdrawal would result in a negative balance.
     */
    public double convertCurrency(double amountToConvert, String originCurrency, String resultCurrency) {

        double convertedAmount = 0;
        System.out.println("in facade: "+ originCurrency + "/"+ resultCurrency);
        
        //System.out.println(originRate);
        
        ConvRatePK convRatePK = new ConvRatePK();
        convRatePK.setOriginCurrency(originCurrency);
        convRatePK.setResultCurrency(resultCurrency);
        ConversionRateDTO found =  em.find(ConversionRate.class, convRatePK);
        
        if(found == null){
            System.out.println("not found");
            throw new EntityNotFoundException("No currency pair found that matches " + originCurrency + "/"+ resultCurrency);
        }else{
            System.out.println(found.getResultCurrency());
            convertedAmount = amountToConvert * found.getRate();
                    System.out.println(convertedAmount);
        }        
        return convertedAmount;
    }
    
    public boolean login(String name, String password, boolean isAdmin){
        
        boolean loginSuccessful = false;
        
        Client client = em.find(Client.class, name);
        
        if(client != null){
            if(isAdmin){
                if (client.getAdmin() == 1 && client.getPassword().equals(password)){
                    loginSuccessful = true;
                }
            }else{
                if (client.getAdmin() == 0 && client.getPassword().equals(password)){
                    loginSuccessful = true;
                }
            }
        }
        
        return loginSuccessful;
    }
    
    public boolean register(String name, String password, boolean isAdmin){
        
        boolean result;
        try{
            Client client = new Client(name, password, 0, 0);
            em.persist(client);
            result = true;
        }catch(Exception e){
            result = false;
            System.out.println("Error when registering a user.");
        }
        return result;
    }

    public void addNewGnomeToInventory(String name, int amount){
        try{
            Gnome gnome = new Gnome(name, amount);
            em.persist(gnome);
        }catch(Exception e){
            System.out.println("Error when adding a new gnome.");
        }
    }
    
        public void removeGnomeFromInventory(String name, int amount){
        try{
            Gnome gnome = new Gnome(name, amount);
            em.persist(gnome);
        }catch(Exception e){
            System.out.println("Error when adding a new gnome.");
        }
        }    
        
        public ArrayList<Gnome> getInventory(){
        return null;
        }
        
        public void buyGnome(String gnomeName, int amount){
            
        }
        
        public void addGnomeToInventory(String gnomeName, int amount){
            
        }
}