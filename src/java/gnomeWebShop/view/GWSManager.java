package gnomeWebShop.view;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import gnomeWebShop.controller.GWSFacade;
import gnomeWebShop.model.ConversionRateDTO;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("shopManager")
@ConversationScoped
public class GWSManager implements Serializable {

    private static final long serialVersionUID = 16247164405L;
    @EJB
    private GWSFacade gwsFacade;
    private String username;
    private String password;
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
     * @return Login the user.
     */
    public String login() {
        gwsFacade.getInventory();
        if(gwsFacade.login(username, password,false)){
          
          return "shop.xhtml?faces-redirect=true";
        }
        
        return "login.xhtml?faces-redirect=true";    
    }
    
     /**
     * @return Login as Admin.
     */
    public String loginAdmin() {
        if(gwsFacade.login(username, password,true)){
            return "adminpanel.xhtml?faces-redirect=true";
        }
        return "login.xhtml?faces-redirect=true";
    }
    
     /**
     * Register a new user.
     */
    public void register() {
        gwsFacade.register(username, password, false);
    }

    
  
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

     /**
     * @return the username
     */
    public String getPassword() {
        return password;
    }

     /**
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
     /**
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
  

}