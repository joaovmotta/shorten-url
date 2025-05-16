package br.com.shorten_url.repositories;

import br.com.shorten_url.model.Shorten;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import static software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean;

@Repository
public class ShortenRepository {

    private final DynamoDbEnhancedClient enhancedClient;
    private final DynamoDbTable<Shorten> table;

    @Autowired
    public ShortenRepository(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
        this.table = enhancedClient.table("short_url", fromBean(Shorten.class));
    }

    public void save(Shorten shorten) {

        this.table.putItem(shorten);
    }

    public Shorten get(String shortCode) {

        return this.table.getItem(
                Key.builder()
                        .partitionValue(shortCode)
                        .build());
    }
}
