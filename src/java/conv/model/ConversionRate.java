package conv.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A persistent representation of an account.
 */
@Entity
public class ConversionRate implements ConversionRateDTO, Serializable {

    private static final long serialVersionUID = 16247164401L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int convRate;
    private String originCurrency;
    private String resultCurrency;

    /**
     * Creates a new instance of Account
     */
    public ConversionRate() {
    }

    /**
     * Creates a new instance of Account
     */
    public ConversionRate(int convRate, String firstName, String lastName) {
        this.convRate = convRate;
        this.originCurrency = firstName;
        this.resultCurrency = lastName;
    }

    /**
     * Get the value of lastNAme
     *
     * @return the value of lastNAme
     */
    @Override
    public String getResultCurrency() {
        return resultCurrency;
    }

    /**
     * Get the value of firstName
     *
     * @return the value of firstName
     */
    @Override
    public String getOriginCurrency() {
        return originCurrency;
    }

    /**
     * Get the value of account number.
     *
     * @return the value of account number.
     */
    @Override
    public int getRate() {
        return convRate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        return new Integer(convRate).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ConversionRate)) {
            return false;
        }
        ConversionRate other = (ConversionRate) object;
        return this.convRate == other.convRate;
    }

    @Override
    public String toString() {
        return "conv.model.ConversionRate[id=" + convRate + "]";
    }
}