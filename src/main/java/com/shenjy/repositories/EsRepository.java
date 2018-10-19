package com.shenjy.repositories;

import com.shenjy.entities.Account;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 【 enter the class description 】
 *
 * @author shenjy 2017/12/15
 */
public interface EsRepository extends ElasticsearchRepository<Account, String>{
}
