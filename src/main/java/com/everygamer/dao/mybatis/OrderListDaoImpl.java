package com.everygamer.dao.mybatis;

import com.everygamer.bean.OrderItem;
import com.everygamer.dao.OrderListDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("OrderListDao")
public class OrderListDaoImpl extends SqlSessionDaoSupport implements OrderListDao {
    private OrderListDao dao;

    @Resource(name = "sqlSessionFactory")
    public void setSuperSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public int addOrder(OrderItem orderItem) {
        int cRows = dao.addOrder(orderItem);
        if (cRows <= 0) {
            throw new DBUpdateException("操作失败(返回ID<0),引发类: " + this.getClass().getName() + " 方法: addOrder");
        }
        return 1;
    }

    @Override
    public OrderItem getOrderById(int id) {
        return dao.getOrderById(id);
    }

    @Override
    public void init() {
        dao = getSqlSession().getMapper(OrderListDao.class);
    }
}
