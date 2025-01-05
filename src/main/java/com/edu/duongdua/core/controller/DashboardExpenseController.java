package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.model.Bill;
import com.edu.duongdua.core.view.Scene_DashboardExpense;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class DashboardExpenseController extends Controller implements EventHandler<Event>
{
    public int yearSelected;
    public Scene_DashboardExpense sceneDashboardExpense = new Scene_DashboardExpense();
    public DashboardExpenseController()
    {
        sceneDashboardExpense.addEventListener(this);
        sceneDashboardExpense.createScene();
        sceneDashboardExpense.initDataBox(getTotalExpenseByType(1), getTotalExpenseByType(2), getTotalExpenseByType(3));
        sceneDashboardExpense.initYearsComboBox(getComboBoxYears());
        sceneDashboardExpense.addOnActionListener(this::handleOnAction);

        System.out.println(getTotalExpenseByType(2));
    }
    public String getCurrentTime()
    {
        String tmpTime = "";
        if (sceneDashboardExpense.currentMonth <= 9)
        {
            tmpTime = "0" + sceneDashboardExpense.currentMonth + "/" + sceneDashboardExpense.currentYear;
        } else
        {
            tmpTime = sceneDashboardExpense.currentMonth + "/" + sceneDashboardExpense.currentYear;
        }
        return tmpTime;
    }

    public ArrayList<List<String>> getChartData(int billType, int yearSelected)
    {
        ArrayList<List<String>> lineChartData = new ArrayList<>();
        List<String> tmpMonthList = new ArrayList<>();
        for(Bill billForMonth : billList){
            String month = billForMonth.getTime();
            if (month.substring(3).equals(Integer.toString(yearSelected)))
            {
                if(!tmpMonthList.contains(month) && billForMonth.getType() == billType){
                    tmpMonthList.add(month);
                    int totalSalary = 0;
                    List<String> data = new ArrayList<>();
                    for(Bill bill : billList){
                        if(bill.getTime().equals(month) && bill.getType() == billType){
                            totalSalary += bill.getTotal_price();
                        }
                    }
                    data.add(month);
                    data.add(Integer.toString(totalSalary));
                    lineChartData.add(data);
                }
            }
        }
        return lineChartData;
    }

    public int getTotalExpenseByType(int billType)
    {
        int totalExpense = 0;
        for(Bill bill : billDAO.getAllBill()){
            if(bill.getTime().equals(getCurrentTime()) && bill.getType() == billType){
                totalExpense += bill.getTotal_price();
            }
        }
        return totalExpense;
    }

    public File uploadFile()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Excel file to upload");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel file", "*.xlsx")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        return file;
    }

    public ArrayList<List<String>> readExcel(File file)
    {
        ArrayList<List<String>> arrayList = new ArrayList<>();
        try {
            FileInputStream fs = new FileInputStream(file.getPath());
            XSSFWorkbook workbook = new XSSFWorkbook(fs);

            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getLastRowNum();
            for (int i = 1; i <= rows; i++)
            {
                List<String> list = new ArrayList<>();

                XSSFRow csvcRow = sheet.getRow(i);
                XSSFCell nameCell = csvcRow.getCell(1);
                XSSFCell quantityCell = csvcRow.getCell(2);
                XSSFCell priceCell = csvcRow.getCell(3);
                XSSFCell totalPriceCell = csvcRow.getCell(4);
                XSSFCell timeCell = csvcRow.getCell(5);

                String nameCellValue = nameCell.getStringCellValue();
                double quantityCellValue = quantityCell.getNumericCellValue();
                Integer _quantityCellValue = (int) quantityCellValue;
                String __quantityCellValue = _quantityCellValue.toString();

                double priceCellValue = priceCell.getNumericCellValue();
                Integer _priceCellValue = (int) priceCellValue;
                String __priceCellValue = _priceCellValue.toString();

                double totalPriceCellValue = totalPriceCell.getNumericCellValue();
                Integer _totalPriceCellValue = (int) totalPriceCellValue;
                String __totalPriceCellValue = _totalPriceCellValue.toString();

                String timeCellValue = timeCell.getStringCellValue();

                list.add(nameCellValue);
                list.add(__quantityCellValue);
                list.add(__priceCellValue);
                list.add(__totalPriceCellValue);
                list.add(timeCellValue);

                arrayList.add(list);
            }
            fs.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return arrayList;
    }

    public boolean addBill(ArrayList<List<String>> excelFile)
    {
        boolean isAdded = false;
        for (List<String> item : excelFile)
        {
            String name = item.getFirst();
            String quantity = item.get(1);
            int _quantity = Integer.parseInt(quantity);
            String price = item.get(2);
            int _price = Integer.parseInt(price);
            String totalPrice = item.get(3);
            int _totalPrice = Integer.parseInt(totalPrice);
            String month = item.getLast().substring(3);

            Bill bill = new Bill();
            bill.setAccount_id(1);
            bill.setName(name);
            bill.setQuantity(_quantity);
            bill.setPrice(_price);
            bill.setTotal_price(_totalPrice);
            bill.setTime(month);
            bill.setType(3);
            billDAO.addBill(bill);
            isAdded = true;
        }
        return isAdded;
    }

    public List<Integer> getComboBoxYears()
    {
        List<Integer> years = new ArrayList<>();
        for (Bill bill : billDAO.getBillStatistical(2))
        {
            if (!years.contains(Integer.parseInt(bill.getTime().substring(3))))
            {
                years.add(Integer.parseInt(bill.getTime().substring(3)));
            }
        }
        return years;
    }

    public void handleOnAction(ActionEvent event) {
        yearSelected = sceneDashboardExpense.comboBox.getValue();
        sceneDashboardExpense.renderLineChart(getChartData(1, yearSelected), getChartData(2, yearSelected));
        sceneDashboardExpense.renderBarChart(getChartData(3, yearSelected), "Cơ sở vật chất");
    }

    @Override
    public void handle(Event event) {
        String id = ((Node) event.getSource()).getId();
        switch (id)
        {
            case "uploadBtn":
                File uploadFile = uploadFile();
                ArrayList<List<String>> arrayList = readExcel(uploadFile);
                if (addBill(arrayList))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Thêm bill thành công!");
                    alert.show();
                    sceneDashboardExpense.renderBarChart(getChartData(3, yearSelected), "Cơ sở vật chất");
                    sceneDashboardExpense.initDataBox(getTotalExpenseByType(1), getTotalExpenseByType(2), getTotalExpenseByType(3));
                } else
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Thêm bill thất bại!");
                    alert.show();
                }
                break;
        }
    }
}
