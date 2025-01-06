package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.dao.BillDAO;
import com.edu.duongdua.core.model.Bill;
import com.edu.duongdua.core.view.Scene_DashboardEarning;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

public class DashboardEarningController extends Controller {
    public Integer yearSelected;
    public Integer totalEarningByYear = 0;
    public Integer avgMonthlyEarning;
    public Integer totalInterset;
    public Double increase;

    public Scene_DashboardEarning sceneDashboardEarning = new Scene_DashboardEarning();

    public DashboardEarningController () {
        sceneDashboardEarning.onActionListener(this::handleOnAction);
        sceneDashboardEarning.initYearCB(getListYear());
        yearSelected = sceneDashboardEarning.currentYear;
        sceneDashboardEarning.selectYearCB.setValue(yearSelected);
        sceneDashboardEarning.renderBarChart(getEarningByMonth());
        calculateLabelData();
        sceneDashboardEarning.renderLabel(totalEarningByYear, avgMonthlyEarning, totalInterset, increase);
    }

    public List<Integer> getListYear () {
        List<Integer> years = new ArrayList<>();
        for (Bill bill : billList)
        {
            if (!years.contains(Integer.parseInt(bill.getTime().substring(3))))
            {
                years.add(Integer.parseInt(bill.getTime().substring(3)));
            }
        }
        Collections.sort(years);
        return years;
    }

    public List<Bill> getEarningByMonth () {
        totalEarningByYear = 0;
        List<Bill> billsByYear =  new ArrayList<>();
        List<Integer> months = new ArrayList<>();

        // Lấy các bill có năm bằng năm được chọn
        for (Bill bill : billDAO.getAllBill()) {
            if (Integer.parseInt(bill.getTime().substring(3)) == yearSelected && bill.getType() == 4) {
                // add bill của năm được chọn vào list
                billsByYear.add(bill);

                // cộng dồn tổng thu của năm
                totalEarningByYear += bill.getTotal_price();

                // gán các tháng của năm vào mảng months
                if (!months.contains(Integer.parseInt(bill.getTime().substring(0, 2)))) {
                    months.add(Integer.parseInt(bill.getTime().substring(0, 2)));
                }
            }
        }
        Collections.sort(months);

        List<Bill> earningByMonths = new ArrayList<>();
        for (Integer month : months) {
            int monthlyEarning = 0;
            Bill _bill = new Bill();
            for (Bill bill : billsByYear) {
                if (Integer.parseInt(bill.getTime().substring(0, 2)) == month) {
                    monthlyEarning += bill.getTotal_price();
                }
            }
            // set thuộc tính: tháng, tổng thu tháng cho bill
            _bill.setTime(month.toString());
            _bill.setTotal_price(monthlyEarning);
            earningByMonths.add(_bill);
        }

        // Tính doanh thu trung bình
        avgMonthlyEarning = totalEarningByYear / months.size();
        return earningByMonths;
    }

    public void calculateLabelData () {
        int totalSpending = 0;

        // Lặp qua bill và tính tổng chi
        for (Bill bill : billDAO.getAllBill()) {
            if (Integer.parseInt(bill.getTime().substring(3)) == yearSelected && bill.getType() != 4) {
                totalSpending += bill.getTotal_price();
            }
        }

        // tính tổng lãi theo năm và set cho label
        totalInterset = totalEarningByYear - totalSpending;

        // Tính tổng thu của năm trước
        double lastYearEarning = 0;
        for (Bill bill : billDAO.getAllBill()) {
            if (Integer.parseInt(bill.getTime().substring(3)) == yearSelected - 1 && bill.getType() == 4) {
                lastYearEarning += bill.getTotal_price();
            }
        }

        // Tính tổng chi của năm trước
        double lastYearSpending = 0;
        for (Bill bill : billDAO.getAllBill()) {
            if (Integer.parseInt(bill.getTime().substring(3)) == yearSelected - 1 && bill.getType() != 4) {
                lastYearSpending += bill.getTotal_price();
            }
        }

        // Tính tổng lãi của năm trước và set cho label
        double lastYearInterest = lastYearEarning - lastYearSpending;
        if (lastYearInterest != 0) {
            increase = ((totalInterset - lastYearInterest) / abs(lastYearInterest)) * 100;
        } else {
            increase = 0.00;
        }
    }

    public void handleOnAction (ActionEvent event) {
        yearSelected = sceneDashboardEarning.selectYearCB.getValue();
        sceneDashboardEarning.renderBarChart(getEarningByMonth());
        calculateLabelData();
        sceneDashboardEarning.renderLabel(totalEarningByYear, avgMonthlyEarning, totalInterset, increase);
    }
}
