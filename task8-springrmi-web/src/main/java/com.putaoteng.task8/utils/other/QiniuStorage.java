package com.putaoteng.task8.utils.other;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class QiniuStorage {
    private static Logger logger = Logger.getLogger(QiniuStorage.class.getName());

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endpoint;

    public String getEndpoint() { return endpoint; }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    /* 通过字节数组上传文件*/
    public void uploadFileByByteArray(byte[] uploadBytes, String fileName) throws QiniuException {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成验证令牌,使用字节数组流
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
        Auth auth = Auth.create(this.accessKey, this.secretKey);
        String upToken = auth.uploadToken(this.bucket);
        // 上传
        Response response = uploadManager.put(byteInputStream, fileName, upToken, null, null);

        // 解析上传成功的结果,记录日志
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
    }

    /*通过url抓取网络上的资源到空间*/
    public void uploadFileByUrl(String url, String fileName) throws QiniuException {
        //创建验证令牌以及配置地域类型
        Auth auth = Auth.create(this.accessKey, this.secretKey);
        Configuration cfg = new Configuration(Zone.zone2());
        //创建bucket管理对象
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //抓取网络资源到空间
        FetchRet fetchRet = bucketManager.fetch(url, bucket, fileName);
        System.out.println(fetchRet.hash);
        System.out.println(fetchRet.key);
        System.out.println(fetchRet.mimeType);
        System.out.println(fetchRet.fsize);
    }

    /*删除一个文件*/
    public void deleteFile(String fileName) throws QiniuException {
        //创建验证令牌以及配置地域类型
        Auth auth = Auth.create(this.accessKey, this.secretKey);
        Configuration cfg = new Configuration(Zone.zone2());
        //创建bucket管理对象
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //删除指定的文件
        bucketManager.delete(bucket, fileName);
    }

    /*批量删除文件*/
    public boolean deleteBach(String[] fileNameList) throws QiniuException {
        //创建验证令牌以及配置地域类型
        Auth auth = Auth.create(this.accessKey, this.secretKey);
        Configuration cfg = new Configuration(Zone.zone2());
        //创建bucket管理对象
        BucketManager bucketManager = new BucketManager(auth, cfg);

        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        batchOperations.addDeleteOp(bucket, fileNameList);
        Response response = bucketManager.batch(batchOperations);
        BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
        for (int i = 0; i < fileNameList.length; i++) {
            BatchStatus status = batchStatusList[i];
            String key = fileNameList[i];
            System.out.print(key + "\t");
            if (status.code != 200) {
                logger.error("云存储迁移失败: 七牛向阿里迁移失败!deleteBach() failed");
                return false;
            }
        }

        return true;
    }

    /*获取空间中的所有文件列表*/
    public List<String> getFileList() {
        //创建验证令牌以及配置地域类型
        Auth auth = Auth.create(this.accessKey, this.secretKey);
        Configuration cfg = new Configuration(Zone.zone2());
        //创建bucket管理对象
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
        List<String> list = new ArrayList<>();
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
              /*
                //文件名
                System.out.println(item.key);
                //内容的hash值
                System.out.println(item.hash);
                //文件大小(字节)
                System.out.println(item.fsize);
                //contentType
                System.out.println(item.mimeType);
                //上传时间的时间戳
                System.out.println(item.putTime);
                //???
                System.out.println(item.endUser);*/
                list.add(item.key);
            }
        }
        return list;
    }
}
