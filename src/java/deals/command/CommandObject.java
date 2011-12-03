/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deals.command;

import java.util.Date;


/**
 *
 * @author hatem
 */
public class CommandObject {
    private Object object;
    private Date currentServerDate;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
    
    public Date getCurrentServerDate() {
        return currentServerDate;
    }

    public void setCurrentServerDate(Date currentServerDate) {
        this.currentServerDate = currentServerDate;
    }


}
