package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.model.Account;
import com.edu.duongdua.core.model.Bill;
import com.edu.duongdua.core.model.Classes;
import com.edu.duongdua.core.view.Scene_ManageStudent;
import com.edu.duongdua.core.view.Scene_ManageTeacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.List;

public class ManageTeacherController extends Controller implements EventHandler<Event> {
    public final Scene_ManageTeacher sceneManageTeacher = new Scene_ManageTeacher();
    boolean edit;
    private Account teacher = null;
    public ManageTeacherController(){
        sceneManageTeacher.addActionListener(this);
        sceneManageTeacher.loadTeacherTable(getTeacherTableData());
        sceneManageTeacher.setUpComboBox();
    }

    public ObservableList<Account> getTeacherTableData(){
        final ObservableList<Account> TeacherList = FXCollections.observableArrayList();
        List<Account> teachersList = accountDAO.getAllAccountByRole(2);
        TeacherList.addAll(teachersList);
        return TeacherList;
    }

    public ObservableList<Bill> getBillTableData(int teacherId){
        final ObservableList<Bill> BillList = FXCollections.observableArrayList();
        List<Bill> billList = billDAO.getTeacherBillDataById(teacherId);
        for(Bill bill : billList){
            BillList.add(bill);
        }
        return BillList;
    }

    public void deleteTeacher(Account teacher){
        if(teacher != null){
            int teacherId = teacher.getId();
            accountDAO.deleteAccountById(teacherId);
            sceneManageTeacher.informationAlert("Xóa giáo viên thành công");
            sceneManageTeacher.refreshTeacherTable(getTeacherTableData());
        } else {
            sceneManageTeacher.informationAlert("Chọn một giáo viên để xóa");
        }
    }

    public boolean validateTeacher(Account teacher){
        String email =  teacher.getEmail();
        String password = teacher.getPassword();
        String name = teacher.getName();
        int age = teacher.getAge();
        String gender = teacher.getGender();
        String phone = teacher.getPhone();
        String address = teacher.getAddress();
        String certificates = teacher.getCertificates();
        int salary = teacher.getSalary();
        if(email.isEmpty() || password.isEmpty() || name.isEmpty() || age == 0 || gender.isEmpty() || phone.isEmpty() || address.isEmpty() || certificates.isEmpty() || salary == 0){
            sceneManageTeacher.informationAlert("Vui lòng nhập đầy đủ thông tin");
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void handle(Event event) {
        String id = ((Node) event.getSource()).getId();
        switch (id) {
            case "Delete":
                deleteTeacher(sceneManageTeacher.teacherTable.getSelectionModel().getSelectedItem());
                break;
            case "TeacherTable":
                // Chọn vào teacher thì hiển thị bảng danh sách lương của teacher đó
                if(sceneManageTeacher.teacherTable.getSelectionModel().getSelectedItem() != null){
                    sceneManageTeacher.loadBillTable(getBillTableData(sceneManageTeacher.teacherTable.getSelectionModel().getSelectedItem().getId()));
                    sceneManageTeacher.addActionListener(this);
                }
                break;
            case "UpdateBill":
                Bill bill = billDAO.findBillById(sceneManageTeacher.billTable.getSelectionModel().getSelectedItem().getId());
                bill.setStatus("Đã thanh toán");
                billDAO.updateBill(bill);
                sceneManageTeacher.loadBillTable(getBillTableData(sceneManageTeacher.teacherTable.getSelectionModel().getSelectedItem().getId()));
                break;
            case "Edit":
                teacher = sceneManageTeacher.teacherTable.getSelectionModel().getSelectedItem();
                if(teacher != null) {
                    edit = true;
                    sceneManageTeacher.setUpModalStage(teacher);
                }else {
                    sceneManageTeacher.informationAlert("Vui lòng chọn giáo viên để sửa");
                }
                break;
            case "Add":
                edit = false;
                sceneManageTeacher.createModalStage();
                break;
            case "ModalConfirm":
                if(!edit){
                    try{
                        Account tmpTeacher = new Account();
                        tmpTeacher.setName(sceneManageTeacher.getName());
                        tmpTeacher.setAge(sceneManageTeacher.getAge());
                        tmpTeacher.setGender(sceneManageTeacher.getGender());
                        tmpTeacher.setEmail(sceneManageTeacher.getEmail());
                        tmpTeacher.setPassword(sceneManageTeacher.getPassword());
                        tmpTeacher.setPhone(sceneManageTeacher.getPhone());
                        tmpTeacher.setAddress(sceneManageTeacher.getAddress());
                        tmpTeacher.setCertificates(sceneManageTeacher.getCertificates());
                        tmpTeacher.setSalary(Integer.parseInt(sceneManageTeacher.getSalary()));
                        tmpTeacher.setStatus(sceneManageTeacher.getStatus());
                        if(validateTeacher(tmpTeacher)){
                            accountDAO.addTeacher(tmpTeacher);
                            sceneManageTeacher.refreshTeacherTable(getTeacherTableData());
                            sceneManageTeacher.informationAlert("Thêm giáo viên thành công!");
                            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                            stage.close();
                        }
                    } catch (RuntimeException e) {
                        sceneManageTeacher.informationAlert("Vui lòng nhập đầy đủ thông tin!");
                        throw new RuntimeException(e);
                    }
                }else {
                    try{
                        Account tmpTeacher = new Account();
                        tmpTeacher.setId(sceneManageTeacher.teacherTable.getSelectionModel().getSelectedItem().getId());
                        tmpTeacher.setName(sceneManageTeacher.getName());
                        tmpTeacher.setAge(sceneManageTeacher.getAge());
                        tmpTeacher.setGender(sceneManageTeacher.getGender());
                        tmpTeacher.setEmail(sceneManageTeacher.getEmail());
                        tmpTeacher.setPassword(sceneManageTeacher.getPassword());
                        tmpTeacher.setPhone(sceneManageTeacher.getPhone());
                        tmpTeacher.setAddress(sceneManageTeacher.getAddress());
                        tmpTeacher.setCertificates(sceneManageTeacher.getCertificates());
                        tmpTeacher.setSalary(Integer.parseInt(sceneManageTeacher.getSalary()));
                        tmpTeacher.setStatus(sceneManageTeacher.getStatus());
                        if(validateTeacher(tmpTeacher)){
                            accountDAO.updateTeacher(tmpTeacher);
                            sceneManageTeacher.refreshTeacherTable(getTeacherTableData());
                            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                            stage.close();
                            sceneManageTeacher.informationAlert("Sửa giáo viên thành công!");
                        }
                    } catch (RuntimeException e) {
                        sceneManageTeacher.informationAlert("Vui lòng nhập đầy đủ thông tin!");
                        throw new RuntimeException(e);
                    }
                }
                break;
        }
    }
}
