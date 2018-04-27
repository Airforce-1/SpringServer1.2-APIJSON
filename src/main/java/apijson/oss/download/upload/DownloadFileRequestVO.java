package apijson.oss.download.upload;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 下载文件请求参数类型。
 * 
 * @author Elon
 * @version 1.0 2015-06-30
 */
@XmlRootElement(name = "DownloadFileRequest")
public class DownloadFileRequestVO implements Serializable
{
    /**
     * 序列化编码
     */
    private static final long serialVersionUID = 3142085277564296839L;

    // 文件路径
    private String filePath;

    // 读文件数据起始位置
    private int start;

    // 第一次读取文件时间(用于在分批传送文件过程中判断文件是否被修改了)
    private long fileLastModifiedTime;

    DownloadFileRequestVO() 
    {
        setFilePath("");
        setStart(0);
        setFileLastModifiedTime(-1);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getFileLastModifiedTime() {
        return fileLastModifiedTime;
    }

    public void setFileLastModifiedTime(long fileLastModifiedTime) {
        this.fileLastModifiedTime = fileLastModifiedTime;
    }

    @Override
    public String toString() {
        return "filePath: " + filePath + "\n"
                + "start: " + String.valueOf(start);
    }
}
