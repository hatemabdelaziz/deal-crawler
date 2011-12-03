/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deals.command;

import java.util.Date;
import java.util.List;

/**
 *
 * @author hatem
 */
public class CommandList {
    private List<Object> list;
    private Date currentServerDate;
    
    public Date getCurrentServerDate() {
        return currentServerDate;
    }

    public void setCurrentServerDate(Date currentServerDate) {
        this.currentServerDate = currentServerDate;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

}
