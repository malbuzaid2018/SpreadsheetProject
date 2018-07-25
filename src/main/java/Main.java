import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Sheets sheet;
        Map<String, ArrayList<String>> theMap = new HashMap<>();
        Map<String, ArrayList<String>> timeKeys = new HashMap<>();
        PersonMapHash peopleHash = new PersonMapHash(); // This is necessary to avoid infinite recursion utilizing the Comparator on the String key. It's worth the sacrirfice in space. HashMap is constant lookup time versus O(log n)
        try {
            sheet = SheetsServiceUtil.getSheetsService();
            String SPREADSHEET_ID = "1BAvWAR78ghD7UAqESnxe8V5L6xtwazJ89hkhz3waQyM";
            List<String> ranges = Arrays.asList("Sheet1");
            Sheets.Spreadsheets.Values.BatchGet request = sheet.spreadsheets().values().batchGet(SPREADSHEET_ID);
            request.setRanges(ranges);
            request.setMajorDimension("COLUMNS");
            BatchGetValuesResponse response = request.execute();
                                                          ///0             //col    ///row
            System.out.println(response.getValueRanges().get(0).getValues().get(0).get(1));
            int numberOfCols = response.getValueRanges().get(0).getValues().size();
            System.out.println(response.getValueRanges().get(0).getValues());
            ArrayList<String> times = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();
            for (int i=0; i<response.getValueRanges().get(0).getValues().get(0).size(); i++){
                times.add( (String) response.getValueRanges().get(0).getValues().get(0).get(i));  //fills a times array up with values
            }
            for (int i=0; i<numberOfCols; i++){
                dates.add((String) response.getValueRanges().get(0).getValues().get(i).get(0));
            }
            int numberOfRows = times.size();
             // going to implement so it doesn't have to keep checking for names in same row. Once it discovers a name it will add it.
            //It almost seems unavoidable to not use these nested for loops. We should make sure to do whatever we are doing efficiently in the for loop to cut down on time.
            String time = "";
            for (int i = 0; i<numberOfCols; i++) {
                Boolean columnEmpty = response.getValueRanges().get(0).getValues().get(i).isEmpty();
                int numberOfRowsToProcess = response.getValueRanges().get(0).getValues().get(i).size();
                if (columnEmpty){
                    continue;
                }
                for (int j = 0; j < numberOfRowsToProcess; j++) {
                    Boolean timeChanged = !(times.get(j).equals(time)) && !(times.get(j) == "");
                    if (i == 0) {
                        continue;
                    }
                    String name = "";
                    String timeDate = "";
                    Person newPerson = new Person(name);
                    if (timeChanged) {
                        time = times.get(j);
                    }
                    boolean meetsCriteriaToAddName = (j > 0 && !(response.getValueRanges().get(0).getValues().get(i).get(j) == ""));
                    if (j > 0 && !(time.isEmpty())) { //we can redo this (j>1) to account for the multiple rows that the date takes up in the real sheet.
                        timeDate = time + " " + dates.get(i);
                    }
                    if (meetsCriteriaToAddName) {
                        name = (String) response.getValueRanges().get(0).getValues().get(i).get(j); //simplify this so we don't have to do it every time.
                        newPerson.setName(name);
                    }
                    if (theMap.containsKey(name) && timeDate != "") {
                        theMap.get(name).add(timeDate);
                    }
                    if ( (peopleHash.containsKey(name)) && (timeDate != "") && (name != null)){
                        peopleHash.get(name).incrementIntiallyAvailable(); //increment the number of slots    the person initially has available.
                        peopleHash.get(name).addTimeFree(timeDate);
                    }
                    else {
                        if (name != "" && timeDate != "" && name != null){
                            ArrayList<String> toAdd = new ArrayList();
                            newPerson.addTimeFree(timeDate);
                            toAdd.add(timeDate);
                            theMap.put(name, toAdd);
                            peopleHash.put(name, newPerson);
                        }
                    }
                    if (timeKeys.containsKey(timeDate) && timeDate != "") {
                        if (!(name.isEmpty())) {
                            timeKeys.get(timeDate).add(name);
                        }
                    }
                    else {
                        if ( !(name.isEmpty()) && !timeDate.isEmpty()){
                            ArrayList<String> toAdd = new ArrayList();
                            toAdd.add(name);
                            timeKeys.put(timeDate, toAdd);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        for(Map.Entry<String,ArrayList<String>> entry  : theMap.entrySet()){
            System.out.println("Name: " + entry.getKey());
            System.out.println("Schedule: " + entry.getValue());
        }
        for(Map.Entry<String,ArrayList<String>> entry : timeKeys.entrySet()){
            System.out.println("Time: " + entry.getKey());
            System.out.println("Who's available: " + entry.getValue());
        }
        System.out.println("New");
        ComparePerson comp = new ComparePerson();
        comp.setHash(peopleHash);
        PersonMap sortedPersons = new PersonMap(comp);
        for(Map.Entry<String, Person> entry : peopleHash.entrySet()){
            System.out.println("Name: " + entry.getKey());
            System.out.println("Schedule: " + entry.getValue().getTimes());
            sortedPersons.put(entry.getKey(), entry.getValue());
        }
        /* What this does is create a sorted HashMap. That way when we implement through the HashMap the first item will be the person that we should first add to the schedule. (We can modify criteria as we go)
         The big change is that we now have person objects that are sorted to work with. We can do things in constant time to the objects themselves by just getting the value.
         */
        System.out.println("Test here");
        Set<String> inputSub = new HashSet<>();
        inputSub.add("Jordan");
        inputSub.add("John");
        inputSub.add("Mike");
        NavigableSet<String> subSet = sortedPersons.returnSubsetOfKeys(inputSub, comp);
        for(Map.Entry<String, Person> entry : sortedPersons.entrySet()  ){
            System.out.println("Name " + entry.getKey());
            System.out.println("Schedule free at : " + entry.getValue().getTimes());
        }
        /*
        Testing to see if we can return a sorted navigable set. So we can use this when looking at times.
         */
        for (String str: subSet){
            System.out.println(str);
        }
        }
    }