package conv.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A persistent representation of an account.
 */
@Entity
@Table(name = "CONVERSIONRATE")
public class ConversionRate implements ConversionRateDTO, Serializable {

    private static final long serialVersionUID = 16247164401L;
    @EmbeddedId
    private ConvRatePK convRatePK;
    private double convRate;

    /**
     * Creates a new instance of Account
     */
    public ConversionRate() {
    }

    /**
     * Creates a new instance of Account
     */
    public ConversionRate(ConvRatePK primaryKey, double convRate ) {
        this.convRate = convRate;
        this.convRatePK = primaryKey;
    }

    /**
     * Get the value of lastNAme
     *
     * @return the value of lastNAme
     */
    @Override
    public String getResultCurrency() {
        return convRatePK.getResultCurrency();
    }

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    @Override
    public String getOriginCurrency() {
        return convRatePK.getOriginCurrency();
    }

    /**
     * Get the value of account number.
     *
     * @return the value of account number.
     */
    @Override
    public double getRate() {
        return convRate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        return new Double(convRate).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConvRatePK)) {
            return false;
        }
        ConversionRate other = (ConversionRate) object;
        return this.convRatePK == other.convRatePK;
    }

    @Override
    public String toString() {
        return "conv.model.ConversionRateCurrencys[origin=" + convRatePK.getOriginCurrency() + "result="+ convRatePK.getResultCurrency() +"]";
    }
}