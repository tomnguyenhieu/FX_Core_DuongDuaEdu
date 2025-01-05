package com.edu.duongdua.core.controller;

import com.edu.duongdua.core.model.Account;
import com.edu.duongdua.core.model.Bill;
import com.edu.duongdua.core.model.Comment;
import com.edu.duongdua.core.model.Lesson;
import com.edu.duongdua.core.view.Scene_ManageClass;
import com.edu.duongdua.core.view.Scene_ManageDiary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManageDiaryController extends Controller implements EventHandler<Event>
{
    private int count = 0;
    private int btnLessonId = 0;
    private Lesson lesson = new Lesson();
    private File file;

    Scene_ManageDiary sceneManageDiary = new Scene_ManageDiary();

    public ManageDiaryController()
    {
        sceneManageDiary.addEventListener(this);
        getLessonCount();
        sceneManageDiary.displayLessons(count, this);
    }

    public void getLessonCount()
    {
        count = 0;
        for (int i = 0; i < lessonDAO.getAllLessons().size(); i++)
        {
            if (lessonDAO.getAllLessons().get(i).getClassId() == classesDao.findByName(Controller.getInstance().getClassName()).getClassId())
            {
                count++;
            }
        }
    }

    public int getLessonBtnId(Event event)
    {
        Button btn = (Button)event.getSource();
        String text = btn.getText();
        btnLessonId = Integer.parseInt(text.substring(7));

        return btnLessonId;
    }

    public ObservableList<Lesson> getSelectedLesson(int btnLessonId)
    {
        final ObservableList<Lesson> lessonObservableList = FXCollections.observableArrayList();
        List<Lesson> lessonList = lessonDAO.getLessonsByClassId(classesDao.findByName(Controller.getInstance().getClassName()));
        for (int i = 0; i < lessonList.size(); i++)
        {
            if (i == (btnLessonId-1))
            {
                lesson.setId(lessonList.get(i).getId());
                lesson.setClassName(classesDao.findByID(lessonList.get(i).getClassId()).getClassName());
                lesson.setTitle(lessonList.get(i).getTitle());
                lesson.setContent(lessonList.get(i).getContent());
                lesson.setTeacherName(accountDAO.findTeacherById(classesDao.findByID(lessonList.get(i).getClassId()).getClassTeacherId()).getName());

                lessonObservableList.add(lesson);
            }
        }
        return lessonObservableList;
    }

    public ObservableList<Comment> getSelectedLessonComment(int btnLessonId)
    {
        final ObservableList<Comment> commentObservableList = FXCollections.observableArrayList();
        for (int i = 0; i < getSelectedLesson(btnLessonId).size(); i++)
        {
            for (Comment comment : commentDAO.getLessonComments(getSelectedLesson(btnLessonId).getFirst().getId()))
            {
                commentObservableList.addAll(comment);
            }
        }
        return commentObservableList;
    }

    public boolean isDuplicateLesson()
    {
        lesson = readExcelInfo(file);
        for (Lesson lessonObj : lessonDAO.getAllLessons())
        {
            if (lessonObj.getTitle().equals(lesson.getTitle()) && lessonObj.getClassId() == classesDao.findByName(lesson.getClassName()).getClassId())
            {
                return true;
            }
        }
        return false;
    }

    public void addTeacherBill(String className)
    {
        Account teacher = accountDAO.getTeacherData(className);
        Bill billObj = new Bill();
        billObj.setAccount_id(teacher.getId());
        billObj.setTime(teacher.getTime());
        billObj.setTotal_price(teacher.getSalary() * teacher.getLessonCount());
        billObj.setType(1);
        boolean isDuplicate = false;
        for (Bill bill : billDAO.getAllBill())
        {
            if (bill.getAccount_id() == teacher.getId() && bill.getTime().equals(teacher.getTime()))
            {
                isDuplicate = true;
                bill.setTotal_price(teacher.getSalary() * teacher.getLessonCount());
                billDAO.updateBill(bill);
            }
        }
        if (!isDuplicate || billDAO.getAllBill().isEmpty())
        {
            billDAO.addBill(billObj);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Thêm bill thành công!");
            alert.show();
        }
    }

    // Thêm mới studentBill vào trong database
    public void addStudentBill(File file)
    {
        Lesson lessonObj = readExcelInfo(file);
        for (Comment comment : readExcelComment(file))
        {
            for (Account student : accountDAO.getAllAccountByRole(4))
            {
                if (comment.getStudentId() == student.getId())
                {
                    Bill billObj = new Bill();
                    billObj.setAccount_id(student.getId());
                    billObj.setTime(lessonObj.getTitle().substring(3));
                    billObj.setType(4);
                    boolean isDuplicate = false;
                    for (Bill bill : billDAO.getAllBill())
                    {
                        if (bill.getAccount_id() == student.getId() && bill.getTime().equals(lessonObj.getTitle().substring(3)))
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
    }

    public Lesson readExcelInfo(File file)
    {
        Lesson lesson = new Lesson();
        try
        {
            FileInputStream fs = new FileInputStream(file.getPath());
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFRow titleRow = sheet.getRow(0);
            XSSFRow contentRow = sheet.getRow(1);
            XSSFRow classRow = sheet.getRow(2);

            XSSFCell titleCell = titleRow.getCell(1);
            XSSFCell contentCell = contentRow.getCell(1);
            XSSFCell classCell = classRow.getCell(1);

            String titleValue = titleCell.getStringCellValue();
            String contentValue = contentCell.getStringCellValue();
            String classValue = classCell.getStringCellValue();

            lesson.setTitle(titleValue);
            lesson.setContent(contentValue);
            lesson.setClassId(classesDao.findByName(classValue).getClassId());
            lesson.setClassName(classValue);

            fs.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lesson;
    }

    public List<Comment> readExcelComment(File file)
    {
        List<Comment> commentList = new ArrayList<>();
        try {
            FileInputStream fs = new FileInputStream(file.getPath());
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getLastRowNum();
            for (int i = 4; i <= rows; i++)
            {
                Comment comment = new Comment();
                XSSFRow studentCommentRow = sheet.getRow(i);
                XSSFCell studentIdCell = studentCommentRow.getCell(0);
                XSSFCell studentCommentCell = studentCommentRow.getCell(2);
                XSSFCell studentScoreCell = studentCommentRow.getCell(3);

                int studentIdValue = (int)studentIdCell.getNumericCellValue();
                String studentCommentValue = studentCommentCell.getStringCellValue();
                int studentScoreValue = (int)studentScoreCell.getNumericCellValue();

                comment.setStudentId(studentIdValue);
                comment.setComment(studentCommentValue);
                comment.setScore(studentScoreValue);

                commentList.add(comment);
            }
            fs.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return commentList;
    }

    public void getUploadExcel()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn excel nhật kí để tải lên!");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel file", "*.xlsx")
        );
        file = fileChooser.showOpenDialog(sceneManageDiary.modalStage);
    }

    public void onActionExportExcel()
    {
        if (lesson.getId() != 0)
        {
            List<Comment> commentList = commentDAO.getLessonComments(lesson.getId());
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel file", "*.xlsx")
            );
            File saveFile = fileChooser.showSaveDialog(sceneManageDiary.modalStage);
            try {
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Sheet1");
                XSSFRow titleRow = sheet.createRow(0);
                XSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("Thời gian");
                XSSFCell titleCellValue = titleRow.createCell(1);
                titleCellValue.setCellValue(lesson.getTitle());
                XSSFRow contentRow = sheet.createRow(1);
                XSSFCell contentCell = contentRow.createCell(0);
                contentCell.setCellValue("Nội dung bài học");
                XSSFCell contentCellValue = contentRow.createCell(1);
                contentCellValue.setCellValue(lesson.getContent());
                XSSFRow classRow = sheet.createRow(2);
                XSSFCell classCell = classRow.createCell(0);
                classCell.setCellValue("Lớp");
                XSSFCell classCellValue = classRow.createCell(1);
                classCellValue.setCellValue(lesson.getClassName());
                XSSFRow labelRow = sheet.createRow(3);
                XSSFCell labelID = labelRow.createCell(0);
                labelID.setCellValue("ID");
                XSSFCell labelName = labelRow.createCell(1);
                labelName.setCellValue("Tên");
                XSSFCell labelComment = labelRow.createCell(2);
                labelComment.setCellValue("Nhận xét");
                XSSFCell labelScore = labelRow.createCell(3);
                labelScore.setCellValue("Điểm số");
                for (int i = 0; i < commentList.size(); i++)
                {
                    XSSFRow row = sheet.createRow(i + 4);
                    XSSFCell idCell = row.createCell(0);
                    idCell.setCellValue(commentList.get(i).getStudentId());
                    XSSFCell nameCell = row.createCell(1);
                    nameCell.setCellValue(commentList.get(i).getStudentName());
                    XSSFCell commentCell = row.createCell(2);
                    commentCell.setCellValue(commentList.get(i).getComment());
                    XSSFCell scoreCell = row.createCell(3);
                    scoreCell.setCellValue(commentList.get(i).getScore());
                }
                try (FileOutputStream fileOut = new FileOutputStream(saveFile.getPath())) {
                    workbook.write(fileOut);
                    fileOut.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Export nhật kí thành công!");
                    alert.show();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Export thất bại!");
                alert.show();
                throw new RuntimeException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Vui lòng chọn lesson để export nhật kí!");
            alert.show();
        }
    }

    public void storeDiary(File file, Lesson lesson)
    {
        // Lưu vào db -> set id, className, teacherName cho lesson để có data -> render lên tableview
        lessonDAO.storeLesson(lesson);
        lesson.setId(lessonDAO.findByTitle(lesson.getTitle()).getId());
        lesson.setClassName(lessonDAO.getLessonInfoById(lesson.getId()).getClassName());
        lesson.setTeacherName(lessonDAO.getLessonInfoById(lesson.getId()).getTeacherName());

        // Đọc comments trong nhật kí -> trả về list comments -> lưu vào db
        for (Comment comment : readExcelComment(file))
        {
            comment.setLessonId(lesson.getId());
            commentDAO.storeComment(comment);
        }
        count++;
    }

    @Override
    public void handle(Event event) {
        String id = ((Node) event.getSource()).getId();
        switch (id)
        {
            case "lessonBtn":
                sceneManageDiary.resetAllBtn(count);
                sceneManageDiary.setActive((Button) event.getSource());
                sceneManageDiary.tblComment1.getItems().clear();
                sceneManageDiary.tblComment2.getItems().clear();
                sceneManageDiary.loadTable1Comment(getSelectedLesson(getLessonBtnId(event)));
                sceneManageDiary.loadTable2Comment(getSelectedLessonComment(getLessonBtnId(event)));
                break;
            case "exportBtn":
                onActionExportExcel();
                break;
            case "uploadBtn":
                getUploadExcel();
                if (!isDuplicateLesson())
                {
                    sceneManageDiary.createConfirmModal(file);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("File bị trùng!");
                    alert.show();
                }
                break;
            case "confirmBtn":
                storeDiary(file, lesson);
                sceneManageDiary.lessonsContainer.getChildren().clear();
                sceneManageDiary.displayLessons(count, this);
                sceneManageDiary.resetAllBtn(count);
                sceneManageDiary.tblComment1.getItems().clear();
                sceneManageDiary.tblComment2.getItems().clear();
                addTeacherBill(Controller.getInstance().getClassName());
                addStudentBill(file);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Tải lên thành công!");
                alert.show();
                sceneManageDiary.modalStage.close();
                break;
        }
    }
}
