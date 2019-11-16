package com.norman.job51.service.impl;

import com.norman.job51.dao.JobInfoDao;
import com.norman.job51.pojo.JobInfo;
import com.norman.job51.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class JobInfoServiceImpl implements JobInfoService {
    @Autowired
    private JobInfoDao mJobInfoDao;

    @Override
    @Transactional//事物注解
    public void save(JobInfo jobInfo) {
        //根据URL和更新时间，查询原有的数据
        JobInfo param = new JobInfo();
        param.setUrl(jobInfo.getUrl());
        param.setTime(jobInfo.getTime());
        //执行查询，判断数据库中是否有已存在的数据
        List<JobInfo> list = findJobInfo(param);
        //不存在，执行新增
        if (list.size() == 0) {
            //招聘信息不存在或已变更，需要新增或刷新数据库
            mJobInfoDao.saveAndFlush(jobInfo);
        }


    }

    @Override
    public List<JobInfo> findJobInfo(JobInfo jobInfo) {
        //设置查询条件
        Example example = Example.of(jobInfo);
        List list = mJobInfoDao.findAll(example);
        return list;
    }
}
