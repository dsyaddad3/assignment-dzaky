package com.jalin.signonreminder;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

@Service
public class GenerateTextService {

    /**
     * Name: generateMessage()
     * @return HashMap<String, String>
     * @param bankCodeList (HashSet<String>)
     * @param list (ArrayList<LogsModel> )
     *
     * Inside the function:
     *    1. Populates an HashMap with unique Bank Code & Message each Bank.
     */
    public static HashMap<String, String> generateMessage(HashSet<String> bankCodeList, ArrayList<LogsModel> list){
        HashMap<String, String> hashMap = new HashMap<>();
        for (String bankCode : bankCodeList) {
            String outString = "Selamat Siang Rekan Bank " + bankCode + ",\n\n" +
                    "Mohon bantuan untuk Sign on pada envi berikut : \n\n";
            for(LogsModel log : list){
                if(log.getBankCode().equals(bankCode.trim())){
                    outString += log.toString();
                }
            }
            outString += "\n\nTerima Kasih\n";
            hashMap.put(bankCode, outString);
        }
        return hashMap;
    }

    /**
     * Name: returnLogsModel()
     * @return ArrayList<LogsModel>
     * @throws Exception
     * @param path String
     * Inside the function:
     *    1. Creates a Scanner object and reads the data from input
     *    2. Populates an ArrayList with transaction objects.
     */
    public static ArrayList<LogsModel> returnLogsModel(String path) throws Exception {
        FileInputStream fis = new FileInputStream(path);
        ArrayList<LogsModel> logs = new ArrayList<LogsModel>();
        Scanner scanFile = new Scanner(fis);
        while(scanFile.hasNextLine()){
            String[] values = scanFile.nextLine().split(";");
            boolean status = (values[4].equalsIgnoreCase("offline")) ? false : true;
            logs.add(new LogsModel(values[0],values[1],Integer.parseInt(values[2]),values[3],status));
        }
        scanFile.close();
        return logs;

    }

    /**
     * Name: uniqueBankCodes()
     * @return HashSet<String>
     * @param listAll (ArrayList<LogsModel>)
     * Inside the function:
     *    1. Populates an HashSet with unique Bank Code.
     */
    public static HashSet<String> uniqueBankCodes(ArrayList<LogsModel> listAll){
        HashSet<String> uniqueBankCodes = new HashSet<>();
        for (LogsModel log : listAll) {
            uniqueBankCodes.add(log.getBankCode());
        }
        return uniqueBankCodes;
    }
}
