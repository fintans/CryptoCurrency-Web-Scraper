/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

/**
 *
 * @author fintan
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fintan
 */
import coinmarketcapapi.Coin;
import coinmarketcapapi.GetCoinData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.dean.jraw.RedditClient;
import net.dean.jraw.Version;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.PublicContribution;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.Subreddit;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;
import net.dean.jraw.pagination.Paginator;
import net.dean.jraw.references.SubredditReference;
import net.dean.jraw.tree.CommentNode;
import net.dean.jraw.tree.RootCommentNode;



// scrapes reddit post and comments.
public final class RedditScraper {
   
    

    public List<ScrapedContent> comments() {
        
        //OAuth2 credentials
        Credentials credentials = Credentials.script("scrapeProject", "webscrape123", "FJ1B8R9NFJqMwQ", "DVYhmabSLuv38mzJs4FxfFmOFq4");

        UUID uuid = UUID.randomUUID();
        // Construct our NetworkAdapter
        
        UserAgent userAgent = new UserAgent("bot", "net.dean.jraw.example.script", Version.get(), "scrapeProject");
        NetworkAdapter http = new OkHttpNetworkAdapter(userAgent);

        // Authenticate our client
        RedditClient reddit = OAuthHelper.automatic(http, credentials);

        // Browse through the top posts of the last month, requesting as much data as possible per request
        DefaultPaginator.Builder<Submission, SubredditSort> crypto = reddit.subreddit("CryptoCurrency").posts();

        // Request the first page
        DefaultPaginator<Submission> list = crypto.build();

        Listing<Submission> firstPage = list.next();

        ArrayList<String> postBaseName = new ArrayList<>();

        //gets the base 36 code for each post
        for (Submission post : firstPage) {

            postBaseName.add(post.getId());

        }

        System.out.println(postBaseName);

        List<Integer> mentions = new ArrayList<>();
        List<ScrapedContent> comments = new ArrayList<>();
        //for every post on the first page, get each posts comments
        for (String post : postBaseName) {

            RootCommentNode root = reddit.submission(post).comments();  // "7uahka"

// walkTree() returns a Kotlin Sequence. Since we're using Java, we're going to have to
// turn it into an Iterator to get any use out of it.
            Iterator<CommentNode<PublicContribution<?>>> it = root.walkTree().iterator();

            while (it.hasNext()) {
                // A PublicContribution is either a Submission or a Comment.
                PublicContribution<?> thing = it.next().getSubject();
                ScrapedContent comment = new ScrapedContent(thing.getCreated(), thing.getBody());
                comments.add(comment);
                

            }

        }
        return comments;
    }


    public static void main(String[] args) {
        MentionsCount mentionsCounter = new MentionsCount();
        RedditScraper search = new RedditScraper();
        List<ScrapedContent> allComments = search.comments();
        List<Coin> listMentions = mentionsCounter.mentions(allComments);
        
        
       
        
        for (Coin coin : listMentions) {
            System.out.println(coin.getName() + coin.getMentions());
        }

        


    }


}
