package com.hoho.android.usbserial.at;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.hoho.android.usbserial.at.interfaces.ConnectStatusListener;
import com.hoho.android.usbserial.at.interfaces.Connector;
import com.hoho.android.usbserial.at.interfaces.DataListener;
import com.hoho.android.usbserial.at.interfaces.OnScanListener;
import com.hoho.android.usbserial.at.interfaces.PortConnectListener;
import com.hoho.android.usbserial.at.util.AtOperator;
import com.wms.logger.Logger;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2020/4/29 14:58
 * @UpdateUser: yxf
 * @UpdateDate: 2020/4/29 14:58
 */
public class AtConnector implements Connector {

    private AtOperator atOperator;

    private String patchMac;
    /**
     * 扫描出来的type，用于连接设备的参数
     */
    private int patchType = 0;

    public AtConnector(Activity activity, Context context, int deviceId, int portNum) {
        atOperator = new AtOperator(activity, context, deviceId, portNum);
    }

    public void setPortConnectListener(PortConnectListener portConnectListener) {
//        this.portConnectListener = portConnectListener;
        atOperator.setPortConnectListener(portConnectListener);//设置串口连接状态的回调
    }

    /**
     * 打开串口
     */
    public void openSerialPort() {
        atOperator.openSerialPort();
    }


    public void setPatchMac(String patchMac) {
        this.patchMac = patchMac;
    }

    /**
     * 扫描设备
     */
    public void scanDevices(OnScanListener scanListener) {
        atOperator.scanDevice(scanListener);
    }

    @Override
    public void connect() {
        connect(null, null);
    }

    @Override
    public void connect(ConnectStatusListener connectStatusListener, DataListener dataListener) {

        atOperator.setConnectStatusListener(connectStatusListener);
        atOperator.setDataListener(dataListener);
        if (TextUtils.isEmpty(patchMac)) {
            Logger.w("patchMac is null");
            throw new NullPointerException("patchMac is null,please set first!!!");
        }
        atOperator.connectDevice(patchType, patchMac);
    }

    @Override
    public void disConnect() {
        atOperator.disConnect();
    }

    @Override
    public boolean isConnected() {
        return null == atOperator ? false : atOperator.isConnected();
    }

    /**
     * 串口是否可用
     *
     * @return
     */
    public boolean isPortConnected() {
        return null == atOperator ? false : atOperator.isPortConnected();
    }

    /**
     * 关闭串口
     */
    public void closePort() {
        if (atOperator != null) {
            atOperator.closeSerialPort();
        }
    }

    @Override
    public void cancelConnect() {
        atOperator.disConnect();
    }

}
