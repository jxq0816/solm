package com.thinkgem.jeesite.modules.util.weibo.post;

import com.alibaba.fastjson.JSON;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;
import java.util.Map;

/**
 * WeiBoPostService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>03/13/2017</pre>
 */
public class WeiBoPostServiceTest extends TestCase {
    private WeiBoPostService service = new WeiBoPostService();

    public WeiBoPostServiceTest(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: comment(String id, String comment, String token)
     */
    public void testComment() throws Exception {
        String id = "4087614108017463";
        String rs = service.comment(id, "我方了", "2.00G4OXECnp71ED9509c7fcde0wtkDs");
        System.out.println(rs);
    }
    /**
     * Method: updateSubscribe(String uids)
     */
    public void testUpdateSubscribe() throws Exception {

    }

    /**
     * Method: updateSubscribeAddKeyWord(String keywords)
     */
    public void testUpdateSubscribeAddKeyWord() throws Exception {
        service.updateSubscribeAddKeyWord("@西门子", "");
    }
    /**
     * Method: updateSubscribeAddKeyWord(String keywords)
     */
    public void testUpdateSubscribeDelKeyWord() throws Exception {

        String rs=service.updateSubscribeDelKeyWord("博世家电客户服务 ", "");
        System.out.println("result:"+rs);
    }

    public void testMessagesReply() throws Exception {
        Map map=new HashMap<String,Object>();
        map.put("text","I got it");
        String jsonObject= JSON.toJSONString(map);
        String rs=service.messagesReply("3901147339", jsonObject,"");
        System.out.println("result:"+rs);
    }

    /**
     * Method: receiveMessage(String message)
     */
    public void testReceiveMessage() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: main(String[] args)
     */
    public void testMain() throws Exception {
//TODO: Test goes here... 
    }


    public static Test suite() {
        return new TestSuite(WeiBoPostServiceTest.class);
    }
} 
