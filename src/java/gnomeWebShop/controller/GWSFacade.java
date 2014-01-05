package gnomeWebShop.controller;

import gnomeWebShop.model.ConvRatePK;
import gnomeWebShop.model.ConversionRate;
import gnomeWebShop.model.ConversionRateDTO;
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


}