package cn.neday.iflytek.util.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 工作日判断
 * Created by 苏晟 on 2017/7/14.
 */
public class WeekdayUtil {

    //节假日列表
    private static List<Calendar> holidayList = new ArrayList<Calendar>();
    //周末为工作日
    private static List<Calendar> weekendList = new ArrayList<Calendar>();

    public static WeekdayUtil getWeekdayUtil() {
        WeekdayUtil weekdayUtil = new WeekdayUtil();
        weekdayUtil.initHolidayList("2018-1-1");//节假日
        weekdayUtil.initWeekendList("2018-2-11");//周末为工作日
        weekdayUtil.initHolidayList("2018-2-15");//节假日
        weekdayUtil.initHolidayList("2018-2-16");//节假日
        weekdayUtil.initHolidayList("2018-2-19");//节假日
        weekdayUtil.initHolidayList("2018-2-20");//节假日
        weekdayUtil.initHolidayList("2018-2-21");//节假日
        weekdayUtil.initWeekendList("2018-2-24");//周末为工作日
        weekdayUtil.initHolidayList("2018-4-5");//节假日
        weekdayUtil.initHolidayList("2018-4-6");//节假日
        weekdayUtil.initWeekendList("2018-4-8");//周末为工作日
        weekdayUtil.initWeekendList("2018-4-28");//周末为工作日
        weekdayUtil.initHolidayList("2018-5-1");//节假日
        weekdayUtil.initHolidayList("2018-6-18");//节假日
        weekdayUtil.initHolidayList("2018-9-24");//节假日
        weekdayUtil.initWeekendList("2018-9-29");//周末为工作日
        weekdayUtil.initWeekendList("2018-9-30");//周末为工作日
        weekdayUtil.initHolidayList("2018-10-1");//节假日
        weekdayUtil.initHolidayList("2018-10-2");//节假日
        weekdayUtil.initHolidayList("2018-10-3");//节假日
        weekdayUtil.initHolidayList("2018-10-4");//节假日
        weekdayUtil.initHolidayList("2018-10-5");//节假日
        return weekdayUtil;
    }


    /**
     * 验证日期是否是节假日
     *
     * @return 返回true是节假日，返回false不是节假日
     */
    public boolean checkTodayIsHoliday() {
        Calendar calendar = Calendar.getInstance();
        //判断日期是否是周六周日
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            //判断日期是否是节假日
            for (Calendar ca : weekendList) {
                if (ca.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                        ca.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                        ca.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                    return false;
                }
            }
            return true;
        }
        //判断日期是否是节假日
        for (Calendar ca : holidayList) {
            if (ca.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                    ca.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                    ca.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 把所有节假日放入list
     *
     * @param date 从数据库查查出来的格式2016-05-09
     */
    private void initHolidayList(String date) {
        String[] strings = date.split("-");
        Calendar calendar = setCalendar(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]), Integer.valueOf(strings[2]));
        holidayList.add(calendar);
    }


    /**
     * 初始化周末被调整为工作日的数据
     *
     * @param date 从数据库查查出来的格式2016-05-09
     */
    private void initWeekendList(String date) {
        String[] strings = date.split("-");
        Calendar calendar = setCalendar(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]), Integer.valueOf(strings[2]));
        weekendList.add(calendar);
    }

    /**
     * 构造Calendar
     */
    private Calendar setCalendar(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1); // 月份比正常小1,0代表一月
        calendar.set(Calendar.DAY_OF_MONTH, date);
        return calendar;
    }
}
