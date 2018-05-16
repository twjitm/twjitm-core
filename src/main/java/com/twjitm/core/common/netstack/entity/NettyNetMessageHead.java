package com.twjitm.core.common.netstack.entity;

/**
 * Created by 文江 on 2017/11/15.
 */
public class NettyNetMessageHead {

    public static final short MESSAGE_HEADER_FLAG = 0x2425;

    /**
     * 魔法头
     */
    private short head;
    /**
     * 版本号
     */
    private byte version;
    /**
     * 长度
     */
    private int length;
    /**
     * 命令
     */
    private short cmd;
    /**
     * 序列号
     */
    private int serial;

    public NettyNetMessageHead() {
        this.head = MESSAGE_HEADER_FLAG;
    }

    public short getHead() {
        return head;
    }

    public void setHead(short head) {
        this.head = head;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
}
