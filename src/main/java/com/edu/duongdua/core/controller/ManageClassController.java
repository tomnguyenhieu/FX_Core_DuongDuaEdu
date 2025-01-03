package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.model.Account;
import com.edu.duongdua.core.model.Classes;
import com.edu.duongdua.core.view.Scene_ManageClass;
import com.edu.duongdua.core.view.Scene_ManageStudent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ManageClassController extends Controller implements EventHandler<Event>
{
    private LocalDate today = LocalDate.now();
    private Integer currentDay = today.getDayOfMonth();
    private Integer currentMonth = today.getMonthValue();
    private Integer currentYear = today.getYear();
    private String time = currentDay.toString() + "/" + currentMonth.toString() + "/" + currentYear.toString();

    public Scene_ManageClass sceneManageClass = new Scene_ManageClass();
    public ManageClassController()
    {
        sceneManageClass.addEventListener(this);
        sceneManageClass.initComboBoxStatus();
        sceneManageClass.displayClass(classesDao.getClassesInfo(1), this);
        sceneManageClass.addOnActionListener(this::handleOnAction);
    }

    public List<Account> getActiveTeachers()
    {
        List<Account> accountList = new ArrayList<>();
        for (Account teacher : accountDAO.getAllAccountByRole(2))
        {
            if (teacher.getStatus().equals("Đang hoạt động"))
            {
                accountList.add(teacher);
            }
        }
        return accountList;
    }

    public List<Classes> getActiveClasses()
    {
        List<Classes> classesList = new ArrayList<>();
        for (Classes classObj : classesDao.getAllClasses())
        {
            if (classObj.getClassDeleted() == 1)
            {
                classesList.add(classObj);
            }
        }
        return classesList;
    }

    public void renderClassInfo(Event event)
    {
        VBox vBox = (VBox) event.getSource();
        HBox hBox = (HBox) vBox.getParent();
        VBox vBox1 = (VBox) hBox.getParent();
        Label classNameLabel = (Label) vBox1.getChildren().getFirst();
        Label teacherNameLabel = (Label) vBox.getChildren().getFirst();

        sceneManageClass.createClassInfoModalStage(classNameLabel.getText(), teacherNameLabel.getText().substring(11));
        renderTblStudents(classNameLabel.getText());
    }

    public void storeClass()
    {
        Classes classObj = new Classes();
        classObj.setClassName(sceneManageClass.inputClass.getText());
        classObj.setClassDeleted(1);

        if (sceneManageClass.cbTeachersName.getValue() != null)
        {
            for (Account teacher : accountDAO.getAllAccountByRole(2))
            {
                if (teacher.getName().equals(sceneManageClass.cbTeachersName.getValue()))
                {
                    classObj.setClassTeacherId(teacher.getId());
                }
            }
        } else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Vui long chon giao vien!");
            alert.show();
        }
        classesDao.storeClass(classObj);
    }

    public void deleteClass(Event event)
    {
        AnchorPane iconContainer = (AnchorPane) event.getSource();
        VBox vboxIcons = (VBox) iconContainer.getParent();
        HBox hbox = (HBox) vboxIcons.getParent();
        VBox vboxParent = (VBox) hbox.getParent();
        Label classLabel = (Label) vboxParent.getChildren().getFirst();
        String className = classLabel.getText();

        for (Classes _class : classesDao.getAllClasses())
        {
            if (_class.getClassName().equals(className))
            {
                classesDao.deleteClass(_class);
            }
        }
        sceneManageClass.getTilePane().getChildren().clear();
        sceneManageClass.displayClass(classesDao.getClassesInfo(1), this);
    }

    public boolean exportExcel()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel file", "*.xlsx")
        );
        File exportFile = fileChooser.showSaveDialog(sceneManageClass.modalStage);
        String filePath = exportFile.getPath();
        List<Classes> classes = classesDao.getAllClasses();
        try
        {
            for (Classes _class : classes)
            {
                if (sceneManageClass.cbClasses.getValue().equals(_class.getClassName()))
                {
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    XSSFSheet sheet = workbook.createSheet("Sheet1");

                    XSSFRow titleRow = sheet.createRow(0);
                    XSSFCell titleCell = titleRow.createCell(0);
                    titleCell.setCellValue("Thời gian");
                    XSSFCell titleCellValue = titleRow.createCell(1);
                    titleCellValue.setCellValue(time);

                    XSSFRow contentRow = sheet.createRow(1);
                    XSSFCell contentCell = contentRow.createCell(0);
                    contentCell.setCellValue("Nội dung bài học");

                    XSSFRow classRow = sheet.createRow(2);
                    XSSFCell classCell = classRow.createCell(0);
                    classCell.setCellValue("Lớp");
                    XSSFCell classCellValue = classRow.createCell(1);
                    classCellValue.setCellValue(_class.getClassName());

                    XSSFRow labelRow = sheet.createRow(3);
                    XSSFCell labelID = labelRow.createCell(0);
                    labelID.setCellValue("ID");
                    XSSFCell labelName = labelRow.createCell(1);
                    labelName.setCellValue("Tên");
                    XSSFCell labelComment = labelRow.createCell(2);
                    labelComment.setCellValue("Nhận xét");
                    XSSFCell labelScore = labelRow.createCell(3);
                    labelScore.setCellValue("Điểm số");

                    List<Account> studentsList = new ArrayList<>();
                    for (Account student : accountDAO.getAllAccountByRole(4))
                    {
                        if (student.getClassId() == _class.getClassId())
                        {
                            studentsList.add(student);
                        }
                    }

                    for (int i = 0; i < studentsList.size(); i++)
                    {
                        XSSFRow row = sheet.createRow(i + 4);
                        XSSFCell cell1 = row.createCell(0);
                        cell1.setCellValue(studentsList.get(i).getId());
                        XSSFCell cell2 = row.createCell(1);
                        cell2.setCellValue(studentsList.get(i).getName());
                    }
                    try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                        workbook.write(fileOut);
                        return true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void renderTblStudents(String className)
    {
        final ObservableList<Account> data = FXCollections.observableArrayList();
        List<Account> studentList = accountDAO.getAllAccountByRole(4);
        for (Account student : studentList)
        {
            if (student.getClassId() == classesDao.findByName(className).getClassId())
            {
                data.add(student);
            }
        }
        sceneManageClass.nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sceneManageClass.ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        sceneManageClass.studentTable.setItems(data);
    }

    public void handleOnAction(ActionEvent event) {
        String selectedValue = sceneManageClass.comboBox.getValue();
        int status = selectedValue.equals("Đang hoạt động") ? 1 : 2;
        sceneManageClass.getTilePane().getChildren().clear();
        sceneManageClass.displayClass(classesDao.getClassesInfo(status), this);
    }

    @Override
    public void handle(Event event)
    {
        String id = ((Node) event.getSource()).getId();
        switch (id)
        {
            case "addClassBtn":
                sceneManageClass.createModalStage(null);
                sceneManageClass.initComboBoxTeachersName(getActiveTeachers());
                break;
            case "trashBtn":
                deleteClass(event);
                break;
            case "confirmBtn":
                if (!sceneManageClass.edit)
                {
                    storeClass();
                    sceneManageClass.getTilePane().getChildren().clear();
                    sceneManageClass.displayClass(classesDao.getClassesInfo(1), this);
                    sceneManageClass.modalStage.close();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Them moi thanh cong!");
                    alert.show();
                } else
                {
                    Classes classObj = classesDao.findByName(sceneManageClass.tmpClassName);
                    classObj.setClassName(sceneManageClass.inputClass.getText());

                    for (Account teacher : accountDAO.getAllAccountByRole(2))
                    {
                        if (sceneManageClass.cbTeachersName.getValue().equals(teacher.getName()))
                        {
                            classObj.setClassTeacherId(teacher.getId());
                        }
                    }

                    classesDao.updateClass(classObj);
                    sceneManageClass.getTilePane().getChildren().clear();
                    sceneManageClass.displayClass(classesDao.getClassesInfo(1), this);
                    sceneManageClass.modalStage.close();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Sua thanh cong!");
                    alert.show();
                }
                break;
            case "classInfoBtn":
                renderClassInfo(event);
                break;
            case "exportExcelBtn":
                sceneManageClass.createExportModalStage();
                sceneManageClass.initClassesComboBox(getActiveClasses());
                break;
            case "excelConfirm":
                if (sceneManageClass.cbClasses.getValue() != null)
                {
                    if (exportExcel())
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Export thành công");
                        sceneManageClass.modalStage.close();
                        alert.show();
                    } else
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Export thất bại");
                        sceneManageClass.modalStage.close();
                        alert.show();
                    }
                }
                break;
        }
    }
}
