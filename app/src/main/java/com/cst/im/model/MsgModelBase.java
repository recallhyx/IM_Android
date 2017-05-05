package com.cst.im.model;

import android.util.Log;

/**
 * Created by ASUS on 2017/5/4.
 */

public class MsgModelBase implements IBaseMsg {
    int srcID;//消息源
    int[] dstID;//消息目的
    String msgDate;//消息日期
    IBaseMsg.MsgType msgType;// 是否为收到的消息

    //发送源用户id
    public int getSrc_ID(){return srcID;}
    public void setSrc_ID(int src_id){
        this.srcID=src_id;
    }
    //发送目的用户id组
    public int[] getDst_ID(){return dstID;}
    public int getDst_IDAt(int pos){
        if (pos<dstID.length)
            return dstID[pos];
        Log.w("getDstID","get dst index beyond the group");
        return -1;
    }
    public void setDst_ID(int[] dst_id){
        this.dstID=dst_id;
    }
    //发送时间
    public String getMsgDate(){return msgDate;}
    public void setMsgDate(String msgDate){
        this.msgDate=msgDate;
    }

    //消息类型，标志消息是文字，图片，语音，文件等
    public IBaseMsg.MsgType getMsgType(){return  msgType;}
    public void setMsgType(IBaseMsg.MsgType msgType){
        this.msgType=msgType;
    }
}