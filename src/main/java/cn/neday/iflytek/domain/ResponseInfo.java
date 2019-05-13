package cn.neday.iflytek.domain;

/**
 * /**
 * ResponseInfo
 * <p>
 * Content : {"RankNum":"-1","AttendanceID":"2830917","Message":"Sucess","AttendanceTime":"2017-07-06 16:21:07"}
 * ErrorCode : null
 * Success : true
 */

public class ResponseInfo {

    private ContentBean Content;
    private String ErrorCode;
    private boolean Success;

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public boolean getSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    /**
     * RankNum : -1
     * AttendanceID : 2830917
     * Message : Sucess
     * AttendanceTime : 2017-07-06 16:21:07
     */
    public static class ContentBean {

        private String RankNum;
        private String AttendanceID;
        private String Message;
        private String AttendanceTime;

        public String getRankNum() {
            return RankNum;
        }

        public void setRankNum(String RankNum) {
            this.RankNum = RankNum;
        }

        public String getAttendanceID() {
            return AttendanceID;
        }

        public void setAttendanceID(String AttendanceID) {
            this.AttendanceID = AttendanceID;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public String getAttendanceTime() {
            return AttendanceTime;
        }

        public void setAttendanceTime(String AttendanceTime) {
            this.AttendanceTime = AttendanceTime;
        }
    }

}
