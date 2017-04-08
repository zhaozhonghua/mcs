package com.fjsmu.tools;

//目前没有用到此类，用的时候再研究是否可用
@Deprecated
public class SecretUtil {

    public static byte[] MSG_HEADER = {(byte) 0xcd, (byte) 0xb7};

    public static byte[] MSG_FOOTER = {(byte) 0xce, (byte) 0xb2};

    public static String MSG_YH_KEY = "888888AAAAAA12345678901234567890";

    public static void main(String[] args) {
        String value = "wupeng";
        String key = "==";
        String encodeValue = encodeYH(value.getBytes(), key);
        System.out.println(encodeValue);
        String decodeValue = decodeYH(encodeValue.getBytes(), key);
        System.out.println(decodeValue);
    }

    /**
     * ����
     * @param value ԭ��
     * @param key 
     * @return ���ܺ��ֵ
     */
    public static String encodeYH(byte[] value, String key){
        byte[] source = value;
        byte[] keyData = key.getBytes();
        int j = 0;
        for (int i=0; i<source.length; ++i) {
            source[i] = (byte)(source[i] ^ keyData[j]);
            j = (j+1)%(keyData.length);
        }
        return new String(source);
    }

    /**
     * ����
     * @param encodeValue ���ܺ��ֵ
     * @param key
     * @return ��������
     */
    public static String decodeYH(byte[] encodeValue, String key){
        byte[] value = encodeValue;
        byte[] keyData = key.getBytes();
        int j = 0;
        for (int i=0; i<value.length; ++i) {
            value[i] = (byte)(value[i] ^ keyData[j]);
            j = (j+1)%(keyData.length);
        }
        return new String(value);
    }

    /*******************************************���´������ݽ��� ************************************************/


    /**
     * ��ȡ�����ַ���Ϣͷ: 2λ
     * @param bytes
     * @return
     */
    public static byte[] getMsgHeader(byte[] bytes){

        return new byte[]{bytes[0],bytes[1]};
    }
    /**
     * ��ȡ�����ַ���Ϣβ:2λ
     * @param bytes
     * @return
     */
    public static byte[] getMsgFooter(byte[] bytes){

        return new byte[]{bytes[bytes.length-2],bytes[bytes.length-1]};
    }
    /**
     * ��ȡ�����ַ�У��λ:1λ
     * @param bytes
     * @return
     */
    public static int getMsgCheckCode(byte[] bytes){
        byte[] newbytes = new byte[]{bytes[bytes.length-3]};
        return bytesToInt(newbytes);
    }
    /**
     * ��ȡ����ļ����ַ����ݳ���
     * @param bytes
     * @return
     */
    public static int getExpectMsgContentLength(byte[] bytes){
        byte[] contentLength = new byte[]{bytes[2],bytes[3],bytes[4],bytes[5]};
        return bytesToInt(contentLength);
    }
    /**
     * bytesToInt
     *
     * @param bytes
     * @return
     */
    public  static int bytesToInt(byte[] bytes) {
        int addr = -1;
        if (bytes.length == 1) {
            addr = bytes[0] & 0xFF;
            return addr;
        }
        if (bytes.length == 2) {
            addr = bytes[0] & 0xFF;
            addr |= ((bytes[1] << 8) & 0xFF00);
            return addr;
        }
        if (bytes.length == 3) {
            addr = bytes[0] & 0xFF;
            addr |= ((bytes[1] << 8) & 0xFF00);
            addr |= ((bytes[2] << 16) & 0xFF0000);
            return addr;
        }
        if (bytes.length == 4) {
            addr = bytes[0] & 0xFF;
            addr |= ((bytes[1] << 8) & 0xFF00);
            addr |= ((bytes[2] << 16) & 0xFF0000);
            addr |= ((bytes[3] << 24) & 0xFF000000);
            return addr;
        }
        return addr;
    }

    /**
     * ��ȡ�����ַ�����:��Ϣ����=��6λ->�������λ
     * @param bytes
     * @return
     */
    public static byte[] getMsgContent(byte[] bytes){
        byte[] content = new byte[bytes.length-6-3];
        int index = 0;
        for (int i = 6; i < bytes.length-3; i++) {
            content[index] = bytes[i];
            index++;
        }
        //System.out.println(new String(decodeYH(content, MSG_YH_KEY)));
        return content;
    }

    /**
     * �����յ����ַ��Ƿ�����
     * @param bytes
     * @return
     */
    public static boolean checkMsgLength(byte[] bytes){
        int contentLength = getExpectMsgContentLength(bytes);
        int receiveLength = getMsgContent(bytes).length;
        return receiveLength == contentLength 
                && arrayEquals(getMsgHeader(bytes),MSG_HEADER)
                && arrayEquals(getMsgFooter(bytes), MSG_FOOTER);
    }

    /**
     * ͨ�����У��λ �����յ����ַ��Ƿ񱻴۸�
     * @param bytes
     * @return
     */
    public static boolean checkMsgContent(byte[] bytes) {
        
        return calcSum(getMsgContent(bytes)) == getMsgCheckCode(bytes);
    }

    /**
     * ����У��λ
     * @param bytes
     * @param len
     * @return
     */
    private static int calcSum(byte[] bytes) {
        int sum = 0;
        for (int i = 0; i < bytes.length; ++i) {
            int n = (0x000000FF & ((int) bytes[i]));
            sum ^= n;
        }
        return (0x000000FF & sum);
    }
    
    /**
     * �Ƚ����������Ƿ����
     * @param a
     * @param b
     * @return
     */
    public static boolean arrayEquals(byte[] a, byte[] b){
        if (a.length != b.length) {
            return false;
        }
        for (int i=0;i<a.length;i++)
            if (a[i] != b[i])
                return false;
        return true;
    }
}
