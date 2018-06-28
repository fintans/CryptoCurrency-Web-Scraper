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

public class ToneAnalyser {

public static void main(String...s) {
    ToneAnalyzer service = new ToneAnalyzer("2017-09-21");
service.setUsernameAndPassword("9626125d-8d1f-46d5-b0e1-8f508cff679f", "psVv2e4LU8nN");

String text =
  "I know the times are difficult! Our sales have been "
      + "disappointing for the past three quarters for our data analytics "
      + "product suite. We have a competitive data analytics product "
      + "suite in the industry. But we need to do our job selling it! "
      + "We need to acknowledge and fix our sales challenges. "
      + "We can’t blame the economy for our lack of execution! "
      + "We are missing critical sales opportunities. "
      + "Our product is in no way inferior to the competitor products. "
      + "Our clients are hungry for analytical tools to improve their "
      + "business outcomes. Economy has nothing to do with it.";

// Call the service and get the tone
ToneOptions toneOptions = new ToneOptions.Builder()
  .html(text)
  .build();

ToneAnalysis tone = service.tone(toneOptions).execute();
System.out.println(tone);

}

}