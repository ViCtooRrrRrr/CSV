import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Excel {
    private List<List<String>> workingTable = new ArrayList<>();
    private List<List<List<String>>> tableStatesHolder = new ArrayList<>();
    public void setWorkingTable(List<List<String>> workingTable) {
        this.workingTable = workingTable;
    }
    public void setCell(int row, int column, String text) {
        workingTable.get(row).set(column, text);
        tableStatesHolder.add(cloneArrayList(workingTable));
    }
    public void makeTableFromFile(String filePath) {
        try {
            for (String line : FileUtils.read(filePath).split("\n")) {
                List<String> row = new ArrayList<>();
                Collections.addAll(row, line.split(","));
                if (line.charAt(line.length() - 1) == ',') {
                    row.add("");
                }
                workingTable.add(row);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tableStatesHolder.add(cloneArrayList(workingTable));
    }
    public List<List<String>> cloneArrayList(List<List<String>> tableToClone) {
        List<List<String>> tempTable = new ArrayList<>();
        for (List<String> list : tableToClone) {
            List<String> tempList = new ArrayList<>();
            for (String str : list) {
                tempList.add(str);
            }
            tempTable.add(tempList);
        }
        return tempTable;
    }
    public void saveToFile(String filePath) {
        FileUtils.write(filePath, makeFileFromTable());
    }
    private String makeFileFromTable() {
        String result = "";
        for (int i = 0; i < workingTable.size(); i++) {
            String line = "";
            for (int j = 0; j < workingTable.get(0).size(); j++) {
                line = line + workingTable.get(i).get(j);
                if (j != workingTable.get(0).size() - 1) {
                    line = line + ",";
                }
            }
            result = result + line;
            if (i != workingTable.size() - 1) {
                result = result + "\n";
            }
        }
        return result;
    }
    public void addEmptyRow() {
        List<String> row = new ArrayList<>();
        for (int i = 0; i < workingTable.get(0).size(); i++) {
            row.add("");
        }
        workingTable.add(row);
        tableStatesHolder.add(cloneArrayList(workingTable));
    }
    public void drawTable() {
        List<Integer> maxLengths = new ArrayList<>();
        for (int i = 0; i < workingTable.get(0).size(); i++) {
            maxLengths.add(0);
        }
        for (List<String> row : workingTable) {
            for (int i = 0; i < row.size(); i++) {
                if (row.get(i).length() > maxLengths.get(i)) {
                    maxLengths.set(i, row.get(i).length());
                }
            }
        }
        String verticalSeparator = makeSeparator(maxLengths);
        System.out.println(verticalSeparator);
        for (List<String> row : workingTable) {
            System.out.println(makeLine(row, maxLengths));
            System.out.println(verticalSeparator);
        }
    }
    private String makeLine(List<String> row, List<Integer> maxLengths) {
        String result = "|";
        for (int i = 0; i < row.size(); i++) {
            int difference = maxLengths.get(i) - row.get(i).length();
            result = result + row.get(i);
            while (difference > 0) {
                result = result + " ";
                difference--;
            }
            result = result + "|";
        }
        return result;
    }
    private String makeSeparator(List<Integer> maxLengths) {
        String result = "+";
        for (int a : maxLengths) {
            for (int i = 0; i < a; i++) {
                result = result + "-";
            }
            result = result + "+";
        }
        return result;
    }
    public List<List<String>> getInitialTable() {
        return tableStatesHolder.get(0);
    }
    public void revertWorkingTableToInitial() {
        setWorkingTable(cloneArrayList(getInitialTable()));
        for(int i = tableStatesHolder.size(); i > 1; i--){
            tableStatesHolder.remove(i-1);
        }
    }
    public void revertWorkingTableOneStepBack(){
        if (tableStatesHolder.size()>1){
            tableStatesHolder.remove(tableStatesHolder.size()-1);
            setWorkingTable(cloneArrayList(tableStatesHolder.get((tableStatesHolder.size()-1))));
        }
    }
}