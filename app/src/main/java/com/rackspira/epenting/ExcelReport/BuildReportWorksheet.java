package com.rackspira.epenting.ExcelReport;

import com.rackspira.epenting.database.DataMasuk;
import com.rackspira.epenting.database.Hutang;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.Map;

/**
 * Created by kristiawan on 13/01/17.
 */

public class BuildReportWorksheet extends BaseWorksheetTemplate {

    public BuildReportWorksheet(Map<String, Object> map) {
        super(map);
    }

    @Override
    protected void populateContentData(Sheet sheet, HSSFWorkbook workbook, Map<String, Object> map) {
        List<DataMasuk> pemasukkanList = (List<DataMasuk>) map.get("pemasukkan");
        List<DataMasuk> pengeluaranList = (List<DataMasuk>) map.get("pengeluaran");
        List<Hutang> hutangList = (List<Hutang>) map.get("hutang");

        Row header = sheet.createRow(1);
        Cell cellHeader = header.createCell(0);
        cellHeader.setCellValue("Report Data Menejemen Keuangan dengan Aplikasi E-PenTing");
        cellHeader.setCellStyle(cellStyleHeader());
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 10));

        header = sheet.createRow(3);
        cellHeader = header.createCell(0);
        cellHeader.setCellValue("PEMASUKKAN");
        cellHeader.setCellStyle(cellStyleCenter());
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 2));

        header = sheet.createRow(4);
        cellHeader = header.createCell(0);
        cellHeader.setCellValue("No");
        cellHeader.setCellStyle(cellStyleCenter());

        cellHeader = header.createCell(1);
        cellHeader.setCellValue("Keterangan");
        cellHeader.setCellStyle(cellStyleLeft());

        cellHeader = header.createCell(2);
        cellHeader.setCellValue("Biaya (Rp)");
        cellHeader.setCellStyle(cellStyleRight());

        cellHeader = header.createCell(3);
        cellHeader.setCellValue("Tanggal");
        cellHeader.setCellStyle(cellStyleRight());

        int index = 5;
        long jumlah = 0;
        if (pemasukkanList.size() != 0) {
            int no = 1;
            for (DataMasuk dataMasuk : pemasukkanList) {
                header = sheet.createRow(index);
                cellHeader = header.createCell(0);
                cellHeader.setCellValue(no);

                cellHeader = header.createCell(1);
                cellHeader.setCellValue(dataMasuk.getKet());

                cellHeader = header.createCell(2);
                cellHeader.setCellValue(dataMasuk.getBiaya());
                cellHeader.setCellStyle(cellStyleFormatCurrency());

                cellHeader = header.createCell(3);
                cellHeader.setCellValue(dataMasuk.getTanggal());
                cellHeader.setCellStyle(cellStyleFormatCurrency());

                jumlah = jumlah + Long.valueOf(dataMasuk.getBiaya());
                index++;
                no++;
            }

            header = sheet.createRow(index);
            cellHeader = header.createCell(1);
            cellHeader.setCellValue("Total");
            cellHeader.setCellStyle(cellStyleBold(HorizontalAlignment.LEFT));

            cellHeader = header.createCell(2);
            cellHeader.setCellValue(jumlah);
            cellHeader.setCellStyle(cellStyleFormatCurrencyBold());

            index += 3;
        }


        header = sheet.createRow(index);
        cellHeader = header.createCell(0);
        cellHeader.setCellValue("PENGELUARAN");
        cellHeader.setCellStyle(cellStyleCenter());
        sheet.addMergedRegion(new CellRangeAddress(index, index, 0, 2));

        index++;
        jumlah = 0;
        if (pemasukkanList.size() != 0) {
            int no = 1;
            for (DataMasuk dataMasuk : pengeluaranList) {
                header = sheet.createRow(index);
                cellHeader = header.createCell(0);
                cellHeader.setCellValue(no);

                cellHeader = header.createCell(1);
                cellHeader.setCellValue(dataMasuk.getKet());

                cellHeader = header.createCell(2);
                cellHeader.setCellValue(dataMasuk.getBiaya());
                cellHeader.setCellStyle(cellStyleFormatCurrency());

                cellHeader = header.createCell(3);
                cellHeader.setCellValue(dataMasuk.getTanggal());
                cellHeader.setCellStyle(cellStyleFormatCurrency());

                jumlah = jumlah + Long.valueOf(dataMasuk.getBiaya());
                index++;
                no++;
            }

            header = sheet.createRow(index);
            cellHeader = header.createCell(1);
            cellHeader.setCellValue("Total");
            cellHeader.setCellStyle(cellStyleBold(HorizontalAlignment.LEFT));

            cellHeader = header.createCell(2);
            cellHeader.setCellValue(jumlah);
            cellHeader.setCellStyle(cellStyleFormatCurrencyBold());
        }
    }
}
