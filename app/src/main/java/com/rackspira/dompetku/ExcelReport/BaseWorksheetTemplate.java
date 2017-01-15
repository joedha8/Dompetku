package com.rackspira.dompetku.ExcelReport;

import com.rackspira.dompetku.App;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.util.Map;

/**
 * Created by kristiawan on 13/01/17.
 */

public abstract class BaseWorksheetTemplate {

    private HSSFWorkbook workbook;

    protected abstract void populateContentData(Sheet sheet, HSSFWorkbook workbook, Map<String, Object> map);

    public BaseWorksheetTemplate(Map<String, Object> map) {
        workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        populateContentData(sheet, workbook, map);
    }

    public HSSFWorkbook getWorkbook(){
        return workbook;
    }

    protected CellStyle cellStyleHeader() {
        CellStyle cellStyleHeader = workbook.createCellStyle();
        cellStyleHeader.setFont(createdFontBold());
        cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
        return cellStyleHeader;
    }

    protected CellStyle cellStyleTitle(short alignment) {
        CellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setWrapText(true);
        cellStyleTitle.setAlignment(alignment);
        cellStyleTitle.setFont(createdFontBold());
        return cellStyleTitle;
    }

    protected CellStyle cellStyleVerticalCenter() {
        CellStyle cellStyleVerticalCenter = workbook.createCellStyle();
        cellStyleVerticalCenter.setWrapText(true);
        cellStyleVerticalCenter.setAlignment(HorizontalAlignment.CENTER);
        cellStyleVerticalCenter.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleVerticalCenter.setFont(createdFontBold());
//        cellStyleVerticalCenter.setShrinkToFit(true);
        return cellStyleVerticalCenter;
    }

    protected CellStyle cellStyleBold(HorizontalAlignment alignment) {
        CellStyle cellStyleCenter = workbook.createCellStyle();
        cellStyleCenter.setFont(createdFontBold());
        cellStyleCenter.setAlignment(alignment);
        return cellStyleCenter;
    }

    protected CellStyle cellStyleCenter() {
        CellStyle cellStyleCenter = workbook.createCellStyle();
        cellStyleCenter.setFont(createdFontNormal());
        cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
        return cellStyleCenter;
    }

    protected CellStyle cellStyleLeft() {
        CellStyle cellStyleCenter = workbook.createCellStyle();
        cellStyleCenter.setFont(createdFontNormal());
        cellStyleCenter.setAlignment(HorizontalAlignment.LEFT);
        return cellStyleCenter;
    }

    protected CellStyle cellStyleRight() {
        CellStyle cellStyleCenter = workbook.createCellStyle();
        cellStyleCenter.setFont(createdFontNormal());
        cellStyleCenter.setAlignment(HorizontalAlignment.RIGHT);
        return cellStyleCenter;
    }

    protected CellStyle cellStyleFormatDateLong() {
        CellStyle cellStyleDate = workbook.createCellStyle();
        cellStyleDate.setDataFormat(workbook.createDataFormat().getFormat(App.DATE_TIME_PATTERN));
        cellStyleDate.setAlignment(HorizontalAlignment.CENTER);
        return cellStyleDate;
    }

    protected CellStyle cellStyleFormatCurrency() {
        CellStyle cellStyleFormatCurrency = workbook.createCellStyle();
        cellStyleFormatCurrency.setDataFormat(workbook.createDataFormat().getFormat(App.CURRENCY_FORMAT));
        return cellStyleFormatCurrency;
    }

    protected CellStyle cellStyleFormatCurrencyBold() {
        CellStyle cellStyleFormatCurrency = workbook.createCellStyle();
        cellStyleFormatCurrency.setFont(createdFontBold());
        cellStyleFormatCurrency.setDataFormat(workbook.createDataFormat().getFormat(App.CURRENCY_FORMAT));
        return cellStyleFormatCurrency;
    }

    protected CellStyle cellStyleFormatDateShort() {
        CellStyle cellStyleFormatDateShort = workbook.createCellStyle();
        cellStyleFormatDateShort.setDataFormat(workbook.createDataFormat().getFormat(App.SHORT_DATE_FORMAT));
        cellStyleFormatDateShort.setAlignment(HorizontalAlignment.CENTER);
        return cellStyleFormatDateShort;
    }

    protected Font createdFontBold() {
        Font tahomaBold = workbook.createFont();
        tahomaBold.setFontName(App.DEFAULT_FONT);
        tahomaBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
        return tahomaBold;
    }

    protected Font createdFontNormal() {
        Font tahoma = workbook.createFont();
        tahoma.setFontName(App.DEFAULT_FONT);
        return tahoma;
    }
}
