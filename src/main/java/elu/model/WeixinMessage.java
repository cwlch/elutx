package elu.model;

/**
 * Created by Ch on 17/5/9.
 */
public class WeixinMessage {
    private String fromUserName;
    private String toUserName;
    private String createTime;
    private String msgType;
    private String content;

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WeixinMessage{" +
                "fromUserName='" + fromUserName + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", msgType='" + msgType + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String toXml() {
        return "<xml>\n" +
                "<ToUserName><![CDATA["+fromUserName+"]]></ToUserName>\n" +
                "<FromUserName><![CDATA["+toUserName+"]]></FromUserName>\n" +
                "<CreateTime>"+createTime+"</CreateTime>\n" +
                "<MsgType><![CDATA["+msgType+"]]></MsgType>\n" +
                "<Content><![CDATA["+content+"]]></Content>\n" +
                "</xml>";
    }

    public WeixinMessage(String fromUserName, String toUserName, String createTime, String msgType, String content) {
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
        this.createTime = createTime;
        this.msgType = msgType;
        this.content = content;
    }

    public WeixinMessage() {

    }
}
