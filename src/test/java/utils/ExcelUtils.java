package utils;

import cases.ProjectAdd;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import constants.Constants;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import pojo.WriteBackInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Barry
 * @Date 2020/6/13
 */
public class ExcelUtils {
    public static Logger logger = Logger.getLogger(ExcelUtils.class);
    public static List<WriteBackInfo> list = new ArrayList<WriteBackInfo>();

    /**
     * 返回数组，提取的Excel数据
     * @param clazz             映射Excel的类
     * @param startSheetIndex   开始取数的sheet位置
     * @param numSheet          总共取sheet数
     * @return
     */
    public static Object[] getTestData(Class clazz, int startSheetIndex, int numSheet){
        return getTestData(Constants.EXCEL_PATH, clazz, startSheetIndex, numSheet);
    }

    /**
     *返回数组，提取的Excel数据
     * @param clazz             映射Excel的类
     */
    public static Object[] getTestData(Class clazz){
        int startSheetIndex = 0;
        int numSheet = 1;
        return getTestData(Constants.EXCEL_PATH, clazz, startSheetIndex, numSheet);
    }

    /**
     *返回数组，提取的Excel数据
     * @param filePath          存放用例的Excel路径
     * @param clazz             映射Excel的类
     * @param startSheetIndex   开始取数的sheet位置
     * @param numSheet          总共取sheet数
     * @return                  返回dataProvider数组
     */
    public static Object[] getTestData(String filePath, Class clazz, int startSheetIndex, int numSheet){
        logger.info("===========readfilepath===" + filePath);
        // 获取Excel数据返回list
        List list = ExcelUtils.excelRead(filePath, clazz,startSheetIndex,numSheet);
        // list转换成数组
        Object[] datas = list.toArray();
        return datas;
    }

    /**
     *获取表格测试数据，并返回数据集合
     * @param filePath          存放用例的Excel路径
     * @param clazz             映射Excel的类
     * @param startSheetIndex   开始取数的sheet位置
     * @param numSheet          总共取sheet数
     * @return                  返回List类型
     */
    public static List<Object> excelRead(String filePath, Class clazz, int startSheetIndex, int numSheet){
        List datas = null;
        try {
            // 新建输入流
            FileInputStream fis = new FileInputStream(filePath);
            // 导入参数，进行起始sheet，sheet个数等等设置
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(startSheetIndex);
            params.setSheetNum(numSheet);
            // importExcel(EXCEL文件流，映射关系字节码对象，导入参数)
            datas = ExcelImportUtil.importExcel(fis, clazz, params);
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到，请确认文件路径和文件是否完整！");
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * 写入Excel内容
     * @param filePath  excel路径
     */
    public static void excelWrite(String filePath){
        logger.info("==========execlWrite=======");
        logger.info("=========writefilepath===" + filePath);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(filePath);
            // Excel操作对象
            Workbook workBook = WorkbookFactory.create(fis);
            for (WriteBackInfo writeBackInfo : list) {
                // 确定sheet
                Sheet sheet = workBook.getSheetAt(writeBackInfo.getSheetIndex());
                // 确定row
                Row row = sheet.getRow(writeBackInfo.getRowNum());
                // 确定cell
                Cell cell = row.getCell(writeBackInfo.getCellNum(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                // cell写入值
                cell.setCellValue(writeBackInfo.getWriteBackInfo());
                logger.info(writeBackInfo.getWriteBackInfo());
            }
            // 输出流
            fos = new FileOutputStream(filePath);
            // 写入
            workBook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(fis != null){
                    if(fos != null){
                        fos.close();
                    }
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
