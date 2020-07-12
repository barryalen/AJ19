package cases;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import constants.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import pojo.CaseInfo;
import utils.Authentication;
import utils.ExcelUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Base {
    public static Logger logger = Logger.getLogger(Base.class);

    public int sheetIndex;

    @BeforeSuite
    public void init(){
        Constants.HEADERS.put("X-Lemonban-Media-Type", "lemonban.v2");
        Constants.HEADERS.put("Content-Type", "application/json");
        read();
    }
    @AfterSuite
    public void finish(){
        ExcelUtils.excelWrite(Constants.EXCEL_PATH);
    }

    @BeforeClass
    @Parameters({"sheetIndex"})
    public void beforeClass(int sheetIndex){
        this.sheetIndex = sheetIndex;
    }

    /**
     * 判断实际结果与预期结果，返回结果
     * @param body              响应返回的body json字符串
     * @param expectJson  期望结果json字符串
     * @return
     */
    public String getExecuteFlag(String body, String expectJson){
        // 获取期望结果，封装map
        Map<String, Object> map = JSONObject.parseObject(expectJson, Map.class);
        Set<String> jsonPath = map.keySet();
        boolean executeFlag = true;
        for (String jsp : jsonPath) {
            Object expectResult = map.get(jsp);
            Object actualResult = JSONPath.read(body, jsp);
            logger.info("=======expectResult===" + expectResult);
            logger.info("=======actualResult===" + actualResult);
            if(actualResult == null && expectResult != null){
                executeFlag = false;
                break;
            }
            if(expectResult == null && actualResult != null){
                executeFlag = false;
                break;
            }
            if(!actualResult.toString().contains(expectResult.toString())){
                executeFlag = false;
                break;
            }
        }
        return executeFlag  ?  "pass" : "failed";
    }

    /**
     * 处理参数化，当case中需要用到参数化时
     * @param ci    映射case表格类
     */
    public static void parameterization(CaseInfo ci){
        logger.info("============参数化处理开始==============");
        // 从键值对存储仓库中获取可能存在的参数化键值对
        Set<String> sets = Authentication.VARS.keySet();
        for (String key : sets) {
            String value = Authentication.VARS.get(key).toString();
            // 处理参数中的参数化
            if(StringUtils.isNoneBlank(ci.getParams())){
                ci.setParams(ci.getParams().replace(key, value));
            }
            // 处理预期结果中的参数化
            if(StringUtils.isNoneBlank(ci.getExpectResult())){
                ci.setExpectResult(ci.getExpectResult().replace(key, value));
            }
            // 处理sql中的参数化
            if(StringUtils.isNoneBlank(ci.getSql())){
                ci.setSql(ci.getSql().replace(key, value));
            }
        }
        logger.info("============参数化处理结束==============");
    }

    /**
     * 读取Properties文件参数
     */
    public static void read(){
        String path = Constants.PARAMS_PATH;
        logger.info("=======Base=====" + path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            Properties prop = new Properties();
            prop.load(fis);
            Authentication.VARS.putAll((Map) prop);
            logger.info("=======Base=====" + Authentication.VARS);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
