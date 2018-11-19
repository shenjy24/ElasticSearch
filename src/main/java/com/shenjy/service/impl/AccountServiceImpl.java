package com.shenjy.service.impl;

import com.google.common.collect.Lists;
import com.shenjy.entity.Account;
import com.shenjy.repository.EsRepository;
import com.shenjy.service.AccountService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * 【 enter the class description 】
 *
 * @author shenjy 2017/12/15
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private EsRepository esRepository;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Override
    public List<Account> find(String content) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(content);
        Iterable<Account> searchResult = esRepository.search(builder);
        Iterator<Account> iterator = searchResult.iterator();
        List<Account> accounts = Lists.newArrayList();
        while (iterator.hasNext()) {
            accounts.add(iterator.next());
        }
        return accounts;
    }

    @Override
    public List<Account> findAll(String content) {

        Pageable pageable = PageRequest.of(0, 20);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryStringQuery(content))
                .withPageable(pageable).build();

        Page<Account> page = esTemplate.queryForPage(searchQuery, Account.class);
        return page.getContent();
    }

    @Override
    public Double sumAge(String content) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(termQuery("name", content))
                .withSearchType(SearchType.DEFAULT)
                .withIndices("accounts")
                .withTypes("person")
                .addAggregation(AggregationBuilders.sum("ageSum").field("age"))
                .build();
        Aggregations aggregations = esTemplate.query(searchQuery, response -> response.getAggregations());

        Sum ageSum = aggregations.get("ageSum");
        return ageSum.getValue();
    }

    @Override
    public String saveAccount(String id, String name, String motto, Integer age) {
        Account account = new Account(id, name, motto, age);
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(id).withObject(account).build();
        return esTemplate.index(indexQuery);
    }
}
