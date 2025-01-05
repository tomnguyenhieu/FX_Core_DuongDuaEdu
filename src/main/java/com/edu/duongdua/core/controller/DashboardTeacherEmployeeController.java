package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.model.Account;
import com.edu.duongdua.core.model.Bill;
import com.edu.duongdua.core.view.Scene_DashboardTeacherEmployee;
import com.edu.duongdua.core.view.Scene_ManageDiary;
import com.edu.duongdua.core.view.Scene_SideBar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.XYChart;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashboardTeacherEmployeeController extends Controller implements EventHandler<Event>
{
    int selectedValue;
    public Scene_DashboardTeacherEmployee sceneDashboardTeacherEmployee = new Scene_DashboardTeacherEmployee();

    public DashboardTeacherEmployeeController()
    {
        sceneDashboardTeacherEmployee.addActionListener(this);
        sceneDashboardTeacherEmployee.initYearsComboBox(getComboBoxYears());
        sceneDashboardTeacherEmployee.initBoxData(getStatistcal(2, sceneDashboardTeacherEmployee.currentYear), getStatistcal(3, sceneDashboardTeacherEmployee.currentYear));
        sceneDashboardTeacherEmployee.addOnActionListener(this::handleOnAction);
    }

    public List<Bill> getStatistcal(int role, int year)
    {
        List<Bill> statisticalList = new ArrayList<>();
        for (Bill bill : billDAO.getBillStatistical(role))
        {
            if (bill.getTime().substring(3).equals(Integer.toString(year)))
            {
                statisticalList.add(bill);
            }
        }
        return statisticalList;
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

    public ObservableList<Account> getTeachers()
    {
        List<Integer> teachersId = new ArrayList<>();
        for (Bill bill : billDAO.getAllBill())
        {
            if (Integer.parseInt(bill.getTime().substring(3)) == selectedValue && bill.getType() == 1)
            {
                if (!teachersId.contains(bill.getAccount_id()))
                {
                    teachersId.add(bill.getAccount_id());
                }
            }
        }
        List<Account> teachersList = new ArrayList<>();
        for (Integer id : teachersId)
        {
            if (accountDAO.findTeacherById(id) != null)
            {
                Account teacher = accountDAO.findTeacherById(id);
                teachersList.add(teacher);
            }
        }
        final ObservableList<Account> data = FXCollections.observableArrayList();
        for (Account teacher : teachersList)
        {
            data.addAll(teacher);
        }
        return data;
    }

    public List<Bill> getLessons(Account account, int year)
    {
        List<Bill> lessonList = new ArrayList<>();
        if (account != null)
        {
            List<Bill> teacherLessons = billDAO.getTeacherBillDataById(account.getId());
            List<Bill> teacherLessonsByYear = new ArrayList<>();
            XYChart.Series<String, Number> dataSeriesLessons = new XYChart.Series<>();
            dataSeriesLessons.setName("Số lượng lessons");
            for (Bill bill : teacherLessons)
            {
                if (Integer.parseInt(bill.getTime().substring(3)) == year)
                {
                    teacherLessonsByYear.add(bill);
                }
            }
            if (!teacherLessonsByYear.isEmpty()) {
                for (Bill bill : teacherLessonsByYear) {
                    if (bill.getAccount_id() == account.getId() && bill.getLessonQty() != 0) {
                        lessonList.add(bill);
                    }
                }
            }
        }
        return lessonList;
    }

    public void handleOnAction(ActionEvent event) {
        selectedValue = sceneDashboardTeacherEmployee.yearComboBox.getValue();
        sceneDashboardTeacherEmployee.renderTeachersTable(getTeachers());
        sceneDashboardTeacherEmployee.renderLineChart(getStatistcal(2, selectedValue), getStatistcal(3, selectedValue));
    }

    @Override
    public void handle(Event event) {
        String id = ((Node) event.getSource()).getId();
        switch (id)
        {
            case "teacherTable":
                if (sceneDashboardTeacherEmployee.teacherTableView.getSelectionModel().getSelectedItem() != null)
                {
                    Account teacher = sceneDashboardTeacherEmployee.teacherTableView.getSelectionModel().getSelectedItem();
                    sceneDashboardTeacherEmployee.renderBarChart(getLessons(teacher, selectedValue));
                }
                break;
        }
    }
}
