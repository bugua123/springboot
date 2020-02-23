package com.wzw.microboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.microboot.entity.Loginfo;
import com.wzw.microboot.mapper.LogInfoMapper;
import com.wzw.microboot.service.LoginfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginfoServiceImpl extends ServiceImpl<LogInfoMapper, Loginfo> implements LoginfoService {
}
