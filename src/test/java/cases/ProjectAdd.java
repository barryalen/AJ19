package cases;

import constants.Constants;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.CaseInfo;
import pojo.WriteBackInfo;
import utils.Authentication;
import utils.ExcelUtils;
import utils.HttpUtils;

import java.util.Set;

public class ProjectAdd extends Base {
    public static Logger logger = Logger.getLogger(ProjectAdd.class);

    @Test(dataProvider = "projectAddData")
    public void testProjectAdd(CaseInfo ci) {
        // 判断本次是否执行
        logger.info(ci);
        if ("Y".equalsIgnoreCase(ci.getIfExecute())) {
            //1、参数化
            // 初始化读取prop文件，防止time重复增加
            read();
            Long timeStamp = (Long)System.currentTimeMillis();
            String t = timeStamp.toString();
            logger.info("=============timestamp===" + t);
            Authentication.VARS.put("${name}",Authentication.VARS.get("${name}") + t);
            Authentication.VARS.put("${leader}",Authentication.VARS.get("${leader}") + t);
            Authentication.VARS.put("${tester}",Authentication.VARS.get("${tester}") + t);
            Authentication.VARS.put("${programmer}",Authentication.VARS.get("${programmer}") + t);
            Authentication.VARS.put("${publish_app}",Authentication.VARS.get("${publish_app}") + t);
            Authentication.VARS.put("${desc}",Authentication.VARS.get("${desc}") + t);
            logger.info(Authentication.VARS.get("${name}"));
            parameterization(ci);
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

    @DataProvider(name = "projectAddData")
    public Object[] projectAddData() {
        return ExcelUtils.getTestData(CaseInfo.class, sheetIndex, 1);
    }
}
