package geombong.itemservice.domain.item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void tearDown() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        //given
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 20000, 20);

        itemRepository.save(itemA);
        itemRepository.save(itemB);

        //when
        List<Item> items = itemRepository.findAll();

        //then
        assertThat(items).hasSize(2).contains(itemA, itemB);
    }

    @Test
    void updateItem() {
        //given
        Item itemA = new Item("itemA", 10000, 10);

        Item savedItem = itemRepository.save(itemA);
        Long itemId = savedItem.getId();


        //when
        Item updateParam = new Item("itemB", 200000, 30);
        itemRepository.update(itemId, updateParam);

        Item findItem = itemRepository.findById(itemId);
        //then

        assertSoftly(test -> {
            assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
            assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
            assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
        });
    }
}
