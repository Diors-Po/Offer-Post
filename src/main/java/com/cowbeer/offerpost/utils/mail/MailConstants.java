package com.cowbeer.offerpost.utils.mail;

public class MailConstants {
    /**
     * The email accounts for the administrators, emails are sent when someone
     * or some problems are to be reviewed
     */
    public static final String[] ADMIN_EMAILS = { "pyzychen@gmail.com",
            "zicong.liu.nju@gmail.com" };

    /**
     * The email for account management
     */
    // public static final String SYSTEM_EMAIL = "noreply_witest_net@163.com";
    // public static final String SYSTEM_EMAIL_PASS = "witest";
    // public static final String SYSTEM_EMAIL = "admin@mooctest.net";
    // public static final String SYSTEM_EMAIL_PASS = "NJU83621360";
    //暂时使用EDM代替企业邮箱
    public static final String SYSTEM_EMAIL = "edmc04621115";
    public static final String SYSTEM_EMAIL_PASS = "IN1QB4EG";

    /**
     * The email for notice and invitation
     */
    public static final String[] NOTICE_EMAILS = new String[]{
//		"mooctest@sina.com",
//		"mooctest@sina.cn",
//		"mooctestnet@sina.com",
//		"mooctestnet@sina.cn",
//		"mooctestnet@163.com",
//		"mooctestnet@126.com",
            "edmc04621115"  //EDM账户
    };
    public static final String[] NOTICE_EMAILS_PASS = new String[]{
//		"NJU83621360",
//		"NJU83621360",
//		"NJU83621360",
//		"NJU83621360",
//		"fvhcfobhridqasdz",
//		"oqjiimxokcceukmr",
            "IN1QB4EG"
    };

    public static final String DEFAULT_SENDER_NAME = "慕测平台";
    //使用EDM账户时需要指定sender
    public static final String EDM_SENDER_ADDRESS = "service@edm.mooctest.net";
    public static final String EDM_SENDER_NAME = DEFAULT_SENDER_NAME;

    /**
     * The Maximum buffer size in file system when handling files
     */
    public static final int BUFFER_SIZE = 2 * 1024;

    /**
     * The maximum timeout of link for "forget password" function
     */

    public static final long BIND_EMAIL_TIMEOUT = 1000 * 60 * 60 * 12;
}

