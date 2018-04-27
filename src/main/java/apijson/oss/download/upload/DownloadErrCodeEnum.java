package apijson.oss.download.upload;

/**
 * 下载文件错误码
 * @author Elon
 * @version 1.0 2015-06-30
 */
public enum DownloadErrCodeEnum 
{
    DOWN_LOAD_SUCCESS, // 下载成功
    READ_FILE_EXCEPTION, // 读取文件异常
    FILE_HAS_MODIFIED_WHILE_DOWNLOAD, // 在分批下载文件的过程中文件发生了修改
    ERR_CODE_NA, // 无效错误码
}