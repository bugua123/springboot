package com.wzw.microboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.microboot.entity.Notice;
import com.wzw.microboot.mapper.NoticeMapper;
import com.wzw.microboot.service.NoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
