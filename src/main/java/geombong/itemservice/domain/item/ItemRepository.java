package geombong.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    /**
     * 간단한 예제코드로는 문제가 없지만,
     * 실제 멀티 스레드 환경에서는 단순하게 Map, long을 사용하면 안된다.(동시성 문제)
     * ConcurrentHashMap, AtomicLong을 사용
     */
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * 원래 정석은 따로 itemName, price, quantity만을 가지는 객체를
     * 만드는 것이 더 좋다.(ParameterDto 등등)
     *
     * @param itemId
     * @param updateParam
     */
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    /**
     * 테스트 용도로 생성
     */
    public void clearStore() {
        store.clear();
    }
}
