package cases;

import constants.Constants;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.CaseInfo;
import pojo.WriteBackInfo;
import utils.Authentication;
import utils.ExcelUtils;
import utils.HttpUtils;

public class ConfigList extends Base{
    public static Logger logger = Logger.getLogger(Login.class);

    @Test(dataProvider = "configListData")
    public void testConfigList(CaseInfo ci){
        // 判断本次是否执行
        logger.info(ci);
        if("Y".equalsIgnoreCase(ci.getIfExecute())){
            //1、参数化
            parameterization(ci);
            //添加token
//            Object token = Authentication.VARS.get("${token}");
//            logger.info("========token===" + token);
//            Constants.HEADERS.put("Authorization", "JWT " + token);
            //请求
            HttpResponse response = HttpUtils.request(ci.getType(), ci.getUrl(), Constants.HEADERS, ci.getParams(), ci.getContentType());
            String body = HttpUtils.getResponseBody(response);
            // 非数据库断言
            logger.info("非数据库断言");
            String executeResult = getExecuteFlag(body, ci.getExpectResult());
            // 回写结果
            ExcelUtils.list.add(new WriteBackInfo(sheetIndex, ci.getCaseId(), Constants.WRITE_BACK_CELL_NUM_BODY, body));
            ExcelUtils.list.add(new WriteBackInfo(sheetIndex, ci.getCaseId(), Constants.WRITE_BACK_CELL_NUM_EXECUTE_RESULT, executeResult));
            Assert.assertEquals(executeResult, "pass");
        }
    }

    @DataProvider(name = "configListData")
    public Object[] configListData(){
        return ExcelUtils.getTestData(CaseInfo.class, sheetIndex,1);
    }
}
