/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import java.util.Date;

/**
 *
 * @author fintan
 */
public class ScrapedContent {
    
    Date date;
    String comment;
    String newsArticle;
    

    public ScrapedContent(Date date, String comment) {
        this.date = date;
        this.comment = comment;
    }
    
   
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
    
    
}
