package conv.model;

/**
 * The views read-only view of an Account.
 */
public interface ConversionRateDTO {
    /**                                     
     * Gets the number of this Account.
     *
     * @return the account number.
     */
    int getRate();

    /**
     * Gets the first name of the holder of this Account.
     *
     * @return the first name of the holder of this Account.
     */
    String getOriginCurrency();

    /**
     * Gets the last name of the holder of this Account.
     *
     * @return the last name of the holder of this Account.
     */
    String getResultCurrency();

}