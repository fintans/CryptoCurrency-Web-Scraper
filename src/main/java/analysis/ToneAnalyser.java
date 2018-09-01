/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analysis;

/**
 *
 * @author fintan
 */
import com.ibm.watson.developer_cloud.tone_analyzer.v3.*;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;
import org.json.JSONArray;
import org.json.JSONObject;

public class ToneAnalyser {

    public void tone() {

        ToneAnalyzer service = new ToneAnalyzer("2017-09-21");
        service.setUsernameAndPassword("9626125d-8d1f-46d5-b0e1-8f508cff679f", "psVv2e4LU8nN");

        String text
                = "I know the times are difficult! Our sales have been ";
//      + "disappointing for the past three quarters for our data analytics "
//      + "product suite. We have a competitive data analytics product "
//      + "suite in the industry. But we need to do our job selling it! "
//      + "We need to acknowledge and fix our sales challenges. "
//      + "We can’t blame the economy for our lack of execution! "
//      + "We are missing critical sales opportunities. "
//      + "Our product is in no way inferior to the competitor products. "
//      + "Our clients are hungry for analytical tools to improve their "
//      + "business outcomes. Economy has nothing to do with it.";

// Call the service and get the tone
        if(text.contains("the")){
        ToneOptions toneOptions = new ToneOptions.Builder()
                .html(text).sentences(Boolean.FALSE)
                .build();

        ToneAnalysis tone = service.tone(toneOptions).execute();
        System.out.println(tone);

        JSONObject json = new JSONObject(tone.getDocumentTone());
        // JSONObject data = json.getJSONObject("tones");
        JSONArray ja = (JSONArray) json.get("tones");
        // for (Object object : dataNo) {

        System.out.println(ja);
        System.out.println("HERE " + ja.get(1).toString());

        for (int i = 0; i < ja.length() - 1; i++) {
            String info = ja.get(i).toString();
            if (info.contains("sadness")) {
                System.out.println("SAD FOUND");
            }
        }
//        for (Object object : ja) {
//        String name = json.getJSONObject(object.toString()).getString("tone_name");
//        System.out.println(name);
    }
    }
    //reate json array which is upulated with key values

    public static void main(String... s) {
        ToneAnalyser fuck = new ToneAnalyser();
        fuck.tone();

    }
}
