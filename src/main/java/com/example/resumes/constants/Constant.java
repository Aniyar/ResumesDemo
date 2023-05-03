package com.example.resumes.constants;


public final class Constant {
    public static final String PATH_TO_NOJPG = "src\\main\\resources\\static\\noavatar.jpg";
    public static final String PATH_TO_NOPDF = "src\\main\\resources\\static\\nocv.pdf";

    public static final String topicExchangeName = "jobsExchange";

    public static final String queueName = "q.get-jobs";
    private Constant() {
        throw new AssertionError();
    }
}
