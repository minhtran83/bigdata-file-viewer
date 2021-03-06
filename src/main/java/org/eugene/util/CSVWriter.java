package org.eugene.util;

import org.apache.hadoop.fs.Path;
import org.eugene.persistent.VirtualDB;
import org.eugene.ui.Constants;

import java.io.PrintWriter;
import java.util.List;

public class CSVWriter {
    public static boolean write(Path path, List<List<String>> data){
        try{
            PrintWriter out = new PrintWriter(path.toString());
            if (data.size() == 0) {
                out.write("");
                return true;
            }
            List<String> propertyList = VirtualDB.getInstance().getCommonData().getPropertyList();
            int colNumber = propertyList.size();
            for (int i = 0; i < colNumber; i++) {
                if (i == (colNumber - 1)){
                    out.println(propertyList.get(i));
                }else{
                    out.print(propertyList.get(i));
                    out.print(",");
                }
            }

            for (List<String> record: data) {
                for (int i = 0; i < colNumber; i++) {
                    if (i == (colNumber - 1)) {
                        if (record.get(i) == null)
                            out.println(Constants.NULL);
                        else {
                                if (record.get(i).toString().contains(",")) {
                                    out.println("\"" + record.get(i) + "\"");
                                }else{
                                    out.println(record.get(i));
                                }
                             }
                    }else{
                        if (record.get(i) == null){
                            out.print(Constants.NULL);
                        }
                        else{
                            if (record.get(i).toString().contains(",")){
                                out.print("\"" + record.get(i) + "\"");
                            }else{
                                out.print(record.get(i));
                            }
                        }
                        out.print(",");
                    }
                }
            }
            out.flush();
            out.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
