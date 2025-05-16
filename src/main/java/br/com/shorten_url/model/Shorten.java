package br.com.shorten_url.model;

import br.com.shorten_url.model.requests.ShortenUrlRequest;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class Shorten {


    private String url;

    private String shortCode;

    private Long ttl;

    @DynamoDbSortKey
    @DynamoDbAttribute("url")
    public String getUrl() {
        return url;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("shortCode")
    public String getShortCode() {
        return shortCode;
    }

    public Shorten(ShortenUrlRequest request){

        this.url = request.url();
    }

    public Shorten(){};

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
