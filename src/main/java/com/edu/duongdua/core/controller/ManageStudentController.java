package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.model.Account;
import com.edu.duongdua.core.model.Bill;
import com.edu.duongdua.core.model.Classes;
import com.edu.duongdua.core.view.Scene_ManageStudent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ManageStudentController extends Controller implements EventHandler<Event> {
    public final Scene_ManageStudent sceneManageStudent = new Scene_ManageStudent();
    boolean edit;
    private Account student = null;

    public ManageStudentController(){
        sceneManageStudent.addActionListener(this);
        sceneManageStudent.loadStudentTable(getStudentTableData());
        sceneManageStudent.setUpComboBox(getAllClassName());
    }

    public List<String> getAllClassName(){
        List<String> classNameList = new ArrayList<>();
        for(Classes classes : classesList){
            if (classes.getClassDeleted() == 1){
                classNameList.add(classes.getClassName());
            }
        }
        return classNameList;
    }

    public ObservableList<Account> getStudentTableData(){
        final ObservableList<Account> StudentList = FXCollections.observableArrayList();
        List<Account> studentsList = accountDAO.getAllAccountByRole(4);
        for (Account student : studentsList){
            for (Classes accClass : classesList){
                if (student.getClassId() == accClass.getClassId()){
                    student.setClassName(accClass.getClassName());
                }
            }
        }
        StudentList.addAll(studentsList);
        return StudentList;
    }

    public ObservableList<Bill> getBillTableData(int studentId){
        final ObservableList<Bill> BillList = FXCollections.observableArrayList();
        List<Bill> billList = billDAO.getAllBill();
        for(Bill bill : billList){
            if(studentId == bill.getAccount_id()){
                bill.setPrice(accountDAO.findStudentById(studentId).getFee());
                BillList.add(bill);
            }
        }
        return BillList;
    }

    public void deleteStudent(Account student){
        if(student != null){
            int studentId = student.getId();
            accountDAO.deleteAccountById(studentId);
            sceneManageStudent.informationAlert("Xóa sinh viên thành công");
            sceneManageStudent.refreshStudentTable(getStudentTableData());
        } else{
            sceneManageStudent.informationAlert("Chọn một học sinh để xóa");
        }
    }
    public boolean validateStudent(Account student){
        String email =  student.getEmail();
        String password = student.getPassword();
        String name = student.getName();
        int age = student.getAge();
        String gender = student.getGender();
        String phone = student.getPhone();
        String address = student.getAddress();
        String pName = student.getPName();
        String pEmail = student.getPEmail();
        String pPhone = student.getPPhone();
        int fee = student.getFee();
        if(email.isEmpty() || password.isEmpty() || name.isEmpty() || age == 0 || gender.isEmpty() || phone.isEmpty() || address.isEmpty() || pName.isEmpty() || pPhone.isEmpty() || pEmail.isEmpty() || fee == 0){
            sceneManageStudent.informationAlert("Vui lòng nhập đầy đủ thông tin");
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
                student = sceneManageStudent.studentTable.getSelectionModel().getSelectedItem();
                if(student != null) {
                    edit = true;
                    sceneManageStudent.setUpModalStage(student);
                }else {
                    sceneManageStudent.informationAlert("Vui lòng chọn sinh viên để sửa");
                }
                break;
            case "Delete":
                deleteStudent(sceneManageStudent.studentTable.getSelectionModel().getSelectedItem());
                System.out.println("Deleteee");
                break;
            case "Add":
                edit = false;
                sceneManageStudent.createModalStage();
                break;
            case "ModalConfirm":
                if(!edit){
                    System.out.println("Added");
                    try{
                        Account tmpStudent = new Account();
                        tmpStudent.setName(sceneManageStudent.getName());
                        tmpStudent.setAge(sceneManageStudent.getAge());
                        tmpStudent.setGender(sceneManageStudent.getGender());
                        tmpStudent.setEmail(sceneManageStudent.getEmail());
                        tmpStudent.setPassword(sceneManageStudent.getPassword());
                        tmpStudent.setPhone(sceneManageStudent.getPhone());
                        tmpStudent.setAddress(sceneManageStudent.getAddress());
                        tmpStudent.setClassId(classesDao.findByName(sceneManageStudent.getClassName()).getClassId());
                        tmpStudent.setPName(sceneManageStudent.getParentName());
                        tmpStudent.setPPhone(sceneManageStudent.getParentPhone());
                        tmpStudent.setPEmail(sceneManageStudent.getParentEmail());
                        tmpStudent.setFee(sceneManageStudent.getFee());
                        tmpStudent.setStatus(sceneManageStudent.getStatus());
                        if(validateStudent(tmpStudent)){
                            accountDAO.addStudent(tmpStudent);
                            sceneManageStudent.refreshStudentTable(getStudentTableData());
                            sceneManageStudent.informationAlert("Thêm sinh viên thành công!");
                            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                            stage.close();
                        }
                    } catch (RuntimeException e) {
                        sceneManageStudent.informationAlert("Vui lòng nhập đầy đủ thông tin!");
                        throw new RuntimeException(e);
                    }
                }else {
                    System.out.println("Editted");
                    try{
                        Account tmpStudent = new Account();
                        tmpStudent.setId(sceneManageStudent.studentTable.getSelectionModel().getSelectedItem().getId());
                        tmpStudent.setName(sceneManageStudent.getName());
                        tmpStudent.setAge(sceneManageStudent.getAge());
                        tmpStudent.setGender(sceneManageStudent.getGender());
                        tmpStudent.setEmail(sceneManageStudent.getEmail());
                        tmpStudent.setPassword(sceneManageStudent.getPassword());
                        tmpStudent.setPhone(sceneManageStudent.getPhone());
                        tmpStudent.setAddress(sceneManageStudent.getAddress());
                        tmpStudent.setClassId(classesDao.findByName(sceneManageStudent.getClassName()).getClassId());
                        tmpStudent.setPName(sceneManageStudent.getParentName());
                        tmpStudent.setPPhone(sceneManageStudent.getParentPhone());
                        tmpStudent.setPEmail(sceneManageStudent.getParentEmail());
                        tmpStudent.setFee(sceneManageStudent.getFee());
                        tmpStudent.setStatus(sceneManageStudent.getStatus());
                        if(validateStudent(tmpStudent)){
                            System.out.println(tmpStudent.getStatus());
                            accountDAO.updateStudent(tmpStudent);
                            sceneManageStudent.refreshStudentTable(getStudentTableData());
                            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                            stage.close();
                            sceneManageStudent.informationAlert("Sửa sinh viên thành công!");
                        }
                    } catch (RuntimeException e) {
                        sceneManageStudent.informationAlert("Vui lòng nhập đầy đủ thông tin!");
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "StudentTable":
                if(sceneManageStudent.studentTable.getSelectionModel().getSelectedItem() != null){
                    sceneManageStudent.loadBillTable(getBillTableData(sceneManageStudent.studentTable.getSelectionModel().getSelectedItem().getId()));
                    sceneManageStudent.addActionListener(this);
                }
                break;
            case "UpdateBill":
                System.out.println(sceneManageStudent.billTable.getSelectionModel().getSelectedItem().getTime());
                Bill bill = billDAO.findBillById(sceneManageStudent.billTable.getSelectionModel().getSelectedItem().getId());
                bill.setStatus("Đã thanh toán");
                billDAO.updateBill(bill);
                sceneManageStudent.loadBillTable(getBillTableData(sceneManageStudent.studentTable.getSelectionModel().getSelectedItem().getId()));
                break;
        }
    }
}
