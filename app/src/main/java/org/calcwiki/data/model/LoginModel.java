package org.calcwiki.data.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 用于登录操作的 Model
 * @author Haruue Icymoon haruue@caoyue.com.cn
 */
public class LoginModel {

    public static class Success {

        /**
         * result : Success
         * lguserid : 9
         * lgusername : 春上冰月
         * lgtoken : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         * cookieprefix : my_wiki_cw_
         * sessionid : 5oc4ls0phovgclc32udf1vstt5
         */

        @JSONField(name = "login")
        public LoginEntity login;

        public static class LoginEntity {
            @JSONField(name = "result")
            public String result;
            @JSONField(name = "lguserid")
            public int lguserid;
            @JSONField(name = "lgusername")
            public String lgusername;
            @JSONField(name = "lgtoken")
            public String lgtoken;
            @JSONField(name = "cookieprefix")
            public String cookieprefix;
            @JSONField(name = "sessionid")
            public String sessionid;
        }
    }

    public static class NeedToken {

        /**
         * result : NeedToken
         * token : xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         * cookieprefix : my_wiki_cw_
         * sessionid : 5oc4ls0phovgclc32udf1vstt5
         */

        @JSONField(name = "login")
        public LoginEntity login;

        public static class LoginEntity {
            @JSONField(name = "result")
            public String result;
            @JSONField(name = "token")
            public String token;
            @JSONField(name = "cookieprefix")
            public String cookieprefix;
            @JSONField(name = "sessionid")
            public String sessionid;
        }
    }

    public static class Result {

        /**
         * result : WrongPass
         */

        /**
         * result: WrongToken
         */

        /**
         * result: NotExists
         */

        @JSONField(name = "login")
        public LoginEntity login;

        public static class LoginEntity {
            @JSONField(name = "result")
            public String result;
        }
    }

}
