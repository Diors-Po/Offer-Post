package com.cowbeer.offerpost.utils.mail;

public class MailSenderFactory {

    private static MailSender systemSender = null;
    private static MailSender[] noticeSenders = null;

    /**
     * 帐号管理类的邮件、同步的邮件，用SYSTEM_EMAIL发
     */
    public static MailSender getSystemSender(MailSenderType type) {
        if (type == MailSenderType.SERVICE) {
            if (systemSender == null) {
                systemSender = new MailSender(MailConstants.SYSTEM_EMAIL, MailConstants.SYSTEM_EMAIL_PASS);
            }
            return systemSender;
        }
        return null;
    }

    /**
     * 通知类的邮件、异步的邮件，用NOTICE_EMAILS发
     */
    public static MailSender[] getNoticeSenders(MailSenderType type) {
        if (type == MailSenderType.NOTICE) {
            if (noticeSenders == null) {
                int nums = MailConstants.NOTICE_EMAILS.length;
                noticeSenders = new MailSender[nums];

                for (int i = 0; i < nums; i++) {
                    noticeSenders[i] = new MailSender(MailConstants.NOTICE_EMAILS[i], MailConstants.NOTICE_EMAILS_PASS[i]);
                }
            }
            return noticeSenders;
        }
        return null;
    }

}
