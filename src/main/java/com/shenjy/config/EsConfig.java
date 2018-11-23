package com.shenjy.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

/**
 * [ElasticSearch配置类]
 *
 * @author shenjy 2017/12/15
 */

@Configuration
@EnableElasticsearchRepositories
public class EsConfig {

    @Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private Integer esPort;

    @Value("${elasticsearch.clusterName}")
    private String esClusterName;

    @Bean
    public Client client() throws Exception {
        Settings settings = Settings.builder()
                .put("cluster.name", esClusterName)
                .put("xpack.security.transport.ssl.enabled",false)
                .put("xpack.security.user", "elastic:123456")
                .build();

        TransportClient client = new PreBuiltXPackTransportClient(settings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName(esHost), esPort));
        return client;
    }

    @Bean
    public ElasticsearchOperations esTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }
}
