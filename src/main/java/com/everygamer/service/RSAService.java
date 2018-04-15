package com.everygamer.service;

import com.everygamer.bean.RSAList;
import com.everygamer.dao.RSARepository;
import com.everygamer.util.RSAUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Service("RSAService")
public class RSAService {
    @Autowired
    private RSARepository rsaRepository;
    private Integer reqCount = 0;

    public RSAService() {
        Timer rsaAddTime = new Timer();
        rsaAddTime.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (reqCount) {
                    if (reqCount > 10) {
                        if (getCount() >= 1000) {
                            deleteRSA(getCount() / 2);
                        }
                        addRSA();
                        reqCount = 0;
                    }
                }
            }
        }, 1000, 500);
    }

    public void addRSA() {
        synchronized (reqCount) {
            RSAList rsa = new RSAList();
            RSAUtils.generateRSA();
            rsa.setPriKey(RSAUtils.getPriKey());
            rsa.setPubKey(RSAUtils.getPubKey());
            rsaRepository.save(rsa);
            reqCount++;
        }
    }

    public int getCount() {
        return (int) rsaRepository.count();
    }

    public RSAList getRandRSA() {
        synchronized (reqCount) {
            reqCount++;
        }
        int num = RandomUtils.nextInt(getCount()) + 1;
        Page<RSAList> rsa = rsaRepository.findAll(PageRequest.of(num, 1));
        if (rsa.getContent().size() == 0) {
            return getRandRSA();
        }
        return rsa.getContent().get(0);
    }

    public RSAList getRSAById(long id) {
        return rsaRepository.findById(id).get();
    }

    public int deleteRSA(int lines) {
        return rsaRepository.deleteLines(lines);
    }
}
