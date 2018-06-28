/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import coinmarketcapapi.Coin;
import coinmarketcapapi.GetCoinData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fintan
 */
public class MentionsCount {
    
    Coin coin = new Coin();

    //searched scraped content for coin mentions, then sets coin object mentions
    public List<Coin> mentions(List<ScrapedContent> content) {
        //uses API to get coin info
        GetCoinData coinData = new GetCoinData();
        //JSON parses API return to create a coin of coin objects
        List<Coin> list = coinData.coinDataList();
        //gets name and symbol of each coin, which is used to find mentions
        for (Coin coin : list) {
            String name = coin.getName();
            String symbol = coin.getSymbol();
            //search content for mentions
            int mentions = (counter(content, name, symbol));
            //set coin mentions
            coin.setMentions(mentions);

        }
        //return new coin list
        return list;
    }

//    public List<String> coinComment(List<ScrapedContent> content, String coinName, String coinSymbol) {
//        List<String> coinComment = new ArrayList<>();
//        
//            for (ScrapedContent scrapedContent : content) {
//                if (scrapedContent.comment.toLowerCase().contains(coinName.toLowerCase()) || scrapedContent.comment.toLowerCase().contains(coinSymbol.toLowerCase())) {
//                    coinComment.add(scrapedContent.comment);
//                }
//            }
//
//
//        return coinComment;
//    }

    public int counter(List<ScrapedContent> comments, String searchTerm, String symbol) throws NullPointerException {
        int count = 0;
        for (ScrapedContent comment : comments.subList(1, comments.size() - 1)) {
            String commentString = comment.getComment();
            if (commentString == null) {
                commentString = "will this work";
            }
            String a[] = commentString.split(" ");

            for (int j = 0; j < a.length; j++) {
                if (searchTerm.equalsIgnoreCase(a[j]) || symbol.equalsIgnoreCase(a[j])) {
                    count++;
                }
            }
        }
        return count;
    }
}
