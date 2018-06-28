/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coinmarketcapapi;

/**
 *
 * @author fintan
 */
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author fintan
 */
public class GetCoinData {

    public Client client;
    public int port = 49000;

    public GetCoinData() {
        client = Client.create();
        //        client.addFilter(new LoggingFilter(System.out));
    }

    public String getAllCustomers() {
        String getUrl = "https://api.coinmarketcap.com/v2/ticker/?start=1&limit=50";
        WebResource target = client.resource(getUrl);
        ClientResponse response = target.get(ClientResponse.class);
        String result = response.getEntity(String.class);
        return result;

    }

    public List<Coin> coinDataList(){
        
        //Create the JSONObject from the string returned from the API query
        JSONObject json = new JSONObject(getAllCustomers());
        JSONObject data = json.getJSONObject("data");
        //create json array which is upulated with key values
        JSONArray dataNo = data.names();
        System.out.println(dataNo);

        //list to store each coin as a class
        List<Coin> coinData = new ArrayList<>();
        for (Object object : dataNo) {
            //object.to string returns key value e.g. 2044
            int id = data.getJSONObject(object.toString()).getInt("id");
            String name = data.getJSONObject(object.toString()).getString("name");
            String symbol = data.getJSONObject(object.toString()).getString("symbol");
            int rank = data.getJSONObject(object.toString()).getInt("rank");
            double price = data.getJSONObject(object.toString()).getJSONObject("quotes").getJSONObject("USD").getDouble("price");
            double volume = data.getJSONObject(object.toString()).getJSONObject("quotes").getJSONObject("USD").getDouble("volume_24h");
            double marketCap = data.getJSONObject(object.toString()).getJSONObject("quotes").getJSONObject("USD").getDouble("market_cap");
            double percentChange = data.getJSONObject(object.toString()).getJSONObject("quotes").getJSONObject("USD").getDouble("percent_change_24h");
            
            Coin allCoins = new Coin(id, name, symbol, rank, price, volume, marketCap, percentChange);
            coinData.add(allCoins);
        }
        
        return coinData;
    }
    
    public static void main(String[] args) {
        GetCoinData coinData = new GetCoinData();
        List<Coin> list = coinData.coinDataList();
        
        for (Coin allCoins : list) {
            System.out.println(allCoins.getId() + " " + allCoins.getName() + " " + allCoins.getPrice());
        }
    }
}
