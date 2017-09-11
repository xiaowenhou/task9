package com.putaoteng.task8.utils.other;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AliStorage {
    //返回文件列表时的最大返回结果
    private final int MAX_FILENAMES=200;

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    public String getEndpoint() {
        return endpoint;
    }
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    public String getAccessKeyId() {
        return accessKeyId;
    }
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }
    public String getAccessKeySecret() {
        return accessKeySecret;
    }
    public void setAccessKeySecret(String accessKeySecret) { this.accessKeySecret = accessKeySecret; }
    public String getBucketName() {
        return bucketName;
    }
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /*通过字节数组上传至空间*/
    public void uploadFileByByteArray(byte[] uploadFile, String fileName){
        //创建客户端
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        //转化为字符数组流
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(uploadFile);
        //上传
        client.putObject(this.bucketName, fileName, byteArrayInputStream);
        //关闭客户端
        client.shutdown();
    }

    /*抓取网络上的资源到空间*/
    public void uploadFileByUrl(String url, String fileName) throws MalformedURLException, IOException {
        //创建客户端
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        //转化为网络流
        InputStream inputStream = new URL(url).openStream();
        //上传
        client.putObject(this.bucketName, fileName, inputStream);
        //关闭链接
        client.shutdown();
    }

    /*获取空间中的文件名列表*/
    public List<String> getFileList(){
        //创建客户端
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 列举Object,最多返回MAX_FILENAMES个结果
        ObjectListing objectListing = client.listObjects(new ListObjectsRequest(this.bucketName).withMaxKeys(MAX_FILENAMES));

        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        List<String> list = new ArrayList<String>();
        for (OSSObjectSummary s : sums) {
           list.add(s.getKey());
        }
        // 关闭client
        client.shutdown();
        return list;
    }

    /*删除一个文件*/
    public void deleteFile(String fileName){
        //创建客户端
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 删除Object
        ossClient.deleteObject(this.bucketName, fileName);
        // 关闭client
        ossClient.shutdown();
    }

    /*批量删除文件,返回结果为删除错误的结果列表*/
    public List<String> deleteBach(List<String> fileNameList){
        //创建客户端
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(this.bucketName);
        deleteObjectsRequest.setKeys(fileNameList);
        //简单模式,只返回删除错误的结果
        deleteObjectsRequest.setQuiet(true);

        //获取删除的结果
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(deleteObjectsRequest);

        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        // 关闭client
        ossClient.shutdown();
        return deletedObjects;
    }
}
