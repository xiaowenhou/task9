package com.putaoteng.task8.utils.other;

import com.qiniu.common.QiniuException;
import org.apache.log4j.Logger;

public class Storage {
    private static Logger logger = Logger.getLogger(Storage.class);

    private String choice;
    private QiniuStorage qiniuStorage;
    private AliStorage aliStorage;

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public QiniuStorage getQiniuStorage() {
        return qiniuStorage;
    }

    public void setQiniuStorage(QiniuStorage qiniuStorage) {
        this.qiniuStorage = qiniuStorage;
    }

    public AliStorage getAliStorage() {
        return aliStorage;
    }

    public void setAliStorage(AliStorage aliStorage) {
        this.aliStorage = aliStorage;
    }

    //上传文件
    public boolean uploadFileByByteArray(byte[] uploadFile, String fileName) {
        logger.info("uploadFile()被执行...");
        long startTime = System.currentTimeMillis();
        if (choice.equals("qiniu")) {
            try {
                qiniuStorage.uploadFileByByteArray(uploadFile, fileName);
            } catch (QiniuException e) {
                logger.error( "云存储上传文件异常: 七牛云异常,原因: " + e.getMessage());
                return false;
            }
        } else if (choice.equals("ali")) {
            aliStorage.uploadFileByByteArray(uploadFile, fileName);
        } else {
            logger.error("云存储上传文件异常:  Parameter error!");
            return false;
        }

        long endTime = System.currentTimeMillis();
        logger.debug("-----" + choice +"上传用时: " + (endTime-startTime)+"ms");
        return true;
    }

    //删除文件
    public boolean deleteFile(String fileName){
        logger.info("deleteFile()被执行...");
        if (choice.equals("qiniu")) {
            try {
                qiniuStorage.deleteFile(fileName);
            } catch (QiniuException e) {
                logger.error("云存储删除文件异常:七牛云异常,原因: " + e.getMessage());
                return false;
            }
        } else if (choice.equals("ali")) {
            aliStorage.deleteFile(fileName);
        } else {
            logger.error("云存储删除文件异常: Parameter error!");
            return false;
        }
        return true;
    }
}
