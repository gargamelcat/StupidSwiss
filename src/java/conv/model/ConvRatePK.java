package conv.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A persistent representation of an account.
 */
@Embeddable
public class ConvRatePK implements Serializable {

 
    @Column
    private String originCurrency;
 
    @Column
    private String resultCurrency;
 
    public ConvRatePK(){
        // Your class must have a no-arq constructor
    }
 
    @Override
    public boolean equals(Object obj) {
        boolean returnValue = false;
        if(obj instanceof ConvRatePK){
            ConvRatePK convRatePK = (ConvRatePK) obj;
 
            if(convRatePK.getOriginCurrency().equals(originCurrency) && convRatePK.getResultCurrency().equals(resultCurrency)){
                returnValue = true;
            }
        }
 
        return returnValue;
    }
 
    @Override
    public int hashCode() {
        return originCurrency.hashCode() + resultCurrency.hashCode();
    }
 
    public String getOriginCurrency() {
        return originCurrency;
    }
 
    public void setOriginCurrency(String originCurrency) {
        this.originCurrency = originCurrency;
    }
 
    public String getResultCurrency() {
        return resultCurrency;
    }
 
    public void setResultCurrency(String resultCurrency) {
        this.resultCurrency = resultCurrency;
    }
}