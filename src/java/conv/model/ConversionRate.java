package conv.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A persistent representation of a conversion rate.
 */
@Entity
@Table(name = "CONVERSIONRATE")
public class ConversionRate implements ConversionRateDTO, Serializable {

    private static final long serialVersionUID = 16247164401L;
    @EmbeddedId
    private ConvRatePK convRatePK;
    private double convRate;

    /**
     * Creates a new instance of ConversionRate
     */
    public ConversionRate() {
    }

    /**
     * Creates a new instance of ConversionRate.
     */
    public ConversionRate(ConvRatePK primaryKey, double convRate ) {
        this.convRate = convRate;
        this.convRatePK = primaryKey;
    }

    /**
     * Get the value of resultCurrency
     *
     * @return the value of resultCurrency
     */
    @Override
    public String getResultCurrency() {
        return convRatePK.getResultCurrency();
    }

    /**
     * Get the value of originCurrency
     *
     * @return the value of originCurrency
     */
    @Override
    public String getOriginCurrency() {
        return convRatePK.getOriginCurrency();
    }

    /**
     * Get the value of convRate
     *
     * @return the value of convRate.
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