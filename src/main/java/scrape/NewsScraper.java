/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import java.io.IOException;
import java.util.ArrayList;
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
    public List<Document> links() throws IOException {

        Document document = Jsoup.connect("https://cryptonews.com/news/").get();
        List<Document> articleLink = new ArrayList<>();
        for (Element article : document.select("div#newsContainer.list")) {

            Elements links = article.getElementsByTag("a");

            for (Element link : links) {
                String linkHref = link.attr("abs:href");
                Document document1 = Jsoup.connect(linkHref).get();
                articleLink.add(document1);
                String linkText = link.text();
                System.out.println(linkHref);
            }

        }
        return articleLink;

    }

    //searchs each link to get the article, each article is stored in a list of strings
    public List<String> articles(List<Document> articleLink) {
        List<String> articleContent = new ArrayList<>();

        for (Document document1 : articleLink) {
            String content = document1.select("div.cn-content").html();
            articleContent.add(content);
        }

        for (String string : articleContent) {
            System.out.println("HERE" + string);
        }
        return articleContent;
    }


    public static void main(String[] args) throws IOException {
        RedditScraper search = new RedditScraper();
        NewsScraper scraper = new NewsScraper();
        List<Document> links = scraper.links();
        List<String> articles = scraper.articles(links);
        

    }

}
