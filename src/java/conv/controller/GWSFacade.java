package conv.controller;

import conv.model.Client;
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
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class GWSFacade {
    @PersistenceContext(unitName = "ConvPU")
    private EntityManager em;


    public boolean register(String name, String password){
        System.out.println("i'm in facade");
        Client client = new Client(name, password, false, false);
        em.persist(client);
        return false;
    }

}