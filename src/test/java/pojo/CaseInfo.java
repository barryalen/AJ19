package pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 映射Excel表格类
 * @Author Barry
 * @Date 2020/6/13
 */
public class CaseInfo {
    @Excel(name = "是否执行")
    private String ifExecute;
    @Excel(name = "用例编号")
    private int caseId;
    @Excel(name = "用例描述")
    private String desc;
    @Excel(name = "接口名称")
    private String name;
    @Excel(name = "请求方式")
    private String type;
    @Excel(name = "url")
    private String url;
    @Excel(name = "参数")
    private String params;
    @Excel(name = "参数类型")
    private String contentType;
    @Excel(name = "期望结果")
    private String expectResult;
    @Excel(name = "执行结果")
    private String executeResult;
    @Excel(name = "SQL")
    private String sql;

    public CaseInfo() {
    }

    public CaseInfo(String ifExecute, int caseId, String desc, String name, String type, String url, String params, String contentType, String expectResult, String executeResult, String sql) {
        this.ifExecute = ifExecute;
        this.caseId = caseId;
        this.desc = desc;
        this.name = name;
        this.type = type;
        this.url = url;
        this.params = params;
        this.contentType = contentType;
        this.expectResult = expectResult;
        this.executeResult = executeResult;
        this.sql = sql;
    }

    public String getIfExecute() {
        return ifExecute;
    }

    public void setIfExecute(String ifExecute) {
        this.ifExecute = ifExecute;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExpectResult() {
        return expectResult;
    }

    public void setExpectResult(String expectResult) {
        this.expectResult = expectResult;
    }

    public String getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(String executeResult) {
        this.executeResult = executeResult;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "CaseInfo{" +
                "ifExecute=" + ifExecute +
                ",caseId=" + caseId + '\'' +
                ", desc='" + desc + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", params='" + params + '\'' +
                ", contentType='" + contentType + '\'' +
                ", expectResult='" + expectResult + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
