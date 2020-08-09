package com.fh.shop.api.recipient.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.recipient.mapper.IRecipientMapper;

import com.fh.shop.api.recipient.po.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("recipientService")
public class IRecipientServiceImpl implements IRecipientService {

    @Autowired
    private IRecipientMapper recipientMapper;

    @Override
    public List<Recipient> findList(Long memberId) {
        QueryWrapper<Recipient> recipientQueryWrapper = new QueryWrapper<>();
        recipientQueryWrapper.eq("memberId", memberId);
        List<Recipient> recipientList = recipientMapper.selectList(recipientQueryWrapper);
        return recipientList;
    }
}
