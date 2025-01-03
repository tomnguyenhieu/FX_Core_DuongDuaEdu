package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.model.Account;
import com.edu.duongdua.core.model.Bill;
import com.edu.duongdua.core.view.Scene_ManageEmployee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class ManageEmployeeController extends Controller implements EventHandler<Event> {
    public final Scene_ManageEmployee sceneManageEmployee = new Scene_ManageEmployee();
    boolean edit;
    private Account employee = null;
    LocalDate today = LocalDate.now();
    String month = today.getMonthValue() < 10 ? "0" + today.getMonthValue() : Integer.toString(today.getMonthValue());
    private String time = month + "/" + today.getYear();

    public ManageEmployeeController(){
        sceneManageEmployee.addActionListener(this);
        sceneManageEmployee.loadEmployeeTable(getEmployeeTableData());
        sceneManageEmployee.setUpComboBox();
        addBillEmployee();
    }

    public void addBillEmployee(){
        for (Account employee : accountDAO.getAllAccountByRole(3))
        {
            if(employee.getStatus().equals("Đang hoạt động")){
                Bill billObj = new Bill();
                billObj.setAccount_id(employee.getId());
                billObj.setTime(time);
                billObj.setTotal_price(employee.getSalary());
                System.out.println(time);
                billObj.setType(2);
                boolean isDuplicate = false;
                for (Bill bill : billDAO.getAllBill())
                {
                    if (bill.getAccount_id() == employee.getId() && bill.getTime().equals(time))
                    {
                        isDuplicate = true;
                    }
                }
                if (!isDuplicate || billDAO.getAllBill().isEmpty())
                {
                    billDAO.addBill(billObj);
                }
            }
        }
    }

    public ObservableList<Account> getEmployeeTableData(){
        final ObservableList<Account> EmployeeList = FXCollections.observableArrayList();
        List<Account> employeeList = accountDAO.getAllAccountByRole(3);
        EmployeeList.addAll(employeeList);
        return EmployeeList;
    }

    public ObservableList<Bill> getBillTableData(int employeeId){
        final ObservableList<Bill> BillList = FXCollections.observableArrayList();
        List<Bill> billList = billDAO.getAllBill();
        for(Bill bill : billList){
            if(employeeId == bill.getAccount_id()){
                BillList.add(bill);
            }
        }
        return BillList;
    }

    public void deleteEmployee(Account employee){
        if(employee != null){
            int employeeId = employee.getId();
            accountDAO.deleteAccountById(employeeId);
            sceneManageEmployee.informationAlert("Xóa nhân viên thành công");
            sceneManageEmployee.refreshEmployeeTable(getEmployeeTableData());
        } else{
            sceneManageEmployee.informationAlert("Chọn một nhân viên để xóa");
        }
    }
    public boolean validateEmployee(Account employee){
        String email =  employee.getEmail();
        String password = employee.getPassword();
        String name = employee.getName();
        int age = employee.getAge();
        String gender = employee.getGender();
        String phone = employee.getPhone();
        String address = employee.getAddress();
        String salary = Integer.toString(employee.getSalary());
        String certificates = employee.getCertificates();
        if(email.isEmpty() || password.isEmpty() || name.isEmpty() || age == 0 || gender.isEmpty() || phone.isEmpty() || address.isEmpty() || salary.isEmpty() || certificates.isEmpty()){
            sceneManageEmployee.informationAlert("Vui lòng nhập đầy đủ thông tin");
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void handle(Event event) {
        String id = ((Node) event.getSource()).getId();
        switch (id){
            case "Edit":
                employee = sceneManageEmployee.employeeTable.getSelectionModel().getSelectedItem();
                if(employee != null) {
                    edit = true;
                    sceneManageEmployee.setUpModalStage(employee);
                }else {
                    sceneManageEmployee.informationAlert("Vui lòng chọn nhân viên để sửa");
                }
                break;
            case "Delete":
                deleteEmployee(sceneManageEmployee.employeeTable.getSelectionModel().getSelectedItem());
                System.out.println("Deleteee");
                break;
            case "Add":
                edit = false;
                sceneManageEmployee.createModalStage();
                break;
            case "ModalConfirm":
                if(!edit){
                    System.out.println("Added");
                    try{
                        Account tmpEmployee = new Account();
                        tmpEmployee.setName(sceneManageEmployee.getName());
                        tmpEmployee.setAge(sceneManageEmployee.getAge());
                        tmpEmployee.setGender(sceneManageEmployee.getGender());
                        tmpEmployee.setEmail(sceneManageEmployee.getEmail());
                        tmpEmployee.setPassword(sceneManageEmployee.getPassword());
                        tmpEmployee.setPhone(sceneManageEmployee.getPhone());
                        tmpEmployee.setAddress(sceneManageEmployee.getAddress());
                        tmpEmployee.setSalary(Integer.parseInt(sceneManageEmployee.getSalary()));
                        tmpEmployee.setCertificates(sceneManageEmployee.getCertificates());
                        tmpEmployee.setStatus(sceneManageEmployee.getStatus());
                        if(validateEmployee(tmpEmployee)){
                            accountDAO.addEmployee(tmpEmployee);
                            sceneManageEmployee.refreshEmployeeTable(getEmployeeTableData());
                            sceneManageEmployee.informationAlert("Thêm nhân viên thành công!");
                            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                            stage.close();
                        }
                    } catch (RuntimeException e) {
                        sceneManageEmployee.informationAlert("Vui lòng nhập đầy đủ thông tin!");
                        throw new RuntimeException(e);
                    }
                }else {
                    System.out.println("Editted");
                    try{
                        Account tmpEmployee = new Account();
                        tmpEmployee.setId(sceneManageEmployee.employeeTable.getSelectionModel().getSelectedItem().getId());
                        tmpEmployee.setName(sceneManageEmployee.getName());
                        tmpEmployee.setAge(sceneManageEmployee.getAge());
                        tmpEmployee.setGender(sceneManageEmployee.getGender());
                        tmpEmployee.setEmail(sceneManageEmployee.getEmail());
                        tmpEmployee.setPassword(sceneManageEmployee.getPassword());
                        tmpEmployee.setPhone(sceneManageEmployee.getPhone());
                        tmpEmployee.setAddress(sceneManageEmployee.getAddress());
                        tmpEmployee.setSalary(Integer.parseInt(sceneManageEmployee.getSalary()));
                        tmpEmployee.setCertificates(sceneManageEmployee.getCertificates());
                        tmpEmployee.setStatus(sceneManageEmployee.getStatus());
                        if(validateEmployee(tmpEmployee)){
                            accountDAO.updateEmployee(tmpEmployee);
                            sceneManageEmployee.refreshEmployeeTable(getEmployeeTableData());
                            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                            stage.close();
                            sceneManageEmployee.informationAlert("Sửa nhân viên thành công!");
                        }
                    } catch (RuntimeException e) {
                        sceneManageEmployee.informationAlert("Vui lòng nhập đầy đủ thông tin!");
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "EmployeeTable":
                if(sceneManageEmployee.employeeTable.getSelectionModel().getSelectedItem() != null){
                    sceneManageEmployee.loadBillTable(getBillTableData(sceneManageEmployee.employeeTable.getSelectionModel().getSelectedItem().getId()));
                    sceneManageEmployee.addActionListener(this);
                }
                break;
            case "UpdateBill":
                System.out.println(sceneManageEmployee.billTable.getSelectionModel().getSelectedItem().getTime());
                Bill bill = billDAO.findBillById(sceneManageEmployee.billTable.getSelectionModel().getSelectedItem().getId());
                bill.setStatus("Đã thanh toán");
                billDAO.updateBill(bill);
                sceneManageEmployee.loadBillTable(getBillTableData(sceneManageEmployee.employeeTable.getSelectionModel().getSelectedItem().getId()));
                break;
        }
    }
}
