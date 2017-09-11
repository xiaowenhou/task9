package com.putaoteng.task8.utils.other;

import com.qiniu.common.QiniuException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class Transfer {
    private static Logger logger = Logger.getLogger(Transfer.class);
    private QiniuStorage qiniuStorage;
    private AliStorage aliStorage;
    private String isDelete;
    private String toOther;

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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getToOther() {
        return toOther;
    }

    public void setToOther(String toOther) {
        this.toOther = toOther;
    }

    public boolean transfer() {
        logger.info("transfer()被执行...");
        if (toOther.equalsIgnoreCase("qiniu-to-ali")) {
            try {
                boolean result = qiniuToAli();
                if (!result) {
                    logger.error("云存储迁移失败: 七牛向阿里迁移失败!transfer() failed");
                    return false;
                }
            } catch (IOException e) {
                logger.error("云存储迁移异常: IO流异常,七牛向阿里迁移失败!原因:" + e.getMessage());
                return false;
            }
        } else if (toOther.equalsIgnoreCase("ali-to-qiniu")) {
            try {
                aliToQiniu();
            } catch (QiniuException e) {
                logger.error("云存储迁移异常: 七牛api异常,阿里向七牛迁移失败!原因:" + e.getMessage());
                return false;
            }
        }
        return true;
    }

    /*从七牛云迁移到阿里云*/
    private boolean qiniuToAli() throws IOException {
        //获取七牛云存储中的文件列表
        List<String> list = qiniuStorage.getFileList();
        //构造url
        String url;
        for (String str : list) {
            url = qiniuStorage.getEndpoint() + "/" + str;
            aliStorage.uploadFileByUrl(url, str);
        }
        //如果isDelete字段的值为"Yes"则删除七牛云空间中的文件.
        if (this.isDelete.equalsIgnoreCase("Yes")) {
            String[] strArray = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                strArray[i] = list.get(i);
            }
            boolean result = qiniuStorage.deleteBach(strArray);
            if (!result) {
                logger.error("云存储迁移失败: 删除七牛云存储中的数据时失败!qiniuToAli() failed");
                return false;
            }
        }
        return true;
    }

    /*从阿里云迁移到七牛云*/
    private void aliToQiniu() throws QiniuException {
        //获取阿里云存储中的文件
        List<String> list = aliStorage.getFileList();

        //构造url
        String url;
        for (String str : list) {
            url = aliStorage.getEndpoint();
            url = url.substring(0, 7) + aliStorage.getBucketName() + "." + url.substring(7) + "/" + str;
            qiniuStorage.uploadFileByUrl(url, str);
        }

        //如果isDelete字段为Yes,则删除阿里云存储中的文件
        if (isDelete.equalsIgnoreCase("Yes")) {
            aliStorage.deleteBach(list);
        }
    }
}
