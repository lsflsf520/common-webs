package com.yisi.stiku.msg.service.impl;

import java.util.Date;

import com.yisi.stiku.common.bean.EntityState;
import com.yisi.stiku.db.dao.impl.BaseDaoImpl;
import com.yisi.stiku.db.service.impl.BaseServiceImpl;
import com.yisi.stiku.msg.dao.impl.EmailTmplDaoImpl;
import com.yisi.stiku.msg.entity.EmailTmpl;
import com.yisi.stiku.msg.rpc.service.EmailTmplRpcService;
import com.yisi.stiku.rpc.annotation.RpcService;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RpcService(EmailTmplRpcService.class)
public class EmailTmplServiceImpl extends BaseServiceImpl<Integer, EmailTmpl> implements EmailTmplRpcService{
    @Resource
    private EmailTmplDaoImpl emailTmplDaoImpl;

    @Override
    protected BaseDaoImpl<Integer, EmailTmpl> getBaseDaoImpl() {
        return emailTmplDaoImpl;
    }

	@Override
	public boolean save(EmailTmpl emailTmpl) {
		if(emailTmpl.getId() == null){
			emailTmpl.setDbState(EntityState.NORMAL);
			emailTmpl.setCreateTime(new Date());
			emailTmpl.setLastUptime(emailTmpl.getCreateTime());
			this.insert(emailTmpl);
			return true;
		}
		
		emailTmpl.setLastUptime(new Date());
		return this.update(emailTmpl);
	}

	@Override
	public boolean invalid(int tmplId) {
		EmailTmpl emailTmpl = new EmailTmpl();
		emailTmpl.setId(tmplId);
		emailTmpl.setLastUptime(new Date());
		emailTmpl.setDbState(EntityState.INVALID);
		
		return this.update(emailTmpl);
	}

	@Override
	public Page<EmailTmpl> searchTmplList(EmailTmpl query, int currPage,
			int maxRows) {
		if(StringUtils.isNotBlank(query.getTitle())){
			query.setTitle("%" + query.getTitle() + "%" );
		}
		return this.findByPage(query, currPage, maxRows, "order by last_uptime desc");
	}
}