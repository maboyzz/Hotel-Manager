//package DAO;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//public class ExportToExcel {
//    public static void exportToExcel(List<String[]> data, String filePath) {
//        // Tạo workbook mới
//        try (Workbook workbook = new XSSFWorkbook()) {
//            // Tạo sheet
//            Sheet sheet = workbook.createSheet("Data");
//
//            // Tạo header row
//            String[] headers = {"Column1", "Column2", "Column3"}; // Thay đổi header theo nhu cầu
//            Row headerRow = sheet.createRow(0);
//            for (int i = 0; i < headers.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(headers[i]);
//            }
//
//            // Đổ dữ liệu từ ArrayList vào sheet
//            for (int i = 0; i < data.size(); i++) {
//                Row row = sheet.createRow(i + 1);
//                String[] rowData = data.get(i);
//                for (int j = 0; j < rowData.length; j++) {
//                    Cell cell = row.createCell(j);
//                    cell.setCellValue(rowData[j]);
//                }
//            }
//
//            // Tự động điều chỉnh kích thước cột
//            for (int i = 0; i < headers.length; i++) {
//                sheet.autoSizeColumn(i);
//            }
//
//            // Ghi workbook ra file
//            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
//                workbook.write(fileOut);
//            }
//            System.out.println("File Excel đã được tạo thành công tại: " + filePath);
//
//        } catch (IOException e) {
//            System.out.println("Lỗi khi tạo file Excel: " + e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        // Dữ liệu mẫu
//        List<String[]> data = new ArrayList<>();
//        data.add(new String[]{"Dữ liệu 1", "Dữ liệu 2", "Dữ liệu 3"});
//        data.add(new String[]{"Dữ liệu 4", "Dữ liệu 5", "Dữ liệu 6"});
//        data.add(new String[]{"Dữ liệu 7", "Dữ liệu 8", "Dữ liệu 9"});
//
//        // Đường dẫn file Excel đầu ra
//        String filePath = "output.xlsx";
//
//        // Gọi hàm xuất Excel
//        exportToExcel(data, filePath);
//    }
//}