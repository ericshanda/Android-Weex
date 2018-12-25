package com.taobao.demo.accs;

import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.utl.ALog;
import com.taobao.demo.EmasInit;
import com.taobao.demo.agoo.AgooActivity;

/**
 * 第一个accs默认实例, 消息回调类
 */
public class TestAccsService extends TaoBaseService {
    private static final String TAG = "TestAccsService";

    private static IAccsResponse mResponse;

    public interface IAccsResponse {
        void onResponse(int errorCode, String response);

        void onData(String serviceId, String dataId, String data);
    }

    public static void setIAccsResponse(IAccsResponse response) {
        mResponse = response;
    }

    /**
     * bindService回调
     *
     * @param serviceId 服务ID
     * @param errorCode 错误码=200为成功，其他失败
     * @param extraInfo
     */
    @Override
    public void onBind(String serviceId, int errorCode, ExtraInfo extraInfo) {
        ALog.d(TAG, "onBind", "serviceId", serviceId, "errorCode", errorCode);
    }

    /**
     * unbindService回调
     *
     * @param serviceId 服务ID
     * @param errorCode 错误码=200为成功，其他失败
     * @param extraInfo
     */
    @Override
    public void onUnbind(String serviceId, int errorCode, ExtraInfo extraInfo) {
        ALog.d(TAG, "onUnbind", "serviceId", serviceId, "errorCode", errorCode);
    }

    /**
     * sendData回调
     *
     * @param serviceId 服务ID
     * @param dataId    数据ID
     * @param errorCode 错误码=200为成功，其他失败
     * @param extraInfo
     */
    @Override
    public void onSendData(String serviceId, String dataId, int errorCode, ExtraInfo extraInfo) {
        ALog.d(TAG, "onSendData", "serviceId", serviceId, "dataId", dataId, "errorCode", errorCode);
    }

    /**
     * sendRequest回调
     *
     * @param serviceId 服务ID
     * @param dataId    数据ID
     * @param errorCode 错误码=200为成功，其他失败
     * @param bytes
     * @param extraInfo
     */
    @Override
    public void onResponse(String serviceId, String dataId, int errorCode, byte[] bytes, ExtraInfo extraInfo) {
        if (mResponse != null) {
            mResponse.onResponse(errorCode, bytes == null ? null : new String(bytes));
        }
    }

    /**
     * 下行数据回调
     *
     * @param serviceId 服务ID
     * @param userId    用户ID
     * @param dataId    数据ID
     * @param bytes
     * @param extraInfo
     */
    @Override
    public void onData(String serviceId, String userId, String dataId, byte[] bytes, ExtraInfo extraInfo) {
        if (mResponse != null) {
            mResponse.onData(serviceId, dataId, bytes == null ? null : new String(bytes));
        }
    }

    /**
     * 连接成功状态回调
     *
     * @param conninfo
     */
    @Override
    public void onConnected(ConnectInfo conninfo) {
        ALog.d(TAG, "onConnected", "host", conninfo.host, "isInapp", conninfo.isInapp);

    }

    /**
     * 连接失败状态回调
     *
     * @param conninfo
     */
    @Override
    public void onDisconnected(ConnectInfo conninfo) {
        ALog.e(TAG, "onDisconnected", "host", conninfo.host, "isInapp", conninfo.isInapp,
                "errorCode", conninfo.errorCode, "errorDetail", conninfo.errordetail);
    }
}
