package conv.view;

import conv.controller.ConvFacade;
import conv.model.ConversionRateDTO;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("convManager")
@ConversationScoped
public class ConvManager implements Serializable {

    private static final long serialVersionUID = 16247164405L;
    @EJB
    private ConvFacade convFacade;
    private ConversionRateDTO currentConversionRate;
    private double originValue;
    private String currency1;
    private String currency2;
    private Exception transactionFailure;
    @Inject
    private Conversation conversation;

    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        transactionFailure = e;
    }

    /**
     * @return <code>true</code> if the latest transaction succeeded, otherwise
     * <code>false</code>.
     */
    public boolean getSuccess() {
        return transactionFailure == null;
    }

    /**
     * Returns the latest thrown exception.
     */
    public Exception getException() {
        return transactionFailure;
    }

    /**
     * This return value is needed because of a JSF 2.2 bug. Note 3 on page 7-10
     * of the JSF 2.2 specification states that action handling methods may be
     * void. In JSF 2.2, however, a void action handling method plus an
     * if-element that evaluates to true in the faces-config navigation case
     * causes an exception.
     *
     * @return an empty string.
     */
    private String jsf22Bugfix() {
        return "";
    }

    /**
     * Withdraws the amount set by the latest call to
     * <code>setTransactionAmount</code> from the account specified by
     * <code>currentAcct.getAcctNo()</code>.
     */
    public int convert() {
        //convFacade.convert(originValue, currency1, currency2);
        System.out.println("value: " + originValue + " curency one: " + currency1 + " currency2: " + currency2);
        return 10;
    }
    
    
    public void setOriginValue(int originValue){
        this.originValue = originValue;
    }
    
    public void setCurrency1(String currency1){
        this.currency1 = currency1;
    }
        
    public void setCurrency2(double originValue){
        this.currency2 = currency2;
    }
    
    
    public double getOriginValue(){
        return originValue;
    }
    
    public String geturrency1(){
        return currency1;
    }
        
    public String getCurrency2(){
        return currency2;
    }
}