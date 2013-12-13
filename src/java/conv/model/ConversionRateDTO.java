package conv.model;

/**
 * The views read-only view of a ConversionRate.
 */
public interface ConversionRateDTO {
    /**                                     
     * Gets the conversion rate of this conversion rate.
     *
     * @return conversion rate
     */
    double getRate();

    /**
     * Gets the origin currency of this conversion rate.
     *
     * @return the origin currency of this conversion rate.
     */
    String getOriginCurrency();

    /**
     * Gets result currency of this conversion rate.
     *
     * @return the result currency of this conversion rate.
     */
    String getResultCurrency();

}