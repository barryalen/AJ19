package cases;

import com.alibaba.fastjson.JSONObject;
import constants.Constants;
import org.apache.commons.lang3.StringUtils;
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
import utils.SQLUtils;

import java.util.*;

public class Login extends Base{
    public static Logger logger = Logger.getLogger(Login.class);

    @Test(dataProvider = "loginData")
    public void testLogin(CaseInfo ci){
        // 判断本次是否执行
        logger.info(ci);
        if("Y".equalsIgnoreCase(ci.getIfExecute())){
            //1、参数化
            parameterization(ci);
            //请求
            HttpResponse response = HttpUtils.request(ci.getType(), ci.getUrl(), Constants.HEADERS, ci.getParams(), ci.getContentType());
            String body = HttpUtils.getResponseBody(response);
            Authentication.json2Vars(body, "$.token", "${token}");
            // 非数据库断言
            logger.info("非数据库断言");
            String executeResult = getExecuteFlag(body, ci.getExpectResult());
            // 回写结果
            ExcelUtils.list.add(new WriteBackInfo(sheetIndex, ci.getCaseId(), Constants.WRITE_BACK_CELL_NUM_BODY, body));
            ExcelUtils.list.add(new WriteBackInfo(sheetIndex, ci.getCaseId(), Constants.WRITE_BACK_CELL_NUM_EXECUTE_RESULT, executeResult));
            Assert.assertEquals(executeResult, "pass");
        }
    }

    @AfterMethod
    public void addToken(){
        //添加token
        Object token = Authentication.VARS.get("${token}");
        logger.info("========token===" + token);
        Constants.HEADERS.put("Authorization", "JWT " + token);
    }

    @DataProvider(name = "loginData")
    public Object[] loginData(){
        Object[] datas = ExcelUtils.getTestData(CaseInfo.class, sheetIndex,1);
        List<CaseInfo> list = new ArrayList(Arrays.asList(datas));
        list.removeIf(ci -> "N".equalsIgnoreCase(ci.getIfExecute()));
        for (CaseInfo caseInfo : list) {
            System.out.println("=========="+caseInfo);
        }
        return list.toArray();
    }
}
