package com.tencent.health.service;

import java.util.Map;

/**
 * 报表数据生成接口
 *
 * @Author: Tang Zhilei
 * @Date: Create in 20:02 2019/11/30
 */
public interface ReportService {
    Map<String, Object> getBusinessReportData() throws Exception;
}
