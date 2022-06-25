package com.zhangzhuorui.framework.rbacsystem.config;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

/**
 * @author :  张涛 zhangtao
 * @version :  1.0
 * @createDate :  2022/6/25 下午5:30
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 */
public class ZtDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String str) {
        String[] patterns = new String[]{
                "yyyy-MM-dd", "yyyy-MM-dd hh:mm:ss", "yyyy/MM/dd", "yyyy/MM/dd hh:mm:ss",
                "MM-dd-yyyy", "dd-MM-yyyy"};
        try {
            Date date = DateUtils.parseDate(str, patterns);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
