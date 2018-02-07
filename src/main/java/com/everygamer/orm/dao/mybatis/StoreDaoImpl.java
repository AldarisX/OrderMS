package com.everygamer.orm.dao.mybatis;

import com.everygamer.bean.BaseItem;
import com.everygamer.orm.dao.StoreDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("StoreDao")
@Scope("singleton")
public class StoreDaoImpl extends SqlSessionDaoSupport implements StoreDao {
    private StoreDao dao;

    @Resource(name = "sqlSessionFactory")
    public void setSuperSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public List<BaseItem> searchStore(Integer type, String kw, Integer manu, String exData) {
        if ("".equals(kw) || kw == null) {
            kw = null;
        } else {
            kw = "%" + kw.replaceAll(" ", "%") + "%";
        }
        return dao.searchStore(type, kw, manu, exData);
    }

    @Override
    public List<BaseItem> listStore(Integer type, String kw, String manu, String exData, Integer startTime, Integer endTime) {
        if ("".equals(kw) || kw == null) {
            kw = null;
        } else {
            kw = "%" + kw.replaceAll(" ", "%") + "%";
        }
        return dao.listStore(type, kw, manu, exData, startTime, endTime);
    }

    @Override
    public List<BaseItem> getTopStore(Integer type, int count) {
        return dao.getTopStore(type, count);
    }

    @Override
    public void init() {
        dao = getSqlSession().getMapper(StoreDao.class);
    }
}
