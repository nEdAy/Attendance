package cn.neday.iflytek.domain;


import com.alibaba.fastjson.annotation.JSONField;

/**
 * {
 * "PhoneType": "ios",
 * "IMEI": "IMEI",
 * "AppVersion": "1.0.1020",
 * "TimeStamp": "1508718918174",
 * "UserName": "苏晟",
 * "OpeType": "Client005",
 * "AuthID": "d0448af919f843eaa8b96bed793b1c8d",
 * "UserCode": "2017000153",
 * "PointType": "1",
 * "AttendanceAreaID": "29",
 * "AreaCode": "08",
 * "LngLat": "yGIyMUxEsewfF38vN8OxknPHSjf8nhZRgmsdx3Rhdn0%3D",
 * "Token": "FdHz3e4NnZlkBOhd5Vhtn8TqI.eN_c2OByjOnUqeZUE0IBe91KYhdo.JVhFyFFZfesBDWiN1M7j9HlHcwG0DXPC72RlcRFkf8uy0OxiRP4Q=",
 * "UserAccount": "shengsu",
 * "UserID": "R697O9Og3_rgUy1kEKxCo4Dvfe0=",
 * "OrgName": "知学产品管理部知学宝产品线"
 * }
 */
public class RequestInfo {

    private String LngLat;
    private String TimeStamp;

    private String UserAccount;

    private String AppVersion;
    private String AuthID;
    private String UserID;
    private String UserCode;
    private String IMEI;

    private String Token;

    private String UserName;
    private String OrgName;

    private String OpeType;
    private String PointType;
    private String PhoneType;
    private String AreaCode;
    private String AttendanceAreaID;

    public RequestInfo(User user) {
        this.UserAccount = user.getUserAccount();

        this.AppVersion = user.getAppVersion();
        this.AuthID = user.getAuthID();
        this.UserID = user.getUserID();
        this.UserCode = user.getUserCode();
        this.IMEI = user.getIMEI();

        this.Token = user.getToken();

        this.UserName = user.getUserName();
        this.OrgName = user.getOrgName();

        this.OpeType = user.getOpeType();
        this.PointType = user.getPointType();
        this.PhoneType = user.getPhoneType();
        this.AreaCode = user.getAreaCode();
        this.AttendanceAreaID = user.getAttendanceAreaID();
    }

    @JSONField(name = "LngLat")
    public String getLngLat() {
        return LngLat;
    }

    public void setLngLat(String lngLat) {
        LngLat = lngLat;
    }

    @JSONField(name = "TimeStamp")
    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    @JSONField(name = "UserAccount")
    public String getUserAccount() {
        return UserAccount;
    }

    public void setUserAccount(String userAccount) {
        UserAccount = userAccount;
    }

    @JSONField(name = "AppVersion")
    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    @JSONField(name = "AuthID")
    public String getAuthID() {
        return AuthID;
    }

    public void setAuthID(String authID) {
        AuthID = authID;
    }

    @JSONField(name = "UserID")
    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    @JSONField(name = "UserCode")
    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    @JSONField(name = "IMEI")
    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    @JSONField(name = "Token")
    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    @JSONField(name = "UserName")
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @JSONField(name = "OrgName")
    public String getOrgName() {
        return OrgName;
    }

    public void setOrgName(String orgName) {
        OrgName = orgName;
    }

    @JSONField(name = "OpeType")
    public String getOpeType() {
        return OpeType;
    }

    public void setOpeType(String opeType) {
        OpeType = opeType;
    }

    @JSONField(name = "PointType")
    public String getPointType() {
        return PointType;
    }

    public void setPointType(String pointType) {
        PointType = pointType;
    }

    @JSONField(name = "PhoneType")
    public String getPhoneType() {
        return PhoneType;
    }

    public void setPhoneType(String phoneType) {
        PhoneType = phoneType;
    }

    @JSONField(name = "AreaCode")
    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    @JSONField(name = "AttendanceAreaID")
    public String getAttendanceAreaID() {
        return AttendanceAreaID;
    }

    public void setAttendanceAreaID(String attendanceAreaID) {
        AttendanceAreaID = attendanceAreaID;
    }
}
