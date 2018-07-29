import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.Arrays;

/* This class will interface with Google SpreadSheets and read and write to spreadsheets.
   It might also be used to interface with a database in the future.
 */

//TODO add spreadsheet ID as a parameter
public class DataInterface {
    Sheets sheet;
    private int numberOfInputEntries; //WARNING SPREADSHEET must meet formatting to get an accurate number.
    private int numberOfDates; //WARNING: SPREADSHEET must meet formatting to get an accurate number.
    private int defaultMin = 5;
    private int defaultMax = 6;
    public void getDataFromSpreadsheet(PersonMapHash peopleMap, TheTimeMap timeMap) {
        peopleMap.clear();
        timeMap.clear();
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
            ArrayList<String> times = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();
            for (int i = 0; i < response.getValueRanges().get(0).getValues().get(0).size(); i++) {
                times.add((String) response.getValueRanges().get(0).getValues().get(0).get(i));  //fills a times array up with values
            }
            for (int i = 0; i < numberOfCols; i++) {
                if (response.getValueRanges().get(0).getValues().get(i).size() == 0) {
                    dates.add(null);
                    continue;
                }
                dates.add((String) response.getValueRanges().get(0).getValues().get(i).get(0));
            }
            int numberOfRows = times.size();
            String time = "";
            for (int i = 0; i < numberOfCols; i++) {
                Boolean columnEmpty = response.getValueRanges().get(0).getValues().get(i).isEmpty();
                int numberOfRowsToProcess = response.getValueRanges().get(0).getValues().get(i).size();
                if (columnEmpty) {
                    continue;
                }
                for (int j = 0; j < numberOfRows; j++) {
                    Boolean timeChanged = !(times.get(j).equals(time)) && !(times.get(j) == "");
                    if (i == 0) {
                        continue;
                    }
                    if (dates.get(i) == null) {
                        continue;
                    }
                    String name = "";
                    String timeDate = "";
                    Person newPerson = new Person(name);
                    if (timeChanged) {
                        time = times.get(j);
                    }
                    boolean meetsCriteriaToAddName = (j > 0);
                    if (j > 0 && !(time.isEmpty())) { //we can redo this (j>1) to account for the multiple rows that the date takes up in the real sheet.
                        timeDate = time + " " + dates.get(i);
                    }
                    if (meetsCriteriaToAddName) {
                        if (j >= response.getValueRanges().get(0).getValues().get(i).size()) {
                            name = "";
                        } else {
                            name = (String) response.getValueRanges().get(0).getValues().get(i).get(j);
                        }
                        newPerson.setName(name);
                    }
                    timeMap.tryToAddPersonToAvailableWithMap(newPerson, defaultMin, defaultMax, timeDate, time, dates.get(i), peopleMap);
                }
            }
        }   catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public int getDefaultMin(){
        return defaultMin;
    }
    public int getDefaultMax(){
        return defaultMax;
    }
    public void setDefaultMin(int i){
        defaultMin = i;
    }
    public void setDefaultMax(int i){
        defaultMax = i;
    }
}
