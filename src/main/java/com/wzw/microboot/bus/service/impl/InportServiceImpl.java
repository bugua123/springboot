package com.wzw.microboot.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.microboot.bus.domain.Goods;
import com.wzw.microboot.bus.domain.Inport;
import com.wzw.microboot.bus.mapper.GoodsMapper;
import com.wzw.microboot.bus.mapper.InportMapper;
import com.wzw.microboot.bus.service.InportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements InportService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean save(Inport inport) {
        //根据商品编号查询商品
        Goods goods=goodsMapper.selectById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()+inport.getNumber());
        goodsMapper.updateById(goods);
        //保存进货信息
        return super.save(inport);
    }

    @Override
    public boolean updateById(Inport entity) {
        //根据进货ID查询进货
        Inport inport = this.baseMapper.selectById(entity.getId());
        //根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(entity.getGoodsid());
        //库存的算法  当前库存-进货单修改之前的数量+修改之后的数量
        goods.setNumber(goods.getNumber()-inport.getNumber()+entity.getNumber());
        this.goodsMapper.updateById(goods);
        //更新进货单
        return super.updateById(entity);
    }



    @Override
    public boolean removeById(Serializable id) {
        //根据进货ID查询进货
        Inport inport = this.baseMapper.selectById(id);
        //根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(inport.getGoodsid());
        //库存的算法  当前库存-进货单数量
        goods.setNumber(goods.getNumber()-inport.getNumber());
        this.goodsMapper.updateById(goods);
        return super.removeById(id);
    }

}
