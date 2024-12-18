package org.tbot.calc.fileProcessor;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WordFileProcessorImpl {

    /**
     * Создание стрима
     * @param taskList список заданий
     */
    public FileInputStream createWordFile(List<String> taskList) throws IOException {
        // Создание документа на основ шаблона - файла .docx из папки resources
        XWPFDocument document = new XWPFDocument(getClass().getClassLoader().getResourceAsStream("Template.docx"));
        setTaskListToXWPFDocument(document, taskList);
        return createTempFile (doc);
    }

    /**
    * Вставка списка заданий в документ
     * @param document объект докуумента Word
     * @param taskList список заданий
     */
    private void setTaskListToXWPFDocument(XWPFDocument document, List<String> taskList) {
        //Запись первой строки списка заданий в первый абзац документа (создается по умолчанию при создании документа)
        XWPFParagraph paragraph = document.getLastParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(taskList.get(0));
        for (int i = 1; i < taskList.size(); i++) {
            paragraph =document.createParagraph();
            run = paragraph.createRun();
            setRunParameters(run, taskList.get(i));
        }
    }

    /**
     * Запись параметров шрифта и текста для 1го абзаца
     * @param run объект записи для абзаца Word-документа
     * @param task содержимое 1го абзаца (одно задание)
     */

    private void setRunParameters(XWPFRun run, String task) {
        run.setFontSize(20); //размер шрифта
        run.setFontFamily("Calibri"); // тип шрифта
        run.setText(task);
    }

    /**
     * Формирование стрима из объекта документа Word
     * @param document объект Word-документа
     */
    private FileInputStream createTempFile (XWPFDocument document) throws IOException {
        File resultFile = File.createTempFile("Print_this_tasks", ".docx");
        try (FileOutputStream out = new FileOutputStream(resultFile)) {
            doc.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new FileInputStream(resultFile);
    }

}
