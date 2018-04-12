package com.everygamer.dao.mybatis;

import com.everygamer.bean.BaseItem;
import com.everygamer.dao.StoreDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("StoreDao")
@Scope("singleton")
public class StoreDaoImpl implements StoreDao {
    @Autowired
    private StoreDao dao;

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
    public List<String> listName(Integer type, String name, String exData) {
        if (name != null) {
            name = "%" + name.replaceAll(" ", "%") + "%";
        }
        return dao.listName(type, name, exData);
    }

    @Override
    public List<String> listModel(Integer type, String model, String exData) {
        if (model != null) {
            model = "%" + model.replaceAll(" ", "%") + "%";
        }
        return dao.listModel(type, model, exData);
    }

    @Override
    public List<BaseItem> getTopStore(Integer type, int count) {
        return dao.getTopStore(type, count);
    }

}
