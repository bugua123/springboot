package com.wzw.microboot.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.microboot.bus.domain.Goods;
import com.wzw.microboot.bus.domain.Inport;
import com.wzw.microboot.bus.domain.Outport;
import com.wzw.microboot.bus.mapper.GoodsMapper;
import com.wzw.microboot.bus.mapper.InportMapper;
import com.wzw.microboot.bus.mapper.OutportMapper;
import com.wzw.microboot.bus.service.OutportService;
import com.wzw.microboot.common.WebUtils;
import com.wzw.microboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements OutportService {
    @Autowired
    private InportMapper inportMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void addOutPort(Integer id, Integer number, String remark) {
        //1,根据进货单ID查询进货单信息
        Inport inport = this.inportMapper.selectById(id);
        //2,根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()-number);
        this.goodsMapper.updateById(goods);
        //3,添加退货单信息
        Outport outport =new Outport();
        outport.setGoodsid(inport.getGoodsid());
        outport.setNumber(number);
        User user=(User) WebUtils.getSession().getAttribute("user");
        outport.setOperateperson(user.getName());
        outport.setOutportprice(inport.getInportprice());
        outport.setOutputtime(new Date());
        outport.setPaytype(inport.getPaytype());
        outport.setProviderid(inport.getProviderid());
        outport.setRemark(remark);
        this.getBaseMapper().insert(outport);
    }
}
