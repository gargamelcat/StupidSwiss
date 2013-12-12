package conv.controller;

import conv.model.ConvRatePK;
import conv.model.ConversionRate;
import conv.model.ConversionRateDTO;
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
    public int convert(double value, String originCurrency, String resultCurrency) {
        ConversionRate acct = em.find(ConversionRate.class, originCurrency);
        return 10;
    }
    
    
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


}