package com.everygamer.dao;

import com.everygamer.bean.RSAList;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

public interface RSARepository extends CrudRepository<RSAList, Long>, PagingAndSortingRepository<RSAList, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rsa_list WHERE id IN (SELECT id FROM rsa_list LIMIT ?1);", nativeQuery = true)
    int deleteLines(int lines);
}
