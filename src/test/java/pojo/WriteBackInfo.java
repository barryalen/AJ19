package pojo;

public class WriteBackInfo {
    private int sheetIndex;
    private int rowNum;
    private int cellNum;
    private String writeBackInfo;

    public WriteBackInfo() {
    }

    public WriteBackInfo(int sheetIndex, int rowNum, int cellNum, String writeBackInfo) {
        this.sheetIndex = sheetIndex;
        this.rowNum = rowNum;
        this.cellNum = cellNum;
        this.writeBackInfo = writeBackInfo;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public String getWriteBackInfo() {
        return writeBackInfo;
    }

    public void setWriteBackInfo(String writeBackInfo) {
        this.writeBackInfo = writeBackInfo;
    }

    @Override
    public String toString() {
        return "WriteBackInfo{" +
                "sheetIndex=" + sheetIndex +
                ", rowNum=" + rowNum +
                ", cellNum=" + cellNum +
                ", writeBackInfo='" + writeBackInfo + '\'' +
                '}';
    }
}
