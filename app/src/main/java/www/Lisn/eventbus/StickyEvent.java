package www.Lisn.eventbus;

/**
 * ****************************
 * 项目名称：TestEventBus
 * 创建人：LiShan
 * 邮箱：cnlishan@163.com
 * 创建时间：2016/12/26
 * 版权所有违法必究
 * <p>
 * ****************************
 */

//粘性事件消息
public class StickyEvent {
    private String msg;

    public StickyEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
