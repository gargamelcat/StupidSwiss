package conv.controller;

import conv.model.ConversionRate;
import conv.model.ConversionRateDTO;
import conv.model.OverdraftException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

/**
 * A controller. All calls to the model that are executed because of an action taken by
 * the cashier pass through here.
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class ConvFacade {
    @PersistenceContext(unitName = "ConvPU")
    private EntityManager em;

    /**
     * Withdraws the specified amount.
     *
     * @param amount        The amount to withdraw.
     * @throws OverdraftException If withdrawal would result in a negative balance.
     */
    public int convert(String originCurrency, String resultCurrency) throws OverdraftException {
        ConversionRate acct = em.find(ConversionRate.class, acctNo);
        acct.withdraw(amount);
    }


}