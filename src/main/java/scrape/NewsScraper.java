/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author fintan
 */
//scrapes articles from crypto news website
public class NewsScraper {

    //returns a list of links for each article on the site
    public List<ScrapedContent> scrape() throws IOException, ParseException {

        Document document = Jsoup.connect("https://cryptonews.com/news/").get();
        List<Document> articleLink = new ArrayList<>();
        for (Element article : document.select("div#newsContainer")) {

            Elements links = article.getElementsByTag("a");
            for (Element link : links) {
                String linkHref = link.attr("abs:href");
                Document document1 = Jsoup.connect(linkHref).get();
                articleLink.add(document1);
                String linkText = link.text();
                System.out.println(linkHref);
            }
            
            List<ScrapedContent> articleContent = new ArrayList<>();          
            for (Document document2 : articleLink) {
                
                String content = document2.select("div.cn-content").html();    
            //access time within time div
            String dateStr = document2.select("time").attr("datetime");
            //reformat date to create date object
            SimpleDateFormat formatterTest = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date dateTest = formatterTest.parse(dateStr);
            //create new ScrapedContent object
            ScrapedContent scrapedContent = new ScrapedContent(dateTest, content);
            articleContent.add(scrapedContent);
//            System.out.println("CONTENT: " + scrapedContent.getComment());
//            System.out.println("DATE : " + scrapedContent.getDate());              
            }
            return articleContent;
        }
        return null;
    }

    public void tester(List<Document> articleLink) throws ParseException {
        List<String> articleContent = new ArrayList<>();

        for (Document document1 : articleLink) {
            //String dateStr = document1.select("time").html();

            String dateStr = document1.select("time").attr("datetime");

            System.out.println(dateStr);
            SimpleDateFormat formatterTest = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            Date dateTest = formatterTest.parse(dateStr);
            System.out.println(dateTest);
        }

    }

    public static void main(String[] args) throws IOException, ParseException {
        RedditScraper search = new RedditScraper();
        NewsScraper scraper = new NewsScraper();
        List<ScrapedContent> hi = scraper.scrape();
        System.out.println("SIZE" + hi.size());
        for (ScrapedContent scrapedContent : hi) {
            System.out.println(scrapedContent);
        }
        
        
        
//        List<Document> links = scraper.links();
////        List<String> articles = scraper.articles(links);
//        scraper.articles(links);

    }

}
