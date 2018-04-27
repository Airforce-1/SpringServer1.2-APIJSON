package apijson.oss.download.upload;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FileVO")
public class DownloadFileResponseVO implements Serializable
{
    /**
     * 序列化编号
     */
    private static final long serialVersionUID = -2218217669316014388L;

    // 文件字节流缓存
    private byte[] fileByteBuff;

    // 标识文件传输是否结束
    private boolean eof;

    // 下一批读取文件数据起始位置
    private int start;

    // 第一次读取文件时间(用于在分批传送文件过程中判断文件是否被修改了)
    private long fileLastModifiedTime;

    // 错误码
    private DownloadErrCodeEnum errCode;

    public DownloadFileResponseVO() {
        setFileByteBuff(null);
        setEof(false);
        setStart(0);
        setErrCode(DownloadErrCodeEnum.ERR_CODE_NA);
        setFileLastModifiedTime(-1);
    }

    public byte[] getFileByteBuff() {
        return fileByteBuff;
    }

    public void setFileByteBuff(byte[] fileByteBuff) {
        this.fileByteBuff = fileByteBuff;
    }

    public boolean isEof() {
        return eof;
    }

    public void setEof(boolean eof) {
        this.eof = eof;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public DownloadErrCodeEnum getErrCode() {
        return errCode;
    }

    public void setErrCode(DownloadErrCodeEnum errCode) {
        this.errCode = errCode;
    }

    public long getFileLastModifiedTime() {
        return fileLastModifiedTime;
    }

    public void setFileLastModifiedTime(long fileLastModifiedTime) {
        this.fileLastModifiedTime = fileLastModifiedTime;
    }

    @Override
    public String toString() {
        return "fileByteBuff: " + fileByteBuff.toString() + "\n"
                + "eof: " + String.valueOf(eof) + "\n"
                + "start: " + String.valueOf(start);
    }
}
